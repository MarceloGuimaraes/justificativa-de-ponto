package com.model;

import java.util.HashMap;
import java.util.Map;

public enum TipoBancoHoras {

	HORAEXTRA("Hora Extra"), GOZO("Gozo");

	private String tiposBancoHrs;

	private static final Map<String, TipoBancoHoras> tipoMotivo = new HashMap<String, TipoBancoHoras>();

	static {
		for (TipoBancoHoras tiposBancoHrs : TipoBancoHoras.values()) {
			tipoMotivo.put(tiposBancoHrs.getName(), tiposBancoHrs);
		}
	}

	private TipoBancoHoras(String tiposBancoHrs) {
		this.tiposBancoHrs = tiposBancoHrs;
	}

	public String getName() {
		return tiposBancoHrs;
	}

	public short shortValue() {
		return (short) ordinal();
	}
}