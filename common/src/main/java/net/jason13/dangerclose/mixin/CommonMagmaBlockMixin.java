package net.jason13.dangerclose.mixin;

import net.jason13.dangerclose.platform.CommonServices;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaBlock.class)
public class CommonMagmaBlockMixin {
	
	/**
	 * @author Jason13
	 * @reason Not cancellable, needs cancelling.
	 */
	@Overwrite
	public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {}
}
