package br.com.mvpfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mvpfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mvpfood.domain.model.Restaurante;
import br.com.mvpfood.domain.repository.RestauranteRepository;
import br.com.mvpfood.domain.services.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId){
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if(restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
				@RequestBody Restaurante restaurante){
		try {
			Optional<Restaurante> restauranteBanco = restauranteRepository.findById(restauranteId);
			
			if(restauranteBanco.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteBanco.get(), 
						"id", "formasPagamento", "endereco", "dataCriacao", "produtos");
				
				Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteBanco.get());
				return ResponseEntity.ok(restauranteSalvo);
			}
			return ResponseEntity.notFound().build();
			
		}catch(EntidadeNaoEncontradaException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
				@RequestBody Map<String, Object> campos){
		Optional<Restaurante> restauranteBanco = restauranteRepository.findById(restauranteId);
		
		if(restauranteBanco.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteBanco.get());
		
		return atualizar(restauranteId, restauranteBanco.get());
	}

	private void merge(Map<String, Object> campos, Restaurante restauranteBanco) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restaurante = objectMapper.convertValue(campos, Restaurante.class);
		
		campos.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object valor = ReflectionUtils.getField(field, restaurante);
			
			
			ReflectionUtils.setField(field, restauranteBanco, valor);
		});
	}
	
	
}
