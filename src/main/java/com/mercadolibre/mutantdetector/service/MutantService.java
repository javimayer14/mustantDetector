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
		// verificoAdn(i);zzz
		return creoMatriz(dna);
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
}
