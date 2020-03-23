package com.portfolio.application.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.application.model.CustomerDetails;
import com.portfolio.application.model.PortfolioInformation;
import com.portfolio.application.service.PortfolioService;

/**
 * This class is used to calculate portfolio model based on age of customer.
 */
@RestController
public class PortfolioController {

	private static final Logger logger = LogManager.getLogger(PortfolioController.class);

	@Autowired
	private PortfolioService portfolioService;

	/**
	 * Controller method to retrieve portfolio model information.
	 */
	@PostMapping(value = "/assign-portfolio-model")
	public List<PortfolioInformation> assignPortfolioModel(@RequestBody final List<CustomerDetails> customerDetails) {
		logger.info("Inside assignPortfolioModel method");
		return portfolioService.assignPortfolioForCustomer(customerDetails);
	}

}