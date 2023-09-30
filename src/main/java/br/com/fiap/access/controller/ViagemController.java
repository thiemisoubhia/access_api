package br.com.fiap.access.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.access.repository.ViagemRepository;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.fiap.access.model.Carro;
import br.com.fiap.access.model.EstiloCarro;
import br.com.fiap.access.model.Motorista;
import br.com.fiap.access.model.Passageiro;
import br.com.fiap.access.model.TipoDeficiencia;
import br.com.fiap.access.model.Viagem;
import br.com.fiap.access.model.ViagemInfoDTO;
import br.com.fiap.access.model.ViagemResumoDTO;
import br.com.fiap.access.repository.MotoristaRepository;
import br.com.fiap.access.repository.PassageiroRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/viagens")
public class ViagemController {

    @Autowired
    private br.com.fiap.access.repository.ViagemRepository viagemRepository;
    
    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private MotoristaRepository motoristaRepository;


    @Autowired
    public ViagemController(ViagemRepository viagemRepository, MotoristaRepository motoristaRepository) {
        this.viagemRepository = viagemRepository;
        this.motoristaRepository = motoristaRepository;
    }

 
    @GetMapping("/pendentes")
    public List<Viagem> listarViagensPendentes() {
        List<Viagem> viagensPendentes = viagemRepository.findByStatus("pendente");
        return viagensPendentes;
    }
    
    @GetMapping("/aceitas")
    public List<Viagem> listarViagensAceitas() {
        List<Viagem> viagensAceitas = viagemRepository.findByStatus("aceita");
        return viagensAceitas;
    }
      
   
    @GetMapping("/pendentes-info")
    public List<ViagemInfoDTO> listarViagensPendentesInfo() {
        List<Viagem> viagensPendentes = viagemRepository.findByStatus("pendente");
        List<ViagemInfoDTO> viagensInfo = new ArrayList<>();

        for (Viagem viagem : viagensPendentes) {
            ViagemInfoDTO viagemInfo = new ViagemInfoDTO();
            viagemInfo.setNomePassageiro(viagem.getPassageiro().getNome());
            viagemInfo.setTipoDeficiencia(viagem.getPassageiro().getTipoDeficiencia());
            viagemInfo.setLocalPartida(viagem.getLocalPartida());
            viagemInfo.setLocalDestino(viagem.getLocalDestino());
            viagensInfo.add(viagemInfo);
        }

        return viagensInfo;
    }
    

