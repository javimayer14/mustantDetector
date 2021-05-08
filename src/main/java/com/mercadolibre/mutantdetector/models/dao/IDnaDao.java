package com.mercadolibre.mutantdetector.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.mercadolibre.mutantdetector.models.dto.DnaDTO;
import com.mercadolibre.mutantdetector.models.entity.Dna;

public interface IDnaDao extends CrudRepository<Dna, Long>{

}
