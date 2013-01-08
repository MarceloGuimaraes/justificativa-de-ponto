package com.domain.dto;

import com.model.PerfilEnum;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class UsuarioLogado extends UsuarioLogin implements Serializable {

    private Integer id;
    private String cpf;
    private List<PerfilEnum> perfil;

    public UsuarioLogado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean equals(Object o){

        if(!(o instanceof UsuarioLogado)){
            return false;
        }

        UsuarioLogado outro = (UsuarioLogado)o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(cpf, outro.getCpf())
                .isEquals();

    }

    public int hashCode(){

        return new HashCodeBuilder(HASH1, HASH2)
                .appendSuper(super.hashCode())
                .append(cpf)
                .toHashCode();

    }

}
