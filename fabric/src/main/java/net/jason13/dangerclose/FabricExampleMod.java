package net.jason13.dangerclose;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.jason13.dangerclose.util.DangerClose;
import net.jason13.monolib.methods.BlockMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

public class FabricExampleMod implements ModInitializer {
    
    public boolean debuggingEnabled = false;

    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        CommonConstants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        ServerTickEvents.START_SERVER_TICK.register(new ServerTickHandler());
        
    }
    
    public class ServerTickHandler implements ServerTickEvents.StartTick {
        @Override
        public void onStartTick(MinecraftServer server) {
            
            // Iterator<ServerLevel> iterator = server.getAllLevels().iterator();
            
            // while (iterator.hasNext()) {
                
                // try (ServerLevel level = iterator.next()) {
                
            
            for (ServerLevel level : server.getAllLevels()) {
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
                // }
                // catch (IOException e) {
                //     CommonConstants.LOG.info(e.getMessage());
                // }
            // }
            
            
            
            // for (ServerPlayer player : server.getPlayerList().getPlayers()) {
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
