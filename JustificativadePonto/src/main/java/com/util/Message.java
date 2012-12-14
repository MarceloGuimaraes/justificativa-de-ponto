package com.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {

    private static final String PATH_MENSAGENS = "/mensagens";

    private static ResourceBundle bundle;

	public static String getBundleMessage(String codigo) {
        if(bundle==null){
		    bundle = ResourceBundle
				.getBundle(PATH_MENSAGENS);
        }
		String message;
		message = bundle.getString(codigo);

		return message;
	}

    public static String getBundleMessage(String codigo, String... placeholders){
        String resolvedMsg = MessageFormat.format(getBundleMessage(codigo), placeholders);
        return resolvedMsg;
    }

	/**
	 * 
	 * @param codigo
	 *            - Codigo do message.properties
	 * 
	 */
	public static void addMessage(String codigo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo), "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

    public static void addMessage(String codigo, String... placeholders){
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                getBundleMessage(codigo, placeholders), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public static void addMessageText(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto,
				"");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addMessageTextConfig(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, texto,
				"");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void addMessageConfig(String codigo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				getBundleMessage(codigo), "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static FacesMessage getMsgErro(String codigo) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo), "");

	}

}
