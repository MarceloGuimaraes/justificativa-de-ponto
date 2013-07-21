package com.domain.dto.relatorio;

import com.model.*;
import com.util.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 11:40 PM
 */
public class Ocorrencia {
    private Integer id;
    private String solicitante;
    private Date data;
    private Date dataFim;
    private Date horaInicio;
    private Date horaTermino;
    private String motivo;
    private String complemento;
    private String status;
    private String tipoDecisao;

    public Ocorrencia() {
    }

    public Ocorrencia(Date data, Date horaInicio, Date horaTermino, String motivo, String complemento, String status) {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.motivo = motivo;
        this.complemento = complemento;
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public Date getHoraTermino() {
        return horaTermino;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getStatus() {
        return status;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraTermino(Date horaTermino) {
        this.horaTermino = horaTermino;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeriodoDatas(){
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        final StringBuilder periodo = new StringBuilder();
        if(data!=null){
            periodo.append(fmt.format(data));
        }
        if(dataFim!=null){
            periodo.append(" - ");
            periodo.append(fmt.format(dataFim));
        }
        return periodo.toString();
    }

    public String getPeriodo(){
        DateFormat fmt = new SimpleDateFormat("HH:mm");
        final StringBuilder periodo = new StringBuilder();
        if(horaInicio!=null){
            periodo.append(fmt.format(horaInicio));
        }
        if(horaTermino!=null){
            periodo.append(" - ");
            periodo.append(fmt.format(horaTermino));
        }
        return periodo.toString();
    }

    public void setTipoMotivo(final MotivoEnum motivo){
        if(motivo!=null){
            this.motivo = Message.getBundleMessage(motivo.getDescricao());
        }
    }

    public void setTipoStatus(final StatusEnum status){
        if(status!=null){
            this.status = Message.getBundleMessage(status.getDescricao());
        }
    }

    public void setTipoFalta(final TipoFaltaEnum tipoFalta){
        if(tipoFalta!=null){
            complemento = Message.getBundleMessage(tipoFalta.getDescricao());
        }
    }

    public void setTipoBancoHoras(final TipoBancoHorasEnum tipoBancoHoras){
        if(tipoBancoHoras!=null){
            complemento = Message.getBundleMessage(tipoBancoHoras.getDescricao());
        }
    }

    public void setTipoFaltaMarcacao(TipoFaltaMarcacaoEnum tipoFaltaMarcacao){
        if(tipoFaltaMarcacao!=null){
            complemento = Message.getBundleMessage(tipoFaltaMarcacao.getDescricao());
        }
    }

    public void setTipoDecisaoEnum(TipoDecisaoEnum tipoDecisaoEnum){
        if(tipoDecisaoEnum!=null){
            this.tipoDecisao = Message.getBundleMessage(tipoDecisaoEnum.getDescricao());
        }
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getTipoDecisao() {
        return tipoDecisao;
    }

    public void setTipoDecisao(String tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
