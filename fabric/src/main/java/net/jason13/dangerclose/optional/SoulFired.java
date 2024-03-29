package net.jason13.dangerclose.optional;

import crystalspider.soulfired.api.FireManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

// Proxy for soulfired

public final class SoulFired {
	// sets entity on fire with relevant fire for soulfired
	public static void setOnFire(Entity entity, int seconds, ResourceLocation fireType) {
		FireManager.setOnFire(entity, seconds, fireType);
	}
}
