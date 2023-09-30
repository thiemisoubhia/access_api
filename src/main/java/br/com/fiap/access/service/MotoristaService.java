package br.com.fiap.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.access.model.Motorista;
import br.com.fiap.access.repository.MotoristaRepository;

@Service
	public class MotoristaService {

		@Autowired
		private MotoristaRepository motoristaRepository;

		public List<Motorista> list() {
			return motoristaRepository.findAll();
		}
		
		public Motorista save(Motorista motorista) {			
			return motoristaRepository.save(motorista);
		}
	}