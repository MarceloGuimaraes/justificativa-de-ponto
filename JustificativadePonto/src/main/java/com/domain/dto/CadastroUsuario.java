package com.domain.dto;

import com.model.PerfilEnum;

import java.io.Serializable;
import java.util.List;

public class CadastroUsuario implements Serializable {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private List<PerfilEnum> perfil;

    public CadastroUsuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<PerfilEnum> getPerfil() {
        return perfil;
    }

    public void setPerfil(List<PerfilEnum> perfil) {
        this.perfil = perfil;
    }
}
