package br.com.caelum.ingresso.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Compra {

	@Id @GeneratedValue
	private Integer id;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	List<Ingresso> ingressos = new ArrayList<>();
	
	/**
	 * @deprecated Hibernate only
	 */
	public Compra() {}
	
	public Compra(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Ingresso> getIngressos() {
		return ingressos;
	}

	public void setIngressos(List<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
}
