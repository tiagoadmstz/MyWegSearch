/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.util;

import br.com.dsc.mywegsearch.entities.Configuracao;
import br.com.dsc.mywegsearch.enumerated.FLAG;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.bouncycastle.util.Strings;
import org.castor.util.Base64Decoder;
import org.castor.util.Base64Encoder;

/**
 *
 * @author Tiago
 */
public final class ConfigUtil {

    private final Configuracao config;
    private final File file;

    public ConfigUtil(Configuracao config) {
        this.config = config;
        file = new File(System.getProperty("user.dir").concat("/project.conf"));
    }

    public Configuracao getConfiguracao() {
        return config;
    }

    public void readConfigFile() {
        createConfigFile();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                if (line.contains(FLAG.LOGIN.getValor())) {
                    config.setLogin(getValue(line));
                } else if (line.contains(FLAG.PASSWD.getValor())) {
                    try {
                        config.setPasswd(decodeBase64(getValue(line)));
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void writeConfigFile() {
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            for (FLAG flag : FLAG.values()) {
                if (flag.getValor().equals("login")) {
                    bw.append("login::".concat(config.getLogin()));
                } else if (flag.getValor().equals("passwd")) {
                    bw.append("passwd::".concat(encodeBase64(config.getPasswd())));
                }
                bw.newLine();
            }
            bw.close();
            writer.close();
        } catch (Exception e) {
        }
    }

    private String decodeBase64(String line) {
        byte[] bytes = Base64Decoder.decode(line);
        return Strings.fromUTF8ByteArray(bytes);
    }
    
    private String encodeBase64(String line) {
        char[] chars = Base64Encoder.encode(line.getBytes());
        return String.valueOf(chars);
    }

    private String getValue(String line) {
        String[] spl = line.split("::");
        if (spl.length > 1) {
            return spl[1];
        }
        return "";
    }

    private void createConfigFile() {
        try {
            if (!file.exists()) {
                try {
                    FileWriter writer = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(writer);
                    for (FLAG flag : FLAG.values()) {
                        try {
                            bw.append(flag.getValor());
                            bw.newLine();
                        } catch (Exception ex) {
                        }
                    }
                    bw.close();
                    writer.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

}
