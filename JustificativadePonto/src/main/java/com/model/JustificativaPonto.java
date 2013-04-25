package com.model;

import com.domain.SescoopConstants;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "JustificativaPonto")
public class JustificativaPonto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "justificativaId")
	private Integer id;

	@Column(name = "dtCriacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

    @Column(name = "dtSolicitacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

	@Column(name = "hrIni")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrIni;

	@Column(name = "hrFim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date hrFim;

	@Column(name = "descricao", length = 300)
	private String descricao;

	@Column(name = "obsRh", length = 300)
	private String obsRh;

	@Column(name = "cancelamento", length = 300)
	private String cancelamento;
	
	@Column(name = "status", nullable = false)
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.StatusEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private StatusEnum status;

	@Column(name = "motivo", nullable = false)
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.MotivoEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private MotivoEnum motivo;

	@Column(name = "tipodefalta", nullable = true)
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.TipoFaltaEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private TipoFaltaEnum tipofalta;

	@Column(name = "tipobancohoras")
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.TipoBancoHorasEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private TipoBancoHorasEnum tipobancohoras;

	@Column(name = "tipodecisao")
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.TipoDecisaoEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private TipoDecisaoEnum tipoDecisao;

	@Column(name = "tipofaltamarcacao")
	@Type(type = "com.util.hibernate.GenericEnumUserType", parameters = {
			@Parameter(name = "enumClass", value = "com.model.TipoFaltaMarcacaoEnum"),
			@Parameter(name = "identifierMethod", value = "getCodigo"),
			@Parameter(name = "valueOfMethod", value = "fromSigla") })
	private TipoFaltaMarcacaoEnum tipofaltamarcacao;
	
    @ManyToOne(targetEntity = User.class, optional = false)
    private User solicitante;

	@OneToMany(targetEntity = HistoricoJustificativaPonto.class, mappedBy = "justificativaPonto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HistoricoJustificativaPonto> historico;

	public JustificativaPonto() {
	}

	public JustificativaPonto(User solicitante) {
		this.solicitante = solicitante;
		this.data = new Date();
		this.status = StatusEnum.ELABORACAO;
		adiciona(solicitante, TipoEventoJustificativaPontoEnum.REGISTRO_CRIADO);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer justificativaId) {
		this.id = justificativaId;
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

	public TipoFaltaMarcacaoEnum getTipofaltamarcacao() {
		return tipofaltamarcacao;
	}

	public void setTipofaltamarcacao(TipoFaltaMarcacaoEnum tipofaltamarcacao) {
		this.tipofaltamarcacao = tipofaltamarcacao;
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

	public Date getData() {
		return data;
	}

	public void setData(Date dtCriacao) {
		this.data = dtCriacao;
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

	public String getObsRh() {
		return obsRh;
	}

	public void setObsRh(String obsRh) {
		this.obsRh = obsRh;
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

    public void setHistorico(List<HistoricoJustificativaPonto> historico) {
        this.historico = historico;
    }

    public void adiciona(User user, TipoEventoJustificativaPontoEnum evento) {

		if (historico == null) {
			historico = new LinkedList<HistoricoJustificativaPonto>();
		}


        Identificacao identificacao = new Identificacao(user.getNome(), user.getCpf(), user.getEmail());

		historico.add(new HistoricoJustificativaPonto(identificacao, this, evento));

	}

    public void encaminha(User user, User delegado, TipoEventoJustificativaPontoEnum evento){

        if(historico == null){
            historico = new LinkedList<HistoricoJustificativaPonto>();
        }

        Identificacao id1 = new Identificacao(user.getNome(), user.getCpf(), user.getEmail());
        Identificacao id2 = new Identificacao(delegado.getNome(), delegado.getCpf(), delegado.getEmail());

        historico.add(new EncaminhamentoJustificativaPonto(id1, this, evento, id2));

    }

	public String getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(String cancelamento) {
		this.cancelamento = cancelamento;
	}

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(SescoopConstants.HASH1, SescoopConstants.HASH2)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof JustificativaPonto)) {
            return false;
        }

        JustificativaPonto justificativa = (JustificativaPonto) object;

        return new EqualsBuilder()
                .append(id, justificativa.id)
                .isEquals();
    }
}
