package com.portfolio.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.portfolio.application.model.CustomerDetails;
import com.portfolio.application.model.PortfolioInformation;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to calculate portfolio model based on age of customer.
 */
@Service
public class PortfolioService {

	private static final Logger logger = LogManager.getLogger(PortfolioService.class);

	private static final String RETIREMENT = "Retirement";
	private static final String INCOME = "Income";
	private static final String GROWTH = "Growth";
	private static final String AGGRESSIVE_GROWTH = "Aggressive Growth";
	private static final String REGEX = "[0-9]\\.?[0-9]*";
	private static final String EMPTY = "";
	private static final String DOLLAR = "$";
	private static final int SIXTY_SIX = 66;
	private static final int FIFTY_SIX = 56;
	private static final int FORTY_ONE = 41;
    private static final String STR_REGEX = "[a-zA-Z0-9]+";

	/**
	 * Validate customer information and retrieve portfolio information.
	 */
	public List<PortfolioInformation> assignPortfolioForCustomer(final List<CustomerDetails> customerDetailsList) {

		logger.info("Inside assignPortfolioForCustomer method");
		final List<PortfolioInformation> portfolioInformationList = new ArrayList<>();

		if (!CollectionUtils.isEmpty(customerDetailsList)) {
			LocalDate currentDate = LocalDate.now();
			for (final CustomerDetails customerDetails : customerDetailsList) {

				try {
					final boolean isValid = validateCustomerInformation(customerDetails);
	
					if (isValid) {
						final Period period = Period.between(customerDetails.getDateOfBirth(), currentDate);
						final int age = period.getYears();
						logger.info("Age : " + age);
						assignPortfolioInformation(portfolioInformationList, currentDate, customerDetails, age);
					}
				}
				catch(Exception e) {
					logger.error("Error processing for Customer {} {}", customerDetails.getFirstName(), customerDetails.getLastName() );
				}
			}
		}
		return portfolioInformationList;
	}

	/**
	 * This method is used to calculate portfolio information.
	 */
	private void assignPortfolioInformation(final List<PortfolioInformation> portfolioInformationList,
			final LocalDate currentDate, final CustomerDetails customerDetails, final int age) {

		if (currentDate.isAfter(customerDetails.getDateOfBirth()) || currentDate.isEqual(customerDetails.getDateOfBirth())) {

			final PortfolioInformation portfolioInformation = new PortfolioInformation();
			portfolioInformation.setFirstName(customerDetails.getFirstName());
			portfolioInformation.setLastName(customerDetails.getLastName());
			
			if (age < FORTY_ONE) {
				portfolioInformation.setPortfolioName(AGGRESSIVE_GROWTH);
			} else if (age < FIFTY_SIX) {
				portfolioInformation.setPortfolioName(GROWTH);
			} else if (age < SIXTY_SIX) {
				portfolioInformation.setPortfolioName(INCOME);
			} else {
				portfolioInformation.setPortfolioName(RETIREMENT);
			}
			portfolioInformationList.add(portfolioInformation);
		} else {
			logger.info("Date of birth provided is not valid.");
		}
	}

	/**
	 * This method is used to validate customer information.
	 */
	private boolean validateCustomerInformation(final CustomerDetails customerDetails) {

		if (StringUtils.isEmpty(StringUtils.trimWhitespace(customerDetails.getFirstName())) || !(StringUtils.trimWhitespace(customerDetails.getFirstName())).matches(STR_REGEX)) {
			logger.info("First name is not valid");
			return false;
		}

		if (StringUtils.isEmpty(StringUtils.trimWhitespace(customerDetails.getLastName())) || !(StringUtils.trimWhitespace(customerDetails.getLastName())).matches(STR_REGEX)) {
			logger.info("Last name is not valid");
			return false;
		}

		if (null == customerDetails.getDateOfBirth()) {
			logger.info("Date of birth is empty");
			return false;
		}

		if (StringUtils.isEmpty(StringUtils.trimWhitespace(customerDetails.getAssets()))) {
			logger.info("Assets is empty");
			return false;
		} else {
			String price = customerDetails.getAssets().replace(DOLLAR, EMPTY);
			if (price.matches(REGEX)) {
				customerDetails.setAssets(price);
			} else {
				logger.info("Assets is not in correct format");
				return false;
			}
		}
		
		return true;
	}
}