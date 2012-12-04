package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.jsf.ds.impl.ComboMotivoDatasourceImpl;
import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
import com.jsf.ds.impl.ComboTipoFaltaDatasourceImpl;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.util.JsfUtil;
import com.util.Message;

@ManagedBean(name = "justificativaBean")
@RequestScoped
public class JustificativaManagedBean implements Serializable {


	private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "welcome";
    private static final String EDIT = "editJustificativa";
  
  	@ManagedProperty(value = "#{JustificativaService}")
    private IJustificativaService justificativaService;

    @ManagedProperty(value = "#{UserService}")
    IUserService userService;

    private PermissoesBean permissoes;

    List<SelectItem> userList;

    public void setPermissoes(PermissoesBean permissoes) {
		this.permissoes = permissoes;
	}
    
    public PermissoesBean getPermissoes() {
		return permissoes;
	}

    public List<SelectItem> getUserList() {

        if(userList==null){
            userList = new ArrayList<SelectItem>();
            for(User u : getUserService().getUsers()){
                userList.add(new SelectItem(u.getUserId(), u.getNome()));
            }
        }

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
    private List<SelectItem> tipoDecisaoList;

    public List<SelectItem> getTipoBancoHorasList() {

        if (this.tipoBancoHorasList == null) {

            this.tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();
        }
        return tipoBancoHorasList;

    }

    public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
        this.tipoBancoHorasList = tipoBancoHorasList;
    }

    public List<SelectItem> getTipoFaltaList() {

        if (this.tipoFaltaList == null) {

            this.tipoFaltaList = new ComboTipoFaltaDatasourceImpl().findObjects();

        }

        return tipoFaltaList;

    }

    public void setTipoFaltaList(List<SelectItem> tipoFaltaList) {
        this.tipoFaltaList = tipoFaltaList;
    }


    public List<SelectItem> getTipoMotivosList() {

        if (this.tipoMotivosList == null) {

            this.tipoMotivosList = new ComboMotivoDatasourceImpl().findObjects();

        }

        return tipoMotivosList;
    }

    public void setTipoMotivosList(List<SelectItem> tipoMotivosList) {
        this.tipoMotivosList = tipoMotivosList;
    }

    public List<SelectItem> getTipoDecisaoList() {
        if(tipoDecisaoList == null){

            tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

        }

        return tipoDecisaoList;
    }

    public void setTipoDecisaoList(List<SelectItem> tipoDecisaoList) {
        this.tipoDecisaoList = tipoDecisaoList;
    }

    private JustificativaPonto justificativa;
    private Integer idCoordenador;
    private Integer idSuperintendente;

    public JustificativaPonto getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaPonto justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getIdCoordenador() {
        return idCoordenador;
    }

    public void setIdCoordenador(Integer idCoordenador) {
        this.idCoordenador = idCoordenador;
    }

    public Integer getIdSuperintendente() {
        return idSuperintendente;
    }

    public void setIdSuperintendente(Integer idSuperintendente) {
        this.idSuperintendente = idSuperintendente;
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

        permissoes = JsfUtil.getValueExpression(PermissoesBean.class, "#{PermissoesBean}");

        if (this.justificativa == null) {
            this.justificativa = new JustificativaPonto(permissoes.getUsuarioLogado());
        }

    }
    

    public String addJustificativa() {

        //Inserindo o coordenador e o superintendente escolhidos
        justificativa.setCoordenador(userService.recuperar(idCoordenador));
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));

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