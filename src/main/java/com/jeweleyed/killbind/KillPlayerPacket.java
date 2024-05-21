package com.jeweleyed.killbind;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Killbind.MODID, value = Dist.DEDICATED_SERVER)
public class KillPlayerPacket {
    public KillPlayerPacket() {}

    public static void encode(KillPlayerPacket msg, FriendlyByteBuf buffer) {}
    public static KillPlayerPacket decode(FriendlyByteBuf buffer) {
        return new KillPlayerPacket();
    }
    public static void handle(KillPlayerPacket packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {
                    player.hurt(player.damageSources().generic(), player.getMaxHealth());
                    if (player.getHealth() > 0) player.setHealth(0);

                }
            });
            ctx.get().setPacketHandled(true);
        }
    }

}