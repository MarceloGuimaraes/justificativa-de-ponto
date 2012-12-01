package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import com.model.Motivo;

/**
*
* @author shunmuga
*/
@ManagedBean(name = "motivoBean")
@RequestScoped
public class MotivoManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

private String selctedMotivos;
private Map<String, Motivo> tipoMotivosMap = new HashMap<String, Motivo>();
private List<SelectItem> tipoMotivosList = new ArrayList<SelectItem>();

public MotivoManagedBean() {
	fetchTipoMotivosMap();
	fetchTipoMotivosList();
}

private void fetchTipoMotivosMap()
{

for (Motivo motivos : Motivo.values())
{
tipoMotivosMap.put(motivos.getName(), motivos);
}
selctedMotivos = Motivo.ATIVIDADEFORA.name();
}

private void fetchTipoMotivosList()
{
tipoMotivosList.clear();
Motivo[] motivoArray = Motivo.values();
for (Motivo tipoMotivo : motivoArray)
{
System.out.println("tipoMotivo:" + tipoMotivo);
tipoMotivosList.add(new SelectItem(tipoMotivo.getName()));
}
}

/**
* @return the selctedMotivos
*/
public String getSelctedMotivos()
{
return selctedMotivos;
}

/**
* @param selctedMotivos the selctedMotivos to set
*/
public void getSelctedMotivos(String selctedMotivos)
{
this.selctedMotivos = selctedMotivos;
}

/**
* @return the tipoMotivosMap
*/
public Map<String, Motivo> getTipoMotivosMap()
{
return tipoMotivosMap;
}

/**
* @param tipoMotivosMap the tipoMotivosMap to set
*/
public void setTipoMotivosMap(Map<String, Motivo> tipoMotivosMap)
{
this.tipoMotivosMap = tipoMotivosMap;
}

/**
* @return the tipoMotivosList
*/
public List<SelectItem> getTipoMotivosList()
{
return tipoMotivosList;
}

/**
* @param tipoMotivosList the tipoMotivosList to set
*/
public void setTipoMotivosList(List<SelectItem> tipoMotivosList)
{
this.tipoMotivosList = tipoMotivosList;
}
}
