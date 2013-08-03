package com.jsf.model;

import com.jsf.adapter.ILazyDataModelAdapter;
import com.spring.util.ApplicationContextProvider;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:51
 */
public class WrappedBeanPaginadaDataModel<W> extends LazyDataModel<W> {

    private String adapterName;

    public WrappedBeanPaginadaDataModel(String adapterName) {
        this.adapterName = adapterName;
    }

    @Override
    public List<W> load(int startIndex, int pageSize, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
        ILazyDataModelAdapter<W> adapter = ApplicationContextProvider.getBean(ILazyDataModelAdapter.class, adapterName);
        return adapter.atualizar(this, startIndex, pageSize);
    }

    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         * this.rowIndex = rowIndex == -1 ? rowIndex : (rowIndex % pageSize);
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else
            super.setRowIndex(rowIndex % getPageSize());
    }
}
