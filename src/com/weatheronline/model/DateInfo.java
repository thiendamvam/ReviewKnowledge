package com.weatheronline.model;

import java.io.Serializable;

public class DateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String status;
	private String minTemp;
	private String maxTemp;
	
	private String urlIcon;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}

	public String getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the urlIcon
	 */
	public String getUrlIcon() {
		return urlIcon;
	}

	/**
	 * @param urlIcon the urlIcon to set
	 */
	public void setUrlIcon(String urlIcon) {
		this.urlIcon = urlIcon;
	}

}
