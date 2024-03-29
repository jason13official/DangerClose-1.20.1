package net.jason13.dangerclose.mixin;

import crystalspider.soulfired.api.FireManager;
import net.jason13.dangerclose.CommonConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireManager.class)
public class FabricSoulfiredMixin {
	
	@Inject(method = "damageInFire(Lnet/minecraft/world/entity/Entity;Ljava/lang/String;Ljava/lang/String;)Z", at = @At("HEAD"))
	private static void injected(Entity entity, String fireId, String modId, CallbackInfoReturnable<Boolean> cir) {
		CommonConstants.LOG.info("called soulfired damageInFire(Entity, String, String) method!");
	}
	
	@Inject(method = "damageInFire(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/resources/ResourceLocation;)Z", at = @At("HEAD"))
	private static void injected(Entity entity, ResourceLocation fireType, CallbackInfoReturnable<Boolean> cir) {
		CommonConstants.LOG.info("called soulfired damageInFire(Entity, ResourceLocation) method!");
	}
}
