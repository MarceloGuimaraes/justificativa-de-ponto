package com.util.converter;

import com.util.Message;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


public class MesConverter implements Converter {

	String meses[] = {
            "justificativa.label.mes.01",
            "justificativa.label.mes.02",
            "justificativa.label.mes.03",
            "justificativa.label.mes.04",
            "justificativa.label.mes.05",
            "justificativa.label.mes.06",
			"justificativa.label.mes.07",
            "justificativa.label.mes.08",
            "justificativa.label.mes.09",
            "justificativa.label.mes.10",
            "justificativa.label.mes.11",
            "justificativa.label.mes.12"};

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	if (value == null || value.equals("")) {  
            return "";  
        } else {  
        	
        	try{
        		
        		Date dtSelecionada = (Date) value;
            		
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(dtSelecionada);
        		
        		int mes = cal.get(Calendar.MONTH); 
        		return Message.getBundleMessage(meses[mes]);

			} catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Mes Invalido"));  
            }  
        }  
    }

}
