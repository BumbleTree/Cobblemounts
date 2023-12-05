package net.ioixd;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class PlayerCrouch implements ServerPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                        PacketByteBuf buf, PacketSender responseSender) {
        Entity vehicle = player.getVehicle();
        if (vehicle != null && vehicle instanceof PokemonEntity) {
            // Check for two air blocks above the player
            BlockPos playerPos = player.getBlockPos();
            boolean isTwoAirBlocksAbove = player.getWorld().isAir(playerPos.up()) && player.getWorld().isAir(playerPos.up(2));

            if (isTwoAirBlocksAbove) {
                // Existing logic here
                PokemonEntity living = (PokemonEntity) vehicle;
                living.initGoals();
            } else {
                // Send a message to the player
                player.sendMessage(Text.literal("BumbleTree won't let you dismount here or you may suffocate!"), false);
            }
        }
    }
}
