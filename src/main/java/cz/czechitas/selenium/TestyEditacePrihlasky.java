package cz.czechitas.selenium;

import com.google.common.base.Verify;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyEditacePrihlasky {

    // Nejdrive konstanty
    private static final String applicationUrl = "http://localhost:3000/";

    WebDriver driver;


    @BeforeEach
    public void setUp() {
//      System.setProperty("webdriver.gecko.driver", System.getProperty("user.home") + "/Java-Training/Selenium/geckodriver");
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to(applicationUrl);
    }

    @Test
    public void userShouldBeAbleToChangeBaseprice() throws InterruptedException {
        //pak oddelat ten threadsleep a tady to throws
        int basePriceValue = 5;
        String basePriceValueString = Integer.toString(basePriceValue);

        float basePriceValueFloat = (float) (basePriceValue);
        String basePriceValueFloatString = Float.toString(basePriceValueFloat);

        Actions action = new Actions(driver);
        WebElement basePriceArea = driver.findElement(By.xpath("//*[@id=\"BasePrice\"]"));
        action.moveToElement(basePriceArea).moveToElement(driver.findElement(By.xpath("//html/body/div/div/div/ul/div[1]/div[1]/span/i"))).click().build().perform();

        WebElement valueInput = driver.findElement(By.xpath("//*[@id=\"base-value-input\"]"));
        valueInput.click();
        valueInput.clear();
        valueInput.sendKeys(basePriceValueString);

        WebElement checkButton = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[1]/div[4]/span[2]/i"));
        checkButton.click();

        String newBaseprice = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[1]/div[3]/div")).getText();

        double alloySurcharge = 2.15;
        double scrapSurcharge = 3.14;
        double internalSurcharge = 0.7658;
        double externalSurcharge = 1;
        double storageSurcharge = 0.3;

        WebElement labelInput = driver.findElement(By.xpath("//*[@id='ghost-label-input']"));
        labelInput.sendKeys(Keys.CONTROL + "a");
        labelInput.sendKeys(Keys.DELETE);
        labelInput.sendKeys("Alloy Surcharge");

        WebElement componentPrice = driver.findElement(By.xpath("//*[@id=\"ghost-value-input\"]"));
        componentPrice.clear();
        componentPrice.sendKeys("2.15");

        WebElement checkButtonComponents = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]/i"));
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

        //deletes internal surcharge
        WebElement hoverRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]"));
        action.moveToElement(hoverRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]/i"))).click().build().perform();

        //edit price component storage surcharge
        WebElement storageInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]/span"));
        action.moveToElement(storageInput).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[1]/span/i"))).click().build().perform();
        WebElement storageInputEdit = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]/input"));
        storageInputEdit.click();
        storageInputEdit.clear();
        storageInputEdit.sendKeys(Keys.CONTROL + "a");
        storageInputEdit.sendKeys(Keys.DELETE);
        storageInputEdit.sendKeys("T");
        WebElement storageInputCheck = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[4]/span[2]/i"));
        storageInputCheck.click();

        //Edit price component: Scrap surcharge
        WebElement scrapRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]"));
        action.moveToElement(scrapRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[1]/span/i"))).click().build().perform();
        WebElement scrapValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]/input"));
        scrapValueInput.sendKeys(Keys.CONTROL + "a");
        scrapValueInput.sendKeys(Keys.DELETE);
        scrapValueInput.sendKeys("-2.15");
        WebElement checkButtonScrap = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[4]/span[2]/i"));
        checkButtonScrap.click();


//        Hover row
//        Click on ‘Pencil’ icon
//        Enter new value: -2.15
//        Click on ‘Check’ icon
//        Verify Expected Results D.

//        Edit price component: Alloy surcharge
//        Hover row
//        Click on ‘Pencil’ icon
//        Enter new value: 1.79
//        Click on ‘Check’ icon
//        Verify Expected Results A.

        WebElement alloyRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]"));
        action.moveToElement(alloyRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[1]/span/i"))).click().build().perform();

        WebElement alloyValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[3]/input"));
        alloyValueInput.sendKeys(Keys.CONTROL + "a");
        alloyValueInput.sendKeys(Keys.DELETE);
        alloyValueInput.sendKeys("1.79");
        WebElement checkButtonAlloy = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]/i"));
        checkButtonAlloy.click();
        Thread.sleep(4000);


        Assertions.assertEquals(basePriceValueFloatString, newBaseprice);

        List<WebElement> componentsPrices = driver.findElements(By.xpath("//div[contains(@class,'text-right')]"));
        float totalPrices = 0;
        for (int i = 1; i < componentsPrices.size(); i++) {
            WebElement oneComponent = componentsPrices.get(i);
            float addPrices = Float.parseFloat(oneComponent.getText());
            totalPrices += addPrices;
            System.out.println(oneComponent.getText());
            System.out.println(totalPrices);
        }

        DecimalFormat totalPricesTwoDecimals = new DecimalFormat("#.00");
        String totalPricesCalculatedString = totalPricesTwoDecimals.format(totalPrices);

        WebElement totalPrice = driver.findElement(By.xpath("/html/body/div/div/div/div/span"));
        String totalPriceFromPageString = totalPrice.getText();
        totalPriceFromPageString= totalPriceFromPageString.replace(".", ",");
        System.out.println(totalPriceFromPageString);
        //float totalPriceFloat = Float.parseFloat(totalPrice.getText());
        Assertions.assertEquals(totalPriceFromPageString, totalPricesCalculatedString);

//        int nalezenyRadekCislo = -1;
//        for (int i = 0; i < bunkySeJmenyDeti.size(); i++) {
//            WebElement bunka = bunkySeJmenyDeti.get(i);
//            if (bunka.getText().equals("Jitka Dite1621055190111")) {
//                nalezenyRadekCislo = i;

    }


    @AfterEach
    public void tearDown() {
        //browser.close();
    }
}

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
