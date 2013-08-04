package com.service;

import com.dao.IJustificativaDAO;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

@Transactional(readOnly = true)
public class JustificativaService implements IJustificativaService {

	private IJustificativaDAO dao;


    public JustificativaService(IJustificativaDAO dao) {
        this.dao = dao;
    }

    @Override
	@Transactional(readOnly = false)
	public JustificativaPonto adicionar(JustificativaPonto justificativa) {
        if(justificativa.getId()!=null && justificativa.getId()!=0){
            throw new IllegalArgumentException("Nao eh possivel adicionar uma justificativa existente");
        }
		Serializable id = dao.adicionar(justificativa);
        return dao.recuperar(id);
	}

    @Override
	@Transactional(readOnly = false)
	public JustificativaPonto atualizar(JustificativaPonto justificativa) {
		return dao.atualizar(justificativa);
	}

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void encaminha(User usuarioLogado,
                          User proximoResponsavel,
                          JustificativaPonto justificativaPonto,
                          StatusEnum novoStatus,
                          TipoEventoJustificativaPontoEnum eventoHistorico) {
        if(justificativaPonto.getId()==null || justificativaPonto.getId()==0){
            throw new IllegalArgumentException("Justificativa nao esta cadastrada no sistema");
        }

        if(eventoHistorico==null){
            throw new IllegalArgumentException("Nao foi informado o evento de historico");
        }

        justificativaPonto.setStatus(novoStatus);

        justificativaPonto.encaminha(usuarioLogado, proximoResponsavel, eventoHistorico);

        atualizar(justificativaPonto);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void atua(User usuarioLogado,
                     JustificativaPonto justificativaPonto,
                     StatusEnum novoStatus,
                     TipoEventoJustificativaPontoEnum tipoEvento) {
        if(justificativaPonto.getId()==null || justificativaPonto.getId()==0){
            throw new IllegalArgumentException("Justificativa nao esta cadastrada no sistema");
        }

        if(tipoEvento==null){
            throw new IllegalArgumentException("Nao foi informado o evento de historico");
        }

        justificativaPonto.setStatus(novoStatus);

        justificativaPonto.adiciona(usuarioLogado, tipoEvento);

        atualizar(justificativaPonto);
    }

    @Override
    public void atua(User usuarioLogado, JustificativaPonto justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum tipoEvento, String observacao) {
        if(justificativaPonto.getId()==null || justificativaPonto.getId()==0){
            throw new IllegalArgumentException("Justificativa nao esta cadastrada no sistema");
        }

        if(tipoEvento==null){
            throw new IllegalArgumentException("Nao foi informado o evento de historico");
        }

        justificativaPonto.setStatus(novoStatus);

        justificativaPonto.adiciona(usuarioLogado, tipoEvento, observacao);

        atualizar(justificativaPonto);
    }

    @Override
    public JustificativaPonto recuperar(Serializable id) {
        return dao.recuperar(id);
    }

    @Override
    public JustificativaPonto recuperar(Serializable id, String... atributos) {
        JustificativaPonto j = dao.recuperar(id);
        for(String a : atributos){
            try {
                Object proxy = BeanUtils.getProperty(j, a);
                dao.initialize(proxy);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("O atributo informado nao possui o acesso adequado.", e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException("Erro inesperado ao acessar o atributo.", e);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("O atributo informado nao existe na classe.", e);
            }
        }
        return j;
    }

}
