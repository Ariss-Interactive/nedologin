package ru.marduk.nedologin.neoforge;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import ru.marduk.nedologin.NLConfig;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.platform.Service;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;
import ru.marduk.nedologin.server.storage.NLStorage;

public class ServerPayloadHandler {
    public static void handleMessageLogin(final MessageLogin payload, final IPayloadContext ctx) {
        String password = payload.password();

        PlayerLoginHandler.instance().login(ctx.player().getGameProfile().getName(), password);
    }

    public static void handleMessageChangePassword(final MessageChangePassword payload, final IPayloadContext ctx) {
        String username = ctx.player().getGameProfile().getName();
        String from = payload.from();
        String to = payload.to();


        if (!NLConfig.INSTANCE.enableChangePassword) {
            ctx.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_disabled"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse((ServerPlayer) ctx.player(), false);
            return;
        }

        if (NLStorage.instance().storageProvider.checkPassword(username, from)) {
            NLStorage.instance().storageProvider.changePassword(username, to);

            ctx.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_successful"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse((ServerPlayer) ctx.player(), true);
        } else {
            // Should never happen though
            ctx.player().displayClientMessage(
                    Component.translatable("nedologin.info.password_change_fail"),
                    false
            );

            Service.NETWORK.SendMessageChangePasswordResponse((ServerPlayer) ctx.player(), false);
            Nedologin.logger.warn("Player {} tried to change password with a wrong password.", username);
        }
    }
}