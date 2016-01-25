package it.burningboots.join.shared;

import java.io.Serializable;

public class PropertyBean implements Serializable {
	private static final long serialVersionUID = 5717993261825827825L;
	
	private String version = null;
	private String accessKey = null;
	private boolean closed = true;
	private int bedAvailableFrom = -1;
	private int bedAvailableUntil = -1;
	private int bedMax = -1;
	private double bedPrice = -1D;
	private int tentAvailableFrom = -1;
	private int tentAvailableUntil = -1;
	private int tentMax = -1;
	private double tentPrice = -1D;
	
	private int bedCount = -1;
	private int tentCount = -1;
	
	public PropertyBean() {
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean getClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public int getBedAvailableFrom() {
		return bedAvailableFrom;
	}
	public void setBedAvailableFrom(String bedAvailableFrom) {
		this.bedAvailableFrom = Integer.parseInt(bedAvailableFrom);
	}
	public int getTentAvailableFrom() {
		return tentAvailableFrom;
	}
	public void setTentAvailableFrom(String tentAvailableFrom) {
		this.tentAvailableFrom = Integer.parseInt(tentAvailableFrom);
	}
	public int getBedAvailableUntil() {
		return bedAvailableUntil;
	}
	public void setBedAvailableUntil(String bedAvailableUntil) {
		this.bedAvailableUntil = Integer.parseInt(bedAvailableUntil);
	}
	public int getTentAvailableUntil() {
		return tentAvailableUntil;
	}
	public void setTentAvailableUntil(String tentAvailableUntil) {
		this.tentAvailableUntil = Integer.parseInt(tentAvailableUntil);
	}
	public int getBedMax() {
		return bedMax;
	}
	public void setBedMax(String bedMax) {
		this.bedMax = Integer.parseInt(bedMax);
	}
	public int getTentMax() {
		return tentMax;
	}
	public void setTentMax(String tentMax) {
		this.tentMax = Integer.parseInt(tentMax);
	}
	public double getBedPrice() {
		return bedPrice;
	}
	public void setBedPrice(String bedPrice) {
		this.bedPrice = Double.parseDouble(bedPrice);
	}
	public double getTentPrice() {
		return tentPrice;
	}
	public void setTentPrice(String tentPrice) {
		this.tentPrice =  Double.parseDouble(tentPrice);
	}
	public int getBedCount() {
		return bedCount;
	}
	public void setBedCount(int bedCount) {
		this.bedCount = bedCount;
	}
	public int getTentCount() {
		return tentCount;
	}
	public void setTentCount(int tentCount) {
		this.tentCount = tentCount;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	
	public int getAvailableBed() {
		int total = bedCount+tentCount;
		int a1 = bedMax-bedCount;
		int a2 = bedAvailableUntil-total;
		if (a1 < a2) return a1;
		return a2;
	}
	
	public int getAvailableTent() {
		int total = bedCount+tentCount;
		int a1 = tentMax-tentCount;
		int a2 = tentAvailableUntil-total;
		if (a1 < a2) return a1;
		return a2;
	}
}
