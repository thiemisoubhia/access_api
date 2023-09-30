package br.com.fiap.access.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.access.model.Carro;
import br.com.fiap.access.model.Passageiro;
import br.com.fiap.access.repository.CarroRepository;
import br.com.fiap.access.repository.PassageiroRepository;

@RestController

@RequestMapping("api/carros")
public class CarroController {

    @Autowired
    private CarroRepository repository;

    //---------------------------------------------------------------------------
    @GetMapping
    
    @ResponseStatus(HttpStatus.OK)
    public List<Carro> index() {
        return repository.findAll();
    }
    //---------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Carro carroRequest) {
        try {
            if (carroRequest.getPlaca() == null || carroRequest.getAno() == 0) {
                System.out.println("====ERROR=====");
                System.out.println("A PLACA E O ANO DO CARRO SÃO OBRIGATÓRIOS");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Carro carro = new Carro();
            carro.setPlaca(carroRequest.getPlaca());
            carro.setAno(carroRequest.getAno());
            carro.setQuantLugares(carroRequest.getQuantLugares());
            carro.setEstiloCarro(carroRequest.getEstiloCarro());

            repository.save(carro);

            return ResponseEntity.ok("Carro cadastrado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------

    
    public ResponseEntity<Carro> show(@PathVariable("id") long id) {

        Optional<Carro> carro = repository.findById(id);

        if (carro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(carro.get());
    }
    //---------------------------------------------------------------------------

    
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Carro carroRequest) {
        try {
            if (repository.findById(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Carro carro = new Carro();
            carro.setId(id);
            carro.setPlaca(carroRequest.getPlaca());
            carro.setAno(carroRequest.getAno());
            carro.setQuantLugares(carroRequest.getQuantLugares());
            carro.setEstiloCarro(carroRequest.getEstiloCarro());

            repository.save(carro);

            return ResponseEntity.ok("Carro atualizado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------


    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        try {
            if (repository.findById(id).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            repository.deleteById(id);

            return ResponseEntity.ok("Carro excluído com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------
}
