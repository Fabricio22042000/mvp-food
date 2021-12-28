package br.com.mvpfood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.mvpfood.domain.exception.EntidadeEstaVinculadaException;
import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Cozinha;
import br.com.mvpfood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			
		}catch(EmptyResultDataAccessException e){
			throw new EntidadeNaoEncontradaException(
					String.format("Entidade com o id %d não pode ser encontrada", cozinhaId));
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEstaVinculadaException(
					String.format("Entidade com o id %d não pode ser excluida, pois está vinculada com outra", cozinhaId));
		}
		
	}
}
