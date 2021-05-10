package com.mercadolibre.mutantdetector.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data	
public class StatDTO {
	
	@JsonAlias({"count_mutant_dna", "countMutantDna"})
	Long countMutantDna;
	Long countHumanDna;
	Float ratio;
}
