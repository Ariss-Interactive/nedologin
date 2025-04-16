package ru.marduk.nedologin.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.fabric.platform.FabricPlatformHelper;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.server.ServerEvents;

public class NedologinFabricServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerPlayNetworking.registerGlobalReceiver(MessageLogin.TYPE, NedologinFabricServerNetworkHandler::handleMessageLogin);
        ServerPlayNetworking.registerGlobalReceiver(MessageChangePassword.TYPE, NedologinFabricServerNetworkHandler::handleMessageChangePassword);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            FabricPlatformHelper.minecraftServer = server;
            Nedologin.ServerInit(server);
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> Nedologin.ServerStop());
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> ServerEvents.playerLeave(handler.player));
    }
}
