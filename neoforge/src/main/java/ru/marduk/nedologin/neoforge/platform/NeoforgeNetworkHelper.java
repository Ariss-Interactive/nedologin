package ru.marduk.nedologin.neoforge.platform;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.network.MessageRequestLogin;
import ru.marduk.nedologin.platform.IClientNetworkHelper;
import ru.marduk.nedologin.platform.INetworkHelper;
import ru.marduk.nedologin.utils.SHA256;

public class NeoforgeNetworkHelper implements INetworkHelper, IClientNetworkHelper {
    @Override
    public void SendMessageRequestLogin(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, new MessageRequestLogin());
    }

    @Override
    public void SendMessageLogin(String pwd) {
        String pwdHashed = SHA256.getSHA256(SHA256.getSHA256(pwd));

        PacketDistributor.sendToServer(new MessageLogin(pwdHashed));
    }

    @Override
    public void SendMessageChangePassword(String original, String to) {
        String origHashed = SHA256.getSHA256(SHA256.getSHA256(original)), toHashed = SHA256.getSHA256(SHA256.getSHA256(to));

        PacketDistributor.sendToServer(new MessageChangePassword(origHashed, toHashed));
    }

    @Override
    public void SendMessageChangePasswordResponse(ServerPlayer player, boolean status) {
        PacketDistributor.sendToPlayer(player, new MessageChangePasswordResponse(status));
    }
}
