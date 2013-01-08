package com.domain.dto;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

public class UsuarioLogin implements Serializable {

    protected static final int HASH1 = 1156070685;
    protected static final int HASH2 = 119119943;

    private String nome;
    private String email;
    private String senha;

    public UsuarioLogin() {
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

    public boolean equals(Object o){

        if(!(o instanceof UsuarioLogin)){
            return false;
        }

        UsuarioLogin outro = (UsuarioLogin) o;

        return new EqualsBuilder()
                .append(nome, outro.getNome())
                .append(email, outro.getEmail())
                .isEquals();
    }

    public int hashCode(){
        return new HashCodeBuilder(HASH1, HASH2)
                .append(nome)
                .append(email)
                .toHashCode();
    }

}
