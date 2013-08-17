package com.domain.dto;

import com.model.MotivoEnum;
import com.model.StatusEnum;

import java.io.Serializable;
import java.util.Date;

public class JustificativaPontoGrid implements Serializable {
    private Integer id;
    private Date data;
    private Date dataSolicitacao;
    private Date dataSolicitacaoFim;
    private MotivoEnum motivo;
    private StatusEnum status;

    private String nomeSolicitante;

    public JustificativaPontoGrid() {
    }

    public MotivoEnum getMotivo() {
        return motivo;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataSolicitacaoFim() {
        return dataSolicitacaoFim;
    }

    public void setDataSolicitacaoFim(Date dataSolicitacaoFim) {
        this.dataSolicitacaoFim = dataSolicitacaoFim;
    }

    public boolean isPeriodo() {
        return dataSolicitacaoFim!=null;
    }

    public void setMotivo(MotivoEnum motivo) {
        this.motivo = motivo;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }
}
