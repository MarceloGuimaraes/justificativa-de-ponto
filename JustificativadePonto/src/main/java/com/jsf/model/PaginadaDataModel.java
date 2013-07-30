package com.jsf.model;

import com.service.IConsultaPaginadaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:22
 */
public class PaginadaDataModel<T> extends LazyDataModel<T> {
    private IConsultaPaginadaService<T> service;
    private long totalLinhas;

    public PaginadaDataModel(IConsultaPaginadaService<T> service) {
        this.service = service;
    }

    @Override
    public List<T> load(int startIndex, int pageSize, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
        totalLinhas = service.count();
        List<T> resultado = service.todas(startIndex, pageSize);
        return resultado;
    }

    @Override
    public int getRowCount() {
        return (int) totalLinhas;
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
