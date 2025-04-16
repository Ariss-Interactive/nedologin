package ru.marduk.nedologin.fabric;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import ru.marduk.nedologin.NLConfig;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.platform.Service;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;
import ru.marduk.nedologin.server.storage.NLStorage;

public class NedologinFabricServerNetworkHandler {
    public static void handleMessageLogin(MessageLogin payload, ServerPlayNetworking.Context context) {
        String password = payload.password();

        PlayerLoginHandler.instance().login(context.player().getGameProfile().getName(), password);
    }

    public static void handleMessageChangePassword(MessageChangePassword payload, ServerPlayNetworking.Context context) {
        String username = context.player().getGameProfile().getName();
        String from = payload.from();
        String to = payload.to();


        if (!NLConfig.INSTANCE.enableChangePassword) {
            context.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_disabled"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse(context.player(), false);
            return;
        }

        if (NLStorage.instance().storageProvider.checkPassword(username, from)) {
            NLStorage.instance().storageProvider.changePassword(username, to);

            context.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_successful"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse(context.player(), true);
        } else {
            // Should never happen though
            context.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_fail"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse(context.player(), false);
            Nedologin.logger.warn("Player {} tried to change password with a wrong password.", username);
        }
    }
}