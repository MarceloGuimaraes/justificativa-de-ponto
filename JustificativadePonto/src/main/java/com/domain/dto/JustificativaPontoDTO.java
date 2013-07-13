package com.domain.dto;

import com.model.*;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class JustificativaPontoDTO implements Serializable {

    private Integer id;
    private Date data;
    private Date dataSolicitacao;
    private Date dataSolicitacaoFim;
    private UsuarioLogado solicitante;

    private Date hrIni;
    private Date hrFim;
    private String descricao;
    private StatusEnum status;
    private MotivoEnum motivo;
    private TipoFaltaEnum tipofalta;
    private TipoBancoHorasEnum tipobancohoras;
    private TipoFaltaMarcacaoEnum tipofaltamarcacao;

    private TipoDecisaoEnum tipoDecisao;
    private String obsRh;

    private String cancelamento;

    private Integer idProximoResponsavel;

    private List<HistoricoJustificativaPontoDTO> historico;

    private List<HistoricoJustificativaPontoDTO> aprovacoes;
    private List<HistoricoJustificativaPontoDTO> tramite;

    public JustificativaPontoDTO() {
    }

    public JustificativaPontoDTO(UsuarioLogado solicitante) {
        this.solicitante = solicitante;
        this.data = new Date();
        this.dataSolicitacao = new Date();
        this.status = StatusEnum.ELABORACAO;
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

    public Date getHrIni() {
        return hrIni;
    }

    public void setHrIni(Date hrIni) {
        this.hrIni = hrIni;
    }

    public Date getHrFim() {
        return hrFim;
    }

    public void setHrFim(Date hrFim) {
        this.hrFim = hrFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public MotivoEnum getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoEnum motivo) {
        this.motivo = motivo;
    }

    public TipoFaltaEnum getTipofalta() {
        return tipofalta;
    }

    public void setTipofalta(TipoFaltaEnum tipofalta) {
        this.tipofalta = tipofalta;
    }

    public TipoBancoHorasEnum getTipobancohoras() {
        return tipobancohoras;
    }

    public void setTipobancohoras(TipoBancoHorasEnum tipobancohoras) {
        this.tipobancohoras = tipobancohoras;
    }

    public TipoDecisaoEnum getTipoDecisao() {
        return tipoDecisao;
    }

    public void setTipoDecisao(TipoDecisaoEnum tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public TipoFaltaMarcacaoEnum getTipofaltamarcacao() {
		return tipofaltamarcacao;
	}

	public void setTipofaltamarcacao(TipoFaltaMarcacaoEnum tipofaltamarcacao) {
		this.tipofaltamarcacao = tipofaltamarcacao;
	}

	public UsuarioLogado getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(UsuarioLogado solicitante) {
        this.solicitante = solicitante;
    }

    public List<HistoricoJustificativaPontoDTO> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoJustificativaPontoDTO> historico) {
        aprovacoes = new LinkedList<HistoricoJustificativaPontoDTO>();
        tramite = new LinkedList<HistoricoJustificativaPontoDTO>();
        if(historico!=null){
            EnumSet<TipoEventoJustificativaPontoEnum> tipoAprovacao = EnumSet.of(
                    TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR,
                    TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE,
                    TipoEventoJustificativaPontoEnum.APROVADO_RH,
                    TipoEventoJustificativaPontoEnum.REPROVADO_COORDENADOR,
                    TipoEventoJustificativaPontoEnum.REPROVADO_SUPERINTENDENTE,
                    TipoEventoJustificativaPontoEnum.CANCELADO
            );
            EnumSet<TipoEventoJustificativaPontoEnum> tipoEncaminhamento = EnumSet.of(
                    TipoEventoJustificativaPontoEnum.REGISTRO_CRIADO,
                    TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR,
                    TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE,
                    TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH
            );
            for(HistoricoJustificativaPontoDTO h : historico){
                if(tipoAprovacao.contains(h.getTipoEvento())){
                    aprovacoes.add(h);
                }
                if(tipoEncaminhamento.contains(h.getTipoEvento())){
                    tramite.add(h);
                }
            }
        }
        this.historico = historico;
    }

    public String getObsRh() {
        return obsRh;
    }

    public void setObsRh(String obsRh) {
        this.obsRh = obsRh;
    }

    public String getCancelamento() {
        return cancelamento;
    }

    public void setCancelamento(String cancelamento) {
        this.cancelamento = cancelamento;
    }

    public List<HistoricoJustificativaPontoDTO> getAprovacoes() {
        return aprovacoes;
    }

    public List<HistoricoJustificativaPontoDTO> getTramite() {
        return tramite;
    }

    public Integer getIdProximoResponsavel() {
        return idProximoResponsavel;
    }

    public void setIdProximoResponsavel(Integer idProximoResponsavel) {
        this.idProximoResponsavel = idProximoResponsavel;
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
}
