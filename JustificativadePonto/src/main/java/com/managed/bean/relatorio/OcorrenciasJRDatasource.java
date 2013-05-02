package com.managed.bean.relatorio;

import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.service.IConsultaOcorrenciasService;
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
    private Iterator<Ocorrencia> ocorrencias;
    private Ocorrencia corrente;

    public OcorrenciasJRDatasource(final IConsultaOcorrenciasService consultaOcorrenciasService,
                                   final FiltroJustificativa filtro) {
        ocorrencias = consultaOcorrenciasService.pesquisar(filtro).iterator();
    }

    @Override
    public boolean next() throws JRException {
        if (ocorrencias.hasNext()) {
            corrente = ocorrencias.next();
            return true;
        }
        return false;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        if (jrField.getName().equals("solicitante")) {
            return corrente.getSolicitante();
        }
        if (jrField.getName().equals("data")) {
            return corrente.getData();
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
        throw new JRException("campo nao existe: " + jrField.getName());
    }
}
