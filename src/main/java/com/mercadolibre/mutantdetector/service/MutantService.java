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
	public static final Integer COINCIDENCE = 3;
	public static final String GO_DOWN = "DOWN";
	public static final String GO_RIGTH = "RIGHT";
	public static final String GO_RIGTH_DIAGONAL = "RIGHT_DIAGONAL";
	public static final String GO_LEFT_DIAGONAL = "LEFT_DIAGONAL";

	@Autowired
	IDnaDao dnaDao;

	String[][] matriz;
	Integer count;

	/**
	 * <p>
	 * Metodo que se utiliza para comprobar si una determinada secuencia de ADN
	 * pertenece a un mutante
	 * </p>
	 * 
	 * @param dna es el dto que tiene dentro la secuencia de adn se pensó asi para
	 *            incorporar nuevos elementos en el futuro
	 * @return retorna un booleano que permite saber si es mutante o no
	 * 
	 */
	@Override
	public Boolean isMutant(DnaDTO dna) {
		String[] sequence = dna.getDna();
		String[][] matriz = creoMatriz(sequence);
		Boolean result = deepDna(matriz);
		saveDna(dna, result);
		return result;
	}

	/**
	 * <p>
	 * Metodo que se utiliza para crear una matriz de dos dimensiones para una mejor
	 * manipulacion de los datos
	 * </p>
	 * 
	 * @param dna es un array donde cada elemento es ua parte de la secuencia del
	 *            ADN
	 * @return retorna una matriz de dos direcciones
	 * 
	 */
	private String[][] creoMatriz(String[] dna) {
		Integer N = dna[0].length();
		String[][] dnaMatriz = new String[N][N];
		for (int k = 0; k < N; k++) {
			for (int l = 0; l < N; l++) {
				String row = dna[k];
				String[] list = row.split("");
				dnaMatriz[k][l] = list[l];
			}
		}
		this.matriz = dnaMatriz;
		return dnaMatriz;

	}

	/**
	 * <p>
	 * Metodo que se utiliza para poder escanear de manera completa la matriz desde
	 * aquí se llama al metodo recursivo
	 * </p>
	 * 
	 * @param matriz matriz a recorrer
	 * 
	 * @return retorna un booleano que permite saber si es mutante o no
	 * 
	 */
	private Boolean deepDna(String[][] matriz) {
		Integer mutantDna = 0;
		Integer N = 6;
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
//				this.count = 0;
//				if (metodoRecursivo(matriz, GO_DOWN, x, y) >= COINCIDENCE) {
//					mutantDna++;
//				}
				this.count = 0;
				if (metodoRecursivo(matriz, GO_RIGTH, x, y) >= COINCIDENCE) {
					mutantDna++;
				}
//				this.count = 0;
//				if (metodoRecursivo(matriz, GO_RIGTH_DIAGONAL, x, y) >= COINCIDENCE) {
//					mutantDna++;
//				}
//				this.count = 0;
//				if (metodoRecursivo(matriz, GO_LEFT_DIAGONAL, x, y) >= COINCIDENCE) {
//					mutantDna++;
//				}
			}
		}
		if (mutantDna >= 2)
			return true;
		else
			return false;
	}

	/**
	 * <p>
	 * Metodo que utiliza la recursividad para verificar desde un punto determinado
	 * si hay una secuencia de 4 letras iguales (tres coincidencias)
	 * </p>
	 * 
	 * @param dna matriz de dos dimenciones que va a ser recorrida
	 * @param dna description se utiliza para indicar la direccion en la que será
	 *            recorrida
	 * @param x   posicion donde comienza la recursividad (matriz[x][])
	 * @param x   posicion donde comienza la recursividad (matriz[][y])
	 * 
	 * @return retorna la cantidad de veces que se ejecutó la recursivodad
	 *         (coincidencias)
	 * 
	 */
	private int metodoRecursivo(String[][] matriz, String description, int x, int y) {
		String currentElement = getElement(matriz, x, y);
		if (description.equals("DOWN"))
			x += 1;
		else if (description.equals("RIGHT"))
			y += 1;
		else if (description.equals("RIGHT_DIAGONAL")) {
			x += 1;
			y += 1;
		} else if (description.equals("LEFT_DIAGONAL")) {
			x += 1;
			y -= 1;
		}

		String element = getElement(matriz, x, y);
		if (currentElement.equals(element)) {
			this.count++;
			return metodoRecursivo(matriz, description, x, y);
		}
		return this.count;
	}

	/**
	 * <p>
	 * Metodo que se utiliza para obtener un elemento de una matriz determinada
	 * </p>
	 * 
	 * @param matriz matriz donde se extraerá el elemento
	 * @param x      posoción 1
	 * @param y      posoción 2
	 * 
	 * @return retorna un booleano que permite saber si es mutante o no
	 * 
	 */
	private String getElement(String[][] matriz, int x, int y) {
		String element;
		try {
			element = matriz[x][y];
		} catch (Exception e) {
			element = "";
		}
		return element;
	}

	private void verificoAdn(String i) {
		String[] list = i.split("");
		Arrays.stream(list).forEach(j -> {
			if (!Arrays.stream(DNA_WORDS).anyMatch(j::equals))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Error,  la secuencia de adn tiene una letra invalida:' " + j);
		});
	}

	/**
	 * <p>
	 * Metodo genera las estadisicas a partir de los datos en la DB
	 * </p>
	 * 
	 * @return retorna un dto con las estadisticas
	 * 
	 */
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

	/**
	 * <p>
	 * Metodo que se utiliza para guardar la secuencia de ADN independientemente si
	 * es mutante o no
	 * </p>
	 * 
	 * @param dna      secuencia de ADN
	 * @param isMutant parametro que permite determinar que tipo de ADN es *
	 */
	private void saveDna(DnaDTO dna, Boolean isMutant) {
		List<String> list = Arrays.asList(dna.getDna());
		String joinedString = String.join("", list);

		Dna dnaEntity = new Dna();
		dnaEntity.setSequence(joinedString);
		DnaType dnaType = new DnaType();
		if (isMutant) {
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
	}

}
