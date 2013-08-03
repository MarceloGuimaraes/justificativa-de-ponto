package com.jsf.adapter.impl;

import com.jsf.adapter.ILazyDataModelAdapter;
import com.jsf.model.WrappedBeanPaginadaDataModel;
import com.managed.bean.helper.BeanWrapper;
import com.service.IConsultaPaginadaService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: xonda
 * Date: 29/07/13
 * Time: 14:22
 */
public class LazyDataModelAdapter<W,T> implements ILazyDataModelAdapter<W> {
    private IConsultaPaginadaService<T> service;
    private BeanWrapper<W,T> beanWrapper;

    public LazyDataModelAdapter(IConsultaPaginadaService<T> service,
                                BeanWrapper<W, T> beanWrapper) {
        this.service = service;
        this.beanWrapper = beanWrapper;
    }

    public List<W> atualizar(WrappedBeanPaginadaDataModel<W> dataModel, int startIndex, int pageSize){
        dataModel.setRowCount((int) service.count());
        List<T> wrappeds = service.todas(startIndex, pageSize);
        List<W> resultado = new ArrayList<W>();
        for(T t : wrappeds){
            resultado.add(beanWrapper.wrap(t));
        }
        return resultado;
    }
}
