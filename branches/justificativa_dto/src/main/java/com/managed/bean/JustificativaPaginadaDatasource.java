package com.managed.bean;

import com.model.JustificativaPonto;
import com.service.IConsultaPaginadaService;
import com.util.JsfUtil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class JustificativaPaginadaDatasource implements Serializable {

    IConsultaPaginadaService<JustificativaPonto> service;

    private LazyDataModel<JustificativaPonto> justificativas;

    public JustificativaPaginadaDatasource(IPermissoesBean permissoes,
                                           IConsultaPaginadaService<JustificativaPonto> serviceAdmin,
                                           IConsultaPaginadaService<JustificativaPonto> serviceUser) {
        if(permissoes.isAdmin() || permissoes.isSupport() || permissoes.isRh()){
            this.service = serviceAdmin;
        }else{
            this.service = serviceUser;
        }
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
