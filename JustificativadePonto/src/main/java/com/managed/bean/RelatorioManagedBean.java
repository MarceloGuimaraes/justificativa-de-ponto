package com.managed.bean;

import com.managed.bean.relatorio.JRCustomDatasource;
import com.service.IJustificativaService;
import com.service.JustificativaService;
import com.spring.util.ApplicationContextProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 4:40 PM
 * MB para gerar relatorio
 */
public class RelatorioManagedBean implements Serializable {
    private final IJustificativaService justificativaService;

    public RelatorioManagedBean(final IJustificativaService JustificativaService) {
        justificativaService = JustificativaService;
    }

    public String geraRelatorio(){
        final Map<String, Object> parametros = new LinkedHashMap<String, Object>();
        parametros.put("FUNCIONARIO", "Raphael R Costa");
        parametros.put("INICIO", new Date());
        parametros.put("TERMINO", new Date());
        parametros.put("STATUS", "Concluído");
        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        final HttpServletResponse response = (HttpServletResponse) context.getResponse();
        final InputStream in = context.getResourceAsStream("/WEB-INF/relatorios/relatorio2.jasper");
        final OutputStream out;
        try {
            out = response.getOutputStream();
            FacesContext.getCurrentInstance().responseComplete();
            JasperRunManager.runReportToPdfStream(in, out, parametros, JRCustomDatasource.getInstance());
            response.setContentType("application.pdf");
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new FacesException("Erro ao gerar o relatorio, arquivo nao encontrado", e);
        } catch (JRException e) {
            throw new FacesException("Erro inesperado ao gerar o relatorio", e);
        }

        return null;
    }
}
