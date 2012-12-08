package com.managed.bean.handler;

import com.jsf.ds.impl.ComboMotivoDatasourceImpl;
import com.jsf.ds.impl.ComboTipoFaltaDatasourceImpl;
import com.model.MotivoEnum;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public class HandlerMotivosManagedBean implements Serializable {
    private List<SelectItem> tipoMotivosList;
    private List<SelectItem> tipoFaltaList;
    private boolean exibeFaltas;

    public HandlerMotivosManagedBean() {
        this.tipoMotivosList = new ComboMotivoDatasourceImpl().findObjects();
        this.tipoFaltaList = new ComboTipoFaltaDatasourceImpl().findObjects();
        this.exibeFaltas = false;
    }

    public HandlerMotivosManagedBean(MotivoEnum motivo){
        this();
        if(MotivoEnum.FALTAS.equals(motivo)){
            exibeFaltas = true;
        }
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

    public boolean isExibeFaltas() {
        return exibeFaltas;
    }

    public void mudouMotivo(ValueChangeEvent e) {

        try {
            MotivoEnum novoMotivo = (MotivoEnum) e.getNewValue();
            if(MotivoEnum.FALTAS.equals(novoMotivo)){
                exibeFaltas = true;
            }else{
                exibeFaltas = false;
            }
        } catch (ClassCastException cce) {
            throw new RuntimeException("O controlador recebeu um valor inesperado");
        }
    }

}
