package br.com.fiap.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.access.model.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long> {

}
