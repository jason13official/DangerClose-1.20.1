package net.jason13.dangerclose.mixin;

import net.jason13.dangerclose.CommonConstants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(ServerPlayer.class)
public class CommonServerPlayerMixin {
	
	@Inject(method = "tick", at = @At("RETURN"))
	private void injected(CallbackInfo ci) {
		
		System.out.println("ticking on player!");
		
		ServerPlayer player = ((ServerPlayer)(Object)this);
		
		try (Level level = player.level()) {
			if (!level.isClientSide()) {
				// DangerClose.detect((ServerLevel) level, player);
			}
		}
		catch (IOException e) {
			CommonConstants.LOG.info(e.getMessage());
		}
	}
}
