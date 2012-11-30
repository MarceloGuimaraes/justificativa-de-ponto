package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

	private static final long serialVersionUID = -4170943271401686415L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "perfilId")
	private int perfilId;

	@Column(name = "tipo", unique = true, nullable = false, length = 20)
	private String tipo;

	public int getPerfilId() {
		return perfilId;
	}

	public void setPerfilId(int perfilId) {
		this.perfilId = perfilId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Perfil [id=").append(perfilId).append(", tipo=")
				.append(tipo).append("]");
		return builder.toString();
	}

}
