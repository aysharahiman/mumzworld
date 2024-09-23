package com.aysha;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.awt.*;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MumzworldTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Set up WebDriver (ChromeDriver)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("https://www.mumzworld.com");
    }

    @Test
    public void purchaseProduct() {

        // Search for a product
        try {
            WebElement searchProduct = driver.findElement(By.cssSelector("input[placeholder='What are you looking for?']"));
            searchProduct .sendKeys("Skullcandy - Crusher Evo Wireless Over Ear Chill Headphones - Grey");
            searchProduct.submit();
        } catch (Exception e) {
            System.out.println("Got exception while getting search element");
            throw new RuntimeException(e);
        }

       //  Clsoing Popup to continue
        try {
            WebElement searchButton = driver.findElement(By.cssSelector("button[title='Submit your search query']")); // Click anywhere on the page
            searchButton.click(); // This will attempt to close the popup by clicking outside of it
        } catch (Exception e) {
            System.out.println("No popup was detected or click on the body element failed.");
        }

        // Wait for the product detail page to load and click on the product image
        try {
            WebElement productImage = driver.findElement(By.cssSelector("img[alt='Skullcandy - Crusher Evo Wireless Over Ear Chill Headphones - Grey']")); // Adjust the selector as needed
            productImage.click();
        }  catch (Exception e) {
            System.out.println("Got exception while getting product Image");
            throw new RuntimeException(e);
        }

        // Add the  product to the bag
        try {
            WebElement product = driver.findElement(By.cssSelector("section[class='productFullDetail-addToCartSection-dA4'] span[class='button-content-7Hl'] span:nth-child(1)"));
            product.click();
        } catch (Exception e) {
            System.out.println("Got Exception while adding to bag");
            throw new RuntimeException(e);
        }

        // Go to view bag page
        try {
            WebElement viewBagButton = driver.findElement(By.cssSelector("a[title='View Bag']"));
            viewBagButton.click();
        }  catch (Exception e) {
            System.out.println("Got Exception while viewing bag");
            throw new RuntimeException(e);
        }

        //Increase product quantity
        WebElement incrementButton = driver.findElement(By.cssSelector("div[class='product-detailsRight-2ix'] button[aria-label='Increase Quantity'] span[class='quantity-icon-2Pi'] svg"));
        for (int i = 0; i < 2; i++) {
            incrementButton.click();
        }

        // Proceed to checkout
        WebElement checkoutButton = driver.findElement(By.cssSelector("button[class='proceedToCheckoutBtn-button-3S2 button-root_highPriority-2iR button-root-dxQ clickable-root-orq']"));
        checkoutButton.click();

        // Signup for checkout
        WebElement signUpButton = driver.findElement(By.cssSelector("button[title='Sign up'] span[class='button-content-7Hl']"));
        signUpButton.click();

        // Fill out registration form
        WebElement firstNameField = driver.findElement(By.name("customer.firstname"));
        firstNameField.sendKeys("Aysha");

        WebElement lastNameField = driver.findElement(By.name("customer.lastname"));
        lastNameField.sendKeys("Rahiman");

        WebElement emailField = driver.findElement(By.name("customer.email"));
        emailField.sendKeys(generateRandomEmail());

        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("Aysh@1234");

        // Submit registration
        WebElement submitButton = driver.findElement(By.cssSelector("button[title='Register'] span[class='button-content-7Hl']"));
        submitButton.click();

    }

    @AfterClass
    public void tearDown() {
        // Commenting this, so that reviewer can see the app flow properly
        //driver.quit();
    }

    private void moveAndClickCursor(int x, int y) {
        try {
            Actions actions = new Actions(driver);
            Robot robot = new Robot();
            robot.mouseMove(x, y);
            actions.click().build().perform();
        } catch (Exception e) {
            System.out.println("Exception in moving and clicking cursor");
        }
    }

    private String generateRandomEmail() {
        Random r = new Random();
        int low = 100;
        int high = 10000;
        int randomNum = r.nextInt(high-low) + low;
        return "aysharah+" + randomNum + "@gmail.com";
    }
}

