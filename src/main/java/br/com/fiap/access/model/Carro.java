package br.com.fiap.access.model;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;



@Entity
@Table(name="TB_CARRO")
public class Carro {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String placa;
    private int ano;
    private int quantLugares;

    @Enumerated
    private EstiloCarro estiloCarro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getQuantLugares() {
		return quantLugares;
	}

	public void setQuantLugares(int quantLugares) {
		this.quantLugares = quantLugares;
	}

	public EstiloCarro getEstiloCarro() {
		return estiloCarro;
	}

	public void setEstiloCarro(EstiloCarro estiloCarro) {
		this.estiloCarro = estiloCarro;
	}
	
    
}
