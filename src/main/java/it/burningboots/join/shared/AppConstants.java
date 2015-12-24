package it.burningboots.join.shared;

import java.util.HashMap;
import java.util.Map;



public class AppConstants {

	public static final String BASE_URL= "http://ibb.tarine.net";
	public static final String IPN_URL= BASE_URL+"/ipn";
	public static final String THANKYOU_URL= BASE_URL+"/thankyou.jsp";
	public static final String EVENT_URL= "http://www.italianburningboots.org";
	public static final String EVENT_EMAIL= "registration@italianburningboots.org";
	public static final String PAYPAL_URL = "https://www.paypal.com/cgi-bin/webscr";
	//public static final String PAYPAL_URL= "https://www.sandbox.paypal.com/cgi-bin/webscr";//SANDBOX
	public static final String PAYPAL_ACCOUNT = "ibb.registration@tarine.net";
	//public static final String PAYPAL_ACCOUNT = "paolo-facilitator@tarine.net";//SANDBOX
	public static final int ITEM_NUMBER_LENGHT = 6;
	
	public static final String CONFIG_MAX_TICKET_COUNT = "maxTicketCount";
	public static final String CONFIG_MAX_FOREIGNER_TICKET_COUNT = "maxForeignTicketCount";
	public static final String CONFIG_PRICE_TICKET = "priceFull";
	public static final String CONFIG_PRICE_FOREIGNER_TICKET = "priceForeign";
	public static final String CONFIG_PRICE_REDUCED_TICKET = "priceReduced";
	public static final String CONFIG_SERVICE_OPEN = "serviceOpen";
	
	// CONFIG FILES
	public static final String HIBERNATE_CONFIG_FILE="/hibernate.cfg.xml";
	public static final String APP_PROPERTY_FILE = "/app.properties";
	public static final String TESTING_PROPERTY_FILE = "/testing.properties";
	
	// PARAMS
	public static final String PARAMS_ID = "id";
	
	// VALUES
	//TIPI SPEDIZIONE
	public static final Integer ACCOMMODATION_BED = 1;
	public static final Integer ACCOMMODATION_TENT = 2;
	public static final Map<Integer, String> ACCOMMODATION_DESC = new HashMap<Integer, String>();
	static {
		ACCOMMODATION_DESC.put(ACCOMMODATION_BED, "Hut / Rifugio");
		ACCOMMODATION_DESC.put(ACCOMMODATION_TENT, "Tent / Tenda");};
	public static final Integer ACCOMMODATION_DEFAULT = ACCOMMODATION_BED;
	
	// FORMATS
	public static final String PATTERN_TIMESTAMP = "dd/MM/yyyy HH:mm:ss z";//"dd/MM/yyyy HH:mm";
	public static final String PATTERN_DAY = "dd/MM/yyyy";
	public static final String PATTERN_MONTH = "MM/yyyy";
	public static final String PATTERN_CURRENCY = "#0.00";
	public static final long HOUR = 3600000L;
	public static final long DAY = HOUR*24;
	public static final long MONTH = DAY*30; //millisecondi in 30 giorni 1000 * 60 * 60 * 24 * 30;
	public static final long YEAR = DAY*365; 

	// LOOKUP: RESALE TYPE
	public static final String RESALE_TYPE_SELLING = "SELL";
	public static final String RESALE_TYPE_BUYING = "BUY";

}
