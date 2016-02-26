package it.burningboots.appennino.shared;

import java.io.Serializable;

public class PropertyBean implements Serializable {
	private static final long serialVersionUID = 5717993261825827825L;
	
	private String version = null;
	private String accessKey = null;
	private boolean closed = true;
	private int hutAvailableFrom = -1;
	private int hutAvailableUntil = -1;
	private int hutMax = -1;
	private double hutPrice = -1D;
	private double hutPriceLow = -1D;
	private int tentAvailableFrom = -1;
	private int tentAvailableUntil = -1;
	private int tentMax = -1;
	private double tentPrice = -1D;
	private double tentPriceLow = -1D;
	
	private int hutCount = -1;
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
	public int getHutAvailableFrom() {
		return hutAvailableFrom;
	}
	public void setHutAvailableFrom(String hutAvailableFrom) {
		this.hutAvailableFrom = Integer.parseInt(hutAvailableFrom);
	}
	public int getTentAvailableFrom() {
		return tentAvailableFrom;
	}
	public void setTentAvailableFrom(String tentAvailableFrom) {
		this.tentAvailableFrom = Integer.parseInt(tentAvailableFrom);
	}
	public int getHutAvailableUntil() {
		return hutAvailableUntil;
	}
	public void setHutAvailableUntil(String hutAvailableUntil) {
		this.hutAvailableUntil = Integer.parseInt(hutAvailableUntil);
	}
	public int getTentAvailableUntil() {
		return tentAvailableUntil;
	}
	public void setTentAvailableUntil(String tentAvailableUntil) {
		this.tentAvailableUntil = Integer.parseInt(tentAvailableUntil);
	}
	public int getHutMax() {
		return hutMax;
	}
	public void setHutMax(String hutMax) {
		this.hutMax = Integer.parseInt(hutMax);
	}
	public int getTentMax() {
		return tentMax;
	}
	public void setTentMax(String tentMax) {
		this.tentMax = Integer.parseInt(tentMax);
	}
	public double getHutPrice() {
		return hutPrice;
	}
	public void setHutPrice(String hutPrice) {
		this.hutPrice = Double.parseDouble(hutPrice);
	}
	public double getTentPrice() {
		return tentPrice;
	}
	public void setTentPrice(String tentPrice) {
		this.tentPrice =  Double.parseDouble(tentPrice);
	}
	public int getHutCount() {
		return hutCount;
	}
	public void setHutCount(int hutCount) {
		this.hutCount = hutCount;
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
	public double getHutPriceLow() {
		return hutPriceLow;
	}
	public void setHutPriceLow(String hutPriceLow) {
		this.hutPriceLow = Double.parseDouble(hutPriceLow);
	}
	public double getTentPriceLow() {
		return tentPriceLow;
	}
	public void setTentPriceLow(String tentPriceLow) {
		this.tentPriceLow = Double.parseDouble(tentPriceLow);
	}

	public int getAvailableHut() {
		int total = hutCount+tentCount;
		int a1 = hutMax-hutCount;
		int a2 = hutAvailableUntil-total;
		if (a1 < a2) return a1;
		return a2;
	}
	
	public int getAvailableTent() {
		int total = hutCount+tentCount;
		int a1 = tentMax-tentCount;
		int a2 = tentAvailableUntil-total;
		if (a1 < a2) return a1;
		return a2;
	}
}
