package com.managed.bean.handler;

import com.domain.dto.JustificativaPontoDTO;
import com.jsf.ds.impl.ComboMotivoDatasourceImpl;
import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
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
    private static final String TIPO_BANCO_HORAS = "TIPO_BANCO_HORAS";

    private List<SelectItem> tipoMotivosList;
    private List<SelectItem> tipoFaltaList;
    private List<SelectItem> tipoBancoHorasList;
//    Map<MotivoEnum, Map<String,Boolean>>escolhas;
    private Map<String,Boolean> configuracao;

    public HandlerMotivosManagedBean() {
        this.tipoMotivosList = new ComboMotivoDatasourceImpl().findObjects();
        this.tipoFaltaList = new ComboTipoFaltaDatasourceImpl().findObjects();
        tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();
//        escolhas = new LinkedHashMap<MotivoEnum, Map<String, Boolean>>();
//        escolhas.put(MotivoEnum.FALTAS, getConfiguracaoFaltas());
//        escolhas.put(MotivoEnum.BANCODEHORAS, getConfiguracaoBancoHoras());
//        escolhas.put(MotivoEnum.FALTADEMARCACAO, getConfiguracaoFaltaMarcacao());
//        escolhas.put(MotivoEnum.ATIVIDADEFORA, getConfiguracaoOutros());
//        escolhas.put(MotivoEnum.ATRASOS, getConfiguracaoOutros());
//        escolhas.put(MotivoEnum.SAIDAANTECIPADA, getConfiguracaoOutros());

    }

    public Map<String, Boolean> getConfiguracao() {
        return configuracao;
    }

    public void setJustificativa(JustificativaPontoDTO justificativa){
        setConfiguracao(justificativa.getMotivo());
    }

    private void setConfiguracao(MotivoEnum motivo){
//        configuracao = escolhas.get(motivo);
        if(MotivoEnum.FALTAS.equals(motivo)){
            configuracao = getConfiguracaoFaltas();
        }else if(MotivoEnum.FALTADEMARCACAO.equals(motivo)){
            configuracao = getConfiguracaoFaltaMarcacao();
        } else if(MotivoEnum.BANCODEHORAS.equals(motivo)){
            configuracao = getConfiguracaoBancoHoras();
        } else {
            configuracao = getConfiguracaoOutros();
        }
    }

    private Map<String, Boolean> getConfiguracaoFaltas(){
        Map<String, Boolean> resultado = new LinkedHashMap<String, Boolean>();
        resultado.put(CAUSA_FALTA, true);
        resultado.put(HORA_INI, false);
        resultado.put(HORA_FIM, false);
        resultado.put(TIPO_BANCO_HORAS, false);
        return resultado;
    }

    private Map<String, Boolean> getConfiguracaoFaltaMarcacao(){
        Map<String, Boolean> resultado = new LinkedHashMap<String, Boolean>();
        resultado.put(CAUSA_FALTA, false);
        resultado.put(HORA_INI, true);
        resultado.put(HORA_FIM, false);
        resultado.put(TIPO_BANCO_HORAS, false);
        return resultado;
    }

    private Map<String, Boolean> getConfiguracaoBancoHoras(){
        Map<String, Boolean> resultado = new LinkedHashMap<String, Boolean>();
        resultado.put(CAUSA_FALTA, false);
        resultado.put(HORA_INI, true);
        resultado.put(HORA_FIM, true);
        resultado.put(TIPO_BANCO_HORAS, true);
        return resultado;
    }

    private Map<String, Boolean> getConfiguracaoOutros(){
        Map<String, Boolean> resultado = new LinkedHashMap<String, Boolean>();
        resultado.put(CAUSA_FALTA, false);
        resultado.put(HORA_INI, true);
        resultado.put(HORA_FIM, true);
        resultado.put(TIPO_BANCO_HORAS, false);
        return resultado;
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

    public List<SelectItem> getTipoBancoHorasList() {
        return tipoBancoHorasList;
    }

    public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
        this.tipoBancoHorasList = tipoBancoHorasList;
    }
}
