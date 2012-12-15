package com.domain.service.impl;

import com.domain.dto.AcessoJustificativa;
import com.domain.service.IWorkflow;
import com.managed.bean.IPermissoesBean;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.User;
import com.service.IUserService;
import org.dozer.Mapper;

public class Workflow implements IWorkflow {

    IPermissoesBean permissoes;

    IUserService userService;

    Mapper mapper;

    public Workflow(IPermissoesBean permissoes,
                    IUserService userService,
                    Mapper mapper) {
        this.permissoes = permissoes;
        this.userService = userService;
        this.mapper = mapper;
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

        if ((justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
                .getCoordenador().equals(usuarioLogado))
                || (justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
                .getCoordenador().equals(usuarioLogado))
                || (justificativa.getStatus().equals(
                StatusEnum.APROVSUPERINTENDENTE) && justificativa
                .getSuperintendente().equals(usuarioLogado))
                || ((!justificativa.getStatus().equals(StatusEnum.ELABORACAO)) && permissoes
                .isAdmin())) {

            showFldCancelar = true;

        }

        return new AcessoJustificativa(editElaboracao,
                editAguardaAprovCoord,
                editAguardaAprovSuperintendente,
                editAguardaAprovRh,
                showFldCancelar);
    }
}
