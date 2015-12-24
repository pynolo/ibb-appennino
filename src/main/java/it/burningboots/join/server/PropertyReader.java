package it.burningboots.join.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyReader {

	public static final String PROPERTY_VERSION = "version";
	public static final String PROPERTY_CLOSED = "closed";
	public static final String PROPERTY_BED_FROM = "bed_available_from";
	public static final String PROPERTY_BED_UNTIL = "bed_available_until";
	public static final String PROPERTY_BED_PRICE = "bed_price";
	public static final String PROPERTY_TENT_FROM = "tent_available_from";
	public static final String PROPERTY_TENT_UNTIL = "tent_available_until";
	public static final String PROPERTY_TENT_PRICE = "tent_price";
	
	private static Logger LOG = LoggerFactory.getLogger(PropertyReader.class);

	public static String readProperty(String propertyName) {
		URL confUrl = new PropertyReader().getClass().getResource(ServerConstants.PROPERTY_FILE);
		if (confUrl == null) LOG.error("Could not find "+confUrl);
		LOG.debug(ServerConstants.PROPERTY_FILE + " exists (path "+confUrl.getPath()+")");
		Properties props = new Properties();
		String value = "";
		try {
			File f = new File(confUrl.getPath());
			props.load(new BOMInputStream(new FileInputStream(f)));
			value = props.getProperty(propertyName);
		} catch (IOException e) { // catch exception in case properties file does not exist
			LOG.error(e.getMessage(), e);
		}
		return value;
	}
	
}
