package it.burningboots.appennino.client;

import it.burningboots.appennino.client.service.DataService;
import it.burningboots.appennino.client.service.DataServiceAsync;
import it.burningboots.appennino.shared.PropertyBean;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Appennino implements EntryPoint {

	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		loadProperties();
	}

	private void loadProperties() {
		AsyncCallback<PropertyBean> callback = new AsyncCallback<PropertyBean>() {
			@Override
			public void onFailure(Throwable caught) {
				//Shouldn't load the full UI
				UiSingleton.get().addWarning("The system cannot verify current IBB version!");
				UiSingleton.get().addError(caught);
			}
			@Override
			public void onSuccess(PropertyBean bean) {
				WizardSingleton.get().setPropertyBean(bean);
				//hardReloadOnVersionChange
				String cookieVersion = CookieSingleton.get().getCookie(ClientConstants.COOKIE_VERSION);
				if (cookieVersion == null) cookieVersion="";
				if (!cookieVersion.equals(bean.getVersion())) {
					CookieSingleton.get().setCookie(ClientConstants.COOKIE_VERSION, bean.getVersion());
					UriDispatcher.hardReload();
				}
				//LAUNCH APP:
				//GWT.setUncaughtExceptionHandler(new BrowserException());
				UiSingleton.get().drawUi();
				//UiSingleton.get().addInfo("IBB-join version "+bean.getVersion());
			}
		};
		dataService.getPropertyBean(callback);
	}
	
}
