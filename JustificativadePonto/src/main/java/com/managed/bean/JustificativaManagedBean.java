package com.managed.bean;

import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.util.JsfUtil;
import com.util.Message;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private final HandlerMotivosManagedBean handler;

    private MotivoEnum tipoMotivo;
    private TipoBancoHorasEnum tipoBancoHoras;
    private TipoFaltaEnum tipoFalta;

    private List<SelectItem> tipoBancoHorasList;
    private List<SelectItem> tipoDecisaoList;
    private List<SelectItem> userList;

    private JustificativaPonto justificativa;

    private Integer idCoordenador;
    private Integer idSuperintendente;
    private Integer idRh;
    private boolean showFldCancelar = false;
    private boolean showFldConcluir = false;
    private boolean userAdmin = false;

    public JustificativaManagedBean() {

        permissoes = JsfUtil.getValueExpression(PermissoesBean.class, "#{PermissoesBean}");

        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

        this.tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();

        if (this.justificativa == null) {
            this.justificativa = new JustificativaPonto(permissoes.getUsuarioLogado());
        }

        editElaboracao = this.permissoes.editElaboracao(this.justificativa);
        editAguardaAprovCoord = this.permissoes.editAguardaAprovCoord(this.justificativa);
        editAguardaAprovSuperintendente =  this.permissoes.editAguardaAprovSuperintendente(this.justificativa);
        editAguardaAprovRh = this.permissoes.editAguardaAprovRh(this.justificativa);
        userAdmin = this.permissoes.isAdmin();
        showFldCancelar = this.permissoes.showFldCancelar(this.justificativa);
        //	showFldConcluir = this.permissoes.showFldConcluir(this.justificativa);

        handler = new HandlerMotivosManagedBean(justificativa.getMotivo());

    }

    public HandlerMotivosManagedBean getHandler() {
        return handler;
    }

    public void setPermissoes(PermissoesBean permissoes) {
		this.permissoes = permissoes;
	}
    
    public PermissoesBean getPermissoes() {
		return permissoes;
	}

    public List<SelectItem> getUserList() {

        if (userList==null) {
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

    public List<SelectItem> getTipoBancoHorasList() {

        return tipoBancoHorasList;

    }

    public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
        this.tipoBancoHorasList = tipoBancoHorasList;
    }

    public List<SelectItem> getTipoDecisaoList() {
        return tipoDecisaoList;
    }

    public void setTipoDecisaoList(List<SelectItem> tipoDecisaoList) {
        this.tipoDecisaoList = tipoDecisaoList;
    }

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

    public Integer getIdRh() {
		return idRh;
	}

	public void setIdRh(Integer idRh) {
		this.idRh = idRh;
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

	private Boolean editElaboracao = false;

	public Boolean getEditElaboracao() {
		return this.editElaboracao;
	}
	
	public void setEditElaboracao(Boolean editElaboracao) {
		this.editElaboracao = editElaboracao;
	}

	private Boolean editAguardaAprovCoord  = false;
	
	public Boolean getEditAguardaAprovCoord() {
		return this.editElaboracao;
	}
	
	 public void setEditAguardaAprovCoord(Boolean editAguardaAprovCoord) {
			this.editAguardaAprovCoord = editAguardaAprovCoord;
		}
	 
	private Boolean editAguardaAprovRh = false;
	
	public Boolean getEditAguardaAprovRh() {
		return editAguardaAprovRh;
	}

	public void setEditAguardaAprovRh(Boolean editAguardaAprovRh) {
		this.editAguardaAprovRh = editAguardaAprovRh;
	}
	
	private Boolean editAguardaAprovSuperintendente = false;
	
	public Boolean getEditAguardaAprovSuperintendente() {
		return editAguardaAprovSuperintendente;
	}

	public void setEditAguardaAprovSuperintendente(
			Boolean editAguardaAprovSuperintendente) {
		this.editAguardaAprovSuperintendente = editAguardaAprovSuperintendente;
	}


	public Boolean getIsUserAdmin() {
		return userAdmin;
	}

	public void setIsUserAdmin(Boolean isUserAdmin) {
		this.userAdmin = isUserAdmin;
	}

    public boolean isShowFldCancelar() {
        return showFldCancelar;
    }

    public void setShowFldCancelar(boolean showFldCancelar) {
        this.showFldCancelar = showFldCancelar;
    }

    public boolean isShowFldConcluir() {
        return showFldConcluir;
    }

    public void setShowFldConcluir(boolean showFldConcluir) {
        this.showFldConcluir = showFldConcluir;
    }

    public String addJustificativa() {

        //Inserindo o coordenador, superintendente e Rh escolhidos
        justificativa.setCoordenador(userService.recuperar(idCoordenador));
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));
        justificativa.setRh(userService.recuperar(idRh));

        // se a justificativa existir atualiza
        if (this.justificativa.getJustificativaId() != 0) {
            // selecaoToPerfilEnum(this.user);
            justificativaService.updateJustificativaPonto(
                    this.justificativa);
            return SUCCESS;
        } else {
            justificativaService.addJustificativaPonto(justificativa);
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

    public void setJustificativaService(
            IJustificativaService justificativaService) {
        this.justificativaService = justificativaService;
    }
}