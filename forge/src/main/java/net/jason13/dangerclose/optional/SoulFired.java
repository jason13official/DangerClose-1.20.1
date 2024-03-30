package net.jason13.dangerclose.optional;

import crystalspider.soulfired.api.FireManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public final class SoulFired {
	public static void setOnRegularFire(Entity entity, int seconds) {
		FireManager.setOnFire(entity, seconds, new ResourceLocation(""));
	}
	public static void setOnSoulFire(Entity entity, int seconds) {
		FireManager.setOnFire(entity, seconds, new ResourceLocation("soul"));
	}
	
	public static void setOnTypedFire(Entity entity, int seconds, ResourceLocation fireType) {
		FireManager.setOnFire(entity, seconds, fireType);
	}
}
