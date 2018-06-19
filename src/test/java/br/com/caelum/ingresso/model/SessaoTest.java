package br.com.caelum.ingresso.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Test;

public class SessaoTest {

	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDoPrecoDaSalaMaisOPrecoDoFilme() {
		
		Sala salaZeroUm = new Sala("Sala 01", BigDecimal.TEN);
		Filme oCeuEhDeVerdade = new Filme("O Céu é de Verdade", Duration.ofMinutes(100), "Família", BigDecimal.TEN);
		BigDecimal precoEsperado = oCeuEhDeVerdade.getPreco().add(salaZeroUm.getPreco());
		
		Sessao sessao = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, oCeuEhDeVerdade);
		
		assertEquals(precoEsperado, sessao.getPreco());
		
	}
	
}
