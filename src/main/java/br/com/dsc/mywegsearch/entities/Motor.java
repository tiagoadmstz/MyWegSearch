/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.entities;

import br.com.dsc.mywegsearch.converters.LocalDateConverter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Tiago
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "CAD_EQUIPAMENTOS")
@SequenceGenerator(name = "SEQ_MOTOR", initialValue = 1, allocationSize = 1)
public class Motor implements Serializable {

    private static final long serialVersionUID = 3494763904312918172L;
    private Long id;
    //dados de pesquisa
    private String material;
    private String numeroSerie;
    //Identificação
    private String motor;//Motor: 10063648
    private LocalDate data; //Data: 04-06-2014
    private String isolamento;//Classe de isolamento: F
    //Ferragem
    private Double entreferro;//Entreferro: 0,45
    private Integer ranhuras; //Nr. ranhuras estator: 36
    private Integer comprimento;//Pacote: 110
    private Double diametroExterno;//Diam. ext. estator: 220.00000
    private Double diametroInterno;//Diam. int. estator: 150.00000
    //Descrição
    private String descricao;
    //Bobinagem
    private String descricao_bob;
    private String polos;
    private String fios;
    private String fios2;
    private String espiras;
    private String passo;
    private String camada;
    private String ligacao;
    private String bg;
    private String gf;
    private String enchimento;
    private String lz;
    private String esquemaLigacao;
    private String resistenciaLigacao;
    private String resistenciaFase;
    //Capacitores
    private String descricaoCapacitores;
    private String tensaoCapacitores;
    private String tipoCapacitores;
    //Espiras invertidas
    private String observacao;
    //Arquivos
    private byte[] relatorio;
    private byte[] esquema;

    public Motor() {
    }

