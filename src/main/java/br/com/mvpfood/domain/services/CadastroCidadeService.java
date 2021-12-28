package br.com.mvpfood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.mvpfood.domain.exception.EntidadeEstaVinculadaException;
import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Cidade;
import br.com.mvpfood.domain.model.Estado;
import br.com.mvpfood.domain.repository.CidadeRepository;
import br.com.mvpfood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(() ->
			new EntidadeNaoEncontradaException(
				String.format("O estado com o id %d não pode ser encontrado", estadoId)));
		
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("A cidade com o id %d não pode ser encontrada", cidadeId));
			
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEstaVinculadaException(
					String.format("A cidade com o id %d está vinculada a um estado", cidadeId));
		}
	}
}
