package com.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -8772516708049621911L;
    private static final int hash1 = 1156070685;
    private static final int hash2 = 119119943;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int id;

	@Column(name = "cpf", unique = true, nullable = false, length = 14)
	private String cpf;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "email", unique = true, length = 50)
	private String email;

	@Column(name = "senha", length = 200)
	private String senha;

    /*@CollectionOfElements(targetElement = PerfilEnum.class)
	@JoinTable(name = "Perfis", joinColumns = { @JoinColumn(name = "id_user") })*/
    @ElementCollection(targetClass = PerfilEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Perfis", joinColumns = { @JoinColumn(name = "id_user") })
    @Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
            @org.hibernate.annotations.Parameter(name = "enumClass", value = "com.model.PerfilEnum"),
            @org.hibernate.annotations.Parameter(name = "identifierMethod", value = "getCodigo"),
            @org.hibernate.annotations.Parameter(name = "valueOfMethod", value = "fromSigla") })
    @Column(name = "perfil")
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder(hash1,hash2 )
                .append(this.nome)
				.append(this.cpf)
                .append(this.email)
                .toHashCode();
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof User)) {
			return false;
		}

		User usuario = (User) object;

        boolean resultado = new EqualsBuilder()
                .append(nome, usuario.nome)
                .append(cpf, usuario.cpf)
                .append(email, usuario.email).isEquals();


		return resultado;
	}

}
