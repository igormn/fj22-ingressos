package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {
	
	@PersistenceContext
	private EntityManager manager;

	public void save(Sessao sessao) {
		manager.persist(sessao);
	}
	
	public List<Sessao> buscarSessoesDaSala(Sala sala) {
		
		TypedQuery<Sessao> query = manager.createQuery(" select s from Sessao s "
				+ " where s.sala = :pSala", Sessao.class);
		query.setParameter("pSala", sala);
		
		return query.getResultList();
	}

	public List<Sessao> buscarSessoesDoFilme(Filme filme) {

		TypedQuery<Sessao> query = manager.createQuery(" select s from Sessao s "
				+ " where s.filme = :pFilme ", Sessao.class);
		query.setParameter("pFilme", filme);
		
		return query.getResultList();
	}
	
	public Sessao findOne(Integer id) {
		return manager.find(Sessao.class, id);
	}
	
}
