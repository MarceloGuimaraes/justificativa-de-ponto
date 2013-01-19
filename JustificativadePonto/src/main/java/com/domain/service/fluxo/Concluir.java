package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

public class Concluir extends ProximoPasso {

    public Concluir(IJustificativaService justificativaService,
                    IUserService userService,
                    IMailService mailService,
                    IPermissoesBean permissoes,
                    Mapper mapper) {
        super(
                justificativaService,
                userService,
                mailService,
                permissoes,
                mapper
        );
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        Map<TipoEventoJustificativaPontoEnum,EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);

        Identificacao usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        if (justificativa.getStatus().equals(StatusEnum.EXECUCAORH)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH).getResponsavel().equals(usuarioLogado)) {

            return true;

        }

        return false;
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaRH();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(false, false, false, true, true);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
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

}
