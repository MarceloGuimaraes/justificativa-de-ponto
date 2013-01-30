package com.util;
import java.io.Serializable;

public interface IEnumeracaoComCodigoDescricao  extends Serializable {

    /**
     * Recupera o codigo do ENUM.
     *
     * @return o codigo do ENUM.
     */
    Integer getCodigo();

    /**
     * Recupera a descricao do ENUM.
     *
     * @returna descricao do ENUM.
     */
    String getDescricao();
}
