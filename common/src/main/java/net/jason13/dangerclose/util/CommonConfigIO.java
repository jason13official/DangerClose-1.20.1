package net.jason13.dangerclose.util;

import com.mojang.logging.LogUtils;
import net.jason13.dangerclose.CommonConstants;
import org.slf4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommonConfigIO {
	
	public static final String FILE_PATH = "config/dangerclose.properties";
	
	private static final Logger LOG = LogUtils.getLogger();
	
	public static void initializeConfiguration() {
		try {
			
			File directory = new File("config");
			if (!directory.isDirectory()) {
				directory.mkdir();
			}
			
			File file = new File(FILE_PATH);
			if (!file.isFile()) {
				file.createNewFile();
			}
			
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				for (Map.Entry<String, Boolean> pair : CommonConstants.DEFAULT_CONFIGURATION.entrySet()) {
					writer.write(pair.getKey() + "=" + pair.getValue() + "\n");
				}
			}
		}
		catch (IOException e) {
			LOG.info(e.getMessage());
		}
	}
	
	public static Map<String, Boolean> readConfigurationFromFile() {
		Map<String, Boolean> map = new HashMap<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			
			String line;
			int index = 1;
			
			while ((line = reader.readLine()) != null) {
				
				if (!line.isEmpty() && !line.isBlank()) {
					if (line.contains("=")) {
						String[] pair = line.split("\\=", 2);
						map.put(pair[0], Boolean.parseBoolean(pair[1]));
					} else {
						throw new IOException(CommonConstants.MOD_ID + ".properties contains an error on Line " + index + "! Line does not contain '='?");
					}
				}
				else {
					throw new IOException(CommonConstants.MOD_ID + ".properties contains an error on Line " + index + "! Line is empty or blank?");
				}
				
				index++;
			}
		}
		catch (IOException e) {
			LOG.info(CommonConstants.MOD_ID + ".properties was unable to be read as plaintext or contains errors!");
			LOG.info(e.getMessage());
		}
		
		return map;
	}
}
