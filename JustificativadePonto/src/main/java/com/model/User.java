package com.model;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
	@CollectionTable(name = "Perfis", joinColumns = { @JoinColumn(name = "id_user") })
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@org.hibernate.annotations.Parameter(name = "enumClass", value = "com.model.PerfilEnum"),
			@org.hibernate.annotations.Parameter(name = "identifierMethod", value = "getCodigo"),
			@org.hibernate.annotations.Parameter(name = "valueOfMethod", value = "fromSigla") })
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
	
	private static final int hash1 = 1156070685;
	private static final int hash2 = 119119943;
	

	@Override
	public int hashCode() {
		return new HashCodeBuilder(hash1,hash2 )
				.appendSuper(super.hashCode()).append(this.nome)
				.append(this.cpf).append(this.email).toHashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof User)) {
			return false;
		}

		User usuario = (User) object;
/*		System.out.println("equals 4");
		System.out.println("this.nome" + this.nome);
		System.out.println("usuario.nome" + usuario.nome);
		System.out.println("this.cpf" + this.cpf);
		System.out.println("usuario.cpf" + usuario.cpf);
		System.out.println("this.email" + this.email);
		System.out.println("usuario.email" + usuario.email); */
		
		return new EqualsBuilder().appendSuper(super.equals(object))
				.append(this.nome, usuario.nome).append(this.cpf, usuario.cpf)
				.append(this.email, usuario.email).isEquals();
	}

}
