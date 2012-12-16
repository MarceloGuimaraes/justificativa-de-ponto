package com.util;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class JsfUtil {

    public static <T> T getValueExpression(Class<T> clazz, String formula){
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().getExpressionFactory()
                .createValueExpression(context.getELContext(), formula, clazz)
                .getValue(context.getELContext());
    }

    public static void setSessionValue(String key, Object value){
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put(key, value);
    }

    public static void removeFromSession(String key){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .remove(key);
    }

    public static String getParameter(String key){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> parameters = context.getRequestParameterMap();
        if(parameters.containsKey(key)){
            return parameters.get(key);
        }
        return null;
    }
    
   
    
    public static String getUrlToSendMail(){
    	
    	FacesContext fc = FacesContext.getCurrentInstance(); 
    	HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest(); 
    	String contextURL = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "") + request.getContextPath();
    	
        return contextURL;
    }
    

}
