package com.domain.dto;

import com.model.*;

import java.io.Serializable;
import java.util.Date;

public class JustificativaPontoGrid implements Serializable {
    private Integer id;
    private Date data;
    private Date dataSolicitacao;
    private Date dataSolicitacaoFim;
    private boolean periodo;
    private CadastroUsuario solicitante;
    private CadastroUsuario coordenador;
    private MotivoEnum motivo;
    private StatusEnum status;
    private HistoricoJustificativaPontoDTO ultimoHistorico;

    public JustificativaPontoGrid() {
    }

    public void setJustificativa(JustificativaPonto justificativa){
        id = justificativa.getId();
        data = justificativa.getData();
        dataSolicitacao = justificativa.getDataSolicitacao();
        dataSolicitacaoFim = justificativa.getDataSolicitacaoFim();
        periodo = justificativa.getDataSolicitacaoFim()!=null;
        solicitante = new CadastroUsuario();
        solicitante.setNome(justificativa.getSolicitante().getNome());
        solicitante.setCpf(justificativa.getSolicitante().getCpf());
        solicitante.setEmail(justificativa.getSolicitante().getEmail());
        status = justificativa.getStatus();
        motivo = justificativa.getMotivo();
        Date maiorData = justificativa.getHistorico().get(0).getData();
        for (HistoricoJustificativaPonto h : justificativa.getHistorico()){
            if(h.getData().after(maiorData)){
                ultimoHistorico = new HistoricoJustificativaPontoDTO();
                ultimoHistorico.setData(h.getData());
                ultimoHistorico.setTipoEvento(h.getTipoEvento());
            }
            if(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR.equals(h.getTipoEvento())){
                Identificacao coordUser = ((EncaminhamentoJustificativaPonto)h).getResponsavel();
                coordenador = new CadastroUsuario();
                coordenador.setNome(coordUser.getNome());
                coordenador.setCpf(coordUser.getCpf());
                coordenador.setEmail(coordUser.getEmail());
            }
        }
    }

    public CadastroUsuario getSolicitante() {
        return solicitante;
    }

    public CadastroUsuario getCoordenador() {
        return coordenador;
    }

    public MotivoEnum getMotivo() {
        return motivo;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public HistoricoJustificativaPontoDTO getUltimoHistorico() {
        return ultimoHistorico;
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
        return periodo;
    }

    public void setPeriodo(boolean periodo) {
        this.periodo = periodo;
    }
}
