package com.model;

import com.util.IEnumeracaoComCodigoDescricao;

import java.io.Serializable;

public enum TipoEventoJustificativaPontoEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    REGISTRO_CRIADO(1, "tipoeventojustificativaponto.descricao.registrocriado"),
    ENVIADO_APROVACAO_COORDENADOR(2, "tipoeventojustificativaponto.descricao.enviadaaprovacaocoordenador"),
    APROVADO_COORDENADOR(2, "tipoeventojustificativaponto.descricao.aprovadocoordenador"),
    REPROVADO_COORDENADOR(2, "tipoeventojustificativaponto.descricao.reprovadocoordenador"),
    ENVIADO_APROVACAO_SUPERINTENDENTE(3, "tipoeventojustificativaponto.descricao.enviadaaprovacaosuperintendente"),
    APROVADO_SUPERINTENDENTE(3, "tipoeventojustificativaponto.descricao.aprovadosuperintendente"),
    REPROVADO_SUPERINTENDENTE(3,"tipoeventojustificativaponto.descricao.reprovadosuperintendente"),
    ENVIADO_APROVACAO_RH(4, "tipoeventojustificativaponto.descricao.enviadoaprovacaorh"),
    APROVADO_RH(4, "tipoeventojustificativaponto.descricao.aprovadorh"),
    CANCELADO(5, "tipoeventojustificativaponto.descricao.cancelado");

    private Integer codigo;
    private String descricao;

    private TipoEventoJustificativaPontoEnum(Integer codigo, String descricao) {
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

    public static TipoEventoJustificativaPontoEnum fromSigla(Integer codigo){
        for(TipoEventoJustificativaPontoEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}
