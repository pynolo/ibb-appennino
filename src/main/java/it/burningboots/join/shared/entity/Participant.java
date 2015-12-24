package it.burningboots.join.shared.entity;

import it.burningboots.join.shared.AppConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "participant")
public class Participant implements Serializable {
	private static final long serialVersionUID = -7816100274638131540L;
	
	@Id
    @Basic(optional = false)
	@Column(name = "item_number", length = 255, nullable = false)
	private String itemNumber = "";//Codice personale
	@Column(name = "email", length = 255)
	private String email = "";
	@Column(name = "first_name", length = 255)
	private String firstName = "";
	@Column(name = "last_name", length = 255)
	private String lastName = "";
	@Column(name = "created", length = 255)
	private Date created = null;
	@Column(name = "arrival_time", length = 255)
	private String arrivalTime = "";
	@Column(name = "country_name", length = 255)
	private String countryName = "";
	@Column(name = "food_restrictions", length = 255)
	private String foodRestrictions = "";
	@Column(name = "volunteering", length = 255)
	private String volunteering = "";
	@Column(name = "amount")
	private Double amount = null;
	@Column(name = "payment_dt")
	@Temporal(TemporalType.DATE)
	private Date paymentDt = null;
	@Column(name = "accommodation_type", nullable = false)
	private Integer accommodationType = AppConstants.ACCOMMODATION_DEFAULT;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy="ipnResponse")
    private Set<IpnResponse> ipnResponses;
	
	
	public Participant() {
	}
		
	public Participant(String itemNumber, String email,
			String firstName, String lastName, Date created,
			String arrivalTime, String countryName, String foodRestrictions,
			String volunteering, Double amount, Date paymentDt,
			Integer accommodationType) {
		this.itemNumber = itemNumber;
		this.email = email;
		this.firstName = firstName;
		this.created = created;
		this.arrivalTime = arrivalTime;
		this.countryName = countryName;
		this.foodRestrictions = foodRestrictions;
		this.volunteering = volunteering;
		this.amount = amount;
		this.paymentDt = paymentDt;
		this.accommodationType = accommodationType;
	}

	public String getItemNumber() {
		return itemNumber;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getFoodRestrictions() {
		return foodRestrictions;
	}

	public void setFoodRestrictions(String foodRestrictions) {
		this.foodRestrictions = foodRestrictions;
	}

	public String getVolunteering() {
		return volunteering;
	}

	public void setVolunteering(String volunteering) {
		this.volunteering = volunteering;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(Date paymentDt) {
		this.paymentDt = paymentDt;
	}

	public Integer getAccommodationType() {
		return accommodationType;
	}

	public void setAccommodationType(Integer accommodationType) {
		this.accommodationType = accommodationType;
	}

	public Set<IpnResponse> getIpnResponses() {
		return ipnResponses;
	}

	public void setIpnResponses(Set<IpnResponse> ipnResponses) {
		this.ipnResponses = ipnResponses;
	}


}
