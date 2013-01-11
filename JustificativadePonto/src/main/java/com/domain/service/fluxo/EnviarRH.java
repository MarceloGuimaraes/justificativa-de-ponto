package com.domain.service.fluxo;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import javax.faces.model.SelectItem;
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
        temProximoPasso = true;
        permiteEditar = false;
        permiteCancelar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(userService.recuperaRH());
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
        User rh = getUser();
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

}
