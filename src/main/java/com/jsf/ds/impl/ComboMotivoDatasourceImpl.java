package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.MotivoEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboMotivoDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        List<SelectItem> resultado = new ArrayList<SelectItem>(6);
        resultado.add(new SelectItem(MotivoEnum.ATIVIDADEFORA, Message.getBundleMessage("motivo.descricao.atividadefora")));
        resultado.add(new SelectItem(MotivoEnum.ATRASOS, Message.getBundleMessage("motivo.descricao.atrasos")));
        resultado.add(new SelectItem(MotivoEnum.BANCODEHORAS, Message.getBundleMessage("motivo.descricao.bancodehoras")));
        resultado.add(new SelectItem(MotivoEnum.FALTADEMARCACAO, Message.getBundleMessage("motivo.descricao.faltamarcacao")));
        resultado.add(new SelectItem(MotivoEnum.FALTAS, Message.getBundleMessage("motivo.descricao.faltas")));
        resultado.add(new SelectItem(MotivoEnum.SAIDAANTECIPADA, Message.getBundleMessage("motivo.descricao.saidaantecipada")));
        return resultado;
    }
}