    @Id
    @GeneratedValue(generator = "SEQ_MOTOR", strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "MATERIAL", length = 20)
    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Column(name = "NUMEROSERIE", length = 20)
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Column(name = "MOTOR", length = 20)
    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "DATA", columnDefinition = "date")
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Column(name = "ISOLAMENTO", length = 3)
    public String getIsolamento() {
        return isolamento;
    }

    public void setIsolamento(String isolamento) {
        this.isolamento = isolamento;
    }

    @Column(name = "ENTREFERRO", length = 12)
    public Double getEntreferro() {
        return entreferro;
    }

    public void setEntreferro(Double entreferro) {
        this.entreferro = entreferro;
    }

    @Column(name = "RANHURAS", length = 12)
    public Integer getRanhuras() {
        return ranhuras;
    }

    public void setRanhuras(Integer ranhuras) {
        this.ranhuras = ranhuras;
    }

    @Column(name = "COMPRIMENTO", length = 12)
    public Integer getComprimento() {
        return comprimento;
    }

    public void setComprimento(Integer comprimento) {
        this.comprimento = comprimento;
    }

    @Column(name = "DIAMETROEXTERNO", length = 12)
    public Double getDiametroExterno() {
        return diametroExterno;
    }

    public void setDiametroExterno(Double diametroExterno) {
        this.diametroExterno = diametroExterno;
    }

    @Column(name = "DIAMETROINTERNO", length = 12)
    public Double getDiametroInterno() {
        return diametroInterno;
    }

    public void setDiametroInterno(Double diametroInterno) {
        this.diametroInterno = diametroInterno;
    }

    @Column(name = "DESCRICAO", columnDefinition = "long varchar")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "DESCRICAO_BOB", length = 20)
    public String getDescricao_bob() {
        return descricao_bob;
    }

    public void setDescricao_bob(String descricao_bob) {
        this.descricao_bob = descricao_bob;
    }

    @Column(name = "POLOS", length = 20)
    public String getPolos() {
        return polos;
    }

    public void setPolos(String polos) {
        this.polos = polos;
    }

    @Column(name = "FIOS", length = 20)
    public String getFios() {
        return fios;
    }

    public void setFios(String fios) {
        this.fios = fios;
    }

    @Column(name = "FIOS2", length = 20)
    public String getFios2() {
        return fios2;
    }

    public void setFios2(String fios2) {
        this.fios2 = fios2;
    }

    @Column(name = "ESPIRAS", length = 20)
    public String getEspiras() {
        return espiras;
    }

    public void setEspiras(String espiras) {
        this.espiras = espiras;
    }

    @Column(name = "PASSO", length = 20)
    public String getPasso() {
        return passo;
    }

    public void setPasso(String passo) {
        this.passo = passo;
    }

    @Column(name = "CAMADA", length = 20)
    public String getCamada() {
        return camada;
    }

    public void setCamada(String camada) {
        this.camada = camada;
    }

    @Column(name = "LIGACAO", length = 20)
    public String getLigacao() {
        return ligacao;
    }

    public void setLigacao(String ligacao) {
        this.ligacao = ligacao;
    }

    @Column(name = "BG", length = 20)
    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    @Column(name = "GF", length = 20)
    public String getGf() {
        return gf;
    }

    public void setGf(String gf) {
        this.gf = gf;
    }

    @Column(name = "ENCHIMENTO", length = 20)
    public String getEnchimento() {
        return enchimento;
    }

    public void setEnchimento(String enchimento) {
        this.enchimento = enchimento;
    }

    @Column(name = "LZ", length = 20)
    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    @Column(name = "ESQUEMALIGACAO", length = 20)
    public String getEsquemaLigacao() {
        return esquemaLigacao;
    }

    public void setEsquemaLigacao(String esquemaLigacao) {
        this.esquemaLigacao = esquemaLigacao;
    }

    @Column(name = "RESISTENCIALIGACAO", length = 20)
    public String getResistenciaLigacao() {
        return resistenciaLigacao;
    }

    public void setResistenciaLigacao(String resistenciaLigacao) {
        this.resistenciaLigacao = resistenciaLigacao;
    }

    @Column(name = "RESISTENCIAFASE", length = 20)
    public String getResistenciaFase() {
        return resistenciaFase;
    }

    public void setResistenciaFase(String resistenciaFase) {
        this.resistenciaFase = resistenciaFase;
    }

    @Column(name = "DESCRICAOCAPACITORES", length = 20)
    public String getDescricaoCapacitores() {
        return descricaoCapacitores;
    }

    public void setDescricaoCapacitores(String descricaoCapacitores) {
        this.descricaoCapacitores = descricaoCapacitores;
    }

    @Column(name = "TENSAOCAPACITORES", length = 20)
    public String getTensaoCapacitores() {
        return tensaoCapacitores;
    }

    public void setTensaoCapacitores(String tensaoCapacitores) {
        this.tensaoCapacitores = tensaoCapacitores;
    }

    @Column(name = "TIPOCAPACITORES", length = 20)
    public String getTipoCapacitores() {
        return tipoCapacitores;
    }

    public void setTipoCapacitores(String tipoCapacitores) {
        this.tipoCapacitores = tipoCapacitores;
    }

    @Column(name = "OBSERVACAO", length = 255)
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Lob
    @Column(name = "RELATORIO", columnDefinition = "blob")
    public byte[] getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(byte[] relatorio) {
        this.relatorio = relatorio;
    }

    @Lob
    @Column(name = "ESQUEMA", columnDefinition = "blob")
    public byte[] getEsquema() {
        return esquema;
    }

    public void setEsquema(byte[] esquema) {
        this.esquema = esquema;
    }

    @Override
    public String toString() {
        return "Motor{" + "id=" + id + ", material=" + material + ", numeroSerie=" + numeroSerie + ", motor=" + motor + ", data=" + data + ", isolamento=" + isolamento + ", entreferro=" + entreferro + ", ranhuras=" + ranhuras + ", comprimento=" + comprimento + ", diametroExterno=" + diametroExterno + ", diametroInterno=" + diametroInterno + ", descricao=" + descricao + ", descricao_bob=" + descricao_bob + ", polos=" + polos + ", fios=" + fios + ", fios2=" + fios2 + ", espiras=" + espiras + ", passo=" + passo + ", camada=" + camada + ", ligacao=" + ligacao + ", bg=" + bg + ", gf=" + gf + ", enchimento=" + enchimento + ", lz=" + lz + ", esquemaLigacao=" + esquemaLigacao + ", resistenciaLigacao=" + resistenciaLigacao + ", resistenciaFase=" + resistenciaFase + ", descricaoCapacitores=" + descricaoCapacitores + ", tensaoCapacitores=" + tensaoCapacitores + ", tipoCapacitores=" + tipoCapacitores + ", observacao=" + observacao + ", relatorio=" + relatorio + ", esquema=" + esquema + '}';
    }

}
