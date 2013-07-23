package com.domain.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.model.PerfilEnum;

public class CadastroUsuario implements Serializable {
    private Integer id;
    private String nome;
    private String email;
    private String cpf;
	private Date hrInicial;
	private Date hrFinal;
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

	public List<PerfilEnum> getPerfil() {
        return perfil;
    }

    public void setPerfil(List<PerfilEnum> perfil) {
        this.perfil = perfil;
    }
}
