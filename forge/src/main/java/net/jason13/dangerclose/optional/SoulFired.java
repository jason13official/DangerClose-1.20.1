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
}
