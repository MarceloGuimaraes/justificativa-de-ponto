package com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "JustificativaPonto")
public class JustificativaPonto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "justificativaId")
	private int justificativaId;

	@Column(name = "dtCriacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCriacao;

	@Column(name = "dtAprovCoord")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAprovCoord;

	@Column(name = "dtAprovSuper")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAprovSuper;

	@Column(name = "dtAprovRh")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAprovRh;

	@Column(name = "dtOcorrenciaInit")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtOcorrenciaInit;

	@Column(name = "dtOcorrenciaFim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtOcorrenciaFim;

	@Column(name = "hrIni")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrIni;

	@Column(name = "hrFim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrFim;

	@Column(name = "descricao", length = 300)
	private String descricao;

	@Column(name = "obsSuperInt", length = 300)
	private String obsSuperInt;

	@Column(name = "staus", length = 300)
	private String status;

	@Column(name = "motivo")
	@Enumerated(EnumType.STRING)
	private Motivo motivo;

	@Column(name = "tipodefalta")
	@Enumerated(EnumType.STRING)
	private TipoFalta tipofalta;

	@Column(name = "tipobancohoras")
	@Enumerated(EnumType.STRING)
	private TipoBancoHoras tipobancohoras;
	
	@Column(name = "coordenador")
	private User coordenador;
	
	@Column(name = "superintendente")
	private User superintendente;
	

	public User getSuperintendente() {
		return superintendente;
	}

	public void setSuperintendente(User superintendente) {
		this.superintendente = superintendente;
	}

	public User getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(User coordenador) {
		this.coordenador = coordenador;
	}
	
	/*
	 * public JustificativaPonto(){
	 * 
	 * } public JustificativaPonto(Date dtCriacao, Date dtAprovCoord, Date
	 * dtAprovSuper,Date dtAprovRh, Date dtOcorrenciaInit, Date dtOcorrenciaFim)
	 * { super(); dtCriacao = new Date(); this.dtAprovCoord = dtAprovCoord;
	 * this.dtAprovSuper = dtAprovSuper; this.dtAprovRh = dtAprovRh;
	 * this.dtOcorrenciaInit = dtOcorrenciaInit; this.dtOcorrenciaFim =
	 * dtOcorrenciaFim; }
	 */


	public int getJustificativaId() {
		return justificativaId;
	}

	public void setJustificativaId(int justificativaId) {
		this.justificativaId = justificativaId;
	}

	public TipoFalta getTipofalta() {
		return tipofalta;
	}

	public void setTipofalta(TipoFalta tipofalta) {
		this.tipofalta = tipofalta;
	}

	public TipoBancoHoras getTipobancohoras() {
		return tipobancohoras;
	}

	public void setTipobancohoras(TipoBancoHoras tipobancohoras) {
		this.tipobancohoras = tipobancohoras;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtAprovCoord() {
		return dtAprovCoord;
	}

	public void setDtAprovCoord(Date dtAprovCoord) {
		this.dtAprovCoord = dtAprovCoord;
	}

	public Date getDtAprovSuper() {
		return dtAprovSuper;
	}

	public void setDtAprovSuper(Date dtAprovSuper) {
		this.dtAprovSuper = dtAprovSuper;
	}

	public Date getDtAprovRh() {
		return dtAprovRh;
	}

	public void setDtAprovRh(Date dtAprovRh) {
		this.dtAprovRh = dtAprovRh;
	}

	public Date getDtOcorrenciaInit() {
		return dtOcorrenciaInit;
	}

	public void setDtOcorrenciaInit(Date dtOcorrenciaInit) {
		this.dtOcorrenciaInit = dtOcorrenciaInit;
	}

	public Date getDtOcorrenciaFim() {
		return dtOcorrenciaFim;
	}

	public void setDtOcorrenciaFim(Date dtOcorrenciaFim) {
		this.dtOcorrenciaFim = dtOcorrenciaFim;
	}

	public Date getHrIni() {
		return hrIni;
	}

	public void setHrIni(Date hrIni) {
		this.hrIni = hrIni;
	}

	public Date getHrFim() {
		return hrFim;
	}

	public void setHrFim(Date hrFim) {
		this.hrFim = hrFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObsSuperInt() {
		return obsSuperInt;
	}

	public void setObsSuperInt(String obsSuperInt) {
		this.obsSuperInt = obsSuperInt;
	}

}
