package com.managed.bean.relatorio;

import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.service.IConsultaFiltradaPaginadaService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

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
        if (jrField.getName().equals("solicitante")) {
            return corrente.getSolicitante();
        }
        if (jrField.getName().equals("periodoDatas")) {
            return corrente.getPeriodoDatas();
        }
        if (jrField.getName().equals("horaInicio")) {
            return corrente.getHoraInicio();
        }
        if (jrField.getName().equals("horaTermino")) {
            return corrente.getHoraTermino();
        }
        if (jrField.getName().equals("motivo")) {
            return corrente.getMotivo();
        }
        if (jrField.getName().equals("complemento")) {
            return corrente.getComplemento();
        }
        if (jrField.getName().equals("status")) {
            return corrente.getStatus();
        }
        if (jrField.getName().equals("periodo")) {
            return corrente.getPeriodo();
        }
        if (jrField.getName().equals("tipoDecisao")) {
            return corrente.getTipoDecisao();
        }
        throw new JRException("campo nao existe: " + jrField.getName());
    }

    private void fetch(){
        ocorrencias = consultaOcorrenciasService.todas(filtro, proximaPagina, PAGE_SIZE).iterator();
        proximaPagina+=PAGE_SIZE;
    }

    private boolean temProximaPagina(){
        return proximaPagina < total;
    }
}
