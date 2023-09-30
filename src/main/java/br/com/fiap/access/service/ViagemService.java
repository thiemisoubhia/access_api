package br.com.fiap.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.access.model.Carro;
import br.com.fiap.access.model.Viagem;
import br.com.fiap.access.repository.CarroRepository;
import br.com.fiap.access.repository.ViagemRepository;


@Service
	public class ViagemService {

		@Autowired
		private ViagemRepository viagemRepository;

		public List<Viagem> list() {
			return viagemRepository.findAll();
		}
		
		public Viagem save(Viagem viagem) {			
			return viagemRepository.save(viagem);
		}
	}