package com.util;
import java.io.Serializable;

public interface IEnumeracaoComCodigoDescricao  extends Serializable {

    /**
     * Recupera o c�digo do ENUM.
     *
     * @return o c�digo do ENUM.
     */
    Integer getCodigo();

    /**
     * Recupera a descricaoo do ENUM.
     *
     * @returna descricao do ENUM.
     */
    String getDescricao();
}
