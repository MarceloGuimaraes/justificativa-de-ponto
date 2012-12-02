package com.filter;

import com.model.User;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AuthorizationListener implements PhaseListener {


    private static final long serialVersionUID = 1L;
    private static final String PAGES_LOGIN_XHTML = "/pages/login.xhtml";
    private static final String USUARIO_LOGADO = "usuarioLogado";
    public static final String PAGES_LOGIN_FACES_REDIRECT_TRUE = "/pages/login?faces-redirect=true";

    @Override
    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();

        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf(PAGES_LOGIN_XHTML) > -1);
        ExternalContext context = facesContext.getExternalContext();
        User currentUser = (User) context.getSessionMap().get(USUARIO_LOGADO);

        if (!isLoginPage && currentUser == null) {

            NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
            handler.handleNavigation(facesContext, null, PAGES_LOGIN_FACES_REDIRECT_TRUE);
            facesContext.renderResponse();

        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

}
