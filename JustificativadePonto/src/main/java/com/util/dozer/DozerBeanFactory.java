package com.util.dozer;

import com.domain.dto.HistoricoJustificativaPontoDTO;
import com.model.EncaminhamentoJustificativaPonto;
import com.model.HistoricoJustificativaPonto;
import com.model.Identificacao;
import com.model.TipoEventoJustificativaPontoEnum;
import org.dozer.BeanFactory;

import java.util.EnumSet;

public class DozerBeanFactory implements BeanFactory {
    @Override
    public Object createBean(Object o, Class<?> aClass, String s) {
        HistoricoJustificativaPontoDTO source = (HistoricoJustificativaPontoDTO) o;
        EnumSet<TipoEventoJustificativaPontoEnum> tiposEventosEncaminhamento = EnumSet.of(
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH
        );
        if(tiposEventosEncaminhamento.contains(source.getTipoEvento())){
            EncaminhamentoJustificativaPonto resultado = new EncaminhamentoJustificativaPonto();
            Identificacao responsavel = new Identificacao(
                    source.getResponsavel().getId(),
                    source.getResponsavel().getNome(),
                    source.getResponsavel().getCpf(),
                    source.getResponsavel().getEmail()
            );
            resultado.setResponsavel(responsavel);
            return resultado;
        }else{
            return new HistoricoJustificativaPonto();
        }
    }
}
