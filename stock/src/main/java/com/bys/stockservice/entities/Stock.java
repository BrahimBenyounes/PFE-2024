package com.bys.stockservice.entities;


import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idStock;
	private String libelleStock;
	private Integer qte;
	private Integer qteMin;

	public Stock(String libelleStock, Integer qte, Integer qteMin) {
		super();
		this.libelleStock = libelleStock;
		this.qte = qte;
		this.qteMin = qteMin;
	}

}
