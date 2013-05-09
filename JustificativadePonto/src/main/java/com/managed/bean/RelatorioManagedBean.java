package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.filtro.FiltroJustificativa;
import com.jsf.ds.impl.ComboStatusDatasourceImpl;
import com.managed.bean.relatorio.OcorrenciasJRDatasource;
import com.model.User;
import com.service.IConsultaOcorrenciasService;
import com.service.IUserService;
import com.service.UserService;
import com.service.impl.ConsultaOcorrenciasService;
import com.util.Message;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 4:40 PM
 * MB para gerar relatorio
 */
public class RelatorioManagedBean implements Serializable {

    private static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String CONTENT_TYPE_PDF = "application/pdf";
    private static final String JASPER_BASE_DIR = "/reports/com/relatorios/";
    private static final String JASPER_REPORT_GERENCIAL = "relatorioGerencial.jasper";
    private static final String JASPER_REPORT_DEFAULT = "relatorio1.jasper";

    private final IUserService userService;
    private final IPermissoesBean permissoesBean;
    private final FiltroJustificativa filtro;
    private final IConsultaOcorrenciasService consultaOcorrenciasService;
    private List<SelectItem> escolhasStatus;
    private List<SelectItem> escolhasFuncionarios;
    private String relatorio;

    public RelatorioManagedBean(final IUserService userService,
                                final IPermissoesBean permissoesBean,
                                final FiltroJustificativa filtro,
                                final IConsultaOcorrenciasService consultaOcorrenciasService) {
        this.userService = userService;
        this.permissoesBean = permissoesBean;
        this.filtro = filtro;
        this.consultaOcorrenciasService = consultaOcorrenciasService;
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
        if(permissoesBean.isRh()){
            relatorio = JASPER_REPORT_GERENCIAL;
            final User user = userService.recuperar(filtro.getIdFuncionario());
            parametros.put("FUNCIONARIO", filtro.isIdFuncionarioInformado()?user.getNome():null);
        } else {
            relatorio = JASPER_REPORT_DEFAULT;
            parametros.put("FUNCIONARIO", permissoesBean.getUsuarioLogado().getNome());
            filtro.setIdFuncionario(permissoesBean.getUsuarioLogado().getId());
        }
        parametros.put("INICIO", filtro.getInicio());
        parametros.put("TERMINO", filtro.getTermino());
        final StringBuilder periodoTxt = new StringBuilder();
        final DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        if(filtro.isInicioInformado()){
            periodoTxt.append(fmt.format(filtro.getInicio()));
        }
        if(filtro.isTerminoInformado()){
            periodoTxt.append(" - ");
            periodoTxt.append(fmt.format(filtro.getTermino()));
        }
        parametros.put("PERIODO", periodoTxt.toString());
        parametros.put("STATUS", filtro.isStatusInformado()?Message.getBundleMessage(filtro.getStatus().getDescricao()):null);
        parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));

        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        final HttpServletResponse response = (HttpServletResponse) context.getResponse();
        final InputStream in = RelatorioManagedBean.class.getResourceAsStream(JASPER_BASE_DIR+relatorio);
        try {
            final File tempFile = File.createTempFile("~jp",".xls");
//            final File tempFile = File.createTempFile("~jp",".pdf");
            tempFile.deleteOnExit();
            final OutputStream oo = new FileOutputStream(tempFile);

            //JasperRunManager.runReportToPdfStream(in, oo, parametros, new OcorrenciasJRDatasource(consultaOcorrenciasService, filtro));

            JasperPrint jasperPrint = JasperFillManager.fillReport(in, parametros, new OcorrenciasJRDatasource(consultaOcorrenciasService, filtro));
            JRExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oo);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
            exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, false);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, true);
            exporter.exportReport();

            oo.flush();
            oo.close();

            final OutputStream out = response.getOutputStream();
            final InputStream ii = new FileInputStream(tempFile);

            response.setContentType(CONTENT_TYPE_EXCEL);
//            response.setContentType(CONTENT_TYPE_PDF);
            response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.xls\"");
//            response.setHeader("Content-Disposition", "attachment; filename=\"relatorio.pdf\"");
            response.setHeader("Cache-Control", "no-cache");

            byte[] b = new byte[255];
            int i;
            while ((i=ii.read(b))>0){
                out.write(b, 0, i);
            }

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
