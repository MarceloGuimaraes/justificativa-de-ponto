package com.domain.dto;

import com.domain.service.IWorkflow;
import com.spring.util.ApplicationContextProvider;

import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class ProximoPasso implements Serializable {

    private Integer id;

    protected boolean temProximoPasso;

    protected boolean permiteEditar;

    protected boolean permiteCancelar;

    protected boolean concluir;

    private transient List<SelectItem> escolhas;

    protected transient IWorkflow workflow;

    protected ProximoPasso() {
    }

    public ProximoPasso(IWorkflow workflow) {
        this.workflow = workflow;
        this.escolhas = populaEscolhas();
    }

    public List<SelectItem> getEscolhas() {
        return escolhas;
    }

    public void setEscolhas(List<SelectItem> escolhas) {
        this.escolhas = escolhas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isTemProximoPasso() {
        return temProximoPasso;
    }

    public boolean isPermiteEditar() {
        return permiteEditar;
    }

    public boolean isPermiteCancelar() {
        return permiteCancelar;
    }

    public boolean isConcluir() {
        return concluir;
    }

    protected List<SelectItem> retornaItemAPartirDeUser(List<CadastroUsuario> users) {

        if (users == null) {
            return null;
        }

        List<SelectItem> resultado = new LinkedList<SelectItem>();

        for (CadastroUsuario u : users) {
            resultado.add(new SelectItem(u.getId(), u.getNome()));
        }

        return resultado;

    }

    protected abstract List<SelectItem> populaEscolhas();

    public abstract void proximo(JustificativaPontoDTO justificativa);

    private void readObject(ObjectInputStream o) throws ClassNotFoundException, IOException {
        o.defaultReadObject();
        workflow = (IWorkflow) ApplicationContextProvider.getBean("workflow");
        escolhas = populaEscolhas();
    }

}
