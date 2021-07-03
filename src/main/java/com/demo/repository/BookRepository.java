package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	Book findByBookName(String bookName);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE BOOK SET SALES_VOLUME = (SALES_VOLUME + ?2), MODIFY_TIME = NOW() WHERE BOOK_ID = ?1 ", nativeQuery = true)
	void addBookVolume(Integer bookId,Integer saleVolume);
	
}
