package ru.marduk.nedologin.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.network.MessageChangePassword;
import ru.marduk.nedologin.network.MessageChangePasswordResponse;
import ru.marduk.nedologin.network.MessageLogin;
import ru.marduk.nedologin.network.MessageRequestLogin;

@EventBusSubscriber(modid = NLConstants.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkLoader {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.NETWORK); // All subsequent payloads will register on the network thread

        registrar.playToServer(
                MessageLogin.TYPE,
                MessageLogin.STREAM_CODEC,
                ServerPayloadHandler::handleMessageLogin
        );

        registrar.playToServer(
                MessageChangePassword.TYPE,
                MessageChangePassword.STREAM_CODEC,
                ServerPayloadHandler::handleMessageChangePassword
        );

        registrar.playToClient(
                MessageRequestLogin.TYPE,
                MessageRequestLogin.STREAM_CODEC,
                ClientPayloadHandler::handleMessageRequestLogin
        );

        registrar.playToClient(
                MessageChangePasswordResponse.TYPE,
                MessageChangePasswordResponse.STREAM_CODEC,
                ClientPayloadHandler::handleMessageChangePasswordResponse
        );
    }
}