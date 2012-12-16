package com.filter;

import com.domain.SescoopConstants;
import com.managed.bean.IPermissoesBean;
import com.util.JsfUtil;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AuthorizationListener implements PhaseListener {


    private static final long serialVersionUID = 1L;
    private static final String PAGE_LOGIN = "/pages/login.xhtml";
    public static final String PAGE_LOGIN_REDIRECT = "/pages/login?faces-redirect=true";

    @Override
    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();

        String currentPage = facesContext.getViewRoot().getViewId();

        boolean isLoginPage = (currentPage.lastIndexOf(PAGE_LOGIN) > -1);

        IPermissoesBean permissoes = JsfUtil.getValueExpression(IPermissoesBean.class, SescoopConstants.USUARIO_LOGADO);

        if (!isLoginPage && (permissoes == null || !permissoes.isLogged())) {

            NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
            handler.handleNavigation(facesContext, null, PAGE_LOGIN_REDIRECT);
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
