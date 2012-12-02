package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.model.*;
import com.model.MotivoEnum;
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

    private MotivoEnum tipoMotivo;
    private TipoBancoHorasEnum tipoBancoHoras;
    private TipoFaltaEnum tipoFalta;

    private List<SelectItem> tipoBancoHorasList;
    private List<SelectItem> tipoMotivosList;
    private List<SelectItem> tipoFaltaList;



    public List<SelectItem> getTipoBancoHorasList() {

        if (this.tipoBancoHorasList == null) {

            this.tipoBancoHorasList = new ArrayList<SelectItem>();

            for (TipoBancoHorasEnum tipoBancoHoras : TipoBancoHorasEnum.values()) {
                tipoBancoHorasList.add(new SelectItem(tipoBancoHoras, tipoBancoHoras.getDescricao()));
            }
        }
        return tipoBancoHorasList;

    }

    public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
        this.tipoBancoHorasList = tipoBancoHorasList;
    }

    public List<SelectItem> getTipoFaltaList() {

        if (this.tipoFaltaList == null) {

            this.tipoFaltaList = new ArrayList<SelectItem>();

            for (TipoFaltaEnum tipoFalta : TipoFaltaEnum.values()) {
                tipoFaltaList.add(new SelectItem(tipoFalta, tipoFalta.getDescricao()));
            }
        }
            return tipoFaltaList;

    }

    public void setTipoFaltaList(List<SelectItem> tipoFaltaList) {
        this.tipoFaltaList = tipoFaltaList;
    }


    public List<SelectItem> getTipoMotivosList() {

        if (this.tipoMotivosList == null) {

            this.tipoMotivosList = new ArrayList<SelectItem>();

            for (MotivoEnum tipoMotivo : MotivoEnum.values()) {
                tipoMotivosList.add(new SelectItem(tipoMotivo, tipoMotivo
                        .getDescricao()));
            }
        }

        return tipoMotivosList;
    }

    public void setTipoMotivosList(List<SelectItem> tipoMotivosList) {
        this.tipoMotivosList = tipoMotivosList;
    }

    private JustificativaPonto justificativa;

    public JustificativaPonto getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaPonto justificativa) {
        this.justificativa = justificativa;
    }

    public MotivoEnum getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(MotivoEnum tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public TipoBancoHorasEnum getTipoBancoHoras() {
        return tipoBancoHoras;
    }

    public void setTipoBancoHoras(TipoBancoHorasEnum tipoBancoHoras) {
        this.tipoBancoHoras = tipoBancoHoras;
    }

    public TipoFaltaEnum getTipoFalta() {
        return tipoFalta;
    }

    public void setTipoFalta(TipoFaltaEnum tipoFalta) {
        this.tipoFalta = tipoFalta;
    }



    public JustificativaManagedBean() {
        if (this.justificativa == null) {
            this.justificativa = new JustificativaPonto();
            this.justificativa.setDtCriacao(new Date());
            this.justificativa.setStatus(StatusEnum.ELABORACAO.getDescricao());
        }
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


    public void reset() {
        this.justificativa = new JustificativaPonto();
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