package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingresso {

	@Id @GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Sessao sessao;
	
	@Enumerated(EnumType.STRING)
	private TipoDeIngresso tipoDeIngresso;
	
	@OneToOne
	private Lugar lugar;
	
	private BigDecimal preco;
	
	/**
	 * @deprecated Hibernate only
	 */
	public Ingresso(){}
	
	public Ingresso(Sessao sessao, TipoDeIngresso tipoDeIngresso, Lugar lugar) {
		this.sessao = sessao;
		this.tipoDeIngresso = tipoDeIngresso;
		this.lugar = lugar;
		this.preco = this.tipoDeIngresso.aplicaDesconto(sessao.getPreco());
	}

	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public TipoDeIngresso getTipoDeIngresso() {
		return tipoDeIngresso;
	}
	public void setTipoDeIngresso(TipoDeIngresso tipoDeIngresso) {
		this.tipoDeIngresso = tipoDeIngresso;
	}

	public Lugar getLugar() {
		return lugar;
	}
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public BigDecimal getPreco() {
		if (preco != null) {
    		return preco.setScale(2, RoundingMode.HALF_UP);
    	}
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

}
