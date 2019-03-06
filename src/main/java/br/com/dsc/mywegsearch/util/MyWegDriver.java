/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dsc.mywegsearch.util;

import br.com.dsc.mywegsearch.entities.Configuracao;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author Tiago
 */
public class MyWegDriver {

    /**
     * Alterar senha
     * <a href="javascript:void(0);" onclick="window.open('http://mail.weg.net/owa/auth/changepassword.aspx', 'wprchangepwd', 'width=800,height=500,top=0,left=0,scrollbars=yes');">Alterar
     * senha </a>
     *
     * @return
     */
    public synchronized WebClient getLoginPageMyWeg() {
        try {
            WebClient wc = new WebClient(BrowserVersion.FIREFOX_45);
            wc.getOptions().setJavaScriptEnabled(true);
            wc.getOptions().setDownloadImages(true);
            wc.getOptions().setRedirectEnabled(true);
            wc.getOptions().setTimeout(90000);
            wc.getOptions().setPopupBlockerEnabled(false);
            wc.getOptions().setUseInsecureSSL(true);

            wc.setJavaScriptTimeout(90000);
            WebResponse wr = wc.loadWebResponse(new WebRequest(new URL("https://www.myweg.net:443/irj/portal/weg_logon/")));
            System.out.println(wr.getStatusCode());
            System.out.println(wr.getStatusMessage());
            //wc.getOptions().setSSLClientCertificate(new URL("com.sap.portal.runtime.logon.certlogon"), "fofampjmdnncpgedabkghbgeajkkaijk", "");

            WebWindow ww = wc.openWindow(new URL("https://www.myweg.net:443/irj/portal/weg_logon/"), "Weg");
            System.out.println(ww.getEnclosedPage().toString());
            //System.out.println(wr.getContentAsString());
            HtmlPage page_login = wc.getPage(wr.getWebRequest().getUrl());
            HtmlForm form_login = page_login.getHtmlElementById("certLogonForm");
            form_login.getInputByName("j_username").setValueAttribute("t-tese");
            form_login.getInputByName("j_password").setValueAttribute("wegser123(");

            System.out.println(page_login.getBaseURL());
            System.out.println(page_login.getTitleText());

            //HtmlPage home_page = form_login.getInputByName("uidPasswordLogon").click();
            //System.out.println(home_page.getBaseURL());
            //System.out.println(home_page.getTitleText());
            return wc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HtmlPage getSessionHomePage() {
        try {
            WebClient wc = new WebClient(BrowserVersion.INTERNET_EXPLORER);
            wc.getOptions().setJavaScriptEnabled(true);
            wc.getOptions().setDownloadImages(true);
            wc.getOptions().setRedirectEnabled(true);
            wc.getOptions().setTimeout(90000);
            wc.getOptions().setPopupBlockerEnabled(false);
            wc.getOptions().setUseInsecureSSL(true);
            wc.getOptions().setActiveXNative(true);
            wc.getOptions().setAppletEnabled(true);
            wc.setJavaScriptTimeout(90000);
            wc.waitForBackgroundJavaScript(90000);
            
            System.out.println(wc.openWindow(new URL("https://www.myweg.net:443/irj/portal"), "Weg").getEnclosedPage().toString());

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getLoginPageMyWeg(Configuracao config) {
        try {
            HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_52);
            driver.manage().timeouts().setScriptTimeout(2, TimeUnit.MINUTES);
            driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
            driver.setAcceptSslCertificates(true);
            driver.setDownloadImages(true);
            driver.setJavascriptEnabled(true);

            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

            driver.get("https://www.myweg.net/irj/portal");
            System.out.println(driver.getCurrentUrl());
            WebElement usuario = driver.findElementById("logonuidfield");
            WebElement senha = driver.findElementById("logonpassfield");
            WebElement acesso = driver.findElementByName("uidPasswordLogon");

            usuario.sendKeys(config.getLogin());
            senha.sendKeys(config.getPasswd());
            acesso.submit();

            WebElement bem_vindo = driver.findElementById("welcome_message_container");
            System.out.println(bem_vindo.getAttribute("title"));
            //System.out.println(driver.getCurrentUrl());
            System.out.println("4 segundos para carregar a página");
            Thread.sleep(4000);
            //WebElement all_tabs = driver.findElementById("allFirstLevelTabs");
            //all_tabs.click();
            WebElement asstec = driver.findElementById("tabIndex1");
            System.out.println("Assistentes Técnicos");
            asstec.click();

            System.out.println("2 segundos para carregar a página");
            Thread.sleep(2000);

            WebElement sistema_bobinagem = driver.findElementById("subTabIndex1");
            System.out.println("Sistema de Bobinagem (WMO)");
            sistema_bobinagem.click();

            System.out.println("2 segundos para carregar a página");
            Thread.sleep(2000);

            WebElement wireFrame = driver.findElementById("L2N0");
            wireFrame.click();
            System.out.println(wireFrame.getAttribute("title"));

            System.out.println("2 segundos para carregar a página");
            Thread.sleep(2000);

            WebClient wc = new WebClient(BrowserVersion.INTERNET_EXPLORER);
            HtmlUnitDriver driver2 = (HtmlUnitDriver) driver.switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea");
            System.out.println("\nPesquisando motor requisitado!\n");

            WebElement material_motor = driver2.findElementById("aaaa.NseView.MotorItem");
            WebElement material_data = driver2.findElementById("aaaa.NseView.ValidFrom");
            WebElement serial_motor = driver2.findElementById("aaaa.NseView.SerialNumber");
            WebElement button_pesquisar = driver2.findElementById("aaaa.NseView.SearchButton");
            serial_motor.sendKeys("1024218982");
            button_pesquisar.click();
            Thread.sleep(2000);
            System.out.println("2 segundos para carregar a página");
            //System.out.println(driver.getPageSource());
            System.out.println("Identificação");
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.MotorItemResponse_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.motorItemResponseTextView").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.ValidFromResponse_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.validFromResponseTextView").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.InsulationClass_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.InsulationClass").getText());
            System.out.println("Ferragem");
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.Gap_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.Gap").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.NumberStatorSlot_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.NumberStatorSlot").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.Package_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.Package").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.StatorExternalDiameter_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.StatorExternalDiameter").getText());
            System.out.println("\t" + driver2.findElementById("aaaa.NseView.StatorInternalDiameter_label-text").getText() + ": " + driver2.findElementById("aaaa.NseView.StatorInternalDiameter").getText());
            System.out.println("Itens");
            //System.out.println("\t" + driver2.findElementById("aaaa.NseView.Item_editor.0").getText());
            System.out.println("Bobinagem");
            /*
            
            */
        } catch (Exception ex) {
            Logger.getLogger(MyWegDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
