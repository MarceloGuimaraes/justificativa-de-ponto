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
import com.service.mail.JavaMailService;
import com.util.JsfUtil;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class JustificativaManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "welcome";

    private IJustificativaService justificativaService;
    private IUserService userService;
    private JavaMailService mailService;

    private IPermissoesBean permissoes;

    private HandlerMotivosManagedBean handler;

    private List<SelectItem> tipoBancoHorasList;
    private List<SelectItem> tipoDecisaoList;
    private List<SelectItem> coordenadorList;
    private List<SelectItem> superintendenteList;
    private List<SelectItem> rhList;

    private JustificativaPonto justificativa;

    private Integer idCoordenador;
    private Integer idSuperintendente;
    private Integer idRh;


    private boolean editElaboracao = false;
    private boolean editAguardaAprovCoord = false;
    private boolean editAguardaAprovRh = false;
    private boolean editAguardaAprovSuperintendente = false;
    private boolean showFldCancelar = false;

    public JustificativaManagedBean(IJustificativaService justificativaService,
                                    IUserService userService,
                                    JavaMailService mailService,
                                    IPermissoesBean permissoes) {

        this.justificativaService = justificativaService;
        this.userService = userService;
        this.mailService = mailService;

        this.permissoes = permissoes;

        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

        tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl()
                .findObjects();

        coordenadorList = retornaItemAPartirDeUser(userService.recuperaCoordenadores());
        superintendenteList = retornaItemAPartirDeUser(userService.recuperaSuperintendentes());
        rhList = retornaItemAPartirDeUser(userService.recuperaRH());

        JustificativaPonto justificativaRecebida = null;

        String id = JsfUtil.getParameter("id");

        if (id != null) {
            justificativaRecebida = this.justificativaService.recuperar(Integer
                    .parseInt(id));
            idCoordenador = justificativaRecebida.getCoordenador().getId();
            if (justificativaRecebida.getSuperintendente() != null) {
                idSuperintendente = justificativaRecebida.getSuperintendente()
                        .getId();
            }
            if (justificativaRecebida.getRh() != null) {
                idRh = justificativaRecebida.getRh().getId();
            }
        }

        if (justificativaRecebida == null) {
            justificativaRecebida = new JustificativaPonto(
                    permissoes.getUsuarioLogado());
        }

        setJustificativa(justificativaRecebida);

    }


    public HandlerMotivosManagedBean getHandler() {
        return handler;
    }

    public List<SelectItem> getCoordenadorList() {
        return coordenadorList;
    }

    public void setCoordenadorList(List<SelectItem> coordenadorList) {
        this.coordenadorList = coordenadorList;
    }

    public List<SelectItem> getSuperintendenteList() {
        return superintendenteList;
    }

    public void setSuperintendenteList(List<SelectItem> superintendenteList) {
        this.superintendenteList = superintendenteList;
    }

    public List<SelectItem> getRhList() {
        return rhList;
    }

    public void setRhList(List<SelectItem> rhList) {
        this.rhList = rhList;
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

        editElaboracao = permissoes.editElaboracao(justificativa);
        editAguardaAprovCoord = permissoes.editAguardaAprovCoord(justificativa);
        editAguardaAprovSuperintendente = permissoes.editAguardaAprovSuperintendente(justificativa);
        editAguardaAprovRh = permissoes.editAguardaAprovRh(justificativa);
        showFldCancelar = permissoes.showFldCancelar(justificativa);

        handler = new HandlerMotivosManagedBean(justificativa.getMotivo());

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



    // AUTOR SOLICITANTE
    public String enviarCoordenador() {

        // Inserindo o coordenador escolhido
        justificativa.setCoordenador(userService.recuperar(idCoordenador));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());

        mailService.sendMail(justificativa.getSolicitante(), destinos,
                justificativa.getJustificativaId());

        justificativa.setStatus(StatusEnum.APROVCOORD);

        justificativa.adiciona(permissoes.getUsuarioLogado(),
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR);

        justificativaService.adicionar(justificativa);
        return SUCCESS;
    }

    // AUTOR COORDENADOR
    public String enviarSuperintendente() {

        // Inserindo o superintendente escolhido
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getSolicitante());
        destinos.add(justificativa.getSuperintendente());

        mailService.sendMail(justificativa.getCoordenador(), destinos,
                justificativa.getJustificativaId());

        justificativa.setDtAprovCoord(new Date());
        justificativa.setStatus(StatusEnum.APROVSUPERINTENDENTE);
        justificativa.adiciona(justificativa.getCoordenador(),
                TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR);
        justificativa
                .adiciona(permissoes.getUsuarioLogado(),
                        TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        justificativaService.atualizar(justificativa);

        return SUCCESS;
    }

    // AUTOR SUPERINTENDENTE
    public String enviarRh() {

        // Inserindo o Rh escolhidos
        justificativa.setRh(userService.recuperar(idRh));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());
        destinos.add(justificativa.getSolicitante());
        destinos.add(justificativa.getRh());

        mailService.sendMail(justificativa.getSuperintendente(), destinos,
                justificativa.getJustificativaId());

        justificativa.setDtAprovSuper(new Date());
        justificativa.setStatus(StatusEnum.EXECUCAORH);
        justificativa.adiciona(permissoes.getUsuarioLogado(),
                TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE);
        justificativa.adiciona(permissoes.getUsuarioLogado(),
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH);

        justificativaService.atualizar(justificativa);
        return SUCCESS;
    }

    // AUTOR RH
    public String concluiRh() {

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getSolicitante());
        destinos.add(justificativa.getCoordenador());
        destinos.add(justificativa.getSuperintendente());

        mailService.sendMail(justificativa.getRh(), destinos,
                justificativa.getJustificativaId());

        justificativa.setDtAprovRh(new Date());
        justificativa.setStatus(StatusEnum.CONCLUIDO);
        justificativa.adiciona(permissoes.getUsuarioLogado(),
                TipoEventoJustificativaPontoEnum.APROVADO_RH);
        justificativaService.atualizar(justificativa);
        return SUCCESS;
    }

    public String cancelado() {

        List<User> destinos = new LinkedList<User>();

        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD) || permissoes.isAdmin()){
            //AUTOR COORDENADOR
            destinos.add(justificativa.getSolicitante());
            mailService.sendMail(justificativa.getCoordenador(), destinos, justificativa.getJustificativaId());
            justificativa.adiciona(justificativa.getCoordenador(), TipoEventoJustificativaPontoEnum.CANCELADO);

        }else if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE) || permissoes.isAdmin()){
            //AUTOR SUPERINTENDENTE
            destinos.add(justificativa.getSolicitante());
            destinos.add(justificativa.getCoordenador());
            mailService.sendMail(justificativa.getSuperintendente(), destinos, justificativa.getJustificativaId());
            justificativa.adiciona(justificativa.getSuperintendente(), TipoEventoJustificativaPontoEnum.CANCELADO);

        }else if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE) || permissoes.isAdmin()){
            //AUTOR RH
            destinos.add(justificativa.getSolicitante());
            destinos.add(justificativa.getCoordenador());
            destinos.add(justificativa.getSuperintendente());
            mailService.sendMail(justificativa.getRh(), destinos, justificativa.getJustificativaId());
            justificativa.adiciona(justificativa.getRh(), TipoEventoJustificativaPontoEnum.CANCELADO);
        }

        justificativa.setDtCancelamento(new Date());
        justificativa.setStatus(StatusEnum.CANCELADO);
        justificativaService.atualizar(justificativa);
        return SUCCESS;
    }



    public boolean isEditElaboracao() {
        return editElaboracao;
    }


    public boolean isShowFldCancelar() {
        return showFldCancelar;
    }

    public boolean isEditAguardaAprovCoord() {
        return editAguardaAprovCoord;
    }


    public boolean isEditAguardaAprovRh() {
        return editAguardaAprovRh;
    }


    public boolean isEditAguardaAprovSuperintendente() {
        return editAguardaAprovSuperintendente;
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

    private List<SelectItem> retornaItemAPartirDeUser(List<User> users){
        if(users==null){
            return null;
        }

        List<SelectItem> resultado = new LinkedList<SelectItem>();

        for(User u : users){
            resultado.add(new SelectItem(u.getId(), u.getNome()));
        }

        return resultado;
    }

}