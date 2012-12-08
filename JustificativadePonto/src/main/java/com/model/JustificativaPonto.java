package com.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "JustificativaPonto")
public class JustificativaPonto implements Serializable {

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

	@Column(name = "status", nullable = false)
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.StatusEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
	private StatusEnum status;

    @Column(name = "motivo", nullable = false)
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.MotivoEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
	private MotivoEnum motivo;

	@Column(name = "tipodefalta", nullable = false)
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.TipoFaltaEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
	private TipoFaltaEnum tipofalta;

	@Column(name = "tipobancohoras", nullable = false)
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.TipoBancoHorasEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
	private TipoBancoHorasEnum tipobancohoras;

    @Column(name = "tipodecisao")
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.TipoDecisaoEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
    private TipoDecisaoEnum tipoDecisao;
	
	@ManyToOne(targetEntity = User.class, optional = false)
	private User coordenador;
	
	@ManyToOne(targetEntity = User.class, optional = true)
	private User superintendente;
	
	@ManyToOne(targetEntity = User.class, optional = false)
	private User solicitante;
	
	@ManyToOne(targetEntity = User.class, optional = true)
	private User rh;

    @OneToMany(targetEntity = HistoricoJustificativaPonto.class,
            mappedBy = "justificativaPonto",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<HistoricoJustificativaPonto> historico;

    public JustificativaPonto() {
    }

    public JustificativaPonto(User solicitante) {
        this.solicitante = solicitante;
        this.dtCriacao = new Date();
        this.status = StatusEnum.ELABORACAO;
        adiciona(solicitante, TipoEventoJustificativaPontoEnum.REGISTRO_CRIADO);
    }

    public int getJustificativaId() {
		return justificativaId;
	}

	public void setJustificativaId(int justificativaId) {
		this.justificativaId = justificativaId;
	}

	public TipoFaltaEnum getTipofalta() {
		return tipofalta;
	}

	public void setTipofalta(TipoFaltaEnum tipofalta) {
		this.tipofalta = tipofalta;
	}

	public TipoBancoHorasEnum getTipobancohoras() {
		return tipobancohoras;
	}

	public void setTipobancohoras(TipoBancoHorasEnum tipobancohoras) {
		this.tipobancohoras = tipobancohoras;
	}

    public TipoDecisaoEnum getTipoDecisao() {
        return tipoDecisao;
    }

    public void setTipoDecisao(TipoDecisaoEnum tipoDecisao) {
        this.tipoDecisao = tipoDecisao;
    }

    public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public MotivoEnum getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoEnum motivo) {
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

	public User getRh() {
		return rh;
	}

	public void setRh(User rh) {
		this.rh = rh;
	}

	public User getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(User solicitante) {
		this.solicitante = solicitante;
	}

    public List<HistoricoJustificativaPonto> getHistorico() {
        return historico;
    }

    public void adiciona(User user, TipoEventoJustificativaPontoEnum evento) {

        if(historico==null){
            historico = new LinkedList<HistoricoJustificativaPonto>();
        }

        historico.add(new HistoricoJustificativaPonto(user, this, evento));

    }

    public HistoricoJustificativaPonto getUltimoHistorico(){
        if(historico==null || historico.isEmpty()){
            return null;
        }
        Set<HistoricoJustificativaPonto> ordenado = new TreeSet<HistoricoJustificativaPonto>(new Comparator<HistoricoJustificativaPonto>() {
            @Override
            public int compare(HistoricoJustificativaPonto o1, HistoricoJustificativaPonto o2) {
                return (o1.getId().intValue()-o2.getId().intValue())*-1;
            }
        });
        ordenado.addAll(historico);
        return ordenado.iterator().next();
    }

}
