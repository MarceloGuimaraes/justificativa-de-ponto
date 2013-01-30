package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.PerfilEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboPerfisDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        List<SelectItem> resultado = new ArrayList<SelectItem>();
        for(PerfilEnum p : PerfilEnum.values()){
            resultado.add(new SelectItem(p, Message.getBundleMessage(p.getDescricao())));
        }
        return resultado;
    }
}
