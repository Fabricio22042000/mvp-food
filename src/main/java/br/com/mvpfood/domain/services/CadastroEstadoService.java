package br.com.mvpfood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.mvpfood.domain.exception.EntidadeEstaVinculadaException;
import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Estado;
import br.com.mvpfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("O estado com o id %d não foi encontrado", estadoId));
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEstaVinculadaException(
					String.format("O estado com o id %d está vinculada a uma cidade", estadoId));
			
		}
	}
	
}
