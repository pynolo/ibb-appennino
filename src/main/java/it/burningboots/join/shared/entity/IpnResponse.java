package it.burningboots.join.shared.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Table(name = "ipn_response")
public class IpnResponse implements Serializable {
	private static final long serialVersionUID = 6665621291812512821L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private String id;
	@Column(name = "item_number", length = 64)
    private String itemNumber;
	@Column(name = "payment_status", length = 64)
    private String paymentStatus;
	@Column(name = "payer_email", length = 64)
    private String payerEmail;
	@Column(name = "mc_gross", length = 64)
    private String mcGross;
	@Column(name = "mc_currency", length = 64)
    private String mcCurrency;
	@Column(name = "payment_date", length = 64)
    private String paymentDate;
	@Column(name = "pending_reason", length = 64)
    private String pendingReason;
	@Column(name = "payment_type", length = 64)
    private String paymentType;
	@Basic(optional = false)
	@Column(name = "participant_found", nullable = false)
	private boolean participantFound = false;
	
	
	public IpnResponse() {
	}
	
	public IpnResponse(String id, String itemNumber, String paymentStatus, String payerEmail,
			String mcGross, String mcCurrency, String paymentDate,
			String pendingReason, String paymentType) {
		this.id = id;
		this.itemNumber = itemNumber;
		this.paymentStatus = paymentStatus;
		this.payerEmail = payerEmail;
		this.mcGross = mcGross;
		this.mcCurrency = mcCurrency;
		this.paymentDate = paymentDate;
		this.pendingReason = pendingReason;
		this.paymentType = paymentType;
	}
	
	public String getId() {
		return id;
	}
	
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPayerEmail() {
		return payerEmail;
	}

	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public String getMcGross() {
		return mcGross;
	}

	public void setMcGross(String mcGross) {
		this.mcGross = mcGross;
	}

	public String getMcCurrency() {
		return mcCurrency;
	}

	public void setMcCurrency(String mcCurrency) {
		this.mcCurrency = mcCurrency;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPendingReason() {
		return pendingReason;
	}

	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public boolean getParticipantFound() {
		return participantFound;
	}

	public void setParticipantFound(boolean participantFound) {
		this.participantFound = participantFound;
	}
	
}
