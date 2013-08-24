package com.managed.bean.relatorio;

import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.service.IConsultaFiltradaPaginadaService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 11:14 PM
 */
public class OcorrenciasJRDatasource implements JRDataSource {
    private static final int PAGE_SIZE = 50;
    private Ocorrencia corrente;
    private IConsultaFiltradaPaginadaService<Ocorrencia, FiltroJustificativa> consultaOcorrenciasService;
    private FiltroJustificativa filtro;
    private Iterator<Ocorrencia> ocorrencias;
    private long total;
    private int proximaPagina;

    public OcorrenciasJRDatasource(final IConsultaFiltradaPaginadaService<Ocorrencia, FiltroJustificativa> consultaOcorrenciasService,
                                   final FiltroJustificativa filtro) {
        this.consultaOcorrenciasService = consultaOcorrenciasService;
        this.filtro = filtro;
        total = consultaOcorrenciasService.count(filtro);
        proximaPagina = 0;
        fetch();
    }

    @Override
    public boolean next() throws JRException {
        if (!ocorrencias.hasNext()) {
            if(temProximaPagina()){
                fetch();
            } else {
                return false;
            }
        }
        corrente = ocorrencias.next();
        return true;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        try {
            return PropertyUtils.getProperty(corrente, jrField.getName());
        } catch (IllegalAccessException e) {
            throw new JRException("Nao foi possivel acessar o campo: " + jrField.getName(), e);
        } catch (InvocationTargetException e) {
            throw new JRException("Ocorreu um erro inesperado ao acessar o campo: " + jrField.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new JRException("O campo invocado nao existe: " + jrField.getName(), e);
        }
    }

    private void fetch(){
        ocorrencias = consultaOcorrenciasService.todas(filtro, proximaPagina, PAGE_SIZE).iterator();
        proximaPagina+=PAGE_SIZE;
    }

    private boolean temProximaPagina(){
        return proximaPagina < total;
    }
}
