package com.domain.dto;

import java.io.Serializable;

public class CadastroSenha implements Serializable {

    private Integer id;
    private String senha;
    private String novaSenha;
    private String confirmaNovaSenha;

    public CadastroSenha() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmaNovaSenha() {
        return confirmaNovaSenha;
    }

    public void setConfirmaNovaSenha(String confirmaNovaSenha) {
        this.confirmaNovaSenha = confirmaNovaSenha;
    }

    public boolean isSenhaInformada(){
        return senha!=null || !senha.equals("");
    }

    public boolean isNovaSenhaInformada(){
        return novaSenha!=null || !novaSenha.equals("");
    }

    public boolean isConfirmaNovaSenhaInformada(){
        return confirmaNovaSenha!=null || !confirmaNovaSenha.equals("");
    }

    public boolean isSenhaConfirmada(){
        return novaSenha.equals(confirmaNovaSenha);
    }

}
