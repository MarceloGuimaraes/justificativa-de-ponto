package com.model;

public enum Status {

    ELABORACAO(1, "Em elaboração"),
    APROVCOORD(2, "Aguardando aprovação do coordenador"),
    REPROVADOCOORD(3, "Reprovado pelo coordenador"),
    APROVSUPERINTENDENTE(4, "Aguardando aprovação do superintendente"),
    REPROVADOSUPERINTENDENTE(5, "Reprovado pelo superintendente"),
    EXECUCAORH(6,"Aguardando execução do RH"),
    CONCLUIDO(7,"Concluído"),
    CANCELADO(8, "Cancelado");

    private Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Status getStatusByCode(int code) {
        for(Status status : Status.values()) {
            if(status.getCode() == code)
                return status;
        }
        throw new IllegalArgumentException("Code " + code + " is not valid!");
    }

    public static Status getStatusByDescription(String description) {
        for(Status status : Status.values()) {
            if(status.getDescription().equalsIgnoreCase(description))
                return status;
        }
        throw new IllegalArgumentException("Description " + description + " is not valid!");
    }
}

	

