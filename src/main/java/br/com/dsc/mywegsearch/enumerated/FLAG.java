/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.enumerated;

/**
 *
 * @author Tiago
 */
public enum FLAG {

    LOGIN("login"), PASSWD("passwd");

    private final String valor;

    private FLAG(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

}
