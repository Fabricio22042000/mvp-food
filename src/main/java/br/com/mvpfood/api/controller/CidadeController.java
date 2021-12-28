package br.com.mvpfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mvpfood.domain.exception.EntidadeEstaVinculadaException;
import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Cidade;
import br.com.mvpfood.domain.repository.CidadeRepository;
import br.com.mvpfood.domain.services.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId){
		Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
		
		if(cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Cidade cidade){
		try {
			cidade = cadastroCidade.salvar(cidade);
			return ResponseEntity.ok(cidade);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
				@RequestBody Cidade cidade){
		try {
			Optional<Cidade> cidadeBanco = cidadeRepository.findById(cidadeId);
			
			if(cidadeBanco.isPresent()) {
				BeanUtils.copyProperties(cidade, cidadeBanco.get(), "id");
				Cidade cidadeSalva = cadastroCidade.salvar(cidadeBanco.get());
				return ResponseEntity.ok(cidadeSalva);
			}
			return ResponseEntity.notFound().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId){
		try {
			cadastroCidade.excluir(cidadeId);
			return ResponseEntity.noContent().build();
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		}catch (EntidadeEstaVinculadaException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
