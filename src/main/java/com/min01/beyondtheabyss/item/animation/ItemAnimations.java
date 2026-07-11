package com.min01.beyondtheabyss.item.animation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.capabilities.IItemAnimationCapability;
import com.min01.beyondtheabyss.capabilities.ItemAnimationCapabilityImpl;
import com.min01.beyondtheabyss.item.model.HierarchicalItemModel;
import com.min01.beyondtheabyss.misc.SmoothAnimationState;
import com.min01.beyondtheabyss.network.BTANetwork;
import com.min01.beyondtheabyss.network.UpdateItemAnimationPacket;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.TriPredicate;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.network.PacketDistributor;

public class ItemAnimations 
{
	public static final Long2ObjectMap<ItemAnimationState> STATES = new Long2ObjectOpenHashMap<>();
	public static final List<AnimationHolder> HOLDERS = new ArrayList<>();
	
    public static final long UNASSIGNED = -1L;
	public static final Random RANDOM = new Random(BeyondtheAbyss.MODID.hashCode());
	public static final AtomicLong NEXT_INSTANCE_ID = new AtomicLong(RANDOM.nextInt(Integer.MAX_VALUE));
    
    public static int getItemAnimationTick(ItemStack stack)
    {
        return stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).map(cap -> cap.getAnimationTick()).orElse(0);
    }
    
    public static int getItemAnimationState(ItemStack stack)
    {
        return stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).map(cap -> cap.getAnimationState()).orElse(0);
    }
    
    public static boolean isAnimationPlaying(ItemStack stack)
    {
    	return getItemAnimationState(stack) != 0 && getItemAnimationTick(stack) > 0;
    }
    
	public static ItemStack findStackByInstanceId(LivingEntity entity, long instanceId)
	{
	    for(ItemStack stack : collectAnimatableStacks(entity))
	    {
	        if(getOrAssignId(stack) - 1 == instanceId || getOrAssignId(stack) == instanceId)
	        {
	            return stack;
	        }
	    }
	    return ItemStack.EMPTY;
	}
	
	public static Iterable<ItemStack> collectAnimatableStacks(Entity entity)
	{
		if(entity instanceof Player player)
		{
			Inventory inventory = player.getInventory();
			NonNullList<ItemStack> slots = NonNullList.create();
			slots.addAll(inventory.items);
			slots.addAll(inventory.armor);
			slots.addAll(inventory.offhand);
			return slots;
		}
		return entity.getAllSlots();
	}

    public static long getOrAssignId(ItemStack stack)
    {
        return stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).map(cap ->
        {
        	if(cap.getInstanceId() == UNASSIGNED)
        	{
        		cap.setInstanceId(NEXT_INSTANCE_ID.getAndDecrement());
        	}
        	return cap.getInstanceId();
        }).orElse(UNASSIGNED);
    }

    public static void clearId(ItemStack stack)
    {
        stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap -> cap.setInstanceId(UNASSIGNED));
    }
    
    public static void stop(Entity entity, ItemStack stack)
    {
        long id = getOrAssignId(stack);
        stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap ->
        {
            cap.setAnimationState(0);
            cap.setAnimationTick(0);
            send(id, entity, stack, cap);
        });
    }
    
    public static void play(Entity entity, ItemStack stack, int state, int tick)
    {
        long id = getOrAssignId(stack);
        stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap ->
        {
            cap.setAnimationState(state);
            cap.setAnimationTick(tick);
            send(id, entity, stack, cap);
        });
    }
    
    public static void send(long id, Entity entity, ItemStack stack, IItemAnimationCapability cap)
    {
        if(entity.level.isClientSide())
        	return;
        BTANetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new UpdateItemAnimationPacket(entity.getUUID(), id, cap.getAnimationState(), cap.getAnimationTick()));
    }
    
    public static void tickItemAnimations(LivingTickEvent event)
    {
		LivingEntity entity = event.getEntity();
	    for(ItemStack stack : collectAnimatableStacks(entity))
	    {
	        stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap ->
	        {
	            int prevTick = cap.getAnimationTick();
	            int prevState = cap.getAnimationState();
	            cap.tick();
	            if(cap.getAnimationTick() != prevTick || cap.getAnimationState() != prevState)
	            {
	               	send(cap.getInstanceId(), entity, stack, cap);
	            }
	            if(entity.level.isClientSide)
	            {
	        		long id = getOrAssignId(stack);
	        		ItemAnimationState itemState = getItemState(stack, id);
	        		itemState.accept(stack, (t, u) -> u.updateWhen(t.predicate.test(cap.getAnimationState(), stack, entity), entity.tickCount));
	            }
	        });
	    }
    }
    
	@OnlyIn(Dist.CLIENT)
    public static void register(Item item, AnimationDefinition definition, TriPredicate<Integer, ItemStack, LivingEntity> predicate)
    {
    	AnimationHolder holder = new AnimationHolder(item, definition, predicate);
        HOLDERS.add(holder);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void animate(ItemStack stack, HierarchicalItemModel model, float partialTick)
    {
    	stack.getCapability(ItemAnimationCapabilityImpl.ITEM_ANIMATION).ifPresent(cap ->
    	{
    		long id = getOrAssignId(stack);
    		ItemAnimationState itemState = getItemState(stack, id);
    		itemState.accept(stack, (t, u) -> model.animate(stack, u, t.definition, BTAClientUtil.MC.player.tickCount + partialTick));
    	});
    }

	@OnlyIn(Dist.CLIENT)
    public static ItemAnimationState getItemState(ItemStack stack, long id)
    {
        return STATES.computeIfAbsent(id, k -> new ItemAnimationState(HOLDERS));
    }

	@OnlyIn(Dist.CLIENT)
    public static class ItemAnimationState
    {
    	public final Object2ObjectMap<AnimationHolder, SmoothAnimationState> states = new Object2ObjectOpenHashMap<>();
    	
    	public ItemAnimationState(Collection<AnimationHolder> holders)
    	{
    		holders.forEach(t ->
    		{
        		this.states.computeIfAbsent(t, k -> new SmoothAnimationState());
    		});
		}
    	
    	public void accept(ItemStack stack, BiConsumer<AnimationHolder, SmoothAnimationState> consumer)
    	{
    		for(Entry<AnimationHolder, SmoothAnimationState> entries : this.states.entrySet())
    		{
    			AnimationHolder holder = entries.getKey();
    			SmoothAnimationState state = entries.getValue();
    			if(!stack.is(holder.item))
    			{
    				continue;
    			}
    			consumer.accept(holder, state);
    		}
    	}
    }
    
	@OnlyIn(Dist.CLIENT)
	public static record AnimationHolder(Item item, AnimationDefinition definition, TriPredicate<Integer, ItemStack, LivingEntity> predicate)
	{
		
	}
}
