package com.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.Book;
import com.demo.entity.Params;
import com.demo.form.ResponseForm;
import com.demo.repository.BookRepository;
import com.demo.repository.ParamsRepository;
import com.demo.task.SaleAvailableTask;

@Service
public class BookService {

	private Logger log = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private ParamsRepository paramsRepository;
	
	@Transactional
	public ResponseForm addBook(String bookName) {
		if(bookRepository.findByBookName(bookName) != null) {
			return ResponseForm.getFailed("BookName already exist");
		}
		
		Book book = new Book();
		book.setBookName(bookName);
		book.setSalesVolume(0);
		book.setCreateTime(new Date());
		bookRepository.save(book);
		
		Map<String, Object> result = new HashMap<>();
		result.put("book_id", String.valueOf(book.getBookId()));
		
		log.info(String.format("addBook bookId:%d, bookName:%s", book.getBookId(), bookName));
		return ResponseForm.getSuccessed(result);
	}
	
	@Transactional
	public ResponseForm addSalesVolume(Integer bookId, Integer saleVolume) {
		if(bookRepository.findById(bookId).isPresent() == false) {
			return ResponseForm.getFailed("BookId not exist");
		}
		
		bookRepository.addBookVolume(Integer.valueOf(bookId), saleVolume);
		
		log.info(String.format("addSalesVolume bookId:%d, saleVolume:%d", bookId, saleVolume));
		return ResponseForm.getSuccessed();
	}
	
	@Transactional
	public ResponseForm updateSaleAvailable(Boolean saleAvailable) {
		Params param = paramsRepository.findByName(SaleAvailableTask.PARAM_SALE);
		
		if(saleAvailable) {
			param.setValue("Y");
		} else {
			param.setValue("N");
		}
		
		param.setModifyTime(new Date());
		paramsRepository.save(param);
		
		log.info(String.format("updateSaleAvailable saleAvailable:%b", saleAvailable));
		return ResponseForm.getSuccessed();
	}
	
}
