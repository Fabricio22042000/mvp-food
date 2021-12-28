package br.com.mvpfood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Cozinha;
import br.com.mvpfood.domain.model.Restaurante;
import br.com.mvpfood.domain.repository.CozinhaRepository;
import br.com.mvpfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("A cozinha com o id %d não foi encontrada", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
}
