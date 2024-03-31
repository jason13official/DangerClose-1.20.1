package net.jason13.dangerclose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

	public static final String MOD_ID = "dangerclose";
	public static final String MOD_NAME = "DangerClose";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	
	public static final Map<String, Boolean> DEFAULT_CONFIGURATION = Map.ofEntries(
		Map.entry("TORCHES_BURN", false),
		Map.entry("SOUL_TORCHES_BURN", false),
		Map.entry("CAMPFIRES_BURN", true),
		Map.entry("SOUL_CAMPFIRES_BURN", true),
		Map.entry("STONECUTTERS_CUT", true),
		Map.entry("ENABLE_BLAZE_DAMAGE", true),
		Map.entry("ENABLE_MAGMA_CUBE_DAMAGE", true),
		Map.entry("ENABLE_MAGMA_BLOCK_DAMAGE", true),
		Map.entry("ENABLE_DANGER_CLOSE", true)
	);
}