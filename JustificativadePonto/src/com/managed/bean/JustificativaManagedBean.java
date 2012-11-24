package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.model.Motivo;
import com.model.TipoBancoHoras;
import com.model.TipoFalta;


@ManagedBean(name = "justificativaBean")
@RequestScoped
public class JustificativaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String selctedMotivos;
	private String selectedTipoBancoHoras;
	private String selctedTipoFalta;
	
	private List<SelectItem> tipoBancoHorasList = new ArrayList<SelectItem>(); 
	private List<SelectItem> tipoMotivosList = new ArrayList<SelectItem>(); 
	private List<SelectItem> tipoFaltaList = new ArrayList<SelectItem>();

	
	
	public JustificativaManagedBean() {
		fetchTipoMotivosList();
		fetchTipoBancoHorasList();
		fetchTipoFaltaList();
	}

	private void fetchTipoMotivosList() {
		tipoMotivosList.clear();
		Motivo[] motivoArray = Motivo.values();
		for (Motivo tipoMotivo : motivoArray) {
			System.out.println("tipoMotivo:" + tipoMotivo);
			tipoMotivosList.add(new SelectItem(tipoMotivo.getName()));
		}
	}
	
	public String getSelctedMotivos() {
		return selctedMotivos;
	}


	public void getSelctedMotivos(String selctedMotivos) {
		this.selctedMotivos = selctedMotivos;
	}

	public List<SelectItem> getTipoMotivosList() {
		return tipoMotivosList;
	}

	public void setTipoMotivosList(List<SelectItem> tipoMotivosList) {
		this.tipoMotivosList = tipoMotivosList;
	}
	
	private void fetchTipoBancoHorasList() {
		tipoBancoHorasList.clear();
		TipoBancoHoras[] bancoHorasArray = TipoBancoHoras.values();
		for (TipoBancoHoras tipoBancoHoras : bancoHorasArray) {
			System.out.println("tipoBancoHoras:" + tipoBancoHoras);
			tipoBancoHorasList.add(new SelectItem(tipoBancoHoras.getName()));
		}
	}

	public String getSelectedTipoBancoHoras() {
		return selectedTipoBancoHoras;
	}


	public void getSelectedTipoBancoHoras(String selectedTipoBancoHoras) {
		this.selectedTipoBancoHoras = selectedTipoBancoHoras;
	}

	public List<SelectItem> getTipoBancoHorasList() {
		return tipoBancoHorasList;
	}

	public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
		this.tipoBancoHorasList = tipoBancoHorasList;
	}
	
	
	private void fetchTipoFaltaList() {
		tipoFaltaList.clear();
		TipoFalta[] bancoHorasArray = TipoFalta.values();
		for (TipoFalta tipoFalta : bancoHorasArray) {
			System.out.println("tipoFalta:" + tipoFalta);
			tipoFaltaList.add(new SelectItem(tipoFalta.getName()));
		}
	}

	public String getSelectedTipoFalta() {
		return selctedTipoFalta;
	}


	public void getSelectedTipoFalta(String selectedTipoFalta) {
		this.selctedTipoFalta = selectedTipoFalta;
	}

	public List<SelectItem> getTipoFaltaList() {
		return tipoFaltaList;
	}

	public void setTipoFaltaList(List<SelectItem> tipoFaltaList) {
		this.tipoFaltaList = tipoFaltaList;

	}
	
	
}