package net.jason13.dangerclose.util;

import net.jason13.dangerclose.CommonClass;
import net.jason13.dangerclose.platform.CommonServices;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DangerClose {
	
	public static void immolate(LivingEntity entity) {
		
		entity.setSecondsOnFire(2);
	}
	
	public static void detect(ServerLevel level, LivingEntity entity) {
		
		if (!CommonClass.ENABLE_DANGER_CLOSE) {
			return;
		}
		
		BlockPos inside = entity.blockPosition();
		BlockPos below = inside.below();
		
		BlockState stateInside = level.getBlockState(inside);
		BlockState stateBelow = level.getBlockState(below);
		
		Stream<TagKey<Block>> insideTagsHolder = level.getBlockState(inside).getTags();
		Stream<TagKey<Block>> belowTagsHolder = level.getBlockState(below).getTags();
		
		List<TagKey<Block>> insideTags = new ArrayList<>();
		List<TagKey<Block>> belowTags = new ArrayList<>();
		
		insideTagsHolder.forEach(insideTags::add);
		belowTagsHolder.forEach(belowTags::add);
		
		boolean anyTaggedTorch = CommonClass.TORCHES_BURN && insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.TORCH_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.TORCH_BURN_DANGER));
		boolean anyTaggedCampfire = CommonClass.CAMPFIRES_BURN && insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.CAMPFIRE_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.CAMPFIRE_BURN_DANGER));
		boolean anyTaggedMagma = CommonClass.ENABLE_MAGMA_BLOCK_DAMAGE && insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.MAGMA_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.MAGMA_BURN_DANGER));
		boolean anyTaggedStoneCutter = CommonClass.STONECUTTERS_CUT && insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.STONECUTTER_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.STONECUTTER_DANGER));
		
		if (anyTaggedTorch || anyTaggedMagma) {
			immolate(entity);
		}
		
		if (anyTaggedCampfire && (stateInside.hasProperty(CampfireBlock.LIT) && !CommonServices.PLATFORM.isModLoaded("soulfired"))) {
			if ((stateInside.getValue(CampfireBlock.LIT))) {
				immolate(entity);
			}
		}
		
		if (anyTaggedCampfire && stateBelow.hasProperty(CampfireBlock.LIT) && !CommonServices.PLATFORM.isModLoaded("soulfired")) {
			if (stateBelow.getValue(CampfireBlock.LIT)) {
				immolate(entity);
			}
		}
		
		if (anyTaggedStoneCutter) {
			entity.hurt(level.damageSources().cactus(), 4.0F);
		}
		
		List<Mob> nearby = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox());
		for (Mob mob : nearby) {
			if (mob.isOnFire()) {
				immolate(entity);
			} else if (entity.isOnFire()) {
				immolate(mob);
			}
			if (CommonClass.ENABLE_BLAZE_DAMAGE && mob instanceof Blaze) {
				immolate(entity);
			}
			if (CommonClass.ENABLE_MAGMA_CUBE_DAMAGE && mob instanceof MagmaCube) {
				immolate(entity);
			}
		}
	}
}
