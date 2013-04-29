package com.managed.bean.relatorio;

import com.domain.dto.relatorio.Ocorrencia;
import com.model.MotivoEnum;
import com.model.StatusEnum;
import com.model.TipoBancoHorasEnum;
import com.model.TipoFaltaMarcacaoEnum;
import com.util.Message;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 11:44 PM
 */
public class JRCustomDatasource implements JRDataSource {

    private int index = -1;
    private Ocorrencia[] ocorrencias;

    public JRCustomDatasource() throws ParseException {
        DateFormat fmt = new SimpleDateFormat("dd/mm/yyyy");
        DateFormat fmt2 = new SimpleDateFormat("HH:mm");
        Date dt1 = fmt.parse("10/01/2013");
        Date dt2 = fmt.parse("15/01/2013");
        Date hr1 = fmt2.parse("10:13");
        Date hr2 = fmt2.parse("13:09");
        Date hr3 = fmt2.parse("20:59");
        ocorrencias = new Ocorrencia[]{
                new Ocorrencia(dt1, hr1, hr2,
                        Message.getBundleMessage(MotivoEnum.BANCODEHORAS.getDescricao()),
                        Message.getBundleMessage(TipoBancoHorasEnum.HORAEXTRA.getDescricao()),
                        Message.getBundleMessage(StatusEnum.CONCLUIDO.getDescricao())),
                new Ocorrencia(dt2, hr2, hr3,
                        Message.getBundleMessage(MotivoEnum.FALTADEMARCACAO.getDescricao()),
                        Message.getBundleMessage(TipoFaltaMarcacaoEnum.SAIDA.getDescricao()),
                        Message.getBundleMessage(StatusEnum.APROVSUPERINTENDENTE.getDescricao()))
        };
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return index<ocorrencias.length;
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        /*data;
    private Date horaInicio;
    private Date horaTermino;
    private String motivo;
    private String complemento;
    private String status;*/
        if(jrField.getName().equals("data")){
            return ocorrencias[index].getData();
        }
        if(jrField.getName().equals("horaInicio")){
            return ocorrencias[index].getHoraInicio();
        }
        if(jrField.getName().equals("horaTermino")){
            return ocorrencias[index].getHoraTermino();
        }
        if(jrField.getName().equals("motivo")){
            return ocorrencias[index].getMotivo();
        }
        if(jrField.getName().equals("complemento")){
            return ocorrencias[index].getComplemento();
        }
        if(jrField.getName().equals("status")){
            return ocorrencias[index].getStatus();
        }

        throw new JRException("campo nao existe: "+jrField.getName());
    }

    public static final JRDataSource getInstance(){
        try {
            return new JRCustomDatasource();
        } catch (ParseException e) {
            throw new RuntimeException("Erro na conversao de data", e);
        }
    }
}
