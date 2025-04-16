package ru.marduk.nedologin.neoforge.platform;

import net.minecraft.server.MinecraftServer;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.platform.IPlatformHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class NeoforgePlatformHelper implements IPlatformHelper {
    @Override
    public Path getConfigPath() {
        return Paths.get(".");
    }

    @Override
    public boolean isDedicated() {
        return false;
    }

    @Override
    public String getVersion() {
        ModInfo info = FMLLoader.getLoadingModList().getMods().stream()
                .filter(modInfo -> modInfo.getModId().equals(NLConstants.MODID))
                .findAny().get();

        return info.getVersion().toString();
    }

    @Override
    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
