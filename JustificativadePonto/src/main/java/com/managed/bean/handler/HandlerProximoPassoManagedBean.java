package com.managed.bean.handler;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IProximoPasso;
import com.spring.util.ApplicationContextProvider;

import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class HandlerProximoPassoManagedBean implements Serializable {

    private Integer id;

    private boolean temProximoPasso;

    private boolean permiteEditar;

    private boolean permiteCancelar;

    private boolean concluir;

    private String idProximoPasso;

    private transient List<SelectItem> escolhas;

    public HandlerProximoPassoManagedBean(boolean temProximoPasso,
                                          boolean permiteEditar,
                                          boolean permiteCancelar,
                                          boolean concluir,
                                          String idProximoPasso) {
        this.temProximoPasso = temProximoPasso;
        this.permiteEditar = permiteEditar;
        this.permiteCancelar = permiteCancelar;
        this.concluir = concluir;
        this.idProximoPasso = idProximoPasso;
        IProximoPasso proximoPasso = (IProximoPasso) ApplicationContextProvider.getBean(this.idProximoPasso);
        this.escolhas = retornaItemAPartirDeUser(proximoPasso.listaCandidatos());
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

    public void proximo(JustificativaPontoDTO justificativa){
        IProximoPasso proximoPasso = (IProximoPasso) ApplicationContextProvider.getBean(idProximoPasso);
        proximoPasso.proximo(justificativa, id);
    }

    private List<SelectItem> retornaItemAPartirDeUser(List<CadastroUsuario> users) {

        if (users == null) {
            return null;
        }

        List<SelectItem> resultado = new LinkedList<SelectItem>();

        for (CadastroUsuario u : users) {
            resultado.add(new SelectItem(u.getId(), u.getNome()));
        }

        return resultado;

    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {

        s.defaultReadObject();

        IProximoPasso proximoPasso = (IProximoPasso) ApplicationContextProvider.getBean(idProximoPasso);
        escolhas = retornaItemAPartirDeUser(proximoPasso.listaCandidatos());

    }

}
