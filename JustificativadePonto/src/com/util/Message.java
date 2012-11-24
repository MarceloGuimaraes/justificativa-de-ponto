package com.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {

	public static String getBundleMessage(String codigo) {
		ResourceBundle resources = ResourceBundle
				.getBundle("/mensagens");
		// resources.setSrc("/mensagens.properties");
		String message;
		message = resources.getString(codigo);
		return message;
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
		FacesContext.getCurrentInstance().addMessage("message1", msg);
	}

	public static void addMessageText(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto,
				"");
		FacesContext.getCurrentInstance().addMessage("message1", msg);
	}

	public static void addMessageTextConfig(String texto) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, texto,
				"");
		FacesContext.getCurrentInstance().addMessage("message1", msg);
	}

	public static void addMessageConfig(String codigo) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				getBundleMessage(codigo), "");
		FacesContext.getCurrentInstance().addMessage("message1", msg);
	}

	public static FacesMessage getMsgErro(String codigo) {
		return new FacesMessage(FacesMessage.SEVERITY_ERROR,
				getBundleMessage(codigo), "");

	}

}
