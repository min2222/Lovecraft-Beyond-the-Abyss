package com.min01.beyondtheabyss.entity.deepabyss;

import com.min01.beyondtheabyss.entity.model.ModelAmarumGhost;
import com.min01.beyondtheabyss.entity.multipart.ClientEntityPartBuilder;
import com.min01.beyondtheabyss.entity.multipart.EntityPartBuilder;
import com.min01.beyondtheabyss.misc.BTAMobType;
import com.min01.beyondtheabyss.util.BTAClientUtil;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityAmarumGhost extends AbstractDeepAbyssMob
{
	public EntityAmarumGhost(EntityType<? extends Monster> p_21683_, Level p_21684_)
	{
		super(p_21683_, p_21684_);
		this.xpReward = this.random.nextInt(5);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 15)
    			.add(Attributes.MOVEMENT_SPEED, 0.3F)
        		.add(Attributes.FOLLOW_RANGE, 10);
    }
    
	@Override
	public EntityPartBuilder<EntityAmarumGhost> createBuilder() 
	{
    	EntityPartBuilder<EntityAmarumGhost> partBuilder = new EntityPartBuilder<EntityAmarumGhost>(this);
		return partBuilder;
	}
	
    @OnlyIn(Dist.CLIENT)
    @Override
    public ClientEntityPartBuilder<EntityAmarumGhost> getClientPartBuilder()
    {
    	ModelAmarumGhost model = new ModelAmarumGhost(BTAClientUtil.MC.getEntityModels().bakeLayer(ModelAmarumGhost.LAYER_LOCATION));
    	ClientEntityPartBuilder<EntityAmarumGhost> clientBuilder = new ClientEntityPartBuilder<EntityAmarumGhost>(this, model);
    	return clientBuilder;
    }

	@Override
	public BTAMobType getBTAMobType()
	{
		return BTAMobType.HOSTILE;
	}
}
