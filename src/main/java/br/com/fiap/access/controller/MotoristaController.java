package br.com.fiap.controller;
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

import br.com.fiap.model.Carro;
import br.com.fiap.model.Motorista;
import br.com.fiap.model.Passageiro;
import br.com.fiap.repository.CarroRepository;
import br.com.fiap.repository.MotoristaRepository;
import br.com.fiap.repository.PassageiroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "API sobre Mobilidade", description = "API que permite gerenciar motoristas")
@RequestMapping("api/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaRepository repository;
    @Autowired
    private CarroRepository carroRepository;


    //-------------------------------------------------------------------
    @GetMapping
    @Operation(summary = "Exibir todos os motoristas")
    @ResponseStatus(HttpStatus.OK)
    public List<Motorista> index() {
        return repository.findAll();
    }
    //-------------------------------------------------------------------

    @PostMapping
    @Operation(summary = "Cadastrar um novo motorista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Motorista cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "ParÃ¢metros insuficientes"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<Motorista> create(@RequestBody Motorista motoristaRequest) {
        try {
            if (motoristaRequest.getNome() == null || motoristaRequest.getEmail() == null
                    || motoristaRequest.getCpf() == null 
                    || motoristaRequest.getCnh() == null || motoristaRequest.getSenha() == null) {
                System.out.println("====ERROR=====");
                System.out.println("TODOS OS PARÃ‚METROS SÃƒO OBRIGATÃ“RIOS");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Motorista motorista = new Motorista();
            motorista.setNome(motoristaRequest.getNome());
            motorista.setEmail(motoristaRequest.getEmail());
            motorista.setCpf(motoristaRequest.getCpf());
            motorista.setCnh(motoristaRequest.getCnh());
            motorista.setSenha(motoristaRequest.getSenha());

            
            // Defina o carro associado ao motorista
            if (motoristaRequest.getCarro() != null) {
                Carro carro = carroRepository.save(motoristaRequest.getCarro());
                motorista.setCarro(carro);
            }
            motorista.setCarro(motoristaRequest.getCarro());

            Motorista novoMotorista = repository.save(motorista);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //-------------------------------------------------------------------

    @GetMapping("{id}")
    @Operation(summary = "Exibir motorista por cÃ³digo de identificaÃ§Ã£o id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motorista localizado"),
            @ApiResponse(responseCode = "404", description = "Motorista nÃ£o localizado")
    })
    public ResponseEntity<Motorista> show(@PathVariable("id") long id) {

        Optional<Motorista> motorista = repository.findById(id);

        if (motorista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(motorista.get());
    }
    //-------------------------------------------------------------------
    @PutMapping("{id}")
    @Operation(summary = "Atualiza motorista por cÃ³digo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados sobre o motorista atualizados"),
            @ApiResponse(responseCode = "404", description = "Motorista nÃ£o localizado")
    })
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Motorista motoristaRequest) {
        try {
            Optional<Motorista> optionalMotorista = repository.findById(id);

            if (optionalMotorista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Motorista motorista = optionalMotorista.get();
            motorista.setNome(motoristaRequest.getNome());
            motorista.setEmail(motoristaRequest.getEmail());
            motorista.setCpf(motoristaRequest.getCpf());
            motorista.setCnh(motoristaRequest.getCnh());
            motorista.setSenha(motoristaRequest.getSenha());


            repository.save(motorista);

            return ResponseEntity.ok("Dados sobre o motorista atualizados!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //-------------------------------------------------------------------

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui um motorista pelo cÃ³digo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Motorista excluÃ­do com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Motorista nÃ£o localizado")
    })
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        try {
            Optional<Motorista> optionalMotorista = repository.findById(id);

            if (optionalMotorista.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            repository.delete(optionalMotorista.get());

            return ResponseEntity.ok("Motorista excluÃ­do com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //-------------------------------------------------------------------
}

