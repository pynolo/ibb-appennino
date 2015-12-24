package it.burningboots.join.shared.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Table(name = "config")
public class Config implements Serializable {
	private static final long serialVersionUID = 2573558514344018622L;
	
	@Id
	private String nameKey;
	@Basic(optional = false)
	@Column(name = "val", nullable = false, length = 64)
	private String val;
	
	public Config() {
	}
	
	public Config(String nameKey, String value) {
		this.nameKey = nameKey;
		this.val = value;
	}

	public String getNameKey() {
		return nameKey;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
		
}
