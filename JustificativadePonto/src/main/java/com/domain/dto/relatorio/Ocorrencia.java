package com.domain.dto.relatorio;

import java.util.Date;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 11:40 PM
 */
public class Ocorrencia {
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
}
