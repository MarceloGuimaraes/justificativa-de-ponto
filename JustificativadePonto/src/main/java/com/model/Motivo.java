package com.model;

import java.util.HashMap;
import java.util.Map;

public enum Motivo {

    FALTAS("Faltas"), FALTADEMARCACAO("Falta de Marcação"), ATIVIDADEFORA("Atividade de Fora"), BANCODEHORAS("Banco de Horas"), ATRASOS("Atrasos"), SAIDAANTECIPADA("Saída Antecipada");

    private String motivos;

    private static final Map<String, Motivo> tipoMotivo = new HashMap<String, Motivo>();

    static {
        for (Motivo motivo : Motivo.values()) {
            tipoMotivo.put(motivo.getName(), motivo);
        }
    }

    private Motivo(String motivos) {
        this.motivos = motivos;
    }

    public String getName() {
        return motivos;
    }

    public short shortValue() {
        return (short) ordinal();
    }
}
