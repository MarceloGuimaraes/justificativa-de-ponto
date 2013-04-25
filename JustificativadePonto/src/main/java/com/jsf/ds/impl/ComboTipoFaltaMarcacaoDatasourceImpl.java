package com.jsf.ds.impl;

import com.jsf.ds.ISelectItemDatasource;
import com.model.TipoFaltaMarcacaoEnum;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ComboTipoFaltaMarcacaoDatasourceImpl implements ISelectItemDatasource {
	@Override
	public List<SelectItem> findObjects() {
		List<SelectItem> resultado = new ArrayList<SelectItem>();
		resultado.add(new SelectItem(TipoFaltaMarcacaoEnum.ENTRADA, Message.getBundleMessage("faltamarcacao.descricao.entrada")));
		resultado.add(new SelectItem(TipoFaltaMarcacaoEnum.SAIDA, Message.getBundleMessage("faltamarcacao.descricao.saida")));
		return resultado;
	}

}
