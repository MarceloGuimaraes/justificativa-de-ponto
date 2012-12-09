package com.managed.bean.handler;

import com.jsf.ds.impl.ComboMotivoDatasourceImpl;
import com.jsf.ds.impl.ComboTipoFaltaDatasourceImpl;
import com.model.MotivoEnum;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HandlerMotivosManagedBean implements Serializable {
    private static final String CAUSA_FALTA = "CAUSA_FALTA";
    private static final String HORA_INI = "HORA_INI";
    private static final String HORA_FIM = "HORA_FIM";
    private List<SelectItem> tipoMotivosList;
    private List<SelectItem> tipoFaltaList;
    private Map<String,Boolean> configuracao;

    public HandlerMotivosManagedBean() {
        this.tipoMotivosList = new ComboMotivoDatasourceImpl().findObjects();
        this.tipoFaltaList = new ComboTipoFaltaDatasourceImpl().findObjects();
        configuracao = new LinkedHashMap<String, Boolean>();
    }

    public HandlerMotivosManagedBean(MotivoEnum motivo){
        this();
        setConfiguracao(motivo);
    }

    public Map<String, Boolean> getConfiguracao() {
        return configuracao;
    }

    private void setConfiguracao(MotivoEnum motivo){
        if(MotivoEnum.FALTAS.equals(motivo)){
            setConfiguracaoFaltas();
        }else if(MotivoEnum.FALTADEMARCACAO.equals(motivo)){
            setConfiguracaoFaltaMarcacao();
        }else {
            setConfiguracaoOutros();
        }
    }

    private void setConfiguracaoFaltas(){
        configuracao.put(CAUSA_FALTA, true);
        configuracao.put(HORA_INI, false);
        configuracao.put(HORA_FIM, false);
    }

    private void setConfiguracaoFaltaMarcacao(){
        configuracao.put(CAUSA_FALTA, false);
        configuracao.put(HORA_INI, true);
        configuracao.put(HORA_FIM, false);
    }

    private void setConfiguracaoOutros(){
        configuracao.put(CAUSA_FALTA, false);
        configuracao.put(HORA_INI, true);
        configuracao.put(HORA_FIM, true);
    }

    public List<SelectItem> getTipoMotivosList() {
        return tipoMotivosList;
    }

    public void setTipoMotivosList(List<SelectItem> tipoMotivosList) {
        this.tipoMotivosList = tipoMotivosList;
    }

    public List<SelectItem> getTipoFaltaList() {
        return tipoFaltaList;

    }

    public void setTipoFaltaList(List<SelectItem> tipoFaltaList) {
        this.tipoFaltaList = tipoFaltaList;
    }

    public void mudouMotivo(ValueChangeEvent e) {

        try {
            MotivoEnum novoMotivo = (MotivoEnum) e.getNewValue();
            setConfiguracao(novoMotivo);
        } catch (ClassCastException cce) {
            throw new RuntimeException("O controlador recebeu um valor inesperado");
        }
    }

}
