package com.util.converter;

import com.util.IEnumeracaoComCodigoDescricao;
import com.util.Message;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * User: rxonda
 * Date: 1/14/13
 * Time: 10:33 PM
 * Conversor dos Enumeradores que implementam a Interface IEnumeracaoComCodigoDescricao
 */
public class EnumeracaoComCodigoDescricaoConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {

        return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o==null){
            return null;
        }
        if(o instanceof IEnumeracaoComCodigoDescricao){
            IEnumeracaoComCodigoDescricao e = (IEnumeracaoComCodigoDescricao) o;
            return Message.getBundleMessage(e.getDescricao());
        }
        return null;
    }
}
