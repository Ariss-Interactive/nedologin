package ru.marduk.nedologin.neoforge;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.client.PasswordHolder;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageRequestLogin;
import ru.marduk.nedologin.platform.Service;

public class ClientPayloadHandler {
    public static void handleMessageRequestLogin(final MessageRequestLogin payload, final IPayloadContext ctx) {
        Nedologin.logger.debug("Sending login packet to the server...");

        Service.CLIENT_NETWORK.SendMessageLogin(PasswordHolder.instance().password());
    }

    public static void handleMessageChangePasswordResponse(final MessageChangePasswordResponse payload, final IPayloadContext ctx) {
        if (payload.status()) {
            PasswordHolder.instance().applyPending();
        } else {
            PasswordHolder.instance().dropPending();
        }
    }
}