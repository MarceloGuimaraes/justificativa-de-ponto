package com.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Identificacao implements Serializable {

    private static final int HASH_1 = 1156070685;
    private static final int HASH_2 = 119119943;

    @Column(name = "id_user")
    private Integer id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "email", length = 50)
    private String email;

    public Identificacao() {
    }

    public Identificacao(Integer id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_1, HASH_2)
                .append(this.nome)
                .append(this.cpf)
                .append(this.email)
                .toHashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Identificacao)) {
            return false;
        }

        Identificacao usuario = (Identificacao) object;

        boolean resultado = new EqualsBuilder()
                .append(nome, usuario.nome)
                .append(cpf, usuario.cpf)
                .append(email, usuario.email).isEquals();

        return resultado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
