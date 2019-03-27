/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.listener;

import br.com.dsc.mywegsearch.entities.Configuracao;
import br.com.dsc.mywegsearch.entities.Motor;
import br.com.dsc.mywegsearch.frames.Form_Main;
import br.com.dsc.mywegsearch.service.Service_MyWeg;
import br.com.dsc.mywegsearch.util.ConfigUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Tiago
 */
public class Listener_Main implements ActionListener {

    private final Form_Main form;
    private final Configuracao config;
    private final Service_MyWeg service;
    private final Motor motor;

    public Listener_Main(Form_Main form) {
        this.form = form;
        this.service = new Service_MyWeg(form);
        this.config = new Configuracao();
        this.motor = new Motor();
        initComponents();
    }

    private void initComponents() {
        attachListener();
        setConfigFromFile();
    }

    private void attachListener() {
        form.getBtSalvarConfig().addActionListener(this);
        form.getBtPesquisar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "salvarConfig":
                salvarConfig();
                break;
            case "pesquisar":
                pesquisar();
        }
    }

    private void getConfigFromFrame() {
        config.setLogin(form.getTxtLogin().getText());
        config.setPasswd(String.valueOf(form.getTxtSenha().getPassword()));
    }

    private void setConfigFromFile() {
        ConfigUtil util = new ConfigUtil(config);
        util.readConfigFile();
        form.getTxtLogin().setText(config.getLogin());
        form.getTxtSenha().setText(config.getPasswd());
    }

    private void salvarConfig() {
        ConfigUtil util = new ConfigUtil(config);
        getConfigFromFrame();
        util.writeConfigFile();
    }

    private void pesquisar() {
        service.pesquisa(motor);
        System.out.println(motor.toString());
    }

}
