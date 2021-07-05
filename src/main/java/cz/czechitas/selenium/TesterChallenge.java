package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TesterChallenge {
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
    public void userShouldBeAbleToAddAndEditItems() {
        int basePriceValue = 5;
        String basePriceValueString = Integer.toString(basePriceValue);
        Actions action = new Actions(driver);
        WebElement basePriceRow = driver.findElement(By.xpath("//*[@id=\"BasePrice\"]"));
        action.moveToElement(basePriceRow).moveToElement(driver.findElement(By.xpath("//html/body/div/div/div/ul/div[1]/div[1]/span/i"))).click().build().perform();
        WebElement valueInput = driver.findElement(By.xpath("//*[@id=\"base-value-input\"]"));
        valueInput.click();
        valueInput.clear();
        valueInput.sendKeys(basePriceValueString);
        WebElement checkButton = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[1]/div[4]/span[2]/i"));
        checkButton.click();

        //Expected results A - Displayed sum shows correct sum
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

        //Expected results B - Displayed values of price components are rounded correctly
        checkNumberOfDecimals();

        //deletes internal surcharge
        WebElement internalSurchargeRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]"));
        action.moveToElement(internalSurchargeRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[4]/div[4]/span[1]/i"))).click().build().perform();

        //Expected results A - Displayed sum shows correct sum
        displayedSumShowsCorrectSum();

        //edit price component storage surcharge - edit the name
        WebElement storagesurchargeRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]"));
        action.moveToElement(storagesurchargeRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[1]/span/i"))).click().build().perform();
        WebElement storageInputEdit = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[2]/input"));
        clearInputField(storageInputEdit);
        storageInputEdit.sendKeys("T");
        WebElement storageInputCheck = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[5]/div[4]/span[2]/i"));
        storageInputCheck.click();

        //Expected result C - Label input validation
        checkLengthOfLabels();
        String storageInputFromSite = storagesurchargeRow.getText();
        Assertions.assertEquals(storageSurchargeString, storageInputFromSite);

        //Edit price component: Scrap surcharge
        WebElement scrapSurchargeRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]"));
        action.moveToElement(scrapSurchargeRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[1]/span/i"))).click().build().perform();
        WebElement scrapValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]/input"));
        clearInputField(scrapValueInput);
        scrapValueInput.sendKeys("-2.15");
        WebElement checkButtonScrap = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[4]/span[2]/i"));
        checkButtonScrap.click();

        //Expected result D - Value cannot be negative
        WebElement scrapValueInputAfter = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[3]/div[3]"));
        String scrapValueInputTextInputFromSite = scrapValueInputAfter.getText();
        boolean valueIsNotPositive = scrapValueInputTextInputFromSite.contains("-");
        Assertions.assertEquals(false, valueIsNotPositive);
        Assertions.assertEquals(scrapSurcharge, scrapValueInputTextInputFromSite);

        //Edit price component: Alloy surcharge
        WebElement alloyRow = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]"));
        action.moveToElement(alloyRow).moveToElement(driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[1]/span/i"))).click().build().perform();
        WebElement alloyValueInput = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[3]/input"));
        clearInputField(alloyValueInput);
        alloyValueInput.sendKeys("1.79");
        WebElement checkButtonAlloy = driver.findElement(By.xpath("/html/body/div/div/div/ul/div[2]/div[4]/span[2]/i"));
        checkButtonAlloy.click();
        checkNumberOfDecimals();

        //Expected results A - Displayed sum shows correct sum
        displayedSumShowsCorrectSum();
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

    public void clearInputField(WebElement inputField) {
        inputField.sendKeys(Keys.CONTROL + "a");
        inputField.sendKeys(Keys.DELETE);
    }

    public void displayedSumShowsCorrectSum() {
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
        String totalPriceFromSiteString = totalPrice.getText();
        totalPriceFromSiteString = totalPriceFromSiteString.replace(".", ",");
        Assertions.assertEquals(totalPriceFromSiteString, totalPricesCalculatedString);
    }

    public void checkNumberOfDecimals() {
        List<WebElement> componentsPrices = driver.findElements(By.xpath("//div[contains(@class,'text-right')]"));
        for (int i = 1; i < componentsPrices.size(); i++) {
            WebElement oneComponent = componentsPrices.get(i);
            String getPricesString = oneComponent.getText();
            String[] array = getPricesString.split("\\.");
            boolean correctLength;
            if (array[1].length() == 1) {
                correctLength = true;
            } else if (array[1].length() == 2) {
                correctLength = true;
            } else {
                correctLength = false;
            }
            Assertions.assertEquals(true, correctLength);
        }
    }

    public void checkLengthOfLabels() {
        List<WebElement> componentsLabels = driver.findElements(By.xpath("//div[contains(@class,'flex-grow flex flex-col')]"));
        for (int i = 0; i < componentsLabels.size() - 1; i++) {
            WebElement oneComponentLabel = componentsLabels.get(i);
            String oneComponentLabelString = oneComponentLabel.getText();
            boolean correctLengthLabelBoolean = true;
            if (oneComponentLabelString.length() >= 3) {
                correctLengthLabelBoolean = true;
            } else if (oneComponentLabelString.length() <= 2) {
                correctLengthLabelBoolean = false;
            }
            Assertions.assertEquals(true, correctLengthLabelBoolean);
        }
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

}
