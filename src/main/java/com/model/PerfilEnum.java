package com.model;

import com.util.IEnumeracaoComCodigoDescricao;

import java.io.Serializable;

public enum PerfilEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    ADMINISTRADOR(1, "perfil.descricao.administrador"),
    CADASTRADOR(2, "perfil.descricao.cadastrador"),
    COORDENADOR(3, "perfil.descricao.coordenador"),
    RH(4, "perfil.descricao.rh"),
    SUPERINTENDENTE(5, "perfil.descricao.superintendente"),
    SUPORTE(6, "perfil.descricao.suporte"),
    USUARIO(7, "perfil.descricao.usuario");

    private Integer codigo;

    private String descricao;

    private PerfilEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public static PerfilEnum fromSigla(Integer codigo){
        for(PerfilEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}
