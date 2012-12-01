package com.model;

import java.util.HashMap;
import java.util.Map;

public enum TipoFalta {

    ATESTADOMEDICO("Atestado Médico"), CASAMENTO("Casamento"), LICENCAPATERNIDADE("Licença Paternidade"), LUTO("Luto"), OUTROS("Outros");

    private String tiposfalta;

    private static final Map<String, TipoFalta> tipoMotivo = new HashMap<String, TipoFalta>();

    static {
        for (TipoFalta tiposfalta : TipoFalta.values()) {
            tipoMotivo.put(tiposfalta.getName(), tiposfalta);
        }
    }

    private TipoFalta(String tiposfalta) {
        this.tiposfalta = tiposfalta;
    }

    public String getName() {
        return tiposfalta;
    }

    public short shortValue() {
        return (short) ordinal();
    }
}