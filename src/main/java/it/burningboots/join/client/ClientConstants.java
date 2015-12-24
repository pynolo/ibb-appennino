package it.burningboots.join.client;

import it.burningboots.join.shared.AppConstants;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.datepicker.client.DateBox;

public class ClientConstants {
	
	//CLIENT DAFAULTS
	public static final String DEFAULT_FRAME_TITLE = "Join IBB";
	public static final long LOGIN_EXPIRATION_TIME = (1000*60*60) * 96; //96 hours = 4 days;
	public static final int COOKIE_EXPIRATION_DAYS = 15;
	
	//FORMATS
	public static final DateTimeFormat FORMAT_TIMESTAMP = DateTimeFormat.getFormat(AppConstants.PATTERN_TIMESTAMP);
	public static final DateTimeFormat FORMAT_DAY = DateTimeFormat.getFormat(AppConstants.PATTERN_DAY);
	public static final DateBox.Format BOX_FORMAT_DAY = new DateBox.DefaultFormat(FORMAT_DAY);
	public static final DateTimeFormat FORMAT_MONTH = DateTimeFormat.getFormat(AppConstants.PATTERN_DAY);
	public static final DateBox.Format BOX_FORMAT_MONTH = new DateBox.DefaultFormat(FORMAT_DAY);
	public static final DateTimeFormat FORMAT_YEAR = DateTimeFormat.getFormat("yyyy");
	public static final NumberFormat FORMAT_CURRENCY = NumberFormat.getFormat(AppConstants.PATTERN_CURRENCY);
	public static final NumberFormat FORMAT_INTEGER = NumberFormat.getFormat("#0");
	
	//Icons
	public static final String ICON_LOADING_BIG = "<img src='img/chat_loading.gif' style='vertical-align:middle;border:none;' title='In corso...' />";
	public static final String ICON_LOADING_SMALL = "<img src='img/ajax-loader-small.gif' style='vertical-align:middle;border:none;' title='In corso...' />";
	public static final Integer ICON_LOADING_WIDTH = 121;
	public static final Integer ICON_LOADING_HEIGHT = 23;
	public static final String MSG_ICON_INFO = "<img src='img/dialog-information.png' style='vertical-align:middle' />";
	public static final String MSG_ICON_WARN = "<img src='img/dialog-warning.png' style='vertical-align:middle' />";
	public static final String MSG_ICON_ERROR = "<img src='img/dialog-error.png' style='vertical-align:middle' />";
	
	//COOKIE
	public static final String COOKIE_VERSION = "appVersion";
	
}
