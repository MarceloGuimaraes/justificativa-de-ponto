package com.model;

import com.domain.SescoopConstants;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = -8772516708049621911L;

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

	@Column(name = "hrInicial")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrInicial;

	@Column(name = "hrFinal")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrFinal;
	
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

	public Date getHrInicial() {
		return hrInicial;
	}

	public void setHrInicial(Date hrInicial) {
		this.hrInicial = hrInicial;
	}

	public Date getHrFinal() {
		return hrFinal;
	}

	public void setHrFinal(Date hrFinal) {
		this.hrFinal = hrFinal;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(SescoopConstants.HASH1, SescoopConstants.HASH2)
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
