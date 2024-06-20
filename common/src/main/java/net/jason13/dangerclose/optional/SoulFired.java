package net.jason13.dangerclose.optional;

import it.crystalnest.soul_fire_d.api.FireManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public final class SoulFired {
	public static void setOnRegularFire(Entity entity, int seconds) {
		FireManager.setOnFire(entity, seconds, FireManager.DEFAULT_FIRE_TYPE);
	}
	public static void setOnSoulFire(Entity entity, int seconds) {
		FireManager.setOnFire(entity, seconds, FireManager.SOUL_FIRE_TYPE);
	}
	
	public static void setOnTypedFire(Entity entity, int seconds, ResourceLocation fireType) {
		FireManager.setOnFire(entity, seconds, fireType);
	}
}
