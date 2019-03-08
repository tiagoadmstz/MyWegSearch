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
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.htmlunit.HtmlUnitWebElement;

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
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
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

            List<WebElement> lista = driver.findElements(By.tagName("iframe"));

            for (WebElement we : lista) {
                if (we.getAttribute("id").equals("contentAreaFrame")) {
                    List<WebElement> lt = driver.switchTo().frame(we).findElements(By.tagName("iframe"));
                    for (WebElement we2 : lt) {
                        if (we2.getAttribute("id").equals("isolatedWorkArea")) {
                            driver.switchTo().frame(we2);
                            break;
                        }
                    }
                    break;
                };
            }

            //WebClient wc = new WebClient(BrowserVersion.INTERNET_EXPLORER);
            //driver.switchTo().frame(driver.findElementById("contentAreaFrame")).switchTo().frame(driver.findElementById("isolatedWorkArea"));
            //HtmlUnitDriver hht2 = (HtmlUnitDriver) driver.switchTo().frame(driver.findElementById("contentAreaFrame")).switchTo().frame(0);
            System.out.println("\nPesquisando motor requisitado!\n");

            WebElement material_motor = driver.findElementById("aaaa.NseView.MotorItem");
            WebElement material_data = driver.findElementById("aaaa.NseView.ValidFrom");
            WebElement serial_motor = driver.findElementById("aaaa.NseView.SerialNumber");
            WebElement button_pesquisar = driver.findElementById("aaaa.NseView.SearchButton");
            serial_motor.sendKeys("1024218982");
            button_pesquisar.click();
            Thread.sleep(2000);
            System.out.println("2 segundos para carregar a página");
            //System.out.println(driver.getPageSource());

            System.out.println("Identificação");
            System.out.println("\t" + driver.findElementById("aaaa.NseView.MotorItemResponse_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.motorItemResponseTextView"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.ValidFromResponse_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.validFromResponseTextView"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.InsulationClass_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.InsulationClass"));
            System.out.println("Ferragem");
            System.out.println("\t" + driver.findElementById("aaaa.NseView.Gap_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.Gap"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.NumberStatorSlot_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.NumberStatorSlot"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.Package_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.Package"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.StatorExternalDiameter_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.StatorExternalDiameter"));
            System.out.println("\t" + driver.findElementById("aaaa.NseView.StatorInternalDiameter_label-text").getText() + ": " + getContentSpan(driver, "aaaa.NseView.StatorInternalDiameter"));
            System.out.println("Itens");
            //System.out.println("\t" + driver2.findElementById("aaaa.NseView.Item_editor.0").getText());
            //descrição do motor
            getTableBody(driver, "aaaa.NseView.Table-contentTBody");

            System.out.println("Bobinagem");
            getTableBody(driver, "aaaa.NseView.Table_0-contentTBody");

            //Download relatório
            getPdfs(driver, "aaaa.NseView.reportFileDownload", "relatorio");
            //Download diagrama - ../../weg.net/astec~nse/App/~wd_cache_/~wd_key_J7VmiDB8ZYRznKgB/ZEL122-WMO00004-_PIRWBUSER04.pdf?sap-wd-download=1&sap-wd-cltwndid=WID1551997766273&sap-wd-secure-id=2FbqF-D9fTPRkzUM3gbGLA%3D%3D&sap-ext-sid=GenhdNNDh4O7Y9jkw221pA--h_FkAv5kJQj_UJlBBCmk9Q--%2Fpcd%3Aportal_content%2Fnet.weg.folder.weg%2Fnet.weg.folder.core%2Fnet.weg.folder.roles%2Fnet.weg.role.technical_assistant_motors%2Fnet.weg.workset.technical_assistant_motors%2Fwireframe_system%2Fweg.net%2Fastec%7Ense%2FApp%2Fbase%2F&sap-wd-norefresh=X&sap-ep-version=7.50.201611260251
            getPdfs(driver, "aaaa.NseView.WindingDiagramFileDownload", "diagrama");
            System.out.println("Fim");
        } catch (Exception ex) {
            Logger.getLogger(MyWegDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getPdfs(HtmlUnitDriver driver, String id, String fileName) {
        try {
            HtmlUnitWebElement wbe = (HtmlUnitWebElement) driver.findElementById(id);
            Field f = wbe.getClass().getDeclaredField("element");
            f.setAccessible(true);
            HtmlAnchor anchor = (HtmlAnchor) f.get(wbe);
            WebResponse wr = anchor.click().getWebResponse();
            InputStream input = wr.getContentAsStream();
            PDDocument document = PDDocument.load(input);
            document.save(new File("C:\\Users\\tiago.teixeira\\Documents\\" + fileName + ".pdf"));
            //document.save(new File("C:\\Users\\Tiago\\" + fileName + ".pdf"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTableBody(HtmlUnitDriver driver, String id) {
        try {
            HtmlUnitWebElement wbe = (HtmlUnitWebElement) driver.findElementById(id);

            //melhoria para pegar os labels das colunas das tabelas
            /*try {
            List<WebElement> lista = wbe.findElementsByTagName("span");
            lista.forEach(lt -> {
            HtmlUnitWebElement huwe = (HtmlUnitWebElement) lt;
            try {
            Field f = huwe.getClass().getDeclaredField("element");
            f.setAccessible(true);
            HtmlSpan span = (HtmlSpan) f.get(wbe);
            System.out.println(span.getTextContent());
            } catch (Exception e) {
            }
            });
            } catch (Exception e) {
            }*/
            //valores
            Field f = wbe.getClass().getDeclaredField("element");
            f.setAccessible(true);
            HtmlTableBody body = (HtmlTableBody) f.get(wbe);
            Iterator<DomElement> elements = body.getChildElements().iterator();
            while (elements.hasNext()) {
                HtmlTableRow row = (HtmlTableRow) elements.next();
                row.getCells().forEach(cell -> {
                    if (cell.getIndex() > 0) {
                        Iterator<DomElement> itens = cell.getChildElements().iterator();
                        while (itens.hasNext()) {
                            DomElement de = itens.next();
                            if (de instanceof HtmlSpan) {
                                HtmlSpan span = (HtmlSpan) de;
                                System.out.println(span.getTextContent());
                            }
                        }
                    }
                });
                //Iterator<DomElement> itens = row.getCell(1).getChildElements().iterator();
                //System.out.println(row.getCell(1).getTextContent());
            }
        } catch (Exception e) {
        }
    }

    private String getContentSpan(HtmlUnitDriver driver, String id) {
        try {
            HtmlUnitWebElement wbe = (HtmlUnitWebElement) driver.findElementById(id);
            Field f = wbe.getClass().getDeclaredField("element");
            f.setAccessible(true);
            HtmlSpan span = (HtmlSpan) f.get(wbe);
            return span.getTextContent();
        } catch (Exception e) {
        }
        return "";
    }

}
