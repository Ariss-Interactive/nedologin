package ru.marduk.nedologin.fabric.platform;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageRequestLogin;
import ru.marduk.nedologin.platform.INetworkHelper;

public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public void SendMessageRequestLogin(ServerPlayer player) {
        ServerPlayNetworking.send(player, new MessageRequestLogin());
    }

    @Override
    public void SendMessageChangePasswordResponse(ServerPlayer player, boolean status) {
        ServerPlayNetworking.send(player, new MessageChangePasswordResponse(true));
    }
}
