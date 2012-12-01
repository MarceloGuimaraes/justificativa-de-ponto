package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.model.JustificativaPonto;
import com.model.Motivo;
import com.model.TipoBancoHoras;
import com.model.TipoFalta;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.util.Message;

@ManagedBean(name = "justificativaBean")
@RequestScoped
public class JustificativaManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "justificativa";
    private static final String EDIT = "editJustificativa";

    @ManagedProperty(value = "#{JustificativaService}")
    private IJustificativaService justificativaService;

    @ManagedProperty(value = "#{UserService}")
    IUserService userService;

    List<User> userList;

    public List<User> getUserList() {
        userList = new ArrayList<User>();
        userList.addAll(getUserService().getUsers());
        return userList;
    }

    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private List<JustificativaPonto> justificativaList;

    private String selctedMotivos;
    private String selectedTipoBancoHoras;
    private String selctedTipoFalta;

    private List<SelectItem> tipoBancoHorasList = new ArrayList<SelectItem>();
    private List<SelectItem> tipoMotivosList = new ArrayList<SelectItem>();
    private List<SelectItem> tipoFaltaList = new ArrayList<SelectItem>();

    private JustificativaPonto justificativa;

    public JustificativaPonto getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaPonto justificativa) {
        this.justificativa = justificativa;
    }

    public JustificativaManagedBean() {
        if (this.justificativa == null) {
            this.justificativa = new JustificativaPonto();
            this.justificativa.setDtCriacao(new Date());
            this.justificativa.setStatus("Em  Elaboração");
        }

        fetchTipoMotivosList();
        fetchTipoBancoHorasList();
        fetchTipoFaltaList();
    }

    public String addJustificativa() {

        System.out.println("ENTROU NO addJustificativa");

        // se a justificativa existir atualiza
        if (this.justificativa.getJustificativaId() != 0) {
            // selecaoToPerfilEnum(this.user);
            getJustificativaService().updateJustificativaPonto(
                    this.justificativa);
            return SUCCESS;
        } else {
            getJustificativaService().addJustificativaPonto(justificativa);
            return SUCCESS;
        }
    }

    private void fetchTipoMotivosList() {
        tipoMotivosList.clear();
        tipoMotivosList.add(new SelectItem("SELECIONE"));
        Motivo[] motivoArray = Motivo.values();
        for (Motivo tipoMotivo : motivoArray) {
            // System.out.println("tipoMotivo:" + tipoMotivo);
            tipoMotivosList.add(new SelectItem(tipoMotivo.getName()));
        }
    }

    public String editJustificativa(JustificativaPonto justificativa) {
        this.justificativa = justificativa;
        return EDIT;
    }

    public String getLabelCadastro() {
        if (this.justificativa.getJustificativaId() == 0) {
            return Message
                    .getBundleMessage("cadastroJustificativa.label.titulo");
        } else {
            return Message
                    .getBundleMessage("cadastroJustificativa.label.alteraUsuario");
        }
    }

    public String getSelctedMotivos() {
        return selctedMotivos;
    }

    public void getSelctedMotivos(String selctedMotivos) {
        this.selctedMotivos = selctedMotivos;
    }

    public List<SelectItem> getTipoMotivosList() {
        return tipoMotivosList;
    }

    public void setTipoMotivosList(List<SelectItem> tipoMotivosList) {
        this.tipoMotivosList = tipoMotivosList;
    }

    private void fetchTipoBancoHorasList() {
        tipoBancoHorasList.clear();
        tipoBancoHorasList.add(new SelectItem("SELECIONE"));
        TipoBancoHoras[] bancoHorasArray = TipoBancoHoras.values();
        for (TipoBancoHoras tipoBancoHoras : bancoHorasArray) {
            // System.out.println("tipoBancoHoras:" + tipoBancoHoras);
            tipoBancoHorasList.add(new SelectItem(tipoBancoHoras.getName()));
        }
    }

    public String getSelectedTipoBancoHoras() {
        return selectedTipoBancoHoras;
    }

    public void getSelectedTipoBancoHoras(String selectedTipoBancoHoras) {
        this.selectedTipoBancoHoras = selectedTipoBancoHoras;
    }

    public List<SelectItem> getTipoBancoHorasList() {
        return tipoBancoHorasList;
    }

    public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
        this.tipoBancoHorasList = tipoBancoHorasList;
    }

    private void fetchTipoFaltaList() {
        tipoFaltaList.clear();
        tipoFaltaList.add(new SelectItem("SELECIONE"));
        TipoFalta[] bancoHorasArray = TipoFalta.values();
        for (TipoFalta tipoFalta : bancoHorasArray) {
            // System.out.println("tipoFalta:" + tipoFalta);
            tipoFaltaList.add(new SelectItem(tipoFalta.getName()));
        }
    }

    public void reset() {
        this.justificativa = new JustificativaPonto();
    }

    public String getSelectedTipoFalta() {
        return selctedTipoFalta;
    }

    public void getSelectedTipoFalta(String selectedTipoFalta) {
        this.selctedTipoFalta = selectedTipoFalta;
    }

    public List<SelectItem> getTipoFaltaList() {
        return tipoFaltaList;
    }

    public void setTipoFaltaList(List<SelectItem> tipoFaltaList) {
        this.tipoFaltaList = tipoFaltaList;
    }

    public List<JustificativaPonto> getJustificativaList() {
        justificativaList = new ArrayList<JustificativaPonto>();
        justificativaList.addAll(getJustificativaService()
                .getJustificativaPontos());
        return justificativaList;
    }

    public void setJustificativaList(List<JustificativaPonto> justificativaList) {
        this.justificativaList = justificativaList;
    }

    public IJustificativaService getJustificativaService() {
        return justificativaService;
    }

    public void setJustificativaService(
            IJustificativaService justificativaService) {
        this.justificativaService = justificativaService;
    }
}