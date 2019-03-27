/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.muwegsearch.test;

import br.com.dsc.mywegsearch.dal.EntityManagerHelper;
import br.com.dsc.mywegsearch.entities.Motor;
import java.io.File;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;

/**
 *
 * @author Tiago
 */
public class PersistenceTest {

    //@Test
    public void recuperarArquivoTest() {
        EntityManagerHelper helper = new EntityManagerHelper();
        List<Motor> lista = helper.getObjectList("SELECT mt FROM Motor AS mt", EntityManagerHelper.DERBY_PU);
        for (Motor motor : lista) {
            try {
                PDDocument document = null;
                if (motor.getEsquema() != null) {
                    document = PDDocument.load(motor.getEsquema());
                    document.save(new File("C:\\Users\\Tiago\\Desktop\\esquema.pdf"));
                    document.close();
                } else {
                    document = PDDocument.load(motor.getRelatorio());
                    document.save(new File("C:\\Users\\Tiago\\Desktop\\relatorio.pdf"));
                    document.close();
                }
            } catch (Exception e) {
            }
        }
    }

}
