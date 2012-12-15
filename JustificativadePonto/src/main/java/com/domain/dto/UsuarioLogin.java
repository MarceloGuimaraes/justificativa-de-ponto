package com.domain.dto;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;

public class UsuarioLogin implements Serializable {
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
}
