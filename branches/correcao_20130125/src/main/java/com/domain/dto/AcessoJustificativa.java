package com.domain.dto;

import java.io.Serializable;

public class AcessoJustificativa implements Serializable {
    boolean editaSolicitante;
    boolean editaCoordenador;
    boolean editaSuperintendente;
    boolean editaRh;
    boolean cancelar;

    public AcessoJustificativa(boolean editaSolicitante,
                               boolean editaCoordenador,
                               boolean editaSuperintendente,
                               boolean editaRh,
                               boolean cancelar) {
        this.editaSolicitante = editaSolicitante;
        this.editaCoordenador = editaCoordenador;
        this.editaSuperintendente = editaSuperintendente;
        this.editaRh = editaRh;
        this.cancelar = cancelar;
    }

    public boolean isEditaSolicitante() {
        return editaSolicitante;
    }

    public boolean isEditaCoordenador() {
        return editaCoordenador;
    }

    public boolean isEditaSuperintendente() {
        return editaSuperintendente;
    }

    public boolean isEditaRh() {
        return editaRh;
    }

    public boolean isCancelar() {
        return cancelar;
    }
}
