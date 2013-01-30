package com.domain.service.impl;

import com.domain.dto.AcessoJustificativa;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IWorkflow;
import com.managed.bean.IPermissoesBean;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;

import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public class Workflow implements IWorkflow {

    private IPermissoesBean permissoes;

    private IUserService userService;

    private IJustificativaService justificativaService;

    private IMailService mailService;

    public Workflow(IPermissoesBean permissoes,
                    IUserService userService,
                    IJustificativaService justificativaService,
                    IMailService mailService) {
        this.permissoes = permissoes;
        this.userService = userService;
        this.justificativaService = justificativaService;
        this.mailService = mailService;
    }

    @Override
    public AcessoJustificativa verificaAcesso(JustificativaPonto justificativa) {

        boolean editElaboracao = false;
        boolean editAguardaAprovCoord = false;
        boolean editAguardaAprovSuperintendente = false;
        boolean editAguardaAprovRh = false;
        boolean showFldCancelar = false;

        User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());

        if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && justificativa.getSolicitante().equals(usuarioLogado)) {

            editElaboracao = true;

        }

        // como o coordenador já foi selecionado, não faço validação
        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)
                && justificativa.getCoordenador().equals(usuarioLogado)) {

            editAguardaAprovCoord = true;

        }

        if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
                && justificativa.getSuperintendente().equals(usuarioLogado)) {

            editAguardaAprovSuperintendente = true;

        }

        if (justificativa.getStatus().equals(StatusEnum.EXECUCAORH)
                && justificativa.getRh().equals(usuarioLogado)) {

            editAguardaAprovRh = true;

        }

        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
                .getCoordenador().equals(usuarioLogado)) {

            showFldCancelar = true;

        }
        if(justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE) && justificativa
                .getSuperintendente().equals(usuarioLogado)){
            showFldCancelar = true;

        }
        if(justificativa.getStatus().equals(StatusEnum.EXECUCAORH) && justificativa
                .getRh().equals(usuarioLogado)){

            showFldCancelar = true;

        }
        if(!EnumSet.of(StatusEnum.ELABORACAO, StatusEnum.CANCELADO, StatusEnum.CONCLUIDO).contains(justificativa.getStatus()) &&  permissoes
                .isAdmin()) {

            showFldCancelar = true;

        }

        return new AcessoJustificativa(editElaboracao,
                editAguardaAprovCoord,
                editAguardaAprovSuperintendente,
                editAguardaAprovRh,
                showFldCancelar);
    }

    @Override
    public void enviarCoordenador(JustificativaPonto justificativa, Integer idCoordenador) {

        // Inserindo o coordenador escolhido
        justificativa.setCoordenador(userService.recuperar(idCoordenador));

        List<User> destinos = new LinkedList<User>();
        destinos.add(justificativa.getCoordenador());

        justificativaService.mudaSituacao(justificativa,
                permissoes.getUsuarioLogado(), StatusEnum.APROVCOORD,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR);

        mailService.enviarCoordenador(
                permissoes.getUsuarioLogado(),
                justificativa.getCoordenador(),
                justificativa.getJustificativaId()
        );

    }

    @Override
    public void enviarSuperintendente(JustificativaPonto justificativa, Integer idSuperintendente) {
        // Inserindo o superintendente escolhido
        justificativa.setSuperintendente(userService.recuperar(idSuperintendente));

        justificativa.setDtAprovCoord(new Date());

        justificativaService.mudaSituacao(
                justificativa,
                permissoes.getUsuarioLogado(),
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        mailService.enviarSuperintendente(
                permissoes.getUsuarioLogado(),
                justificativa.getSolicitante(),
                justificativa.getSuperintendente(),
                justificativa.getJustificativaId()
        );
    }

    @Override
    public void enviarRh(JustificativaPonto justificativa, Integer idRh) {

        // Inserindo o Rh escolhidos
        justificativa.setRh(userService.recuperar(idRh));

        justificativa.setDtAprovSuper(new Date());

        justificativaService.mudaSituacao(justificativa,
                permissoes.getUsuarioLogado(), StatusEnum.EXECUCAORH,
                TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH);

        mailService.enviarRh(
                permissoes.getUsuarioLogado(),
                justificativa.getSolicitante(),
                justificativa.getCoordenador(),
                justificativa.getRh(),
                justificativa.getJustificativaId());
    }

    @Override
    public void concluir(JustificativaPonto justificativa) {

        justificativa.setDtAprovRh(new Date());

        justificativaService.mudaSituacao(justificativa,
                permissoes.getUsuarioLogado(), StatusEnum.CONCLUIDO,
                TipoEventoJustificativaPontoEnum.APROVADO_RH);

        mailService.concluiRh(
                permissoes.getUsuarioLogado(),
                justificativa.getSolicitante(),
                justificativa.getCoordenador(),
                justificativa.getSuperintendente(),
                justificativa.getJustificativaId());

    }

    @Override
    public void cancelar(JustificativaPonto justificativa) {

        boolean cancela = false;

        User userCorrente = userService.recuperar(permissoes.getUsuarioLogado().getId());

        List<User> destinos = new LinkedList<User>();

        if (StatusEnum.APROVCOORD.equals(justificativa.getStatus()) &&
                justificativa.getCoordenador().equals(userCorrente)) {
            // AUTOR COORDENADOR
            cancela = true;

        } else if (StatusEnum.APROVSUPERINTENDENTE.equals(justificativa.getStatus()) &&
                (justificativa.getSuperintendente().equals(userCorrente) || permissoes.isAdmin())) {
            // AUTOR SUPERINTENDENTE
            destinos.add(justificativa.getCoordenador());
            cancela = true;

        } else if (StatusEnum.EXECUCAORH.equals(justificativa.getStatus()) &&
                (justificativa.getRh().equals(userCorrente) || permissoes.isAdmin())) {
            // AUTOR RH
            destinos.add(justificativa.getCoordenador());
            destinos.add(justificativa.getSuperintendente());
            cancela = true;

        } else if (permissoes.isAdmin()) {
            cancela = true;
        }

        if (cancela) {

            justificativaService.cancelar(
                    permissoes.getUsuarioLogado(),
                    justificativa
            );

            mailService.cancelado(
                    permissoes.getUsuarioLogado(),
                    justificativa.getSolicitante(),
                    destinos,
                    justificativa.getJustificativaId()
            );

        } else {
            throw new BusinessException("dialog.cancelar.valida.usuarioinvalido");
        }
    }
}
