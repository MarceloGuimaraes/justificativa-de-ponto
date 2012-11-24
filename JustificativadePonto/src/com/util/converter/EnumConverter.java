package com.util.converter;

import javax.faces.component.UIComponent;


import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class EnumConverter implements Converter {
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		Class enumType = component.getValueExpression("value").getType(
				context.getELContext());
		return Enum.valueOf(enumType, value);
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object object) throws ConverterException {
		if (object == null) {
			return null;
		}
		Enum type = (Enum) object;
		return type.toString();
	}
}
