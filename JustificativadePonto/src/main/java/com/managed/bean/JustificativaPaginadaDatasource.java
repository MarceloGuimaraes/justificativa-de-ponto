package com.managed.bean;

import com.domain.dto.JustificativaPontoGrid;
import com.model.JustificativaPonto;
import com.service.IConsultaPaginadaService;
import com.util.JsfUtil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class JustificativaPaginadaDatasource implements Serializable {

    IConsultaPaginadaService<JustificativaPontoGrid> service;

    private LazyDataModel<JustificativaPontoGrid> justificativas;

    public JustificativaPaginadaDatasource(IPermissoesBean permissoes,
                                           IConsultaPaginadaService<JustificativaPontoGrid> serviceAdmin,
                                           IConsultaPaginadaService<JustificativaPontoGrid> serviceUser) {
        if(permissoes.isAdmin() || permissoes.isSupport() || permissoes.isRh()){
            this.service = serviceAdmin;
        }else{
            this.service = serviceUser;
        }
    }

    public LazyDataModel<JustificativaPontoGrid> getJustificativas() {
        if(justificativas == null){
            justificativas = new LazyDataModel<JustificativaPontoGrid>() {
                private int totalLinhas = 0;
                @Override
                public List<JustificativaPontoGrid> load(int i, int i1, String s, SortOrder sortOrder, Map<String, String> stringStringMap) {
                    List<JustificativaPontoGrid> resultado = service.todas(i, i1);
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
