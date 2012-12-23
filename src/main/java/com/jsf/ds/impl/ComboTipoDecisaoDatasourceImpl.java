package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.TipoDecisaoEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboTipoDecisaoDatasourceImpl implements ISelectItemDatasource {
    @Override
    public List<SelectItem> findObjects() {
        List<SelectItem> resultado = new ArrayList<SelectItem>();
        for(TipoDecisaoEnum t : TipoDecisaoEnum.values()){
            resultado.add(new SelectItem(t, Message.getBundleMessage(t.getDescricao())));
        }

        return resultado;
    }
}
