/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.entities;

import java.time.LocalDate;

/**
 *
 * @author Tiago
 */
public class Motor {

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

}
