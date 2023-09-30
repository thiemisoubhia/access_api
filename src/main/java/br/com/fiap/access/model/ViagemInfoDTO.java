package br.com.fiap.access.model;

public class ViagemInfoDTO {
	
	private String nomePassageiro;
    private TipoDeficiencia tipoDeficiencia;
    private String localPartida;
    private String localDestino;
    
    
    
    
	public String getNomePassageiro() {
		return nomePassageiro;
	}
	public void setNomePassageiro(String nomePassageiro) {
		this.nomePassageiro = nomePassageiro;
	}
	public TipoDeficiencia getTipoDeficiencia() {
		return tipoDeficiencia;
	}
	public void setTipoDeficiencia(TipoDeficiencia tipoDeficiencia) {
		this.tipoDeficiencia = tipoDeficiencia;
	}
	public String getLocalPartida() {
		return localPartida;
	}
	public void setLocalPartida(String localPartida) {
		this.localPartida = localPartida;
	}
	public String getLocalDestino() {
		return localDestino;
	}
	public void setLocalDestino(String localDestino) {
		this.localDestino = localDestino;
	}
    
    

}
