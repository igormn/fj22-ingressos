package br.com.caelum.ingresso.model.validacao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasQuatorze;
	private Sessao sessaoDasDezessete;
	private Sessao sessaoDasVinteETres;
	private Sessao sessaoDasVinteETresETrinta;
	
	@Before
	public void setup() {
		aLagoaAzul = new Filme("A Lagoa Azul", Duration.ofMinutes(120), "Amor", new BigDecimal("5.00"));
		salaZeroUm = new Sala("Sala 01", new BigDecimal("10.00"));
		
		sessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"), salaZeroUm, aLagoaAzul);
		sessaoDasQuatorze = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, aLagoaAzul);
		sessaoDasDezessete = new Sessao(LocalTime.parse("17:00:00"), salaZeroUm, aLagoaAzul);
		sessaoDasVinteETres = new Sessao(LocalTime.parse("23:00:00"), salaZeroUm, aLagoaAzul);
		sessaoDasVinteETresETrinta = new Sessao(LocalTime.parse("23:30:00"), salaZeroUm, aLagoaAzul);
	}
	
	@Test
	public void naoDevePermitirSessaoNoMesmoHorario() {
		
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoDasQuatorze));
		assertFalse(gerenciadorDeSessao.cabe(sessaoDasQuatorze));
		
	}
	
	@Test
	public void naoDeveCaberQuandoComecaAntesETerminaDurante() {
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoDasQuatorze));

		boolean coube = gerenciadorDeSessao.cabe(sessaoDasTreze);
		
		assertFalse(coube);
	}
	
	@Test
	public void deveCaberQuandoComecaAntesETerminaAntes() {
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoDasDezessete));
		
		boolean coube = gerenciadorDeSessao.cabe(sessaoDasQuatorze);
		
		assertTrue(coube);
	}
	
	@Test
	public void naoDeveCaberQuandoComecaAntesETerminaDuranteNoOutroDia() {
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoDasVinteETresETrinta));
		
		boolean coube = gerenciadorDeSessao.cabe(sessaoDasVinteETres);
		
		assertFalse(coube);
	}


}
