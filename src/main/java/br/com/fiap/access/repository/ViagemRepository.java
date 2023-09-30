package br.com.fiap.access.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.access.model.Passageiro;
import br.com.fiap.access.model.Viagem;
import br.com.fiap.access.model.ViagemInfoDTO;

import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagem, Long> {

    List<Viagem> findByStatus(String status);
    List<Viagem> findByPassageiro(Passageiro passageiro);
}


