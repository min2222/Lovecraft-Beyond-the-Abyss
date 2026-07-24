package com.min01.beyondtheabyss.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.tuple.Pair;

import com.min01.beyondtheabyss.capabilities.IPlayerAnimationCapability;
import com.min01.beyondtheabyss.capabilities.PlayerAnimationCapabilityImpl;
import com.min01.beyondtheabyss.item.BTAItems;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdatePlayerAnimationPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.network.PacketDistributor;

public class PlayerAnimations 
{
	public static final Map<String, Pair<ModelPart, ModelPart>> MODEL_MAP = new ConcurrentHashMap<>();
	public static final Long2ObjectMap<PlayerAnimationState> STATES = new Long2ObjectOpenHashMap<>();
	public static final List<AnimationHolder> HOLDERS = new ArrayList<>();
    
    public static int getPlayerAnimationTick(Entity player)
    {
    	return player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).map(cap -> cap.getAnimationTick()).orElse(0);
    }
    
    public static int getPlayerAnimationState(Entity player)
    {
    	return player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).map(cap -> cap.getAnimationState()).orElse(0);
    }
    
    public static boolean isAnimationPlaying(Entity player)
    {
    	return getPlayerAnimationState(player) != 0 && getPlayerAnimationTick(player) > 0;
    }
    
    public static void stop(Entity player)
    {
    	player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).ifPresent(cap ->
        {
            cap.setAnimationState(0);
            cap.setAnimationTick(0);
            send(player, cap);
        });
    }
    
    public static void play(Entity player, int state, int tick)
    {
    	player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).ifPresent(cap ->
        {
            cap.setAnimationState(state);
            cap.setAnimationTick(tick);
            send(player, cap);
        });
    }
    
    public static void send(Entity player, IPlayerAnimationCapability cap)
    {
        if(player.level.isClientSide())
        	return;
        BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new UpdatePlayerAnimationPacket(player.getUUID(), cap.getAnimationState(), cap.getAnimationTick()));
    }
    
    public static void tickPlayerAnimation(PlayerTickEvent event)
    {
		Player player = event.player;
		player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).ifPresent(cap ->
        {
        	if(event.phase == Phase.START)
        		return;
            int prevTick = cap.getAnimationTick();
            int prevState = cap.getAnimationState();
            cap.tick(player);
            if(cap.getAnimationTick() != prevTick || cap.getAnimationState() != prevState)
            {
               	send(player, cap);
            }
            if(player.level.isClientSide)
            {
        		PlayerAnimationState playerState = getPlayerState(player);
        		playerState.accept((t, u) -> u.updateWhen(t.predicate.test(cap.getAnimationState(), player), player.tickCount));
            }
        });
    }
    
	@OnlyIn(Dist.CLIENT)
    public static void register(boolean isFirstPerson, AnimationDefinition definition, BiPredicate<Integer, Player> predicate)
    {
    	AnimationHolder holder = new AnimationHolder(isFirstPerson, definition, predicate);
    	HOLDERS.add(holder);
    }
	
    @OnlyIn(Dist.CLIENT)
    public static void animatePlayerFirstPerson(PlayerModel<?> model, Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    	setupMap(model);
    	player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).ifPresent(cap ->
    	{
    		PlayerAnimationState playerState = getPlayerState(player);
    		playerState.accept((t, u) -> animate(model, u, t.definition, ageInTicks));
    	});
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void animatePlayer(PlayerModel<?> model, Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    	player.getCapability(PlayerAnimationCapabilityImpl.PLAYER_ANIMATION).ifPresent(cap ->
    	{
    		PlayerAnimationState playerState = getPlayerState(player);
    		playerState.accept((t, u) -> 
    		{
    			if(!t.isFirstPerson)
    			{
    				animate(model, u, t.definition, ageInTicks);
    			}
    		});
    	});
    	
    	if(player.isHolding(BTAItems.SKELETAL_GUNBLADE.get()) && (getPlayerAnimationState(player) == 3 || getPlayerAnimationState(player) == 4))
    	{
    		ModelPart head = model.head;
    		Pair<ModelPart, ModelPart> left = MODEL_MAP.get("LeftArm");
    		Pair<ModelPart, ModelPart> right = MODEL_MAP.get("RightArm");
    		BTAClientUtil.copyRotFrom(left.getLeft(), head, true);
    		BTAClientUtil.copyRotFrom(left.getRight(), head, true);
    		BTAClientUtil.copyRotFrom(right.getLeft(), head, false);
    		BTAClientUtil.copyRotFrom(right.getRight(), head, false);
    	}
    }

	@OnlyIn(Dist.CLIENT)
	public static void animate(PlayerModel<?> model, SmoothAnimationState state, AnimationDefinition definition, float ageInTicks)
	{
		state.updateTime(ageInTicks, 1.0F);
		KeyframePlayerAnimations.animate(model, definition, state.getAccumulatedTime(), state.factor(), SmoothAnimationState.ANIMATION_VECTOR_CACHE);
	}
    
	@OnlyIn(Dist.CLIENT)
	public static Optional<Pair<ModelPart, ModelPart>> getAnyDescendantWithName(String name) 
	{
		return Optional.ofNullable(MODEL_MAP.get(name));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void setupMap(PlayerModel<?> model)
	{
		MODEL_MAP.putIfAbsent("Head", Pair.of(model.head, model.hat));
		MODEL_MAP.putIfAbsent("Body", Pair.of(model.body, model.jacket));
		MODEL_MAP.putIfAbsent("LeftArm", Pair.of(model.leftArm, model.leftSleeve));
		MODEL_MAP.putIfAbsent("RightArm", Pair.of(model.rightArm, model.rightSleeve));
		MODEL_MAP.putIfAbsent("LeftLeg", Pair.of(model.leftLeg, model.leftPants));
		MODEL_MAP.putIfAbsent("RightLeg", Pair.of(model.rightLeg, model.rightPants));
		
		MODEL_MAP.values().forEach(t ->
    	{
    		t.getLeft().resetPose();
    		t.getRight().resetPose();
    	});
	}
	
	@OnlyIn(Dist.CLIENT)
    public static PlayerAnimationState getPlayerState(Entity entity)
    {
        return STATES.computeIfAbsent(entity.getId(), k -> new PlayerAnimationState(HOLDERS));
    }

	@OnlyIn(Dist.CLIENT)
    public static class PlayerAnimationState
    {
    	public final Object2ObjectMap<AnimationHolder, SmoothAnimationState> states = new Object2ObjectOpenHashMap<>();
    	
    	public PlayerAnimationState(Collection<AnimationHolder> holders)
    	{
    		holders.forEach(t ->
    		{
        		this.states.computeIfAbsent(t, k -> new SmoothAnimationState());
    		});
		}
    	
    	public void accept(BiConsumer<AnimationHolder, SmoothAnimationState> consumer)
    	{
    		for(Entry<AnimationHolder, SmoothAnimationState> entries : this.states.entrySet())
    		{
    			AnimationHolder holder = entries.getKey();
    			SmoothAnimationState state = entries.getValue();
    			consumer.accept(holder, state);
    		}
    	}
    }

	@OnlyIn(Dist.CLIENT)
	public static record AnimationHolder(boolean isFirstPerson, AnimationDefinition definition, BiPredicate<Integer, Player> predicate)
	{
		
	}
}
