package com.jsf.adapter;

import com.jsf.model.WrappedBeanPaginadaDataModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: xonda
 * Date: 29/07/13
 * Time: 17:49
 */
@Transactional(readOnly = true)
public interface ILazyDataModelAdapter<W> {
    List<W> atualizar(WrappedBeanPaginadaDataModel<W> dataModel, int startIndex, int pageSize);
}
