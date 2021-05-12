package com.mercadolibre.mutantdetector.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StatDTO {

	@JsonProperty("count_mutant_dna")
	Long countMutantDna;
	@JsonProperty("count_human_dna")
	Long countHumanDna;
	Float ratio;
}
