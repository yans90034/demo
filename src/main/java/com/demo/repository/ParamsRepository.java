package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entity.Params;

@Repository
public interface ParamsRepository extends JpaRepository<Params, Integer> {

	Params findByName(String name);
	
	@Query(value = "SELECT VALUE FROM PARAMS WHERE NAME = ?1 ", nativeQuery = true)
	String findValueByName(String name);
	
}
