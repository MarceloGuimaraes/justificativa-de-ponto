package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.filtro.FiltroJustificativa;
import com.jsf.ds.impl.ComboStatusDatasourceImpl;
import com.managed.bean.relatorio.JRCustomDatasource;
import com.managed.bean.relatorio.OcorrenciasJRDatasource;
import com.model.User;
import com.service.IConsultaOcorrenciasService;
import com.service.IUserService;
import com.util.Message;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 4:40 PM
 * MB para gerar relatorio
 */
public class RelatorioManagedBean implements Serializable {

    private IUserService userService;
    private IConsultaOcorrenciasService consultaOcorrenciasService;
    private FiltroJustificativa filtro;
    private List<SelectItem> escolhasStatus;
    private List<SelectItem> escolhasFuncionarios;

    public RelatorioManagedBean(final IUserService userService,
                                final IConsultaOcorrenciasService consultaOcorrenciasService) {
        this.userService = userService;
        this.consultaOcorrenciasService = consultaOcorrenciasService;
        filtro = new FiltroJustificativa();
        escolhasStatus = new ComboStatusDatasourceImpl().findObjects();
        escolhasFuncionarios = retornaTodosFuncionarios();
    }

    private List<SelectItem> retornaTodosFuncionarios() {
        final List<CadastroUsuario> usuarios = userService.todos();
        final List<SelectItem> escolhas = new LinkedList<SelectItem>();
        for(CadastroUsuario c : usuarios){
            escolhas.add(new SelectItem(c.getId(), c.getNome()));
        }
        return escolhas;
    }

    public String geraRelatorio(){
        final Map<String, Object> parametros = new LinkedHashMap<String, Object>();
        final User user = userService.recuperar(filtro.getIdFuncionario());
        parametros.put("FUNCIONARIO", user==null?"":user.getNome());
        parametros.put("INICIO", filtro.getInicio());
        parametros.put("TERMINO", filtro.getTermino());
        parametros.put("STATUS", filtro.isStatusInformado()?Message.getBundleMessage(filtro.getStatus().getDescricao()):"");
        parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        final HttpServletResponse response = (HttpServletResponse) context.getResponse();
        final InputStream in = RelatorioManagedBean.class.getResourceAsStream("/reports/com/relatorios/relatorio1.jasper");
        try {
            filtro.isValid();
            final OutputStream out = response.getOutputStream();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");
            response.setHeader("Cache-Control", "no-cache");
            JasperRunManager.runReportToPdfStream(in, out, parametros, OcorrenciasJRDatasource.getInstance(consultaOcorrenciasService, filtro));

            out.flush();
            out.close();

            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            Message.addMessage("relatorio.erro.mensagem.arquivonaoencontrado");
        } catch (JRException e) {
            Message.addMessage("relatorio.erro.mensagem.erroinesperado");
        } catch (FacesException e){
            Message.addMessage(e.getMessage());
        }

        return null;
    }

    public FiltroJustificativa getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroJustificativa filtro) {
        this.filtro = filtro;
    }

    public List<SelectItem> getEscolhasStatus() {
        return escolhasStatus;
    }

    public void setEscolhasStatus(List<SelectItem> escolhasStatus) {
        this.escolhasStatus = escolhasStatus;
    }

    public List<SelectItem> getEscolhasFuncionarios() {
        return escolhasFuncionarios;
    }

    public void setEscolhasFuncionarios(List<SelectItem> escolhasFuncionarios) {
        this.escolhasFuncionarios = escolhasFuncionarios;
    }
}
