package com.domain.service.fluxo;

import com.domain.service.IProximoPasso;
import com.managed.bean.IPermissoesBean;
import com.model.EncaminhamentoJustificativaPonto;
import com.model.HistoricoJustificativaPonto;
import com.model.JustificativaPonto;
import com.model.TipoEventoJustificativaPontoEnum;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

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

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(false, false, false, false, false);
    }

    private static final String TEM_INFORMAR_DECISAO = "temInformarDecisao";
    private static final String TEM_PROXIMO_PASSO = "temProximoPasso";
    private static final String TEM_CONCLUIR = "temConcluir";
    private static final String TEM_CANCELAR = "temCancelar";
    private static final String PERMITE_EDITAR= "permiteEditar";
    protected Map<String,Boolean> criaViewHandler(boolean permiteEditar,
                                                  boolean informaDecisao,
                                                  boolean temProximoPasso,
                                                  boolean conclui,
                                                  boolean cancela){
        Map<String,Boolean> resultado = new LinkedHashMap<String, Boolean>();
        resultado.put(PERMITE_EDITAR, permiteEditar);
        resultado.put(TEM_INFORMAR_DECISAO, informaDecisao);
        resultado.put(TEM_PROXIMO_PASSO, temProximoPasso);
        resultado.put(TEM_CONCLUIR, conclui);
        resultado.put(TEM_CANCELAR, cancela);
        return resultado;
    }
}
