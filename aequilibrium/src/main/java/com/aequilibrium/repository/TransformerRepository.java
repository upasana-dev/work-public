package com.aequilibrium.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aequilibrium.model.Transformer;

@Repository
public interface TransformerRepository extends CrudRepository<Transformer, Long> {
	
	List<Transformer> findByName(List<String> names);

}
