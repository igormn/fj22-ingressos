package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class DescontoTest {

	private Sala salaZeroUm;
	private Filme oCeuEhDeVerdade;
	private Sessao sessao;

	@Before
	public void setup() {
		salaZeroUm = new Sala("Sala 01", BigDecimal.TEN);
		oCeuEhDeVerdade = new Filme("O Céu é de Verdade", Duration.ofMinutes(100), "Família", BigDecimal.TEN);
		sessao = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, oCeuEhDeVerdade);
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {
		
		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());
		
		BigDecimal precoEsperado = new BigDecimal("20.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescontoDe30PorCentoParaIngressosDeClientesDeBancos() {
		
		Ingresso ingresso = new Ingresso(sessao, new DescontoParaBancos());
		
		BigDecimal precoEsperado = new BigDecimal("14.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescontoDe50PorCentoParaIngressoDeEstudante() {
		
		Ingresso ingresso = new Ingresso(sessao, new DescontoParaEstudande());
		
		BigDecimal precoEsperado = new BigDecimal("10.00");
		
		assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
}
