package com.util;

import javax.faces.context.FacesContext;

public class JsfUtil {

    public static <T> T getValueExpression(Class<T> clazz, String formula){
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), formula, clazz)
                .getValue(context.getELContext());
    }

}
