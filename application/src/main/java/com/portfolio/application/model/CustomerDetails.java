package com.portfolio.application.model;

import java.time.LocalDate;

/**
 * Domain object with customer information.
 */
public class CustomerDetails extends Customer {

	private LocalDate dateOfBirth;
	private String assets;

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

}