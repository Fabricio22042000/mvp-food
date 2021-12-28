package br.com.mvpfood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mvpfood.domain.model.Cozinha;
import br.com.mvpfood.domain.model.Restaurante;
import br.com.mvpfood.domain.repository.CozinhaRepository;
import br.com.mvpfood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository; 
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> buscarPorNome(String nome){
		return cozinhaRepository.findTodasByNome(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public List<Cozinha> buscarUnicaPorNome(String nome){
		return cozinhaRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> buscarRestaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome-e-cozinha")
	public List<Restaurante> buscarRestaurantePorNomeECozinha(String nome, Long cozinhaId){
		return restauranteRepository.consultarPorNomeECozinha(nome, cozinhaId);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> buscarPrimeiroPorNome(String nome){
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> buscarTop2PorNome(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/exists")
	public boolean existsPorNome(String nome){
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes/conte-por-cozinha")
	public int countByCozinhaId(Long cozinhaId){
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/por-nome-taxaFrete")
	public List<Restaurante> buscarRestaurantePorNomeETaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> buscarRestauranteComFreteGratis(String nome){
		
		return restauranteRepository.buscarComFreteGratis(nome);
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> buscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeira")
	public Optional<Cozinha> buscarPrimeira() {
		return cozinhaRepository.buscarPrimeiro();
	}
}
