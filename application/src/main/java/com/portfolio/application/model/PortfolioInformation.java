package com.portfolio.application.model;

/**
 * Response object with portfolio information.
 */
public class PortfolioInformation extends Customer {

	private String portfolioName;

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

}