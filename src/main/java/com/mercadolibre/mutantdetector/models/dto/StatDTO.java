package com.mercadolibre.mutantdetector.models.dto;

import lombok.Data;

@Data	
public class StatDTO {

	Long countMutantDna;
	Long countHumanDna;
	Float ratio;
}
