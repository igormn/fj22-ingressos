package br.com.caelum.ingresso.model.validacao;

import java.util.List;
import java.util.stream.Stream;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesExistentes;

	public GerenciadorDeSessao(List<Sessao> sessoesExistentes) {
		this.sessoesExistentes = sessoesExistentes;
	}
	
	

	public boolean cabe(Sessao sessaoNova) {
		
		//Função Anônima
		
		Stream<Sessao> fluxoDeSessoes = sessoesExistentes.stream();
		return fluxoDeSessoes.noneMatch(sessao -> conflita(sessao, sessaoNova));

	}

	private boolean conflita(Sessao sessaoExistente, Sessao sessaoNova) {

		if (sessaoNova.getHorario().isBefore(sessaoExistente.getHorario())
				&& sessaoNova.getHorarioTermino().isBefore(sessaoExistente.getHorario())) {
			return false;
		}
		if (sessaoExistente.getHorario().isBefore(sessaoNova.getHorario())
				&& sessaoExistente.getHorarioTermino().isBefore(sessaoNova.getHorario())) {
			return false;
			
		}
		return true;
	}

}
