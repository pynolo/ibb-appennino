package it.burningboots.join.shared.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
	private Integer id;
	@Column(name = "created", length = 64)
	private Date created = null;
	@Basic(optional = false)
	@Column(name = "item_number", length = 64, nullable = false)
	private String itemNumber = "";//Codice personale
	@Column(name = "email", length = 64)
	private String email = "";
	@Column(name = "first_name", length = 64)
	private String firstName = "";
	@Column(name = "last_name", length = 64)
	private String lastName = "";
	@Column(name = "food_restrictions", length = 64)
	private String foodRestrictions = "";
	@Column(name = "volunteering", length = 64)
	private String volunteering = "";
	@Basic(optional = false)
	@Column(name = "already_burner", nullable = false)
	private boolean alreadyBurner;
	@Basic(optional = false)
	@Column(name = "already_ibb", nullable = false)
	private boolean alreadyIbb;
	@Column(name = "language", length = 4)
	private String language = "";
	@Column(name = "payment_amount")
	private Double paymentAmount = null;
	@Column(name = "payment_dt")
	@Temporal(TemporalType.DATE)
	private Date paymentDt = null;
	
    @OneToMany(fetch = FetchType.EAGER, mappedBy="participant")
    private Set<IpnResponse> ipnResponses;
	
	
	public Participant() {
	}
		
	public Participant(Date created, String itemNumber,
			String email, String firstName, String lastName,
			String foodRestrictions, String volunteering,
			Boolean alreadyBurner, Boolean alreadyIbb,
			String language,
			Double paymentAmount, Date paymentDt,
			Integer accommodationType) {
		this.itemNumber = itemNumber;
		this.email = email;
		this.firstName = firstName;
		this.created = created;
		this.alreadyBurner = alreadyBurner;
		this.alreadyIbb = alreadyIbb;
		this.foodRestrictions = foodRestrictions;
		this.volunteering = volunteering;
		this.paymentAmount = paymentAmount;
		this.paymentDt = paymentDt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
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

	public Date getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(Date paymentDt) {
		this.paymentDt = paymentDt;
	}

	public Set<IpnResponse> getIpnResponses() {
		return ipnResponses;
	}

	public void setIpnResponses(Set<IpnResponse> ipnResponses) {
		this.ipnResponses = ipnResponses;
	}

	public boolean isAlreadyBurner() {
		return alreadyBurner;
	}

	public void setAlreadyBurner(boolean alreadyBurner) {
		this.alreadyBurner = alreadyBurner;
	}

	public boolean isAlreadyIbb() {
		return alreadyIbb;
	}

	public void setAlreadyIbb(boolean alreadyIbb) {
		this.alreadyIbb = alreadyIbb;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	
	

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Participant)) {
            return false;
        }
        Participant other = (Participant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Participant[id=" + id + ", itemNumber="+itemNumber+"]";
    }
}
