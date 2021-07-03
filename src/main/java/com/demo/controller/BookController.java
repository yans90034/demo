package com.demo.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.form.ResponseForm;
import com.demo.service.BookService;
import com.demo.task.SaleAvailableTask;

@RestController
@RequestMapping("/book")
public class BookController {
	
	private Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public ResponseForm addBook(@RequestBody Map<String, String> reqBody) throws Exception {
		try {
			if(reqBody == null || StringUtils.isBlank(reqBody.get("book_name"))) {
				return ResponseForm.getFailed("BookName is Empty");
			}
			
			return bookService.addBook(reqBody.get("book_name"));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return ResponseForm.getFailed("Unknown Error");
		}
	}
	
	@RequestMapping(value = "/addSalesVolume", method = RequestMethod.POST)
	public ResponseForm addSalesVolume(@RequestBody Map<String, Object> reqBody) throws Exception {
		try {
			if(SaleAvailableTask.checkSaleAvailable() == false) {
				return ResponseForm.getFailed("Sales is not available");
			} else if (reqBody == null || StringUtils.isBlank(reqBody.get("book_id").toString())) {
				return ResponseForm.getFailed("BookId is Empty");
			} else if(reqBody.get("sales_volume") == null) {
				return ResponseForm.getFailed("SalesVolume is Empty");
			}
			
			return bookService.addSalesVolume(Integer.valueOf(reqBody.get("book_id").toString()), Integer.valueOf(reqBody.get("sales_volume").toString()));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return ResponseForm.getFailed("Unknown Error");
		}
	}
	
	
	@RequestMapping(value = "/updateSaleAvailable", method = RequestMethod.POST)
	public ResponseForm updateSaleAvailable(@RequestBody Map<String, Boolean> reqBody) throws Exception {
		try {
			if(reqBody == null || reqBody.get("sale_available") == null) {
				return ResponseForm.getFailed("SaleAvailable is Empty");
			}
			
			return bookService.updateSaleAvailable((Boolean)reqBody.get("sale_available"));
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			return ResponseForm.getFailed("Unknown Error");
		}
	}
}
