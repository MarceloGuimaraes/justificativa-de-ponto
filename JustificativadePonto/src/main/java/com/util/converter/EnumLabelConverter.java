package com.util.converter;

import com.util.IEnumeracaoComCodigoDescricao;
import com.util.JustUtil;
import com.util.Message;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class EnumLabelConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) throws ConverterException {

        Class enumType = component.getValueExpression("value").getType(
                context.getELContext());

        Enum e = null;
        if (JustUtil.isNaoNuloOuVazio(value)) {
            e = Enum.valueOf(enumType, value.trim());
        }

        return e;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) throws ConverterException {

        if (value == null) {
            return null;
        }

        return Message.getBundleMessage(((IEnumeracaoComCodigoDescricao)value).getDescricao());

    }
}
