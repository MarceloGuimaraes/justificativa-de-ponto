package com.filter;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

public class AuthorizationListener implements PhaseListener {


	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {

		FacesContext facesContext = event.getFacesContext();

		String currentPage = facesContext.getViewRoot().getViewId();

		System.out.println("=======validado a página=======");
		System.out.println("currentPage => " + currentPage);
		
		boolean isLoginPage = (currentPage.lastIndexOf("/pages/login.xhtml") > -1);
		System.out.println("=======validado a página======= 1 ");
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		System.out.println("=======validado a página======= 2 ");
		Object currentUser =  session.getAttribute("usuarioLogado");
		System.out.println("=======validado a página======= 3 ");
      //  LoginBean loginBean = (LoginBean) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{loginController}", LoginBean.class);
        //	System.out.println("Login phase" + currentUser.getUser().getEmail());
        	
        	
        	//if (!isLoginPage && !loginBean.isLogado()) {
        	if (!isLoginPage && currentUser == null) {
			
			System.out.println("<-------condicao lançada-------->");  
			NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
			handler.handleNavigation(facesContext, null, "/pages/login?faces-redirect=true");
			facesContext.renderResponse();
			
			//Util.addMessageText("usuario nao logado");
			
	      System.out.println("<-------usuario nao logado-------->"); 
	      
		}
	}

	
	@Override
	public void beforePhase(PhaseEvent event) {
		//System.out.println("PRINT DE IDENTIFICAÇÃO DO EVENTO before: " + event.getPhaseId());  
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