    @PostMapping("/solicitar")
    public ResponseEntity<String> criarSolicitacaoViagem(@RequestBody Viagem viagemRequest) {
        // Valide se o CPF do passageiro está cadastrado no sistema
        Optional<Passageiro> passageiroOptional = passageiroRepository.findByCpf(viagemRequest.getPassageiro().getCpf());

        if (passageiroOptional.isPresent()) {
            Passageiro passageiro = passageiroOptional.get();

            // Verifique se os dados necessários estão presentes
            if (viagemRequest.getLocalPartida() != null && viagemRequest.getLocalDestino() != null) {
                // Crie uma nova viagem com os dados recebidos e o status como "pendente"
                Viagem viagem = new Viagem();
                viagem.setPassageiro(passageiro);
                viagem.setLocalPartida(viagemRequest.getLocalPartida());
                viagem.setLocalDestino(viagemRequest.getLocalDestino());
                viagem.setStatus("pendente");
                
                viagemRepository.save(viagem);

                return ResponseEntity.status(HttpStatus.CREATED).body("Solicitação de viagem efetuada com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados insuficientes para criar a solicitação de viagem.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF do passageiro não encontrado.");
        }
    }


    // Método para excluir uma viagem por ID
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletarSolicitacaoViagem(@PathVariable Long id) {
        Optional<Viagem> viagemOptional = viagemRepository.findById(id);

        if (viagemOptional.isPresent()) {
            Viagem viagem = viagemOptional.get();
            // Realize qualquer validação adicional necessária antes da exclusão

            // Exclua a viagem
            viagemRepository.delete(viagem);

            return ResponseEntity.status(HttpStatus.OK).body("Solicitação de viagem excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitação de viagem não encontrada.");
        }
    }
    
    
    // Método para atualizar uma viagem com base no ID
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarViagem(@PathVariable Long id, @RequestBody Viagem viagemAtualizada) {
        // Verifique se a viagem com o ID fornecido existe no banco de dados
        if (!viagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Atualize os campos da viagem com os dados fornecidos
        Viagem viagemExistente = viagemRepository.findById(id).orElse(null);
        if (viagemExistente != null) {
            // Atualize os campos desejados da viagem existente com os dados da viagem atualizada
            viagemExistente.setLocalPartida(viagemAtualizada.getLocalPartida());
            viagemExistente.setLocalDestino(viagemAtualizada.getLocalDestino());
            viagemExistente.setStatus(viagemAtualizada.getStatus());

            // Salve a viagem atualizada no banco de dados
            viagemRepository.save(viagemExistente);

            // Retorne uma resposta de sucesso
            return ResponseEntity.ok("Viagem atualizada com sucesso");
        } else {
            // Se a viagem não puder ser encontrada, retorne um erro interno do servidor
            return ResponseEntity.status(500).body("Erro interno do servidor");
        }
    }
    
    //Aceitar solicitação de corrida
    
    @PostMapping("/aceitar/{id}")
    public ResponseEntity<String> aceitarViagem(
            @PathVariable Long id,
            @RequestParam("cnh") String cnh) {
        // Verificar se a viagem com o ID fornecido existe e está pendente
        Optional<Viagem> viagemOptional = viagemRepository.findById(id);

        if (viagemOptional.isPresent() && viagemOptional.get().getStatus().equals("pendente")) {
            Viagem viagem = viagemOptional.get();

            // Lógica para validar se a CNH está cadastrada no sistema
            Optional<Motorista> motoristaOptional = motoristaRepository.findByCnh(cnh);

            if (motoristaOptional.isPresent()) {
                Motorista motorista = motoristaOptional.get();

                // Lógica para determinar o estilo de carro necessário com base no tipo de deficiência do passageiro
                EstiloCarro estiloCarroNecessario = determinarEstiloCarroParaDeficiencia(viagem.getPassageiro().getTipoDeficiencia());

                // Obter o estilo de carro do motorista com base na CNH
                EstiloCarro estiloCarroMotorista = motorista.getCarro().getEstiloCarro();

                // Verificar se o estilo de carro do motorista é compatível com o estilo de carro necessário
                if (estiloCarroMotorista == estiloCarroNecessario) {
                    // Atualizar o status da viagem para "aceita" e vincular o motorista à viagem
                    viagem.setStatus("aceita");
                    viagem.setMotorista(motorista);
                    viagemRepository.save(viagem);

                    return ResponseEntity.status(HttpStatus.OK).body("Viagem aceita. Motorista a caminho.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Motorista não é adequado para a viagem.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNH não cadastrada no sistema.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viagem não encontrada ou não está pendente.");
        }
    }

    
  
    // Método para calcular o EstiloCarro requerido com base no TipoDeficiencia
    private EstiloCarro determinarEstiloCarroParaDeficiencia(TipoDeficiencia tipoDeficiencia) {
        switch (tipoDeficiencia) {
            case DEFICIENCIA_MENTAL:
            case DEFICIENCIA_INTELECTUAL:
                return EstiloCarro.SILENCIOSO;
            case DEFICIENCIA_MOTORA:
                return EstiloCarro.MAIS_ESPACOSO_COM_RAMPAS_E_APOIOS;
            case DEFICIENCIA_VISUAL:
            case DEFICIENCIA_AUDITIVA:
                return EstiloCarro.SEM_RESTRICOES;
            default:
                return null; 
        }
    }



}
