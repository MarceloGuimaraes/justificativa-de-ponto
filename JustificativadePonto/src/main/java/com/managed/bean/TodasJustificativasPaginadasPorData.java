package com.managed.bean;

import com.model.JustificativaPonto;
import com.service.IConsultaPaginadaService;
import com.util.JsfUtil;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TodasJustificativasPaginadasPorData implements Serializable {

    IConsultaPaginadaService<JustificativaPonto> service;

    private LazyDataModel<JustificativaPonto> justificativas;

    public TodasJustificativasPaginadasPorData(IConsultaPaginadaService<JustificativaPonto> service,
                                               IConsultaPaginadaService<JustificativaPonto> serviceUser) {
        PermissoesBean permissoes = JsfUtil.getValueExpression(PermissoesBean.class, "#{PermissoesBean}");
        if(permissoes.isAdmin() || permissoes.isSupport()){
            this.service = service;
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
