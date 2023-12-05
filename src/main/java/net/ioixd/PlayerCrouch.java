package net.ioixd;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class PlayerCrouch implements ServerPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        Entity vehicle = player.getVehicle();
        if (vehicle != null && vehicle instanceof PokemonEntity) {
            // Check for two air blocks above the player
            BlockPos playerPos = player.getBlockPos();
            boolean isTwoAirBlocksAbove = player.getWorld().getBlockState(playerPos.up()).isOf(Blocks.AIR)
                                          && player.getWorld().getBlockState(playerPos.up(2)).isOf(Blocks.AIR);

            if (isTwoAirBlocksAbove) {
                PokemonEntity living = (PokemonEntity) vehicle;
                living.initGoals();
            }
        }
    }
}
