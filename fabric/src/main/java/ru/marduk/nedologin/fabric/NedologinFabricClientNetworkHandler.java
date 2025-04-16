package ru.marduk.nedologin.fabric;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.client.PasswordHolder;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageRequestLogin;
import ru.marduk.nedologin.platform.Service;

public class NedologinFabricClientNetworkHandler {
    public static void handleMessageRequestLogin(MessageRequestLogin payload, ClientPlayNetworking.Context context) {
        Nedologin.logger.debug("Sending login packet to the server...");

        Service.CLIENT_NETWORK.SendMessageLogin(PasswordHolder.instance().password());
    }

    public static void handleMessageChangePasswordResponse(MessageChangePasswordResponse payload, ClientPlayNetworking.Context context) {
        if (payload.status()) {
            PasswordHolder.instance().applyPending();
        } else {
            PasswordHolder.instance().dropPending();
        }
    }
}