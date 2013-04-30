package com.domain.dto.filtro;

import com.model.StatusEnum;

import javax.faces.FacesException;
import java.util.Calendar;
import java.util.Date;

/**
 * User: xonda
 * Date: 4/28/13
 * Time: 11:37 PM
 */
public class FiltroJustificativa {
    private Integer idFuncionario;
    private Date inicio;
    private Date termino;
    private StatusEnum status;

    public Date getInicio() {
        return inicio==null?null:new Date(inicio.getTime());
    }

    public void setInicio(Date inicio) {
        if(inicio==null){
            this.inicio = null;
            return;
        }
        final Calendar temp = Calendar.getInstance();
        temp.setTime(inicio);
        temp.set(Calendar.HOUR, 0);
        temp.set(Calendar.MINUTE, 0);
        temp.set(Calendar.SECOND, 0);
        this.inicio = temp.getTime();
    }

    public Date getTermino() {
        return termino==null?null:new Date(termino.getTime());
    }

    public void setTermino(Date termino) {
        if(termino==null){
            this.termino=null;
            return;
        }
        final Calendar temp = Calendar.getInstance();
        temp.setTime(termino);
        temp.set(Calendar.HOUR, 23);
        temp.set(Calendar.MINUTE, 59);
        temp.set(Calendar.SECOND, 59);
        this.termino = termino;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public boolean isIdFuncionarioInformado(){
        return idFuncionario!=null && idFuncionario.intValue()>0;
    }

    public boolean isInicioInformado(){
        return inicio!=null;
    }

    public boolean isTerminoInformado(){
        return termino!=null;
    }

    public boolean isStatusInformado(){
        return status!=null;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public void isValid(){
        if(isInicioInformado() && isTerminoInformado() && inicio.after(termino)){
            throw new FacesException("relatorio.filtro.invalido");
        }
    }
}
