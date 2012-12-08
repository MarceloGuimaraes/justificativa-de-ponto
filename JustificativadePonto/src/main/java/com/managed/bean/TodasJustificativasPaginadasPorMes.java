package com.managed.bean;

import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RequestScoped
@ManagedBean(name = "todasJustificativasPaginadasPorMes")
public class TodasJustificativasPaginadasPorMes implements Serializable {

    @ManagedProperty(value = "#{JustificativaService}")
    IJustificativaService service;

    private LazyDataModel<JustificativaPonto> justificativas;

    public void setService(IJustificativaService service) {
        this.service = service;
    }

    public LazyDataModel<JustificativaPonto> getJustificativas() {
        if(justificativas == null){
            justificativas = new LazyDataModel<JustificativaPonto>() {
                private int totalLinhas = 0;
                @Override
                public List<JustificativaPonto> load(int i, int i1, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
                    List<JustificativaPonto> resultado = service.todas(i, i1);
                    totalLinhas = service.count();
                    return resultado;
                }

                @Override
                public int getRowCount() {
                    return totalLinhas;
                }
            };

        }

        return justificativas;
    }

}
