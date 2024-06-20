package net.jason13.dangerclose.util;

import it.crystalnest.soul_fire_d.api.type.FireTyped;
import net.jason13.dangerclose.CommonClass;
import net.jason13.dangerclose.CommonConstants;
import net.jason13.dangerclose.optional.SoulFired;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
	
	private static void setOnFire(Entity entity, Boolean trueForSoul) {
		
		if (CommonConstants.SOUL_FIRE_D_INSTALLED) {
			if (trueForSoul) {
				SoulFired.setOnSoulFire(entity, 2);
			}
			else {
				SoulFired.setOnRegularFire(entity, 2);
			}
			return;
		}
		
		immolate((LivingEntity) entity);
	}
	
	private static void spreadFire(Entity entity, Mob mob) {
		if (mob.isOnFire() && !entity.isOnFire()) {
			
			if (CommonConstants.SOUL_FIRE_D_INSTALLED) {
				SoulFired.setOnTypedFire(entity, 2, ((FireTyped) entity).getFireType());
				return;
			}
			
			setOnFire(entity, false);
		}
		else if (entity.isOnFire() && !mob.isOnFire()) {
			
			if (CommonConstants.SOUL_FIRE_D_INSTALLED) {
				SoulFired.setOnTypedFire(entity, 2, ((FireTyped) entity).getFireType());
				return;
			}
			
			setOnFire(mob, false);
		}
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
		
		boolean anyTaggedTorch = CommonClass.TORCHES_BURN &&
			(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.TORCH_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.TORCH_BURN_DANGER)));
		boolean anyTaggedSoulTorch = CommonClass.SOUL_TORCHES_BURN &&
			(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.SOUL_TORCH_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.SOUL_TORCH_BURN_DANGER)));
		// boolean anyTaggedCampfire = CommonClass.CAMPFIRES_BURN &&
		// 	(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.CAMPFIRE_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.CAMPFIRE_BURN_DANGER)));
		// boolean anyTaggedSoulCampfire = CommonClass.SOUL_CAMPFIRES_BURN &&
		// 	(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.SOUL_CAMPFIRE_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.SOUL_CAMPFIRE_BURN_DANGER)));
		boolean anyTaggedMagma = CommonClass.ENABLE_MAGMA_BLOCK_DAMAGE &&
			(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.MAGMA_BURN_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.MAGMA_BURN_DANGER)));
		boolean anyTaggedStoneCutter = CommonClass.STONECUTTERS_CUT &&
			(insideTags.stream().anyMatch(Predicate.isEqual(CommonClass.STONECUTTER_DANGER)) || belowTags.stream().anyMatch(Predicate.isEqual(CommonClass.STONECUTTER_DANGER)));
		
		if (anyTaggedTorch) {
			setOnFire(entity, false);
		}
		
		if (anyTaggedSoulTorch) {
			setOnFire(entity, true);
		}
		
		if (anyTaggedMagma) {
			setOnFire(entity, false);
		}
		
		if (stateInside.hasProperty(CampfireBlock.LIT) && stateInside.getValue(CampfireBlock.LIT) && stateInside.is(Blocks.CAMPFIRE) && CommonClass.CAMPFIRES_BURN) {
			setOnFire(entity, false);
		}
		if (stateInside.hasProperty(CampfireBlock.LIT) && stateInside.getValue(CampfireBlock.LIT) && stateInside.is(Blocks.SOUL_CAMPFIRE) && CommonClass.SOUL_CAMPFIRES_BURN) {
			setOnFire(entity, true);
		}
		
		// if (anyTaggedCampfire && (stateInside.hasProperty(CampfireBlock.LIT))) {
		// 	if ((stateInside.getValue(CampfireBlock.LIT))) {
		// 		setOnFire(entity, false);
		// 	}
		// }
		//
		// if (anyTaggedCampfire && stateBelow.hasProperty(CampfireBlock.LIT)) {
		// 	if (stateBelow.getValue(CampfireBlock.LIT)) {
		// 		setOnFire(entity, false);
		// 	}
		// }
		//
		// if (anyTaggedSoulCampfire && stateInside.hasProperty(CampfireBlock.LIT)) {
		// 	if ((stateInside.getValue(CampfireBlock.LIT))) {
		// 		setOnFire(entity, true);
		// 	}
		// }
		//
		// if (anyTaggedSoulCampfire && stateBelow.hasProperty(CampfireBlock.LIT)) {
		// 	if (stateBelow.getValue(CampfireBlock.LIT)) {
		// 		setOnFire(entity, true);
		// 	}
		// }
		
		if (anyTaggedStoneCutter) {
			entity.hurt(level.damageSources().cactus(), 4.0F);
		}
		
		List<Mob> nearby = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, entity, entity.getBoundingBox());
		for (Mob mob : nearby) {
			
			spreadFire(entity, mob);
			
			if (CommonClass.ENABLE_BLAZE_DAMAGE && mob instanceof Blaze) {
				setOnFire(entity, false);
			}
			if (CommonClass.ENABLE_MAGMA_CUBE_DAMAGE && mob instanceof MagmaCube) {
				setOnFire(entity, false);
			}
		}
	}
}
