package br.com.caelum.ingresso.main;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTeste {
	
public static void main(String[] args) {
		
		Filme aLagoaAzul = new Filme("A Lagoa Azul", Duration.ofMinutes(120), "Amor", new BigDecimal("5.00"));
		Sala salaZeroUm = new Sala("Sala 01", new BigDecimal("10.00"));
		Sessao sessaoExistente = new Sessao(LocalTime.parse("23:30:00"), salaZeroUm, aLagoaAzul);
		
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoExistente));
		Sessao sessaoNova = new Sessao(LocalTime.parse("23:00:00"), salaZeroUm, aLagoaAzul);
		
		boolean coube1 = gerenciadorDeSessao.cabe(sessaoNova);
		
		System.out.println("NaoDeveCaberQuandoComecaAntesETerminaDuranteNoOutroDia: " + coube1);
		
		
		
		sessaoExistente = new Sessao(LocalTime.parse("14:00:00"), salaZeroUm, aLagoaAzul);
		
		
		
		gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoExistente));
		sessaoNova = new Sessao(LocalTime.parse("11:00:00"), salaZeroUm, aLagoaAzul);
		
		boolean coube2 = gerenciadorDeSessao.cabe(sessaoNova);
		
		System.out.println("DeveCaberQuandoComecaAntesETerminaAntes: " + coube2);
		
		
		
		gerenciadorDeSessao = new GerenciadorDeSessao(Arrays.asList(sessaoExistente));
		sessaoNova = new Sessao(LocalTime.parse("13:00:00"), salaZeroUm, aLagoaAzul);

		boolean coube3 = gerenciadorDeSessao.cabe(sessaoNova);
		
		System.out.println("NaoDeveCaberQuandoComecaAntesETerminaDurante: " + coube3);
		
	}

}
