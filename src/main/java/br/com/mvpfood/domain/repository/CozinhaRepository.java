package br.com.mvpfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.mvpfood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	List<Cozinha> findTodasByNome(String nome);
	Optional<Cozinha> findByNome(String nome);
	List<Cozinha> findByNomeContaining(String nome);
	
	boolean existsByNome(String nome);
}
