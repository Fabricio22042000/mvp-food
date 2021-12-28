package br.com.mvpfood.infrastructure.repository;

import static br.com.mvpfood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.mvpfood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.mvpfood.domain.model.Restaurante;
import br.com.mvpfood.domain.repository.CustomizedRestauranteRepository;
import br.com.mvpfood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements CustomizedRestauranteRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(nome)) {
			Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");
			predicates.add(nomePredicate);
		}
		
		if(taxaFreteInicial != null) {
			Predicate taxaInicialPredicate = builder
					.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
			predicates.add(taxaInicialPredicate);
		}
		
		if(taxaFreteFinal != null) {
			Predicate taxaFinalPredicate = builder
					.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
			predicates.add(taxaFinalPredicate);
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		
		return query.getResultList();
	}

	@Override
	public List<Restaurante> buscarComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis()
				.and(comNomeSemelhante(nome)));
	}
}
