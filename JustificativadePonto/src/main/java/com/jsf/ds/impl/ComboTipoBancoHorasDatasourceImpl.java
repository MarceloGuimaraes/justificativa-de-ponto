package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.TipoBancoHorasEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboTipoBancoHorasDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        List<SelectItem> resultado = new ArrayList<SelectItem>();
        resultado.add(new SelectItem(TipoBancoHorasEnum.GOZO, Message.getBundleMessage("bancohoras.descricao.gozo")));
        resultado.add(new SelectItem(TipoBancoHorasEnum.HORAEXTRA, Message.getBundleMessage("bancohoras.descricao.horaextra")));
        return resultado;
    }
}
