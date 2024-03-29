package net.jason13.dangerclose;

import net.jason13.dangerclose.util.DangerClose;
import net.jason13.monolib.methods.BlockMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod(CommonConstants.MOD_ID)
public class ForgeExampleMod {
    
    public boolean debuggingEnabled = false;
    
    public ForgeExampleMod() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        CommonConstants.LOG.info("Hello Forge world!");
        CommonClass.init();
        
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onStartTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            
            for (ServerLevel level : event.getServer().getAllLevels()) {
                for (Entity entity : level.getAllEntities()) {
                    if (entity instanceof Mob) {
                        DangerClose.detect(level, (LivingEntity) entity);
                    }
                    if (entity instanceof Player) {
                        
                        DangerClose.detect(level, (LivingEntity) entity);
                        
                        boolean leftHandCommand = BlockMethods.compareBlockToItemStack(Blocks.COMMAND_BLOCK, ((Player) entity).getOffhandItem());
                        boolean rightHandCommand = BlockMethods.compareBlockToItemStack(Blocks.COMMAND_BLOCK, ((Player) entity).getMainHandItem());
                        
                        if (!debuggingEnabled && leftHandCommand && rightHandCommand) {
                            debuggingEnabled = true;
                            entity.sendSystemMessage(Component.literal("debuggingEnabled" + CommonConstants.MOD_NAME));
                        }
                    }
                }
            }
            
            // for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            //
            //     try (ServerLevel level = player.serverLevel()) {
            //         DangerClose.detect(level, player);
            //     }
            //     catch (IOException e) {
            //         CommonConstants.LOG.info(e.getMessage());
            //     }
            //
            //     boolean leftHandCommand = BlockMethods.compareBlockToItemStack(Blocks.COMMAND_BLOCK, player.getOffhandItem());
            //     boolean rightHandCommand = BlockMethods.compareBlockToItemStack(Blocks.COMMAND_BLOCK, player.getMainHandItem());
            //
            //     if (!debuggingEnabled && leftHandCommand && rightHandCommand) {
            //         debuggingEnabled = true;
            //         player.sendSystemMessage(Component.literal("debuggingEnabled" + CommonConstants.MOD_NAME));
            //     }
            // }
        }
    }
}