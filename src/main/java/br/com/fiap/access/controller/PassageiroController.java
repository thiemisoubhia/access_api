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
import br.com.fiap.access.model.Passageiro;
import br.com.fiap.access.repository.PassageiroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "API sobre Mobilidade", description = "Api que permite solicitar um táxi para PCD's através de uma API Java")
@RequestMapping("api/passageiros")
public class PassageiroController {

    @Autowired
    private PassageiroRepository repository;

    //---------------------------------------------------------------------------
    @GetMapping
    @Operation(summary = "Exibir todos os passageiros")
    @ResponseStatus(HttpStatus.OK)
    public List<Passageiro> index() {
        return repository.findAll();
    }
    //---------------------------------------------------------------------------

    @PostMapping
    @Operation(summary = "Cadastrar um novo passageiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passageiro cadastrado com sucesso :)"),
            @ApiResponse(responseCode = "400", description = "Parâmetros insuficientes"),
            @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    public ResponseEntity<Passageiro> create(@RequestBody Passageiro passageiroRequest) {
        try {
            if (passageiroRequest.getNome() == null || passageiroRequest.getEmail() == null 
                    || passageiroRequest.getCpf() == null 
                    || passageiroRequest.getTipoDeficiencia() == null|| passageiroRequest.getSenha() == null) {
                System.out.println("====ERROR=====");
                System.out.println("TODOS OS PARÂMETROS SÃO OBRIGATÓRIOS");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Passageiro passageiro = new Passageiro();
            passageiro.setNome(passageiroRequest.getNome());
            passageiro.setEmail(passageiroRequest.getEmail());
            passageiro.setCpf(passageiroRequest.getCpf());
            passageiro.setTipoDeficiencia(passageiroRequest.getTipoDeficiencia());
            passageiro.setSenha(passageiroRequest.getSenha());

            Passageiro novoPassageiro = repository.save(passageiro);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoPassageiro);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------

    @GetMapping("{id}")
    @Operation(summary = "Exibir passageiro por código de identificação id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passageiro localizado"),
            @ApiResponse(responseCode = "404", description = "Passageiro não localizado")
    })
    public ResponseEntity<Passageiro> show(@PathVariable("id") long id) {

        Optional<Passageiro> passageiro = repository.findById(id);

        if (passageiro.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(passageiro.get());
    }
    //---------------------------------------------------------------------------
    
    @PutMapping("{id}")
    @Operation(summary = "Atualiza passageiro por código id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados sobre o passageiro atualizados!"),
            @ApiResponse(responseCode = "404", description = "Passageiro não localizado")
    })
    public ResponseEntity<String> update(@PathVariable("id") long id, @RequestBody Passageiro passageiroRequest) {
        try {
            Optional<Passageiro> optionalPassageiro = repository.findById(id);

            if (optionalPassageiro.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Passageiro passageiro = optionalPassageiro.get();
            passageiro.setNome(passageiroRequest.getNome());
            passageiro.setEmail(passageiroRequest.getEmail());
            passageiro.setCpf(passageiroRequest.getCpf());
            passageiro.setTipoDeficiencia(passageiroRequest.getTipoDeficiencia());
            passageiro.setSenha(passageiroRequest.getSenha());


            repository.save(passageiro);

            return ResponseEntity.ok("Dados sobre o passageiro atualizados!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------

    @DeleteMapping("{id}")
    @Operation(summary = "Exclui um passageiro pelo código id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Passageiro excluído com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Passageiro não localizado")
    })
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        try {
            Optional<Passageiro> optionalPassageiro = repository.findById(id);

            if (optionalPassageiro.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            repository.delete(optionalPassageiro.get());

            return ResponseEntity.ok("Passageiro excluído com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //---------------------------------------------------------------------------
}