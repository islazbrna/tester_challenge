package cz.czechitas.selenium;

import okhttp3.internal.Internal;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TestyEditacePrihlasky {

    // Nejdrive konstanty
    private static final String applicationUrl = "http://localhost:3000/";

    WebDriver browser;


    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        browser = new FirefoxDriver();
        browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        browser.navigate().to(applicationUrl);
    }

    @Test
    public void userShouldBeAbleToChangeBaseprice() throws InterruptedException {
        //pak oddelat ten threadsleep a tady to throws
        int basePriceValue = 5;
        String basePriceValueString = Integer.toString(basePriceValue);

        Actions action = new Actions(browser);
        WebElement basePriceArea = browser.findElement(By.xpath("//*[@id=\"BasePrice\"]"));
        action.moveToElement(basePriceArea).moveToElement(browser.findElement(By.xpath("//html/body/div/div/div/ul/div[1]/div[1]/span/i"))).click().build().perform();

        WebElement valueInput = browser.findElement(By.xpath("//*[@id=\"base-value-input\"]"));
        valueInput.click();
        valueInput.clear();
        valueInput.sendKeys(basePriceValueString);

        WebElement checkButton = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[1]/div[4]/span[2]/i"));
        checkButton.click();

        String newBaseprice = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[1]/div[3]/div")).getText();

        //Assertions.assertEquals(basePriceValueString, newBaseprice);
        Thread.sleep(3000);

        double alloySurcharge = 2.15;
        double scrapSurcharge = 3.14;
        double internalSurcharge = 0.7658;
        double externalSurcharge = 1;
        double storageSurcharge = 0.3;

        WebElement labelInput = browser.findElement(By.xpath("//*[@id='ghost-label-input']"));
        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("Alloy Surcharge");

        WebElement componentPrice = browser.findElement(By.xpath("//*[@id=\"ghost-value-input\"]"));
        componentPrice.clear();
        componentPrice.sendKeys("2.15");

        WebElement checkButtonComponents = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]/i"));
        checkButtonComponents.click();

        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("Scrap Surcharge");
        componentPrice.clear();
        componentPrice.sendKeys("3.14");
        checkButtonComponents.click();

        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("Internal Surcharge");
        componentPrice.clear();
        componentPrice.sendKeys("0.7658");
        checkButtonComponents.click();

        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("External Surcharge");
        componentPrice.clear();
        componentPrice.sendKeys("1");
        checkButtonComponents.click();

        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("Storage Surcharge");
        componentPrice.clear();
        componentPrice.sendKeys("0.3");
        checkButtonComponents.click();

        //deletes
        WebElement hoverRow = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[4]"));
        action.moveToElement(hoverRow).moveToElement(browser.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]/i"))).click().build().perform();

        //edit price component
        WebElement storageInput = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]/span"));
        storageInput.sendKeys(Keys.CONTROL + "a");
        storageInput.sendKeys(Keys.DELETE);
        storageInput.sendKeys("T");
        checkButtonComponents.click();

        Assertions.assertEquals(basePriceValueString, newBaseprice);
        Thread.sleep(300000);
    }
//    @Disabled
//    @Test
//    public void userShouldDeleteComponent() {
//        Actions action = new Actions(browser);
//        WebElement hoverRow = browser.findElement(By.xpath("/html/body/div/div/div/ul/div[4]"));
//        action.moveToElement(hoverRow).moveToElement(browser.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]/i"))).click().build().perform();
//
//        //WebElement trashButton =
//    }

    @AfterEach
    public void tearDown() {
        //browser.close();
    }
}

//    public void rodicMusiBytSchopenUpravitExistujiciPrihlaskuZeSeznamuPrihlasek() {
//        String urlZakovychPrihlasek = applicationUrl + "zaci";
//        prohlizec.navigate().to(urlZakovychPrihlasek);
//
//        WebElement polickoEmail = prohlizec.findElement(By.id("email"));
//        WebElement polickoHeslo = prohlizec.findElement(By.id("password"));
//        WebElement tlacitkoPrihlasit = prohlizec.findElement(By.xpath("//form//button[contains(text(), 'Přihlásit')]"));
//        polickoEmail.sendKeys("petr.otec@seznam.cz");
//        polickoHeslo.sendKeys("Czechitas123");
//        tlacitkoPrihlasit.click();
//
//        List<WebElement> bunkySeJmenyDeti = prohlizec.findElements(By.xpath("//table[@id = 'DataTables_Table_0']//td[1]"));
//        int nalezenyRadekCislo = -1;
//        for (int i = 0; i < bunkySeJmenyDeti.size(); i++) {
//            WebElement bunka = bunkySeJmenyDeti.get(i);
//            if (bunka.getText().equals("Jitka Dite1621055190111")) {
//                nalezenyRadekCislo = i;
//            }
//        }
//        Assertions.assertTrue(nalezenyRadekCislo > -1);
//
//        WebElement tlacitkoUpravitPrihlasku = prohlizec.findElement(By.xpath("//table[@id = 'DataTables_Table_0']//tr["+(nalezenyRadekCislo+1)+"]/td/div[contains(@class, 'btn-group')]/a[@title = 'Upravit']"));
//        tlacitkoUpravitPrihlasku.click();
//
//        String adresaEditacniStranky = prohlizec.getCurrentUrl();
//
//        WebElement polickoPoznamky = prohlizec.findElement(By.id("note"));
//        polickoPoznamky.clear();
//        String novyTextPoznamky = "Prave ted je " + System.currentTimeMillis();
//        polickoPoznamky.sendKeys(novyTextPoznamky);
//
//        WebElement tlacitkoOdeslat = prohlizec.findElement(By.xpath("//input[@type='submit']"));
//        tlacitkoOdeslat.click();
//
//        WebDriverWait explicitniCekani = new WebDriverWait(prohlizec, 30);
//        explicitniCekani.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message']")));
//
//        WebElement potvrzeniPrihlasky = prohlizec.findElement(By.xpath("//div[@class='toast-message']"));
//        String text = potvrzeniPrihlasky.getText();
//        Assertions.assertTrue(text.startsWith("Žák "));
//        Assertions.assertTrue(text.endsWith(" byl úspěšně upraven"));
//
//        prohlizec.navigate().to(adresaEditacniStranky);
//        WebElement polickoPoznamky2 = prohlizec.findElement(By.id("note"));
//        String puvodniTextPoznamky = polickoPoznamky2.getAttribute("value");
//        Assertions.assertEquals(puvodniTextPoznamky, novyTextPoznamky);
