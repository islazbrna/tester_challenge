package cz.czechitas.selenium;

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
        displayedSumShowsCorrectSum();

        String alloySurcharge = "2.15";
        String scrapSurcharge = "3.14";
        String internalSurcharge = "0.7658";
        String externalSurcharge = "1";
        String storageSurcharge = "0.3";

        String alloySurchargeString = "Alloy surcharge";
        String scrapSurchargeString = "Scrap surcharge";
        String internalSurchargeString = "Internal surcharge";
        String externalSurchargeString = "External surcharge";
        String storageSurchargeString = "Storage surcharge";

        enterComponent(alloySurchargeString, alloySurcharge);
        enterComponent(scrapSurchargeString, scrapSurcharge);
        enterComponent(internalSurchargeString, internalSurcharge);
        enterComponent(externalSurchargeString, externalSurcharge);
        enterComponent(storageSurchargeString, storageSurcharge);

        //deletes internal surcharge
        WebElement hoverRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]"));
        action.moveToElement(hoverRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]/i"))).click().build().perform();
        displayedSumShowsCorrectSum();

        //edit price component storage surcharge - edit the name
        WebElement storageInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]"));
        action.moveToElement(storageInput).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[1]/span/i"))).click().build().perform();
        WebElement storageInputEdit = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]/input"));
        clearInputField(storageInputEdit);
        storageInputEdit.sendKeys("T");
        WebElement storageInputCheck = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[4]/span[2]/i"));
        storageInputCheck.click();

        String storageInputFromSite = storageInput.getText();
        Assertions.assertEquals(storageSurchargeString, storageInputFromSite);

        //Edit price component: Scrap surcharge
        WebElement scrapRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]"));
        action.moveToElement(scrapRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[1]/span/i"))).click().build().perform();
        WebElement scrapValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]/input"));
        clearInputField(scrapValueInput);
        scrapValueInput.sendKeys("-2.15");
        WebElement checkButtonScrap = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[4]/span[2]/i"));
        checkButtonScrap.click();

//      Edit price component: Alloy surcharge
        WebElement alloyRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]"));
        action.moveToElement(alloyRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[1]/span/i"))).click().build().perform();

        WebElement alloyValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[3]/input"));
        clearInputField(alloyValueInput);
        alloyValueInput.sendKeys("1.79");
        WebElement checkButtonAlloy = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]/i"));
        checkButtonAlloy.click();
        Thread.sleep(4000);

        displayedSumShowsCorrectSum();
        Assertions.assertEquals(basePriceValueFloatString, newBaseprice);
    }

    public void enterComponent(String nameOfComponent, String priceOfComponent) {
        WebElement labelInput = driver.findElement(By.xpath("//*[@id='ghost-label-input']"));
        WebElement componentPrice = driver.findElement(By.xpath("//*[@id=\"ghost-value-input\"]"));
        WebElement checkButtonComponents = driver.findElement(By.xpath("//*[@id=\"ghost-check-icon\"]"));
        clearInputField(labelInput);
        labelInput.sendKeys(nameOfComponent);
        componentPrice.clear();
        componentPrice.sendKeys(priceOfComponent);
        checkButtonComponents.click();
    }

    public void clearInputField(WebElement inputField){
        inputField.sendKeys(Keys.CONTROL + "a");
        inputField.sendKeys(Keys.DELETE);
    }

    public void displayedSumShowsCorrectSum (){
        List<WebElement> componentsPrices = driver.findElements(By.xpath("//div[contains(@class,'text-right')]"));
        float totalPrices = 0;
        for (int i = 1; i < componentsPrices.size(); i++) {
            WebElement oneComponent = componentsPrices.get(i);
            float addPrices = Float.parseFloat(oneComponent.getText());
            totalPrices += addPrices;
        }

        DecimalFormat totalPricesTwoDecimals = new DecimalFormat("#.00");
        String totalPricesCalculatedString = totalPricesTwoDecimals.format(totalPrices);

        WebElement totalPrice = driver.findElement(By.xpath("/html/body/div/div/div/div/span"));
        String totalPriceFromPageString = totalPrice.getText();
        totalPriceFromPageString = totalPriceFromPageString.replace(".", ",");
        Assertions.assertEquals(totalPriceFromPageString, totalPricesCalculatedString);
    }


    @AfterEach
    public void tearDown() {
        //browser.close();
    }
}
