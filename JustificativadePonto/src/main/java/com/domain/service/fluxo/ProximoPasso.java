package com.domain.service.fluxo;

import com.domain.service.IProximoPasso;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import com.spring.util.ApplicationContextProvider;
import org.dozer.Mapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ProximoPasso implements IProximoPasso {

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
    }

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

    private void readObject(ObjectInputStream o) throws ClassNotFoundException, IOException {

        o.defaultReadObject();

        justificativaService = (IJustificativaService) ApplicationContextProvider.getBean("JustificativaService");
        userService = (IUserService) ApplicationContextProvider.getBean("UserService");
        mailService = (IMailService) ApplicationContextProvider.getBean("mailService");
        permissoes = (IPermissoesBean) ApplicationContextProvider.getBean("PermissoesBean");
        mapper = (Mapper) ApplicationContextProvider.getBean("mapper");

    }

}
