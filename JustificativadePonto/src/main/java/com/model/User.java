package com.model;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -8772516708049621911L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int userId;

	@Column(name = "cpf", unique = true, nullable = false, length = 14)
	private String cpf;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "email", unique = true, length = 50)
	private String email;

	@Column(name = "senha", length = 50)
	private String senha;
	
	@ElementCollection(targetClass = PerfilEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Perfis", joinColumns = {@JoinColumn(name="id_user")})
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.PerfilEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
	private List<PerfilEnum> perfil;
	
	@Column(name = "ativo")
	private boolean ativo = true;
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<PerfilEnum> getPerfil() {
		return perfil;
	}

	public void setPerfil(List<PerfilEnum> perfil) {
		this.perfil = perfil;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
