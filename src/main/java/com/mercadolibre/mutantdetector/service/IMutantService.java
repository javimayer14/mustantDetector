package com.mercadolibre.mutantdetector.service;

import com.mercadolibre.mutantdetector.models.dto.DnaDTO;
import com.mercadolibre.mutantdetector.models.dto.StatDTO;

public interface IMutantService {

	public Boolean isMutant(DnaDTO dna);
	
	public StatDTO stats();
}
