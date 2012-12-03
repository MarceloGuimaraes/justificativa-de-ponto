package com.util.converter;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


public class MesConverter implements Converter {

	
	String meses[] = {"Janeiro","Fevereiro","Março", "Abril", "Maio", "Junho", 
			"Julho","Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}; 
	

   
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	if(value.trim().equals("")){
    		return null;
    	}	
    		else{
    			try{
    				int mes =(Integer.parseInt(value));
    				return meses[mes];
    			} catch(NumberFormatException exception) {  
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Mes Invalido"));  
                }  
    	}
    	
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
        		return meses[mes];
			} catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Mes Invalido"));  
            }  
        }  
    }

}
