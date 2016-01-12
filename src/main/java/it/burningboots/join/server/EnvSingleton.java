package it.burningboots.join.server;

import it.burningboots.join.shared.AppConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class EnvSingleton {

	//private static final Logger LOG = LoggerFactory.getLogger(PropertyReader.class);
	private static EnvSingleton instance = null;
	
	private String mysqlUsername = System.getenv(ServerConstants.DEFAULT_MYSQL_DB_USERNAME);
	private String mysqlPassword = System.getenv(ServerConstants.DEFAULT_MYSQL_DB_PASSWORD);
	private String mysqlHost = System.getenv(ServerConstants.DEFAULT_MYSQL_DB_HOST);
	private String mysqlPort = System.getenv(ServerConstants.DEFAULT_MYSQL_DB_PORT);
	private String mysqlDbName = System.getenv(ServerConstants.DEFAULT_APP_NAME);
    
	//private static Properties appProps = null;
	private static Properties testingProps = null;
	
	private EnvSingleton() throws IOException {
		//URL appPropertyUrl = this.getClass().getResource(AppConstants.APP_PROPERTY_FILE);
		//File appPropertyFile = new File(appPropertyUrl.getPath());
		//appProps = new Properties();
		//appProps.load(new FileInputStream(appPropertyFile));
		URL testingPropertyUrl = this.getClass().getResource(AppConstants.TESTING_PROPERTY_FILE);
		File testingPropertyFile = null;
		if (testingPropertyUrl != null) {
			testingPropertyFile = new File(testingPropertyUrl.getPath());
			if (testingPropertyFile.exists()) {
				testingProps = new Properties();
				testingProps.load(new FileInputStream(testingPropertyFile));
			}
		}
		
		if (mysqlUsername == null)
			mysqlUsername=readProperty(ServerConstants.DEFAULT_MYSQL_DB_USERNAME);
		if (mysqlUsername.equals(ServerConstants.DEFAULT_MYSQL_DB_USERNAME))
			mysqlUsername=readProperty(ServerConstants.DEFAULT_MYSQL_DB_USERNAME);
		if (mysqlPassword == null)
			mysqlPassword=readProperty(ServerConstants.DEFAULT_MYSQL_DB_PASSWORD);
		if (mysqlPassword.equals(ServerConstants.DEFAULT_MYSQL_DB_PASSWORD))
			mysqlPassword=readProperty(ServerConstants.DEFAULT_MYSQL_DB_PASSWORD);
		if (mysqlHost == null)
			mysqlHost=readProperty(ServerConstants.DEFAULT_MYSQL_DB_HOST);
		if (mysqlHost.equals(ServerConstants.DEFAULT_MYSQL_DB_HOST))
			mysqlHost=readProperty(ServerConstants.DEFAULT_MYSQL_DB_HOST);
		if (mysqlPort == null)
			mysqlPort=readProperty(ServerConstants.DEFAULT_MYSQL_DB_PORT);
		if (mysqlPort.equals(ServerConstants.DEFAULT_MYSQL_DB_PORT))
			mysqlPort=readProperty(ServerConstants.DEFAULT_MYSQL_DB_PORT);
		if (mysqlDbName == null)
			mysqlDbName=readProperty(ServerConstants.DEFAULT_APP_NAME);
		if (mysqlDbName.equals(ServerConstants.DEFAULT_APP_NAME))
			mysqlDbName=readProperty(ServerConstants.DEFAULT_APP_NAME);
	}
	
	public static EnvSingleton get() throws IOException {
		if (instance == null) instance = new EnvSingleton();
		return instance;
	}
	
	public String readProperty(String propertyName) throws IOException {
		String value = null; //TODO appProps.getProperty(propertyName);
		if (value == null && testingProps != null) value = testingProps.getProperty(propertyName);
		return value;
	}

	public String getMysqlUsername() {
		return mysqlUsername;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}

	public String getMysqlHost() {
		return mysqlHost;
	}

	public String getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqlDbName() {
		return mysqlDbName;
	}
	
}
