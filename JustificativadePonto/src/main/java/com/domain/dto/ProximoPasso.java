package com.domain.dto;

import com.domain.service.IProximoPasso;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import com.spring.util.ApplicationContextProvider;
import org.dozer.Mapper;

import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public abstract class ProximoPasso implements IProximoPasso {

    private Integer id;

    protected boolean temProximoPasso;

    protected boolean permiteEditar;

    protected boolean permiteCancelar;

    protected boolean concluir;

    private transient List<SelectItem> escolhas;

    protected transient IJustificativaService justificativaService;

    protected transient IUserService userService;

    protected transient IMailService mailService;

    protected transient IPermissoesBean permissoes;

    protected transient Mapper mapper;


    protected ProximoPasso() {
    }

    public ProximoPasso(IJustificativaService justificativaService,
                        IUserService userService,
                        IMailService mailService,
                        IPermissoesBean permissoes,
                        Mapper mapper) {
        this.justificativaService = justificativaService;
        this.userService = userService;
        this.mailService = mailService;
        this.permissoes = permissoes;
        this.mapper = mapper;
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

    protected Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> retornaHistoricosMapeados(JustificativaPonto justificativa) {
        EnumSet<TipoEventoJustificativaPontoEnum> tiposEventosEncaminhamento = EnumSet.of(
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH
        );

        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = new LinkedHashMap<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto>();
        for(HistoricoJustificativaPonto h : justificativa.getHistorico()){
            if(tiposEventosEncaminhamento.contains(h.getTipoEvento())){
                historicos.put(h.getTipoEvento(), (EncaminhamentoJustificativaPonto)h);
            }
        }
        return historicos;
    }

    protected User getUser(){
        return userService.recuperar(getId());
    }

    private void readObject(ObjectInputStream o) throws ClassNotFoundException, IOException {

        o.defaultReadObject();

        justificativaService = (IJustificativaService) ApplicationContextProvider.getBean("JustificativaService");
        userService = (IUserService) ApplicationContextProvider.getBean("UserService");
        mailService = (IMailService) ApplicationContextProvider.getBean("mailService");
        permissoes = (IPermissoesBean) ApplicationContextProvider.getBean("PermissoesBean");
        mapper = (Mapper) ApplicationContextProvider.getBean("mapper");

        escolhas = populaEscolhas();
    }

}
