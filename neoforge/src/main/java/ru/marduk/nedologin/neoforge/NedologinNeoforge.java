package ru.marduk.nedologin.neoforge;

import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.apache.logging.log4j.core.jmx.Server;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.client.ClientEvents;
import ru.marduk.nedologin.client.PasswordHolder;
import ru.marduk.nedologin.command.CommandLoader;
import ru.marduk.nedologin.platform.Service;
import ru.marduk.nedologin.server.ServerEvents;

@Mod(NLConstants.MODID)
public class NedologinNeoforge {
    public NedologinNeoforge() {
        ModLoadingContext.get().getActiveContainer().getEventBus().addListener(NedologinCommonEvents::commonSetup);
    }

    @EventBusSubscriber(modid = NLConstants.MODID, value = Dist.DEDICATED_SERVER)
    public static class NedologinServerEvents {
        @SubscribeEvent
        public static void serverStopped(ServerStoppedEvent e) {
            Nedologin.ServerStop();
        }

        @SubscribeEvent
        public static void serverStarted(ServerStartingEvent e) {
            Nedologin.ServerInit(e.getServer());
        }

        @SubscribeEvent
        public static void playerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
            ServerEvents.playerLeave(event.getEntity());
        }

        @SubscribeEvent
        public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            // ServerEvents.playerJoin(event.getEntity());
        }
    }

    @EventBusSubscriber(modid = NLConstants.MODID)
    public static class NedologinCommonEvents {
        public static void commonSetup(@SuppressWarnings("unused") FMLCommonSetupEvent event) {
            CommandLoader.argumentRegister();
        }

        @SubscribeEvent
        public static void commandRegister(RegisterCommandsEvent event) {
            CommandLoader.commandRegister(event.getDispatcher());
        }
    }

    @EventBusSubscriber(modid = NLConstants.MODID, value = Dist.CLIENT)
    public static class NedologinClientEvents {
        @SubscribeEvent
        public static void joinServer(ClientPlayerNetworkEvent.LoggingIn event) {
            Nedologin.logger.info("Sending login packet to the server...");
            Service.CLIENT_NETWORK.SendMessageLogin(PasswordHolder.instance().password());
        }

        @SubscribeEvent
        public static void onClientRegisterCommand(RegisterClientCommandsEvent event) {
            ClientEvents.onClientRegisterCommand(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onGuiInit(ScreenEvent.Init.Post event) {
            ClientEvents.onGuiInit(event.getScreen());
        }
    }
}
