package com.min01.beyondtheabyss.efkefc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.min01.beyondtheabyss.BeyondtheAbyss;
import com.min01.beyondtheabyss.misc.BTARenderType;
import com.min01.beyondtheabyss.util.BTAClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class EfkEfcRenderer
{
	public final Minecraft minecraft = BTAClientUtil.MC;
	public final Random random = new Random();
	
    public final Map<String, Object> globalProperties;
    public final List<EfkEfcEmitter> emitters = new ArrayList<>();
	
	public EfkEfcRenderer(List<EfkEfcNode> nodes, Map<String, Object> globalProperties)
	{
        this.globalProperties = globalProperties;
        for(EfkEfcNode node : nodes)
        {
            this.emitters.add(new EfkEfcEmitter(node));
        }
	}
	
	public void render(PoseStack stack, MultiBufferSource bufferSource, int tickCount, float partialTicks)
	{
		for(EfkEfcEmitter emitter : this.emitters)
		{
			this.render(stack, bufferSource, emitter, tickCount, partialTicks);
		}
	}
	
	public void render(PoseStack stack, MultiBufferSource bufferSource, EfkEfcEmitter emitter, int tickCount, float partialTicks) 
	{
		EfkEfcNode node = emitter.node;
	    Map<String, Object> props = node.properties;
	    Map<String, Object> commonValues = this.getMap(props.get("CommonValues"));
	    Map<String, Object> maxGeneration = this.getMap(commonValues.get("MaxGeneration"));
	    Map<String, Object> life = this.getMap(commonValues.get("Life"));
	    Map<String, Object> generationTime = this.getMap(commonValues.get("GenerationTime"));
	    Map<String, Object> timeOffset = this.getMap(commonValues.get("GenerationTimeOffset"));
	    Map<String, Object> rotationValues = this.getMap(props.get("RotationValues"));
	    Map<String, Object> scalingValues = this.getMap(props.get("ScalingValues"));
	    Map<String, Object> locationValues = this.getMap(props.get("LocationValues"));
	    Map<String, Object> rendererCommonValues = this.getMap(props.get("RendererCommonValues"));
	    Map<String, Object> drawingValues = this.getMap(props.get("DrawingValues"));
	    
	    stack.pushPose();
	    
	    float currentTick = tickCount + partialTicks;
	    
	    //float startFrame = Float.parseFloat((String) this.globalProperties.getOrDefault("StartFrame", 0));
	    //float endFrame = Float.parseFloat((String) this.globalProperties.getOrDefault("EndFrame", 20));

	    float lifeTime = 100;
	    
	    int maxGen = 1;
	    int offset = 0;
	    int billboardType = 2;
	    int vertexCount = 3;
	    float spawnRate = 1;
	    float centerRatio = 0.5F;
	    
	    String renderType = "";
	    
	    boolean infinity = false;
	    
	    List<String> types = new ArrayList<>();

	    Vector3f[] rotation = new Vector3f[] {new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f()};
	    Vector3f[] scale = new Vector3f[] {new Vector3f(1), new Vector3f(1), new Vector3f(1), new Vector3f(1), new Vector3f(1)}; 
	    Vector3f[] location = new Vector3f[] {new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f(), new Vector3f()};
		Vector2f[] coords = new Vector2f[] {new Vector2f(0.5F, 0.5F), new Vector2f(0.5F, -0.5F), new Vector2f(-0.5F, -0.5F), new Vector2f(-0.5F, 0.5F)};
		Vector4f[] colors = new Vector4f[] {new Vector4f(255), new Vector4f(255), new Vector4f(255), new Vector4f(255)};
		Vector4f[] easingStartColors = new Vector4f[] {new Vector4f(255), new Vector4f(255), new Vector4f(255), new Vector4f(255)};
		Vector4f[] easingEndColors = new Vector4f[] {new Vector4f(255), new Vector4f(255), new Vector4f(255), new Vector4f(255)};

	    Vector2f[] outerRadius = new Vector2f[] {new Vector2f(2, 1), new Vector2f(2, 1), new Vector2f(), new Vector2f(), new Vector2f()};
	    Vector2f[] innerRadius = new Vector2f[] {new Vector2f(2, 1), new Vector2f(2, 1), new Vector2f(), new Vector2f(), new Vector2f()};
	    
		Vector4f[] outerEasingColors = new Vector4f[] {new Vector4f(255), new Vector4f(255)};
		Vector4f[] innerEasingColors = new Vector4f[] {new Vector4f(255), new Vector4f(255)};
		Vector4f[] centerEasingColors = new Vector4f[] {new Vector4f(255), new Vector4f(255)};
		
		Vector2f easingRotSpeed = new Vector2f();
		Vector2f easingScaleSpeed = new Vector2f();
		Vector2f easingPosSpeed = new Vector2f();
		Vector2f easingColorSpeed = new Vector2f();
	    
	    if(maxGeneration != null)
	    {
	    	String value = (String) maxGeneration.get("Value");
	    	String infinite = (String) maxGeneration.get("Infinite");
	    	if(value != null)
	    	{
	    		maxGen = Integer.parseInt(value);
	    	}
	    	if(infinite != null)
	    	{
	    		infinity = Boolean.parseBoolean(infinite);
	    	}
	    }
	    
	    if(life != null)
	    {
	    	lifeTime = Float.parseFloat((String) life.get("Center")) / 2;
	    }
	    
	    if(timeOffset != null)
	    {
	    	offset = Integer.parseInt((String) timeOffset.get("Center"));
	    }
	    
	    if(generationTime != null)
	    {
	    	spawnRate = Float.parseFloat((String) generationTime.get("Center")) / 2;
	    }

	    if(rotationValues != null)
	    {
	    	rotation = this.getValues(rotationValues, rotation, "Rotation", types);
	    	this.setEasingSpeed(easingRotSpeed, rotationValues);
	    }
	    
	    if(scalingValues != null)
	    {
	    	scale = this.getValues(scalingValues, scale, "Scale", types);
	    	this.setEasingSpeed(easingScaleSpeed, scalingValues);
	    }
	    
	    if(locationValues != null)
	    {
	    	location = this.getValues(locationValues, location, "Location", types);
	    	this.setEasingSpeed(easingPosSpeed, locationValues);
	    }
	    
	    if(drawingValues != null)
	    {
    		this.setColors(colors, easingStartColors[0], easingEndColors[0], easingColorSpeed, drawingValues, types);
		    Map<String, Object> sprite = this.getMap(drawingValues.get("Sprite"));
		    Map<String, Object> ring = this.getMap(drawingValues.get("Ring"));
		    if(sprite != null)
		    {
		    	String billboard = (String) sprite.get("Billboard");
		    	if(billboard != null)
		    	{
			    	billboardType = Integer.parseInt(billboard);
		    	}
	    		this.setColors(colors, easingStartColors[0], easingEndColors[0], easingColorSpeed, sprite, types);
	    		this.setCoords(coords, sprite);
	    		renderType = "Sprite";
		    }
		    if(ring != null)
		    {
		    	String vc = (String) ring.get("VertexCount");
		    	String ratio = (String) ring.get("CenterRatio_Fixed");
		    	if(vc != null)
		    	{
		    		vertexCount = Integer.parseInt(vc);
		    	}
		    	if(ratio != null)
		    	{
			    	centerRatio = Float.parseFloat(ratio);
		    	}
		    	this.setRingValues(ring, outerRadius, innerRadius, types);
		    	this.setRingColorValues(ring, outerEasingColors, innerEasingColors, centerEasingColors, types);
		    	renderType = "Ring";
		    }
	    }
	    
	    emitter.efkefcList.removeIf(t -> !t.isAlive(currentTick));
	    
	    if(infinity)
	    {
		    emitter.efkefcGenerated = emitter.efkefcList.size();
	    }
	    
	    if(currentTick >= emitter.lastSpawnTick + spawnRate)
	    {
	    	boolean canEmit = infinity ? true : emitter.efkefcGenerated < maxGen;
		    if(canEmit)
		    {
		    	emitter.efkefcList.add(new EfkEfc(currentTick, lifeTime, location, rotation, scale, types));
	            emitter.efkefcGenerated++;
	            emitter.lastSpawnTick = currentTick;
		    }
	    }
	    
	    for(EfkEfc efkefc : emitter.efkefcList)
	    {
	    	List<String> typeList = efkefc.types;
	    	Vector3f[] rot = efkefc.rotation;
	    	Vector3f[] size = efkefc.scale;
	    	Vector3f[] loc = efkefc.locaton;
	    	
	    	Vector3f fixedRot = rot[0];
	    	Vector3f pvaRot = rot[1];
	    	Vector3f pvaRotVelocity = rot[2];
	    	Vector3f easingRotStart = rot[3];
	    	Vector3f easingRotEnd = rot[4];
	    	
	    	Vector3f fixedSize = size[0];
	    	Vector3f pvaSize = size[1];
	    	Vector3f pvaSizeVelocity = size[2];
	    	Vector3f easingSizeStart = size[3];
	    	Vector3f easingSizeEnd = size[4];
	    	
	    	Vector3f fixedLoc = loc[0];
	    	Vector3f pvaLoc = loc[1];
	    	Vector3f pvaLocVelocity = loc[2];
	    	Vector3f easingLocStart = loc[3];
	    	Vector3f easingLocEnd = loc[4];
	    	
	    	Camera camera = this.minecraft.gameRenderer.getMainCamera();
	    	
	    	if(currentTick > offset)
	    	{
		    	float age = currentTick - efkefc.generatedTick;
		    	float ageT = Mth.clamp(age / efkefc.life, 0.0F, 1.0F);
		    	
		    	stack.pushPose();
		    	
		    	Vector3f finalLoc = new Vector3f();
		    	
		    	if(types.contains("FixedLocation")) 
		    	{
		    		finalLoc.add(fixedLoc);
		    	}
		    	
		    	if(types.contains("PVALocation")) 
		    	{
		    	    Vector3f pos = new Vector3f(pvaLocVelocity).mul(age).add(pvaLoc);
		    	    finalLoc.add(pos);
		    	}
		    	
		    	if(types.contains("EasingLocation")) 
		    	{
		    		float easedT = this.easing(ageT, this.easing(easingPosSpeed.x, easingPosSpeed.y));
		    		Vector3f l = this.get2Point(easedT, easingLocStart, easingLocEnd);
		    		finalLoc.add(l);
		    	}
		    	
	    		stack.translate(finalLoc.x, finalLoc.y, finalLoc.z);
		    	
		    	Vector3f finalRot = new Vector3f();
		    	
		    	if(types.contains("FixedRotation")) 
		    	{
		    		finalRot.add(fixedRot);
		    	}

		    	if(types.contains("PVARotation")) 
		    	{
		    	    Vector3f rt = new Vector3f(pvaRot).add(pvaRotVelocity);
		    	    finalRot.set(rt);
		    	}
		    	
		    	if(types.contains("EasingRotation")) 
		    	{
		    		float easedT = this.easing(ageT, this.easing(easingRotSpeed.x, easingRotSpeed.y));
		    		Vector3f r = this.get2Point(easedT, easingRotStart, easingRotEnd);
		    	    finalRot.add(r);
		    	}

		    	if(billboardType == 0)
		    	{
		    		stack.mulPose(camera.rotation());
		    	}
		    	else if(billboardType == 1)
		    	{
		    	    stack.mulPose(Axis.XP.rotationDegrees(finalRot.x));
		    	    stack.mulPose(Axis.YP.rotationDegrees(finalRot.y));
		    	    stack.mulPose(Axis.ZP.rotationDegrees(finalRot.z));
		    		stack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
		    	}
		    	else if(billboardType == 2)
		    	{
		    	    stack.mulPose(Axis.XP.rotationDegrees(finalRot.x));
		    	    stack.mulPose(Axis.YP.rotationDegrees(finalRot.y));
		    	    stack.mulPose(Axis.ZP.rotationDegrees(finalRot.z));
		    	}
		    	else if(billboardType == 3)
		    	{
		    	    stack.mulPose(camera.rotation());
		    	    stack.mulPose(Axis.ZP.rotationDegrees(finalRot.z));
		    	}
		    	
		    	Vector3f finalSize = new Vector3f(1);
		    	
		    	if(typeList.contains("FixedScale")) 
		    	{
		    		finalSize.mul(fixedSize);
		    	}
		    	
		    	if(typeList.contains("PVAScale")) 
		    	{
		    	    Vector3f sz = new Vector3f(pvaSizeVelocity).mul(age).add(pvaSize);
		    	    finalSize.mul(sz);
		    	}
		    	
		    	if(typeList.contains("EasingScale"))
		    	{
		    		float easedT = this.easing(ageT, this.easing(easingScaleSpeed.x, easingScaleSpeed.y));
		    		Vector3f s = this.get2Point(easedT, easingSizeStart, easingSizeEnd);
		    		finalSize.mul(s);
		    	}
		    	
		    	stack.scale(finalSize.x, finalSize.y, finalSize.z);
		    	
		    	if(rendererCommonValues != null)
		    	{
		    	    String textureName = rendererCommonValues.get("ColorTexture").toString().split("/")[1].toLowerCase();
		    	    ResourceLocation texture = new ResourceLocation(BeyondtheAbyss.MODID, "textures/vfx/" + textureName);
		    	    
		    	    int fadeIn = this.getFadeIn(rendererCommonValues);
		    	    int fadeOut = this.getFadeOut(rendererCommonValues);
		    	    
		    	    String blendType = (String) rendererCommonValues.get("AlphaBlend");
		    	    int alphaBlend = 0;
		    	    
		    	    if(blendType != null)
		    	    {
		    	    	alphaBlend = Integer.parseInt(blendType);
		    	    }
		    		
		    	    RenderType render = alphaBlend == 2 ? BTARenderType.additive(texture) : BTARenderType.blend(texture);
			    	
			    	float alpha = 1.0F;

			    	if(fadeIn > 0.0F && age < fadeIn)
			    	{
			    	    float t = age / fadeIn;
			    	    alpha *= this.fade(0.0F, 1.0F, t, 0, 0);
			    	}

			    	if(fadeOut > 0.0F && age > efkefc.life - fadeOut)
			    	{
			    	    float t = (age - (efkefc.life - fadeOut)) / fadeOut;
			    	    alpha *= this.fade(1.0F, 0.0F, t, 0, 0);
			    	}
			    	
		    	    if(renderType.equals("Sprite"))
		    	    {
				    	Vector4f col = new Vector4f(1);

				    	col.w *= alpha;
				    	
				    	if(typeList.contains("FixedColor")) 
				    	{
				    		float r = colors[0].x;
				    		float g = colors[0].y;
				    		float b = colors[0].z;
				    		float a = colors[0].w;
					    	col.set(r / 255, g / 255, b / 255, a / 255);
				    	}
				    	
				    	if(typeList.contains("EasingColor"))
				    	{
				    		float easedT = this.easing(ageT, this.easing(easingColorSpeed.x, easingColorSpeed.y));
				    		Vector4f c = this.get2Point(easedT, easingStartColors[0], easingEndColors[0]);
					    	col.set(c.x / 255, c.y / 255, c.z / 255, c.w / 255);
				    	}
				    	
				    	Vector4f[] color = new Vector4f[] {col, col, col, col};
				    	
				    	this.drawQuad(stack, bufferSource.getBuffer(render), coords, color, LightTexture.FULL_BRIGHT);
		    	    }
		    	    else if(renderType.equals("Ring"))
		    	    {
			    		float easedT = this.easing(ageT, this.easing(0, 0));
			    		
			    		Vector2f outerRadiusEasingStart = outerRadius[3];
			    		Vector2f outerRadiusEasingEnd = outerRadius[4];

			    		Vector2f innerRadiusEasingStart = innerRadius[3];
			    		Vector2f innerRadiusEasingEnd = innerRadius[4];

				    	Vector4f innerCol = new Vector4f(1);
				    	Vector4f centerCol = new Vector4f(1);
				    	Vector4f outerCol = new Vector4f(1);

				    	innerCol.w *= alpha;
				    	centerCol.w *= alpha;
				    	outerCol.w *= alpha;

			    		Vector4f innerColor = this.get2Point(easedT, innerEasingColors[0], innerEasingColors[1]);
			    		Vector4f centerColor = this.get2Point(easedT, centerEasingColors[0], centerEasingColors[1]);
			    		Vector4f outerColor = this.get2Point(easedT, outerEasingColors[0], outerEasingColors[1]);
			    		
				    	if(types.contains("EasingColorInner"))
				    	{
					    	innerCol.set(innerColor.x / 255, innerColor.y / 255, innerColor.z / 255, innerColor.w / 255);
				    	}
				    	
				    	if(types.contains("EasingColorCenter"))
				    	{
					    	centerCol.set(centerColor.x / 255, centerColor.y / 255, centerColor.z / 255, centerColor.w / 255);
				    	}
				    	
				    	if(types.contains("EasingColorOuter"))
				    	{
					    	outerCol.set(outerColor.x / 255, outerColor.y / 255, outerColor.z / 255, outerColor.w / 255);
				    	}
			    		
			    		Vector2f outer = this.get2Point(easedT, outerRadiusEasingStart, outerRadiusEasingEnd);
			    		Vector2f inner = this.get2Point(easedT, innerRadiusEasingStart, innerRadiusEasingEnd);
			    		
		    	    	BTAClientUtil.drawRing(inner.x, outer.x, inner.y, outer.y, centerRatio, vertexCount, stack, bufferSource, innerCol, centerCol, outerCol, LightTexture.FULL_BRIGHT, render, Vec3.ZERO);
		    	    }
		    	}
		    	
		    	stack.popPose();
	    	}
	    }
	    
	    if(emitter.childrens != null) 
	    {
	        for(EfkEfcEmitter child : emitter.childrens)
	        {
	            this.render(stack, bufferSource, child, tickCount, partialTicks);
	        }
	    }
	    
	    stack.popPose();
	}
	
	public float fade(float start_, float end_, float t, float a1, float a2)
	{
		float[] abcs = this.easing(a1, a2);
	    float a = abcs[0];
	    float b = abcs[1];
	    float c = abcs[2];
		float df = end_ - start_;
		float d = a * t * t * t + b * t * t + c * t;
		return start_ + d * df;
	}
	
	public Vector4f get2Point(float time, Vector4f start, Vector4f end)
	{
        Vector4f size = new Vector4f(end).sub(start);
        return new Vector4f(start).fma(time, size);
	}
	
	public Vector3f get2Point(float time, Vector3f start, Vector3f end)
	{
        Vector3f size = new Vector3f(end).sub(start);
        Vector3f result = new Vector3f(start).fma(time, size);
        if(Float.isNaN(result.x))
        {
        	result.set(start.x, result.y, result.z);
        }
        if(Float.isNaN(result.y))
        {
        	result.set(result.x, start.y, result.z);
        }
        if(Float.isNaN(result.z))
        {
        	result.set(result.x, result.y, start.z);
        }
        return result;
	}
	
	public Vector2f get2Point(float time, Vector2f start, Vector2f end)
	{
        Vector2f size = new Vector2f(end).sub(start);
        Vector2f result = new Vector2f(start).fma(time, size);
        if(Float.isNaN(result.x))
        {
        	result.set(start.x, result.y);
        }
        if(Float.isNaN(result.y))
        {
        	result.set(result.x, start.y);
        }
        return result;
	}
	
	public float[] easing(float a1, float a2)
	{
		float g1 = (float)Math.tan(((float)a1 + 45.0) / 180.0 * Math.PI);
		float g2 = (float)Math.tan(((float)a2 + 45.0) / 180.0 * Math.PI);
		float c = g1;
		float a = g2 - g1 - (1.0F - c) * 2.0F;
		float b = (g2 - g1 - (a * 3.0F)) / 2.0F;
		return new float[] { a, b, c };
	}
	
	public float easing(float t, float[] abcs) 
	{
	    float a = abcs[0];
	    float b = abcs[1];
	    float c = abcs[2];
	    return a * t * t * t + b * t * t + c * t;
	}
	
	public int getFadeOut(Map<String, Object> map)
	{
		Map<String, Object> fadeOut = this.getMap(map.get("FadeOut"));
		if(fadeOut != null)
		{
			return Integer.parseInt((String) fadeOut.get("Frame"));
		}
		return 1;
	}
	
	public int getFadeIn(Map<String, Object> map)
	{
		Map<String, Object> fadeIn = this.getMap(map.get("FadeIn"));
		if(fadeIn != null)
		{
			return Integer.parseInt((String) fadeIn.get("Frame"));
		}
		return 1;
	}
    
    public void setColors(Vector4f[] colors, Vector4f startColor, Vector4f endColor, Vector2f easingSpeed, Map<String, Object> map, List<String> types)
    {
    	Map<String, Object> colorAll = this.getMap(map.get("ColorAll"));
    	Map<String, Object> colorAllFixed = this.getMap(map.get("ColorAll_Fixed"));
    	//Map<String, Object> colorAllPva = this.getMap(map.get("ColorAll_PVA"));
    	Map<String, Object> colorAllEasing = this.getMap(map.get("ColorAll_Easing"));
		for(Vector4f color : colors)
		{
	    	if(colorAllFixed != null)
	    	{
	    		this.setFixedColor(color, colorAllFixed);
	    		types.add("FixedColor");
	    	}
		}
    	if(colorAllEasing != null)
    	{
    		this.setEasingSpeed(easingSpeed, colorAllEasing);
    		this.setEasingColor(startColor, endColor, colorAllEasing);
    		types.add("EasingColor");
    	}
    	if(colorAll != null)
    	{
        	Map<String, Object> fixed = this.getMap(colorAll.get("Fixed"));
        	Map<String, Object> easing = this.getMap(colorAll.get("Easing"));
    		for(Vector4f color : colors)
    		{
    	    	if(fixed != null)
    	    	{
    	    		this.setFixedColor(color, fixed);
    	    		types.add("FixedColor");
    	    	}
    		}
        	if(easing != null)
        	{
        		this.setEasingSpeed(easingSpeed, easing);
        		this.setEasingColor(startColor, endColor, easing);
        		types.add("EasingColor");
        	}
    	}
    }
    
    public void setCoords(Vector2f[] coords, Map<String, Object> map)
    {
    	Map<String, Object> fixedLL = this.getMap(map.get("Position_Fixed_LL"));
    	Map<String, Object> fixedLR = this.getMap(map.get("Position_Fixed_LR"));
    	Map<String, Object> fixedUL = this.getMap(map.get("Position_Fixed_UL"));
    	Map<String, Object> fixedUR = this.getMap(map.get("Position_Fixed_UR"));
    	Vector2f ll = coords[2];
    	Vector2f lr = coords[1];
    	Vector2f ul = coords[3];
    	Vector2f ur = coords[0];
    	if(fixedLL != null)
    	{
    		this.setFixed(ll, fixedLL);
    	}
    	if(fixedLR != null)
    	{
    		this.setFixed(lr, fixedLR);
    	}
    	if(fixedUL != null)
    	{
    		this.setFixed(ul, fixedUL);
    	}
    	if(fixedUR != null)
    	{
    		this.setFixed(ur, fixedUR);
    	}
    }
    
    public void setEasingSpeed(Vector2f speeds, Map<String, Object> map)
    {
    	Map<String, Object> easing = this.getMap(map.get("Easing"));
    	if(easing != null)
    	{
        	String startSpeed = (String) easing.get("StartSpeed");
        	String endSpeed = (String) easing.get("EndSpeed");
        	if(startSpeed != null)
        	{
        		speeds.x = Float.parseFloat(startSpeed);
        	}
        	if(endSpeed != null)
        	{
        		speeds.y = Float.parseFloat(endSpeed);
        	}
    	}
    }
    
    public void setRingColorValues(Map<String, Object> map, Vector4f[] outer, Vector4f[] inner, Vector4f[] center, List<String> types)
    {
    	Map<String, Object> outerEasing = this.getMap(map.get("OuterColor_Easing"));
    	Map<String, Object> centerEasing = this.getMap(map.get("CenterColor_Easing"));
    	Map<String, Object> innerEasing = this.getMap(map.get("InnerColor_Easing"));
    	if(outerEasing != null)
    	{
    		this.setEasingColor(outer[0], outer[1], outerEasing);
    		types.add("EasingColorOuter");
    	}
    	if(innerEasing != null)
    	{
    		this.setEasingColor(inner[0], inner[1], innerEasing);
    		types.add("EasingColorInner");
    	}
    	if(centerEasing != null)
    	{
    		this.setEasingColor(center[0], center[1], centerEasing);
    		types.add("EasingColorCenter");
    	}
    }
    
    public void setRingValues(Map<String, Object> map, Vector2f[] outer, Vector2f[] inner, List<String> types)
    {
    	Map<String, Object> outerEasing = this.getMap(map.get("Outer_Easing"));
    	Map<String, Object> innerEasing = this.getMap(map.get("Inner_Easing"));
    	if(outerEasing != null)
    	{
    		this.setEasing(outer[3], outer[4], outerEasing);
    		types.add("EasingOuter");
    	}
    	if(innerEasing != null)
    	{
    		this.setEasing(inner[3], inner[4], innerEasing);
    		types.add("EasingInner");
    	}
    }
    
    public Vector3f[] getValues(Map<String, Object> map, Vector3f[] values, String key, List<String> types)
    {
    	Map<String, Object> fixed = this.getMap(map.get("Fixed"));
    	Map<String, Object> pva = this.getMap(map.get("PVA"));
    	Map<String, Object> easing = this.getMap(map.get("Easing"));
    	if(fixed != null)
    	{
	    	Map<String, Object> value = this.getMap(fixed.get(key));
	    	if(value != null)
	    	{
	    		this.setFixed(values[0], value);
	    	}
    		types.add("Fixed" + key);
    	}
    	if(pva != null)
    	{
	    	Map<String, Object> value = this.getMap(pva.get(key));
	    	Map<String, Object> velocity = this.getMap(pva.get("Velocity"));
	    	if(value != null)
	    	{
	    		this.setPVA(values[1], value);
	    	}
	    	if(velocity != null)
	    	{
	    		this.setPVA(values[2], velocity);
	    	}
    		types.add("PVA" + key);
    	}
    	if(easing != null)
    	{
    		this.setEasing(values[3], values[4], easing);
    		types.add("Easing" + key);
    	}
    	return values;
    }
    
    public void setEasingColor(Vector4f startValue, Vector4f endValue, Map<String, Object> map)
    {
    	Map<String, Object> start = this.getMap(map.get("Start"));
    	Map<String, Object> end = this.getMap(map.get("End"));
    	if(start != null)
    	{
    		this.setColorVector(startValue, start);
    	}
    	if(end != null)
    	{
    		this.setColorVector(endValue, end);
    	}
    }
    
    public void setEasing(Vector2f startValue, Vector2f endValue, Map<String, Object> map)
    {
    	Map<String, Object> start = this.getMap(map.get("Start"));
    	Map<String, Object> end = this.getMap(map.get("End"));
    	if(start != null)
    	{
    		this.setPVA(startValue, start);
    	}
    	if(end != null)
    	{
    		this.setPVA(endValue, end);
    	}
    }
    
    public void setPVA(Vector2f value, Map<String, Object> map)
    {
	    Map<String, Object> x = this.getMap(map.get("X"));
	    Map<String, Object> y = this.getMap(map.get("Y"));
    	if(x != null)
    	{
    		String center = (String) x.get("Center");
    		float max = (String) x.get("Max") != null ? Float.parseFloat((String) x.get("Max")) : 0.0F;
    		float min = (String) x.get("Min") != null ? Float.parseFloat((String) x.get("Min")) : 0.0F;
    		if(max != min)
    		{
    			value.x = min + (float)(Math.random() * (max - min));
    		}
    		else if(center != null)
    		{
    			value.x = Float.parseFloat(center);
    		}
    	}
    	if(y != null)
    	{
    		String center = (String) y.get("Center");
    		float max = (String) y.get("Max") != null ? Float.parseFloat((String) y.get("Max")) : 0.0F;
    		float min = (String) y.get("Min") != null ? Float.parseFloat((String) y.get("Min")) : 0.0F;
    		if(max != min)
    		{
    			value.y = min + (float)(Math.random() * (max - min));
    		}
    		else if(center != null)
    		{
    			value.y = Float.parseFloat(center);
    		}
    	}
    }
    
    public void setEasing(Vector3f startValue, Vector3f endValue, Map<String, Object> map)
    {
    	Map<String, Object> start = this.getMap(map.get("Start"));
    	Map<String, Object> end = this.getMap(map.get("End"));
    	if(start != null)
    	{
    		this.setPVA(startValue, start);
    	}
    	if(end != null)
    	{
    		this.setPVA(endValue, end);
    	}
    }
    
    public void setFixedColor(Vector4f value, Map<String, Object> map)
    {
    	String r = (String) map.get("R");
    	String g = (String) map.get("G");
    	String b = (String) map.get("B");
    	String a = (String) map.get("A");
    	if(r != null)
    	{
    		value.x = Float.parseFloat(r);
    	}
    	if(g != null)
    	{
    		value.y = Float.parseFloat(g);
    	}
    	if(b != null)
    	{
    		value.z = Float.parseFloat(b);
    	}
    	if(a != null)
    	{
    		value.w = Float.parseFloat(a);
    	}
    }
    
    public void setFixed(Vector2f value, Map<String, Object> map)
    {
    	String x = (String) map.get("X");
    	String y = (String) map.get("Y");
    	if(x != null)
    	{
    		value.x = Float.parseFloat(x);
    	}
    	if(y != null)
    	{
    		value.y = Float.parseFloat(y);
    	}
    }
    
    public void setFixed(Vector3f value, Map<String, Object> map)
    {
    	String x = (String) map.get("X");
    	String y = (String) map.get("Y");
    	String z = (String) map.get("Z");
    	if(x != null)
    	{
    		value.x = Float.parseFloat(x);
    	}
    	if(y != null)
    	{
    		value.y = Float.parseFloat(y);
    	}
    	if(z != null)
    	{
    		value.z = Float.parseFloat(z);
    	}
    }
    
    public void setColorVector(Vector4f value, Map<String, Object> map)
    {
	    Map<String, Object> r = this.getMap(map.get("R"));
	    Map<String, Object> g = this.getMap(map.get("G"));
	    Map<String, Object> b = this.getMap(map.get("B"));
	    Map<String, Object> a = this.getMap(map.get("A"));
    	if(r != null)
    	{
    		String center = (String) r.get("Center");
    		String maxS = (String) r.get("Max");
    		String minS = (String) r.get("Min");
    		if(maxS == null || minS == null)
    		{
    			if(center != null)
        		{
        			value.x = Float.parseFloat(center);
        		}
    		}
    		else
    		{
        		float max = Float.parseFloat(maxS);
        		float min = Float.parseFloat(minS);
    			value.x = min + (float)(Math.random() * (max - min));
    		}
    	}
    	if(g != null)
    	{
    		String center = (String) g.get("Center");
    		String maxS = (String) g.get("Max");
    		String minS = (String) g.get("Min");
    		if(maxS == null || minS == null)
    		{
    			if(center != null)
        		{
        			value.y = Float.parseFloat(center);
        		}
    		}
    		else
    		{
        		float max = Float.parseFloat(maxS);
        		float min = Float.parseFloat(minS);
    			value.y = min + (float)(Math.random() * (max - min));
    		}
    	}
    	if(b != null)
    	{
    		String center = (String) b.get("Center");
    		String maxS = (String) b.get("Max");
    		String minS = (String) b.get("Min");
    		if(maxS == null || minS == null)
    		{
    			if(center != null)
        		{
        			value.z = Float.parseFloat(center);
        		}
    		}
    		else
    		{
        		float max = Float.parseFloat(maxS);
        		float min = Float.parseFloat(minS);
    			value.z = min + (float)(Math.random() * (max - min));
    		}
    	}
    	if(a != null)
    	{
    		String center = (String) a.get("Center");
    		String maxS = (String) a.get("Max");
    		String minS = (String) a.get("Min");
    		if(maxS == null || minS == null)
    		{
    			if(center != null)
        		{
        			value.w = Float.parseFloat(center);
        		}
    		}
    		else
    		{
        		float max = Float.parseFloat(maxS);
        		float min = Float.parseFloat(minS);
    			value.w = min + (float)(Math.random() * (max - min));
    		}
    	}
    }
    
    public void setPVA(Vector3f value, Map<String, Object> map)
    {
	    Map<String, Object> x = this.getMap(map.get("X"));
	    Map<String, Object> y = this.getMap(map.get("Y"));
	    Map<String, Object> z = this.getMap(map.get("Z"));
    	if(x != null)
    	{
    		String center = (String) x.get("Center");
    		float max = (String) x.get("Max") != null ? Float.parseFloat((String) x.get("Max")) : 0.0F;
    		float min = (String) x.get("Min") != null ? Float.parseFloat((String) x.get("Min")) : 0.0F;
    		if(max != min)
    		{
    			value.x = min + (float)(Math.random() * (max - min));
    		}
    		else if(center != null)
    		{
    			value.x = Float.parseFloat(center);
    		}
    	}
    	if(y != null)
    	{
    		String center = (String) y.get("Center");
    		float max = (String) y.get("Max") != null ? Float.parseFloat((String) y.get("Max")) : 0.0F;
    		float min = (String) y.get("Min") != null ? Float.parseFloat((String) y.get("Min")) : 0.0F;
    		if(max != min)
    		{
    			value.y = min + (float)(Math.random() * (max - min));
    		}
    		else if(center != null)
    		{
    			value.y = Float.parseFloat(center);
    		}
    	}
    	if(z != null)
    	{
    		String center = (String) z.get("Center");
    		float max = (String) z.get("Max") != null ? Float.parseFloat((String) z.get("Max")) : 0.0F;
    		float min = (String) z.get("Min") != null ? Float.parseFloat((String) z.get("Min")) : 0.0F;
    		if(max != min)
    		{
    			value.z = min + (float)(Math.random() * (max - min));
    		}
    		else if(center != null)
    		{
    			value.z = Float.parseFloat(center);
    		}
    	}
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(Object obj)
	{
		return obj instanceof Map ? (Map<String, Object>) obj : null;
	}
	
    public void drawQuad(PoseStack stack, VertexConsumer consumer, Vector2f[] vectorCoords, Vector4f[] colors, int packedLightIn) 
    {
        PoseStack.Pose matrixstack$entry = stack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();

        this.drawVertex(matrix4f, matrix3f, consumer, vectorCoords[0].x, vectorCoords[0].y, 0, 0, 0, colors[0], packedLightIn);
        this.drawVertex(matrix4f, matrix3f, consumer, vectorCoords[1].x, vectorCoords[1].y, 0, 0, 1, colors[0], packedLightIn);
        this.drawVertex(matrix4f, matrix3f, consumer, vectorCoords[2].x, vectorCoords[2].y, 0, 1, 1, colors[0], packedLightIn);
        this.drawVertex(matrix4f, matrix3f, consumer, vectorCoords[3].x, vectorCoords[3].y, 0, 1, 0, colors[0], packedLightIn);
    }
    
    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, Vector4f color, int packedLightIn)
    {
    	vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(color.x, color.y, color.z, color.w).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
	
	public static class EfkEfc 
	{
	    public float generatedTick;
	    public float life;
	    public Vector3f[] locaton = new Vector3f[5];
	    public Vector3f[] rotation = new Vector3f[5];
	    public Vector3f[] scale = new Vector3f[5];
	    public List<String> types = new ArrayList<>();
	    
	    public EfkEfc(float generatedTick, float life, Vector3f[] locaton, Vector3f[] rotation, Vector3f[] scale, List<String> types) 
	    {
	        this.generatedTick = generatedTick;
	        this.life = life;
	        this.locaton = locaton;
	        this.rotation = rotation;
	        this.scale = scale;
	        this.types = types;
	    }
	    
	    public boolean isAlive(float currentTick)
	    {
	        return currentTick < this.generatedTick + this.life;
	    }
	}
	
	public static class EfkEfcEmitter
	{
        public final List<EfkEfcEmitter> childrens = new ArrayList<>();
    	public final List<EfkEfc> efkefcList = new ArrayList<>();
    	public float efkefcGenerated;
    	public float lastSpawnTick;
    	public EfkEfcNode node;
    	
    	public EfkEfcEmitter(EfkEfcNode node)
    	{
    		this.node = node;
            if(node.children != null)
            {
                for(EfkEfcNode childNode : node.children) 
                {
                    this.childrens.add(new EfkEfcEmitter(childNode));
                }
            }
    	}
	}
    
    public static class EfkEfcNode
    {
        public String name;
        public Map<String, Object> properties;
        public List<EfkEfcNode> children;
    }
}