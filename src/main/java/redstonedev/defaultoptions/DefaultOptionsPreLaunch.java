package redstonedev.defaultoptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class DefaultOptionsPreLaunch implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        DefaultOptions.LOGGER.info("Pre-launch: DefaultOptions");

        Path gameDir = FabricLoader.getInstance().getGameDir();
        File optionsTxt = gameDir.resolve("options.txt").toFile();
        File defaultFile = gameDir.resolve("defaultoptions.txt").toFile();

        if (!optionsTxt.exists()) {
            if (defaultFile.exists()) {
                DefaultOptions.LOGGER.info("Copying defaultoptions.txt to options.txt...");

                try {
                    String data = Files.readString(defaultFile.toPath());

                    Files.writeString(optionsTxt.toPath(), data);
                } catch (IOException e) {
                    throw new RuntimeException("Could not copy defaultoptions.txt to options.txt!", e);
                }
            } else {
                DefaultOptions.LOGGER
                        .warn("options.txt doesn't exist, but neither does defaultoptions.txt! Skipping copy...");
            }
        } else {
            DefaultOptions.LOGGER.info("options.txt exists! Not copying! Nuh-uh!");
        }
    }
}
