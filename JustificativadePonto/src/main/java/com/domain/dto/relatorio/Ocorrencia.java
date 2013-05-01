package com.domain.dto.relatorio;

import com.model.*;
import com.util.Message;

import java.util.Date;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 11:40 PM
 */
public class Ocorrencia {
    private String solicitante;
    private Date data;
    private Date horaInicio;
    private Date horaTermino;
    private String motivo;
    private String complemento;
    private String status;

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

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
}
