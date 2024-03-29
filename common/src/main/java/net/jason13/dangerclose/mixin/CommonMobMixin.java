package net.jason13.dangerclose.mixin;

import net.jason13.dangerclose.CommonConstants;
import net.jason13.dangerclose.util.DangerClose;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Mob.class)
public class CommonMobMixin {
	
	@Inject(method = "tick", at = @At("RETURN"))
	private void injected(CallbackInfo ci) {
		
		System.out.println("ticking on mob!");
		
		Mob mob = ((Mob)(Object)this);
		
		try (Level level = mob.level()) {
			if (!level.isClientSide()) {
				DangerClose.detect((ServerLevel) level, mob);
			}
		}
		catch (IOException e) {
			CommonConstants.LOG.info(e.getMessage());
		}
	}
}
