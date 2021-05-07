package com.mercadolibre.mutantdetector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> index(@RequestBody String[] dna) {
		if (mutantService.isMutant(dna))
			return new ResponseEntity<>("El ADN es de mutante !", HttpStatus.OK);
		else
			return new ResponseEntity<>("El ADN no es de mutante", HttpStatus.FORBIDDEN);

	}

}
