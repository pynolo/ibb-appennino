package it.burningboots.join.client.widgets;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.entity.Participant;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParticipantTable extends PagingTable<Participant> {
	
	private static final int TABLE_ROWS = 30;
	
	private AsyncCallback<List<Participant>> callback = new AsyncCallback<List<Participant>>() {
		@Override
		public void onFailure(Throwable caught) {
			setTableRows(new ArrayList<Participant>());
			//WaitSingleton.get().stop();
		}
		@Override
		public void onSuccess(List<Participant> result) {
			setTableRows(result);
			//WaitSingleton.get().stop();
		}
	};
	
	public ParticipantTable(DataModel<Participant> model) {
		super(model, TABLE_ROWS);
		drawPage(0);
	}

	@Override
	public void drawPage(int page) {
		clearInnerTable();
		getInnerTable().setHTML(0, 0, ClientConstants.LABEL_LOADING);
		getModel().find(page*TABLE_ROWS,
				TABLE_ROWS,
				callback);
	}
	
	public void refresh() {
		drawPage(0);
	}
	
	@Override
	protected void addTableRow(int rowNum, Participant rowObj) {
		final Participant rowFinal = rowObj;
		//TRANSFER
		getInnerTable().setHTML(rowNum, 0, rowFinal.getItemNumber());
		//COGNOME
		String cognome = "";
		if (rowFinal.getLastName() != null) cognome = rowFinal.getLastName();
		getInnerTable().setHTML(rowNum, 1, cognome);
		//NOME
		String nome = "";
		if (rowFinal.getFirstName() != null) nome = rowFinal.getFirstName();
		getInnerTable().setHTML(rowNum, 2, nome);
		//EMAIL
		getInnerTable().setHTML(rowNum, 3, rowFinal.getEmail());
		//NASCITA
		String nascita = "";
		if (rowFinal.getBirthCity() != null) nascita = rowFinal.getBirthCity();
		nascita += ClientConstants.FORMAT_DAY.format(rowFinal.getBirthDt());
		getInnerTable().setHTML(rowNum, 4, nascita);
		//CIBO
		String food = "";
		if (rowFinal.getFoodRestrictions() != null) food = rowFinal.getFoodRestrictions();
		getInnerTable().setHTML(rowNum, 5, food);
		//Esperienza
		String exp = "";
		if (rowFinal.getAlreadyIbb()) exp += "IBB ";
		if (rowFinal.getAlreadyBurner()) exp += "Burn ";
		getInnerTable().setHTML(rowNum, 6, exp);
		//VOLONTARIATO
		String vol = "";
		if (rowFinal.getVolunteering() != null) vol = rowFinal.getVolunteering();
		getInnerTable().setHTML(rowNum, 7, vol);
		//PAGAMENTO
		String pag = "";
		if (rowFinal.getPaymentAmount() != null)
				pag += "&euro;"+ClientConstants.FORMAT_CURRENCY.format(rowFinal.getPaymentAmount())+" ";
		if (rowFinal.getPaymentDt() != null)
				pag += ClientConstants.FORMAT_TIMESTAMP.format(rowFinal.getPaymentDt())+" ";
		getInnerTable().setHTML(rowNum, 8, pag);
		//UPDATES
		String repl = "";
		if (rowFinal.getLastNameOriginal() != null)
				repl += rowFinal.getLastNameOriginal()+" ";
		if (rowFinal.getFirstNameOriginal() != null)
				repl += rowFinal.getFirstNameOriginal()+" ";
		if (rowFinal.getUpdateDt().after(rowFinal.getCreationDt()))
				repl += ClientConstants.FORMAT_DAY.format(rowFinal.getUpdateDt())+" ";
		getInnerTable().setHTML(rowNum, 8, repl);
	}
	
	@Override
	protected void addHeader() {
		// Set the data in the current row
		getInnerTable().setHTML(0, 0, "Transfer");
		getInnerTable().setHTML(0, 1, "Last name");
		getInnerTable().setHTML(0, 2, "First name");
		getInnerTable().setHTML(0, 3, "Email");
		getInnerTable().setHTML(0, 4, "Birth");
		getInnerTable().setHTML(0, 5, "Food restr.");
		getInnerTable().setHTML(0, 6, "Experience");
		getInnerTable().setHTML(0, 7, "Volunteer");
		getInnerTable().setHTML(0, 8, "Payment");
		getInnerTable().setHTML(0, 9, "Updates");
	}
	
	@Override
	protected void onEmptyResult() {}
	
	
	
	//Inner classes
	
	
	
	public static class ParticipantModel implements DataModel<Participant> {
		private DataServiceAsync dataService = GWT.create(DataService.class);
		private boolean confirmed = true;
		
		public ParticipantModel(boolean confirmed) {
			this.confirmed=confirmed;
		}

		@Override
		public void find(int offset, int pageSize, AsyncCallback<List<Participant>> callback) {
			//WaitSingleton.get().start();
			dataService.findParticipants(confirmed, callback);
		}
	}
	
}
