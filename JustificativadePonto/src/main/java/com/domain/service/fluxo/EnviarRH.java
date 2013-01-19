package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

public class EnviarRH extends ProximoPasso {

    public EnviarRH(IJustificativaService justificativaService,
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

        if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(usuarioLogado)) {

            return true;

        }
        return false;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o Rh escolhidos
        User rh = userService.recuperar(justificativa.getIdProximoResponsavel());
        User solicitante = mapper.map(justificativa.getSolicitante(), User.class);

        JustificativaPontoDTO justificativaAtualizada = justificativaService.atualizar(justificativa);
        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativaAtualizada);
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
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaRH();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(false,true, true, false, true);
    }

}
