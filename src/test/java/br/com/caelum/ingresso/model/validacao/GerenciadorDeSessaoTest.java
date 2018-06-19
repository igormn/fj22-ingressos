package br.com.caelum.ingresso.model.validacao;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
	
	
	
	private Filme aLagoaAzul;
	private Sala salaZeroUm;
	private Sessao sessaoExistente;

	@Before
	public void setup() {
		
		aLagoaAzul = new Filme("A Lagoa Azul", Duration.ofMinutes(120), "Amor");
		salaZeroUm = new Sala("Sala 01");
		sessaoExistente = new Sessao(LocalTime.parse("14:00:00"), aLagoaAzul, salaZeroUm);
		
		
	}
	
	@Test
	public void deveNaoCaberQuandoComecaAntesETerminaAntes() {
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoExistente));
		Sessao sessaoNova = new Sessao(LocalTime.parse("11:00:00"), aLagoaAzul, salaZeroUm);
		
		boolean coube = gerenciadorDeSessao.cabe(sessaoNova);
		
		assertTrue(coube);
		
	}
	
	@Test
	public void naoDeveCaberQuandoComecaAntesETerminaDurante() {

		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoExistente));
		Sessao sessao = new Sessao(LocalTime.parse("13:00:00"), aLagoaAzul, salaZeroUm);

		boolean coube = gerenciadorDeSessao.cabe(sessao);
		
		assertFalse(coube);
	}

}
