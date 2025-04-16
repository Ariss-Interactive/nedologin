package ru.marduk.nedologin.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.platform.Service;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;

public class ServerEvents {
    public static void playerJoin(Player player) {
        Nedologin.logger.info("Player logged in");

        PlayerLoginHandler.instance().playerJoin((ServerPlayer)player);

        Service.NETWORK.SendMessageRequestLogin((ServerPlayer)player);
    }

    public static void playerLeave(Player player) {
        PlayerLoginHandler.instance().playerLeave((ServerPlayer)player);
    }
}
