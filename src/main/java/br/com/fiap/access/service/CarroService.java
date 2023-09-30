package br.com.fiap.access.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.access.model.Carro;
import br.com.fiap.access.repository.CarroRepository;


@Service
	public class CarroService {

		@Autowired
		private CarroRepository carroRepository;

		public List<Carro> list() {
			return carroRepository.findAll();
		}
		
		public Carro save(Carro carro) {			
			return carroRepository.save(carro);
		}
	}