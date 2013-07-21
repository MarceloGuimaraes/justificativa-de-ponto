package com.jsf.model;

import com.service.IConsultaFiltradaPaginadaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * User: xonda
 * Date: 20/07/13
 * Time: 15:37
 */
public class PaginaFiltradaDataModel<T,F> extends LazyDataModel<T> {
    private long totalLinhas = 0;
    private IConsultaFiltradaPaginadaService<T,F> service;
    private F filtro;

    public PaginaFiltradaDataModel(IConsultaFiltradaPaginadaService<T, F> service, F filtro) {
        this.service = service;
        this.filtro = filtro;
    }

    @Override
    public List<T> load(int startIndex, int pageSize, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
        List<T> resultado = service.todas(filtro, startIndex, pageSize);
        totalLinhas = service.count(filtro);
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
