package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.jsf.model.WrappedBeanPaginadaDataModel;
import com.managed.bean.wrapper.WrapperJustificativa;

/**
 * User: xonda
 * Date: 27/07/13
 * Time: 20:05
 */
public class PendenteAprovacaoWrappedPaginadaDataModel extends WrappedBeanPaginadaDataModel<WrapperJustificativa, JustificativaPontoDTO> {
    public PendenteAprovacaoWrappedPaginadaDataModel(String adapterName) {
        super(adapterName);
    }
}
