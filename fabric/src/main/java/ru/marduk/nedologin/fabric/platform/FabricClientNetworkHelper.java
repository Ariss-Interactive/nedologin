package ru.marduk.nedologin.fabric.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.platform.IClientNetworkHelper;
import ru.marduk.nedologin.utils.SHA256;

public class FabricClientNetworkHelper implements IClientNetworkHelper {
    @Override
    public void SendMessageLogin(String pwd) {
        String pwdHashed = SHA256.getSHA256(SHA256.getSHA256(pwd));

        ClientPlayNetworking.send(new MessageLogin(pwdHashed));
    }

    @Override
    public void SendMessageChangePassword(String original, String to) {
        String origHashed = SHA256.getSHA256(SHA256.getSHA256(original)), toHashed = SHA256.getSHA256(SHA256.getSHA256(to));

        ClientPlayNetworking.send(new MessageChangePassword(origHashed, toHashed));
    }
}
