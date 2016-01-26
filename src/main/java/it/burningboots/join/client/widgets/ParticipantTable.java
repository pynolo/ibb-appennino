package it.burningboots.join.client.widgets;

import it.burningboots.join.client.ClientConstants;
import it.burningboots.join.client.service.DataService;
import it.burningboots.join.client.service.DataServiceAsync;
import it.burningboots.join.shared.AppConstants;
import it.burningboots.join.shared.entity.Participant;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParticipantTable extends PagingTable<Participant> {
	
	private static final int TABLE_ROWS = 1000;
	private int bedCount = 0;
	private int tentCount = 0;
	
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
		//CONFIRM
		if (rowFinal.getPaymentAmount() != null && rowFinal.getPaymentDt() != null) {
			if (rowFinal.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED))
					getInnerTable().setHTML(rowNum, 0, "<i class='fa fa-home'></i>");
			if (rowFinal.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT))
					getInnerTable().setHTML(rowNum, 0, "<i class='fa fa-tree'></i>");
		}
		//COGNOME
		String cognome = "";
		if (rowFinal.getLastName() != null) cognome = rowFinal.getLastName();
		getInnerTable().setHTML(rowNum, 1, "<b>"+cognome+"</b>");
		//NOME
		String nome = "";
		if (rowFinal.getFirstName() != null) nome = rowFinal.getFirstName();
		getInnerTable().setHTML(rowNum, 2, "<b>"+nome+"</b>");
		//ACCOMMODATION TYPE
		String acType = "";
		if (rowFinal.getAccommodationType().equals(AppConstants.ACCOMMODATION_BED)) {
			acType="Hut";
			if (rowFinal.getPaymentAmount() != null && rowFinal.getPaymentDt() != null) {
				bedCount++;
				acType += " ("+bedCount+"&deg;)";
			}
		}
		if (rowFinal.getAccommodationType().equals(AppConstants.ACCOMMODATION_TENT)) {
			acType="Tent";
			if (rowFinal.getPaymentAmount() != null && rowFinal.getPaymentDt() != null) {
				tentCount++;
				acType += " ("+tentCount+"&deg;)";
			}
		}
		getInnerTable().setHTML(rowNum, 3, acType);
		//EMAIL
		getInnerTable().setHTML(rowNum, 4, "<b>"+rowFinal.getEmail()+"</b> ");
		//TRANSFER
		getInnerTable().setHTML(rowNum, 5, rowFinal.getItemNumber());
		//NASCITA
		String nascita = "";
		nascita += ClientConstants.FORMAT_DAY.format(rowFinal.getBirthDt())+" ";
		if (rowFinal.getBirthCity() != null) nascita += rowFinal.getBirthCity()+" ";
		getInnerTable().setHTML(rowNum, 6, nascita);
		//CIBO
		String food = "";
		if (rowFinal.getFoodRestrictions() != null) food = rowFinal.getFoodRestrictions();
		getInnerTable().setHTML(rowNum, 7, food);
		//VOLONTARIATO
		String vol = "";
		if (rowFinal.getVolunteering() != null) vol = rowFinal.getVolunteering();
		getInnerTable().setHTML(rowNum, 8, vol);
		//ESPERIENZA
		String exp = "";
		if (rowFinal.getAlreadyIbb()) exp += "ibb ";
		if (rowFinal.getAlreadyBurner()) exp += "other ";
		getInnerTable().setHTML(rowNum, 9, exp);
		//PAGAMENTO
		String pag = "";
		if (rowFinal.getPaymentAmount() != null)
				pag += "<b>&euro;"+ClientConstants.FORMAT_CURRENCY.format(rowFinal.getPaymentAmount())+"</b> ";
		if (rowFinal.getPaymentDt() != null)
				pag += ClientConstants.FORMAT_TIMESTAMP.format(rowFinal.getPaymentDt())+" ";
		getInnerTable().setHTML(rowNum, 10, pag);
		//UPDATES
		String repl = "";
		if (rowFinal.getUpdateDt().after(rowFinal.getCreationDt())) {
			if (rowFinal.getLastNameOriginal() != null)
					repl += rowFinal.getLastNameOriginal()+" ";
			if (rowFinal.getFirstNameOriginal() != null)
					repl += rowFinal.getFirstNameOriginal()+" ";
			repl += ClientConstants.FORMAT_DAY.format(rowFinal.getUpdateDt())+" ";
		}
		getInnerTable().setHTML(rowNum, 11, repl);
	}
	
	@Override
	protected void addHeader() {
		// Set the data in the current row
		getInnerTable().setHTML(0, 0, "");
		getInnerTable().setHTML(0, 1, "Last name");
		getInnerTable().setHTML(0, 2, "First name");
		getInnerTable().setHTML(0, 3, "Type");
		getInnerTable().setHTML(0, 4, "Email");
		getInnerTable().setHTML(0, 5, "Transfer");
		getInnerTable().setHTML(0, 6, "Birth");
		getInnerTable().setHTML(0, 7, "Experience");
		getInnerTable().setHTML(0, 8, "Food restr.");
		getInnerTable().setHTML(0, 9, "Volunteer");
		getInnerTable().setHTML(0, 10, "Payment");
		getInnerTable().setHTML(0, 11, "Updates");
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
