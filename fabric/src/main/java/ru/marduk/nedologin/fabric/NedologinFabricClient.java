package ru.marduk.nedologin.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import ru.marduk.nedologin.client.ClientEvents;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageRequestLogin;

public class NedologinFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(MessageRequestLogin.TYPE, NedologinFabricClientNetworkHandler::handleMessageRequestLogin);
        ClientPlayNetworking.registerGlobalReceiver(MessageChangePasswordResponse.TYPE, NedologinFabricClientNetworkHandler::handleMessageChangePasswordResponse);

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> ClientEvents.onClientRegisterCommand(dispatcher));
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> ClientEvents.onGuiInit(screen));
    }
}
