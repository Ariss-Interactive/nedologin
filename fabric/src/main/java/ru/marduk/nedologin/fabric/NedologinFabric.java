package ru.marduk.nedologin.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.command.CommandLoader;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.network.MessageRequestLogin;

public class NedologinFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Nedologin.CommonInit();

        // Init packets
        PayloadTypeRegistry.playS2C().register(MessageRequestLogin.TYPE, MessageRequestLogin.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(MessageChangePasswordResponse.TYPE, MessageChangePasswordResponse.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(MessageChangePassword.TYPE, MessageChangePassword.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(MessageLogin.TYPE, MessageLogin.STREAM_CODEC);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> CommandLoader.commandRegister(dispatcher));
    }
}