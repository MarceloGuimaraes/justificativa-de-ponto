package com.domain.dto;

import com.model.TipoEventoJustificativaPontoEnum;

import java.io.Serializable;
import java.util.Date;

public class HistoricoJustificativaPontoDTO implements Serializable {

    private Long id;
    private Date data;
    private UsuarioLogado usuario;
    private UsuarioLogado responsavel;
    private JustificativaPontoDTO justificativaPonto;
    private TipoEventoJustificativaPontoEnum tipoEvento;

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public UsuarioLogado getUsuario() {
        return usuario;
    }

    public JustificativaPontoDTO getJustificativaPonto() {
        return justificativaPonto;
    }

    public TipoEventoJustificativaPontoEnum getTipoEvento() {
        return tipoEvento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setUsuario(UsuarioLogado usuario) {
        this.usuario = usuario;
    }

    public void setJustificativaPonto(JustificativaPontoDTO justificativaPonto) {
        this.justificativaPonto = justificativaPonto;
    }

    public void setTipoEvento(TipoEventoJustificativaPontoEnum tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public UsuarioLogado getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UsuarioLogado responsavel) {
        this.responsavel = responsavel;
    }
}
