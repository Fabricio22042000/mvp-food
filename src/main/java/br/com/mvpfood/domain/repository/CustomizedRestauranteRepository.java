package br.com.mvpfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.mvpfood.domain.model.Restaurante;

public interface CustomizedRestauranteRepository {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	List<Restaurante> buscarComFreteGratis(String nome);
	

}