package com.managed.bean;

import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.util.JavaMailApp;
import com.util.JsfUtil;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JustificativaManagedBean implements Serializable {


	private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "welcome";
    private static final String EDIT = "editJustificativa";

    private IJustificativaService justificativaService;
    private IUserService userService;
    private JavaMailApp mailApp;

    private PermissoesBean permissoes;

    private final HandlerMotivosManagedBean handler;

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

    public JustificativaManagedBean(IJustificativaService justificativaService,
                                    IUserService userService,
                                    JavaMailApp mailApp) {

        this.justificativaService = justificativaService;
        this.userService = userService;
        this.mailApp = mailApp;

        permissoes = JsfUtil.getValueExpression(PermissoesBean.class, "#{PermissoesBean}");

        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

        this.tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();

        userList = new ArrayList<SelectItem>();
        for(User u : this.userService.getUsers()){
            userList.add(new SelectItem(u.getUserId(), u.getNome()));
        }

        String id = JsfUtil.getParameter("id");
        if(id != null ){
            justificativa = this.justificativaService.recuperar(Integer.parseInt(id));
            idCoordenador = justificativa.getCoordenador().getUserId();
            if (justificativa.getSuperintendente()!=null) {
                idSuperintendente = justificativa.getSuperintendente().getUserId();
            }
            if (justificativa.getRh()!=null) {
                idRh = justificativa.getRh().getUserId();
            }
        }

        if (justificativa == null) {
            justificativa = new JustificativaPonto(permissoes.getUsuarioLogado());
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

    public List<SelectItem> getUserList() {

        return userList;

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

    //AUTOR SOLICITANTE
    public String enviarCoordenador() {

        //Inserindo o coordenador escolhido
        justificativa.setCoordenador(userService.recuperar(idCoordenador));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());

        mailApp.sendMail(justificativa.getSolicitante(), destinos, justificativa.getJustificativaId());

        justificativa.setStatus(StatusEnum.APROVCOORD);

        justificativa.adiciona(this.justificativa.getSolicitante(), TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }
    
    
    //AUTOR COORDENADOR
    public String enviarSuperintendente() {

        //Inserindo o superintendente escolhido
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getSolicitante());
        destinos.add(justificativa.getSuperintendente());

        mailApp.sendMail(justificativa.getCoordenador(), destinos, justificativa.getJustificativaId());

        justificativa.setStatus(StatusEnum.APROVSUPERINTENDENTE);
        justificativa.adiciona(justificativa.getCoordenador(), TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR);
        justificativa.adiciona(justificativa.getCoordenador(), TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }
    
    //AUTOR SUPERINTENDENTE
    public String enviarRh() {

        //Inserindo o Rh escolhidos
        justificativa.setRh(userService.recuperar(idRh));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());
        destinos.add(justificativa.getSolicitante());

        mailApp.sendMail(justificativa.getSuperintendente(), destinos, justificativa.getJustificativaId());

        justificativa.setStatus(StatusEnum.EXECUCAORH);

        justificativa.adiciona(justificativa.getSuperintendente(), TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE);
        justificativa.adiciona(justificativa.getSuperintendente(), TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }
    
   
    public String concluiRh() {

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getSolicitante());
        destinos.add(justificativa.getCoordenador());
        destinos.add(justificativa.getSuperintendente());

        mailApp.sendMail(justificativa.getRh(), destinos, justificativa.getJustificativaId());

        justificativa.setStatus(StatusEnum.APROVCOORD);

        
        justificativa.adiciona(justificativa.getRh(), TipoEventoJustificativaPontoEnum.APROVADO_RH);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }
    
    /* 
    public String enviarSuperintendente() {

        //Inserindo o coordenador, superintendente e Rh escolhidos
        justificativa.setCoordenador(userService.recuperar(idCoordenador));
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));
        justificativa.setRh(userService.recuperar(idRh));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());
        destinos.add(justificativa.getSuperintendente());

        mailApp.sendMail(justificativa.getSolicitante(), destinos, 234823);

        justificativa.setStatus(StatusEnum.APROVCOORD);

        justificativa.adiciona(justificativa.getSolicitante(), TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }*/
    
    
    
    
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

    public void setMailApp(JavaMailApp mailApp) {
        this.mailApp = mailApp;
    }
}