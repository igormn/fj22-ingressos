package br.com.caelum.ingresso.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class SessaoTest {

	private Sala salaZeroUm;
	private Filme oCeuEhDeVerdade;
	private Sessao sessao;
	private Lugar lugarA1;
	private Lugar lugarA2;
	private Lugar lugarA3;

	@Before
	public void setup() {
		salaZeroUm = new Sala("Sala 01", BigDecimal.TEN);
		oCeuEhDeVerdade = new Filme("O Céu é de Verdade", Duration.ofMinutes(100), "Família", BigDecimal.TEN);
		sessao = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, oCeuEhDeVerdade);
		
		lugarA1 = new Lugar("A", 1);
		lugarA2 = new Lugar("A", 2);
		lugarA3 = new Lugar("A", 3);
	}
	
	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDoPrecoDaSalaMaisOPrecoDoFilme() {
		BigDecimal precoEsperado = oCeuEhDeVerdade.getPreco().add(salaZeroUm.getPreco());
		
		assertEquals(precoEsperado, sessao.getPreco());
	}
	
	@Test
	public void garanteQueOLugarA1EstaOcupadoEOsLugaresA2EA3Disponiveis() {
		
		Ingresso ingresso = new Ingresso(sessao, TipoDeIngresso.INTEIRO, lugarA1);
		
		Set<Ingresso> ingressos = Stream.of(ingresso).collect(Collectors.toSet());
		
		sessao.setIngressos(ingressos);
		
		assertFalse(sessao.isDisponivel(lugarA1));
		assertTrue(sessao.isDisponivel(lugarA2));
		assertTrue(sessao.isDisponivel(lugarA3));
		
	}
	
}
