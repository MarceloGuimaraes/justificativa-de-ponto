package com.domain.service.impl;

import com.domain.dto.*;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IWorkflow;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Workflow implements IWorkflow {

    private IPermissoesBean permissoes;

    private IUserService userService;

    private IJustificativaService justificativaService;

    private IMailService mailService;

    private Mapper mapper;

    public Workflow(IPermissoesBean permissoes,
                    IUserService userService,
                    IJustificativaService justificativaService,
                    IMailService mailService,
                    Mapper mapper) {
        this.permissoes = permissoes;
        this.userService = userService;
        this.justificativaService = justificativaService;
        this.mailService = mailService;
        this.mapper = mapper;
    }

    @Override
    public ProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativaTela){

        Identificacao usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        JustificativaPonto justificativa = null;

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = mapper.map(justificativaTela, JustificativaPonto.class);
        }else{
            justificativa = justificativaService.recuperar(justificativaTela);
        }

        if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && justificativa.getSolicitante().equals(mapper.map(permissoes.getUsuarioLogado(), User.class))) {

            return new EnviarCoordenador(this);

        }

        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);

        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel().equals(usuarioLogado)) {

            return new EnviarSuperintendente(this);

        }

        if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(usuarioLogado)) {

            return new EnviarRH(this);

        }

        if (justificativa.getStatus().equals(StatusEnum.EXECUCAORH)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH).getResponsavel().equals(usuarioLogado)) {

            return new Concluir(this);

        }

        if(!EnumSet.of(StatusEnum.ELABORACAO, StatusEnum.CANCELADO, StatusEnum.CONCLUIDO).contains(justificativa.getStatus()) &&  permissoes
                .isAdmin()) {

            return null;

        }

        return new PassoSemAcesso();

    }

    private Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> retornaHistoricosMapeados(JustificativaPonto justificativa) {
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

    public IUserService getUserService() {
        return userService;
    }

    @Override
    @Transactional(readOnly = false)
    public void enviarCoordenador(JustificativaPontoDTO justificativa, Integer idCoordenador) {

        // Inserindo o coordenador escolhido
        User coordenador = userService.recuperar(idCoordenador);

        List<User> destinos = new LinkedList<User>();
        destinos.add(coordenador);

        justificativa = justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(coordenador, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVCOORD,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR
        );

        mailService.enviarCoordenador(
                permissoes.getUsuarioLogado(),
                coordenador,
                justificativa.getId()
        );

    }

    @Override
    @Transactional(readOnly = false)
    public void enviarSuperintendente(JustificativaPontoDTO justificativa, Integer idSuperintendente) {
        // Inserindo o superintendente escolhido
        User superintendente=userService.recuperar(idSuperintendente);
        User solicitante = mapper.map(justificativa.getSolicitante(), User.class);

        justificativaService.atua(
                permissoes.getUsuarioLogado(),
                justificativa,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR);

        justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(superintendente, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        mailService.enviarSuperintendente(
                permissoes.getUsuarioLogado(),
                solicitante,
                superintendente,
                justificativa.getId()
        );
    }

    @Override
    @Transactional(readOnly = false)
    public void enviarRh(JustificativaPontoDTO justificativa, Integer idRh) {

        // Inserindo o Rh escolhidos
        User rh = userService.recuperar(idRh);
        User solicitante = mapper.map(justificativa.getSolicitante(), User.class);

        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativa);
        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPersistida);

        User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);

        justificativaService.atua(
                permissoes.getUsuarioLogado(),
                justificativa,
                StatusEnum.EXECUCAORH,
                TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE);

        justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(rh, UsuarioLogado.class),
                justificativa,
                StatusEnum.EXECUCAORH,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH);

        mailService.enviarRh(
                permissoes.getUsuarioLogado(),
                solicitante,
                coordenador,
                rh,
                justificativa.getId());
    }

    @Override
    @Transactional(readOnly = false)
    public void concluir(JustificativaPontoDTO justificativa) {

        justificativaService.atua(
                permissoes.getUsuarioLogado(),
                justificativa,
                StatusEnum.CONCLUIDO,
                TipoEventoJustificativaPontoEnum.APROVADO_RH);

        User solicitante = mapper.map(justificativa.getSolicitante(), User.class);

        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativa);
        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPersistida);

        User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
        User superintendente = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel(), User.class);

        mailService.concluiRh(
                permissoes.getUsuarioLogado(),
                solicitante,
                coordenador,
                superintendente,
                justificativa.getId());

    }

    @Override
    @Transactional(readOnly = false)
    public void cancelar(JustificativaPontoDTO justificativaTela) {

        boolean cancela = false;

        Identificacao userCorrente = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        List<User> destinos = new LinkedList<User>();
        JustificativaPonto justificativa = justificativaService.recuperar(justificativaTela);
        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);
        if (StatusEnum.APROVCOORD.equals(justificativa.getStatus()) &&
                historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel().equals(userCorrente)) {
            // AUTOR COORDENADOR
            cancela = true;

        } else if (StatusEnum.APROVSUPERINTENDENTE.equals(justificativa.getStatus()) &&
                (historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(userCorrente)
                        || permissoes.isAdmin())) {
            // AUTOR SUPERINTENDENTE
            User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
            destinos.add(coordenador);
            cancela = true;

        } else if (StatusEnum.EXECUCAORH.equals(justificativa.getStatus()) &&
                (historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH).getResponsavel().equals(userCorrente)
                        || permissoes.isAdmin())) {
            // AUTOR RH
            User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
            User superintendente = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel(), User.class);
            destinos.add(coordenador);
            destinos.add(superintendente);
            cancela = true;

        } else if (permissoes.isAdmin()) {
            cancela = true;
        }

        if (cancela) {

            justificativaService.cancelar(
                    permissoes.getUsuarioLogado(),
                    justificativaTela
            );

            mailService.cancelado(
                    permissoes.getUsuarioLogado(),
                    justificativa.getSolicitante(),
                    destinos,
                    justificativa.getId()
            );

        } else {
            throw new BusinessException("dialog.cancelar.valida.usuarioinvalido");
        }
    }

}
