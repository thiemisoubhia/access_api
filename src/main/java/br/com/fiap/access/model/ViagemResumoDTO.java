package br.com.fiap.access.model;

public class ViagemResumoDTO {
	
	private String localPartida;
    private String localDestino;
    private String status;
    
    
    public ViagemResumoDTO(String localPartida, String localDestino, String status) {
        this.localPartida = localPartida;
        this.localDestino = localDestino;
        this.status = status;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
