package com.min01.beyondtheabyss.mixin;

import java.io.IOException;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.min01.beyondtheabyss.misc.IOptimizedModelPart;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;

//vanilla code optimization;
@Mixin(value = HierarchicalModel.class, priority = -10000)
public class MixinHierarchicalModel
{
    @Inject(method = "getAnyDescendantWithName", at = @At("HEAD"), cancellable = true)
    private void getAnyDescendantWithName(String pName, CallbackInfoReturnable<Optional<ModelPart>> cir) throws IOException 
    {
    	cir.setReturnValue(this.bta_getAnyDescendantWithName(pName));
    }
    
    @Unique
    public Optional<ModelPart> bta_getAnyDescendantWithName(String pName) 
    {
        if("root".equals(pName))
        {
        	return Optional.of(HierarchicalModel.class.cast(this).root());
        }
        return this.bta_findRecursive(HierarchicalModel.class.cast(this).root(), pName);
    }

    @Unique
    private Optional<ModelPart> bta_findRecursive(ModelPart current, String pName) 
    {
        if(current.hasChild(pName))
        {
            return Optional.of(current.getChild(pName));
        }
        for(ModelPart child : IOptimizedModelPart.class.cast(current).bta_getChildren().values()) 
        {
            Optional<ModelPart> found = this.bta_findRecursive(child, pName);
            if(found.isPresent()) 
            {
            	return found;
            }
        }
        return Optional.empty();
    }
}
