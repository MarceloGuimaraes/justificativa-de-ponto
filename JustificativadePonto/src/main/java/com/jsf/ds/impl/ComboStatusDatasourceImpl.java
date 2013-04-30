package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.StatusEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.LinkedList;
import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 9:46 PM
 */
public class ComboStatusDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        final List<SelectItem> resultado = new LinkedList<SelectItem>();
        resultado.add(new SelectItem(StatusEnum.ELABORACAO, Message.getBundleMessage(StatusEnum.ELABORACAO.getDescricao())));
        resultado.add(new SelectItem(StatusEnum.APROVCOORD, Message.getBundleMessage(StatusEnum.APROVCOORD.getDescricao())));
        resultado.add(new SelectItem(StatusEnum.APROVSUPERINTENDENTE, Message.getBundleMessage(StatusEnum.APROVSUPERINTENDENTE.getDescricao())));
        resultado.add(new SelectItem(StatusEnum.EXECUCAORH, Message.getBundleMessage(StatusEnum.EXECUCAORH.getDescricao())));
        resultado.add(new SelectItem(StatusEnum.CONCLUIDO, Message.getBundleMessage(StatusEnum.CONCLUIDO.getDescricao())));
        resultado.add(new SelectItem(StatusEnum.CANCELADO, Message.getBundleMessage(StatusEnum.CANCELADO.getDescricao())));
        return resultado;
    }
}
