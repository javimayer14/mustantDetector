package com.mercadolibre.mutantdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mutantdetector.service.IMutantService;

@RestController
@RequestMapping("/api")
public class MutantController {

	@Autowired
	private IMutantService mutantService;

	@PostMapping("/mutant")
	public boolean index(@RequestBody String[] dna) {
		return mutantService.isMutant(dna);

	}

}
