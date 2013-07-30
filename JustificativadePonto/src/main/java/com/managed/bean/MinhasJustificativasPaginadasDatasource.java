package com.managed.bean;

import com.domain.dto.JustificativaPontoGrid;
import com.service.IConsultaPaginadaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MinhasJustificativasPaginadasDatasource implements Serializable {

    protected IConsultaPaginadaService<JustificativaPontoGrid> service;

    private LazyDataModel<JustificativaPontoGrid> justificativas;

    public MinhasJustificativasPaginadasDatasource(IConsultaPaginadaService<JustificativaPontoGrid> service) {
        this.service = service;
    }

    public LazyDataModel<JustificativaPontoGrid> getJustificativas() {
        if(justificativas == null){
            justificativas = new LazyDataModel<JustificativaPontoGrid>() {
                private long totalLinhas = 0;
                @Override
                public List<JustificativaPontoGrid> load(int i, int i1, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
                    List<JustificativaPontoGrid> resultado = service.todas(i, i1);
                    totalLinhas = service.count();
                    return resultado;
                }

                @Override
                public int getRowCount() {
                    return (int) totalLinhas;
                }
            };

        }

        return justificativas;
    }
}
