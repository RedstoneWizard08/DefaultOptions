package redstonedev.defaultoptions;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultOptions implements ModInitializer {
	public static final String MOD_ID = "defaultoptions";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("She default on my options until I txt");
	}
}