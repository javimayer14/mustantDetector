package com.mercadolibre.mutantdetector.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mercadolibre.mutantdetector.models.entity.Dna;

/**
 * Esta interface realiza operaciones de Spring Data CRUD
 * 
 */
public interface IDnaDao extends CrudRepository<Dna, Long> {

}
