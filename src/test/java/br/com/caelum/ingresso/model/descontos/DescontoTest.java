package br.com.caelum.ingresso.model.descontos;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;

public class DescontoTest {

	private Lugar lugar;
	private Sala salaZeroUm;
	private Filme oCeuEhDeVerdade;
	private Sessao sessao;

	@Before
	public void setup() {
		lugar = new Lugar("A", 1);
		salaZeroUm = new Sala("Sala 01", BigDecimal.TEN);
		oCeuEhDeVerdade = new Filme("O Céu é de Verdade", Duration.ofMinutes(100), "Família", BigDecimal.TEN);
		sessao = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, oCeuEhDeVerdade);
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("20.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescontoDe30PorCentoParaIngressosDeClientesDeBancos() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.BANCO, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("14.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescontoDe50PorCentoParaIngressoDeEstudante() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.ESTUDANTE, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("10.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
}
