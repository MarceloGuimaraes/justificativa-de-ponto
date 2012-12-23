package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.TipoFaltaEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboTipoFaltaDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        List<SelectItem> resultado = new ArrayList<SelectItem>();
        resultado.add(new SelectItem(TipoFaltaEnum.ATESTADOMEDICO, Message.getBundleMessage("tipofalta.descricao.atestadomedico")));
        resultado.add(new SelectItem(TipoFaltaEnum.CASAMENTO, Message.getBundleMessage("tipofalta.descricao.casamento")));
        resultado.add(new SelectItem(TipoFaltaEnum.LICENCAPATERNIDADE, Message.getBundleMessage("tipofalta.descricao.licencapaternidade")));
        resultado.add(new SelectItem(TipoFaltaEnum.LUTO, Message.getBundleMessage("tipofalta.descricao.luto")));
        resultado.add(new SelectItem(TipoFaltaEnum.OUTROS, Message.getBundleMessage("tipofalta.descricao.outros")));

        return resultado;
    }
}
