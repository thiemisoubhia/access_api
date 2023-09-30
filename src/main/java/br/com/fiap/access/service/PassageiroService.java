package br.com.fiap.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.access.model.Carro;
import br.com.fiap.access.model.Passageiro;
import br.com.fiap.access.repository.CarroRepository;
import br.com.fiap.access.repository.PassageiroRepository;


@Service
	public class PassageiroService {

		@Autowired
		private PassageiroRepository passageiroRepository;

		public List<Passageiro> list() {
			return passageiroRepository.findAll();
		}
		
		public Passageiro save(Passageiro passageiro) {			
			return passageiroRepository.save(passageiro);
		}
	}