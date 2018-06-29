package br.com.caelum.ingresso.model.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesExistentes;

	public GerenciadorDeSessao(List<Sessao> sessoesExistentes) {
		this.sessoesExistentes = sessoesExistentes;
	}

	public boolean cabe(Sessao sessaoNova) {
		Stream<Sessao> fluxoDeSessoes = sessoesExistentes.stream();
		return fluxoDeSessoes.noneMatch(sessao -> conflita(sessao, sessaoNova));
	}

	private boolean conflita(Sessao sessaoExistente, Sessao sessaoNova) {

		LocalDate hoje = LocalDate.now();
		
		LocalDateTime dataComHorarioInicialDaSessaoExistente = sessaoExistente.getHorario().atDate(hoje);
		LocalDateTime dataComHorarioInicialDaSessaoNova = sessaoNova.getHorario().atDate(hoje);
		
		LocalDateTime dataComHorarioFinallDaSessaoExistente = dataComHorarioInicialDaSessaoExistente.plusMinutes(sessaoExistente.getFilme().getDuracao().toMinutes());
		LocalDateTime dataComHorarioFinalDaSessaoNova = dataComHorarioInicialDaSessaoNova.plusMinutes(sessaoNova.getFilme().getDuracao().toMinutes());
		
		if (dataComHorarioInicialDaSessaoNova.isBefore(dataComHorarioInicialDaSessaoExistente)
				&& dataComHorarioFinalDaSessaoNova.isBefore(dataComHorarioInicialDaSessaoExistente)) {
			return false;
		}
		if (dataComHorarioInicialDaSessaoExistente.isBefore(dataComHorarioInicialDaSessaoNova)
				&& dataComHorarioFinallDaSessaoExistente.isBefore(dataComHorarioInicialDaSessaoNova)) {
			return false;
			
		}
		
		return true;
	}

}
