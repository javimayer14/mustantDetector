package com.mercadolibre.mutantdetector.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolibre.mutantdetector.models.dao.IDnaDao;
import com.mercadolibre.mutantdetector.models.dto.DnaDTO;
import com.mercadolibre.mutantdetector.models.dto.StatDTO;
import com.mercadolibre.mutantdetector.models.entity.Dna;
import com.mercadolibre.mutantdetector.models.entity.DnaType;

@Service
public class MutantService implements IMutantService {

	public static final String[] DNA_WORDS = { "A", "T", "C", "G" };

	@Autowired
	IDnaDao dnaDao;

	@Override
	public Boolean isMutant(DnaDTO dna) {
		List<String> list = Arrays.asList(dna.getDna());
		String joinedString = String.join("", list);

		Boolean result = creoMatriz(dna.getDna());
		Dna dnaEntity = new Dna();
		dnaEntity.setSequence(joinedString);
		DnaType dnaType = new DnaType();
		if (result) {
			dnaType.setId(1L);
			dnaEntity.setDnaType(dnaType);
		} else {
			dnaType.setId(2L);
			dnaEntity.setDnaType(dnaType);

		}
		try {
			dnaDao.save(dnaEntity);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error, el adn mutante que se quiere ingresar ya está en la DB'");

		}

		return result;
	}

	private Boolean creoMatriz(String[] dna) {
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
		Integer horizlontalAdn = verificoSecuencia(dnaMatriz);
		Integer verticalAdn = verificoSecuenciaVertical(dnaMatriz);
		Integer oblicualAdn = verificoSecuenciaOblicua(dnaMatriz);

		if ((horizlontalAdn + verticalAdn + oblicualAdn) >= 2)
			return true;
		else
			return false;

	}

	private Integer verificoSecuencia(String[][] dnaMatriz) {
		String letra;
		Integer horizontal = 0;
		Integer N = 6;
		for (int k = 0; k < N; k++) {
			Integer repit = 0;
			letra = "";
			for (int l = 0; l < N; l++) {
				String letraActual = dnaMatriz[k][l];
				if (letraActual.equals(letra)) {
					repit++;
					if (repit == 3) {
						horizontal++;
						break;
					}
				} else {
					repit = 0;
				}
				letra = letraActual;
			}
		}
		System.out.println("HORIZONTAL: " + horizontal);

		return horizontal;
	}

	private Integer verificoSecuenciaVertical(String[][] dnaMatriz) {
		String letra;
		Integer vertical = 0;
		Integer N = 6;
		for (int k = 0; k < N; k++) {
			Integer repit = 0;
			letra = "";
			for (int l = 0; l < N; l++) {
				String letraActual = dnaMatriz[l][k];
				if (letraActual.equals(letra)) {
					repit++;
					if (repit == 3) {
						vertical++;
						break;
					}
				} else {
					repit = 0;
				}
				letra = letraActual;
			}
		}
		System.out.println("VERTICAL" + vertical);

		return vertical;
	}

	private Integer verificoSecuenciaOblicua(String[][] dnaMatriz) {
		String letra;
		Integer oblicua = 0;
		Integer N = 6;
		for (int k = 0; k < N; k++) {
			Integer repit = 0;
			letra = "";
			for (int l = 0; l < N; l++) {
				if ((k + l) < N) {
					String letraActual = dnaMatriz[l][k + l];
					if (letraActual.equals(letra)) {
						repit++;
						if (repit == 3) {
							oblicua++;
							break;
						}
					} else {
						repit = 0;
					}
					letra = letraActual;
				}

			}
		}
		System.out.println("OBLICUO: " + oblicua);

		return oblicua;
	}

	private void verificoAdn(String i) {
		String[] list = i.split("");
		Arrays.stream(list).forEach(j -> {
			if (!Arrays.stream(DNA_WORDS).anyMatch(j::equals))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error,  la secuencia de adn tiene una letra invalida:' " + j);
		});
	}

	@Override
	public StatDTO stats() {
		List<Dna> allDna = (List<Dna>) dnaDao.findAll();
		Long humans = 0L;
		Long mutants = 0L;
		for (Dna d : allDna) {
			Long type = d.getDnaType().getId();
			if (type == 1)
				humans++;
			if (type == 2)
				mutants++;
		}
		Float ratio = (float) (mutants / humans);
		StatDTO stat = new StatDTO();
		stat.setCountHumanDna(humans);
		stat.setCountMutantDna(mutants);
		stat.setRatio(ratio);
		return stat;
	}
}
