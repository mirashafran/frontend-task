package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// This is the base class for all page objects.
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // Constructor that initializes the WebDriver and WebDriverWait
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // This method should be overridden by subclasses to provide the specific locator
    protected abstract By pageLoadedLocator();

    // This method checks if the page is loaded by waiting for a specific element to be visible
    public void ensurePageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageLoadedLocator()));
    }

    // This method opens a URL and ensures the page is loaded
    public void open(String url) {
        driver.get(url);
    }


}
