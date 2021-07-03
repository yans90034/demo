package com.demo.task;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.demo.repository.ParamsRepository;

@Component
public class SaleAvailableTask {

	private Logger log = LoggerFactory.getLogger(SaleAvailableTask.class);
	
	public static final String PARAM_SALE = "sale_available";
	private static boolean saleAvailable = false;
	@Autowired
	private ParamsRepository paramsRepository;
	
	
	public static boolean checkSaleAvailable() {
		return saleAvailable;
	}
	
	@PostConstruct
	private void init() {
		renewSaleAvailable();
	}
	
	@Scheduled(cron = "0 * * * * ?")
	public void saleAvailableask() {
		try {
			renewSaleAvailable();
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void renewSaleAvailable() {
		if("Y".equals(paramsRepository.findValueByName(PARAM_SALE))) {
			saleAvailable = true;
		} else {
			saleAvailable = false;
		}
		log.info(String.format("saleAvailable is: %b", saleAvailable));
	}
}
