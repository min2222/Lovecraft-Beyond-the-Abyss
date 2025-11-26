package com.min01.beyondtheabyss.mixin;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.entity.deepabyss.EntitySubmarine;
import com.min01.beyondtheabyss.lights.DynamicLights;
import com.min01.beyondtheabyss.lights.LevelRendererAccessor;
import com.min01.beyondtheabyss.shader.BTAEntityEffect;
import com.min01.beyondtheabyss.shader.BTAWorldShader;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = LevelRenderer.class, priority = -15000)
public abstract class MixinLevelRenderer implements LevelRendererAccessor
{
    @Nullable
    @Shadow
    private ClientLevel level;
    
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;initOutline()V", at = @At("TAIL"))
    private void initOutline(CallbackInfo ci)
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.initEffect();
    	});
    }
    
    @Inject(method = "Lnet/minecraft/client/renderer/LevelRenderer;resize(II)V", at = @At("TAIL"))
    private void resize(int x, int y, CallbackInfo ci)
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.resize(x, y);
    	});
    }
    
	@Invoker("setSectionDirty")
	@Override
	public abstract void scheduleChunkRebuild(int x, int y, int z, boolean important);

	@Inject(at = @At(value = "HEAD"), method = "renderLevel")
	private void renderLevelHead(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		BTAClientUtil.MC.getProfiler().incrementCounter("dynamic_lighting");
	    DynamicLights.get().updateAll(LevelRenderer.class.cast(this));
        Player player = BTAClientUtil.MC.player;
        if(player != null)
        {
            if(player.getVehicle() instanceof EntitySubmarine submarine && !BTAClientUtil.MC.gameRenderer.getMainCamera().isDetached())
            {
        		float yRot = Mth.rotLerp(frameTime, submarine.yRotO, submarine.getYRot());
                float xRot = Mth.lerp(frameTime, submarine.xRotO, submarine.getXRot());
                mtx.mulPose(Axis.YP.rotationDegrees(-yRot + 180.0F));
                mtx.mulPose(Axis.XP.rotationDegrees(-xRot));
            }
        }
	}
	
    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V", shift = At.Shift.BEFORE))
    private void renderLevelProcess(PoseStack poseStack, float frameTime, long l, boolean b, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) 
    {
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.process();
    	});
    }
	
	@Inject(at = @At(value = "TAIL"), method = "renderLevel")
	private void renderLevelTail(PoseStack mtx, float frameTime, long nanoTime, boolean renderOutline, Camera camera, GameRenderer gameRenderer, LightTexture light, Matrix4f projMat, CallbackInfo ci)
	{
		new ArrayList<>(BTAWorldShader.WORLD_SHADERS).forEach(t -> 
		{
			t.render(mtx, frameTime, camera);
		});
		
    	new ArrayList<>(BTAEntityEffect.EFFECTS).forEach(t -> 
    	{
    		t.doEntityEffect();
    	});
	}
	
	@Inject(at = @At("TAIL"), method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", cancellable = true)
	private static void getLightColor(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir)
	{
		if(!level.getBlockState(pos).isSolidRender(level, pos))
		{
			cir.setReturnValue(DynamicLights.get().getLightmapWithDynamicLight(pos, cir.getReturnValue()));
		}
	}
}
