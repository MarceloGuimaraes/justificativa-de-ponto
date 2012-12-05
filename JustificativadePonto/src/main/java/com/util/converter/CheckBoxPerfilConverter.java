package com.util.converter;

import com.model.PerfilEnum;
import com.util.IEnumeracaoComCodigoDescricao;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CheckBoxPerfilConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null){
            return null;
        }
        return PerfilEnum.fromSigla(Integer.parseInt(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o == null){
            return null;
        }
        return ((IEnumeracaoComCodigoDescricao)o).getCodigo().toString();

    }
}
