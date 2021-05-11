package com.mercadolibre.mutantdetector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.mercadolibre.mutantdetector.models.dto.DnaDTO;
import com.mercadolibre.mutantdetector.models.dto.StatDTO;
import com.mercadolibre.mutantdetector.models.entity.Dna;
import com.mercadolibre.mutantdetector.service.IMutantService;

@SpringBootTest
class MutantDetectorApplicationTests {

	public static final String DNA_INVALID_WORD = "Error,  la secuencia de adn tiene una letra invalida:' ";
	public static final String DNA_DATA_INTEGRITY_ERROR = "Error, el adn mutante que se quiere ingresar ya está en la DB'";
	String[] human = { "AGTCAG", "AGTCAG", "AGTCAG", "TGCATG", "TGCATG", "TGCATG", "TGCATG" };
	String[] mutant = { "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA" };

	@Autowired
	private IMutantService mutantService;

	@Test
	public void contextLoad() throws Exception {
		assertThat(mutantService).isNotNull();
	}

	@Test
	void mutantMatriz() {
		String[] matriz = { "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA" };
		DnaDTO dna = new DnaDTO();
		try {
			dna.setDna(matriz);
			Boolean result = mutantService.isMutant(dna);
			assertTrue(result);

		} catch (Exception e) {
			String actualMessage = e.getMessage();

			assertTrue(actualMessage.contains(DNA_DATA_INTEGRITY_ERROR));

		}
	}

	@Test
	void humanMatriz() {
		String[] matriz = { "AGTCAG", "AGTCAG", "AGTCAG", "TGCATG", "TGCATG", "TGCATG", "TGCATG" };
		DnaDTO dna = new DnaDTO();
		dna.setDna(matriz);
		Boolean result = mutantService.isMutant(dna);
		assertFalse(result);
	}

	@Test
	void invalidMatrizWord() {
		String[] matriz = { "ZZZZZZ", "AGTCAG", "AGTCAG", "TGCATG", "TGCATG", "TGCATG", "TGCATG" };
		DnaDTO dna = new DnaDTO();
		dna.setDna(matriz);

		try {
			mutantService.isMutant(dna);
		} catch (Exception e) {
			String actualMessage = e.getMessage();
			assertTrue(actualMessage.contains(DNA_INVALID_WORD));
		}
	}

	@Test
	void dataIntegrity() {
		String[] matriz = { "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA", "AAAAAA" };
		DnaDTO dna = new DnaDTO();
		dna.setDna(matriz);
		try {
			mutantService.isMutant(dna);
			mutantService.isMutant(dna);
		} catch (DataIntegrityViolationException e) {
			String actualMessage = e.getMessage();
			assertTrue(actualMessage.contains(DNA_DATA_INTEGRITY_ERROR));
		}
	}

	@Test
	void stats() {
		List<Dna> dnaList = new ArrayList<>();
		Dna dna = new Dna();
//		when(dnaDao.findAll()
//        .thenAnswer(i -> i.getArguments()[0]);
		StatDTO stats = mutantService.stats();
	}

}
