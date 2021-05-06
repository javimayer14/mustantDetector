package com.mercadolibre.mutantdetector.service;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MutantService implements IMutantService {

	public static final String[] DNA_WORDS = { "A", "T", "C", "G" };

	@Override
	public Boolean isMutant(String[] dna) {
		Arrays.asList(dna).forEach(i -> {
			// verificoAdn(i);
			creoMatriz(dna);
			throw new ResponseStatusException(HttpStatus.OK, "Error,  la secuencia de adn tiene una letra invalida:' ");

		});

		return null;
	}

	private void creoMatriz(String[] dna) {
		Integer N = dna[0].length();
		String[][] dnaMatriz = new String[N][N];
		for (int k = 0; k < N; k++) {
			for (int l = 0; l < N; l++) {
				String row = dna[k];
				String[] list = row.split("");
				dnaMatriz[k][l] = list[l];
			}
		}
		System.out.println(Arrays.toString(dnaMatriz));

	}

	private void verificoAdn(String i) {
		String[] list = i.split("");
		Arrays.stream(list).forEach(j -> {
			if (!Arrays.stream(DNA_WORDS).anyMatch(j::equals))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error,  la secuencia de adn tiene una letra invalida:' " + j);
		});
	}
}
