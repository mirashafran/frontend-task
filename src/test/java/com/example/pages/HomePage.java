package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

// This class represents the Home Page of the React documentation site.
public class HomePage extends BasePage {

    private static final String URL = "https://react.dev/";

    private static final By HEADER = By.xpath("//p[contains(text(), 'The library for web and native user interfaces')]");

    private static final By LINK_REFERENCE = By.xpath("//a[@href='/reference/react']");

    private static final By BTN_USE_DARK  = By.cssSelector("button[aria-label='Use Dark Mode']");
    private static final By BTN_USE_LIGHT = By.cssSelector("button[aria-label='Use Light Mode']");
    private static final By HTML = By.tagName("html");

    // Constructor that initializes the HomePage with the WebDriver
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // This method returns the locator that indicates the page is loaded
    @Override
    protected By pageLoadedLocator() {
        return HEADER;
    }

    // Opens the Home Page by navigating to the URL and ensuring the page is loaded
    public HomePage open() {
        super.open(URL);
        ensurePageLoaded();
        return this;
    }

    // Navigates to the Reference page by clicking the reference link
    public ReferencePage goToReference() {
        wait.until(ExpectedConditions.elementToBeClickable(LINK_REFERENCE)).click();
        ReferencePage ref = new ReferencePage(driver);
        ref.ensurePageLoaded();
        return ref;
    }

    //check if the page is in dark mode
    public boolean isDark() {
        String cls = driver.findElement(HTML).getAttribute("class");
        return cls != null && cls.contains("dark");
    }

    // sets the theme to dark or light based on expectedDark
    public void setDark(boolean expectedDark) {
        By locator = expectedDark ? BTN_USE_DARK : BTN_USE_LIGHT;
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // waits until the page is in dark mode or light mode based on expectedDark
    public void waitUntilDarkIs(boolean expectedDark) {
        wait.until(d -> {
            String cls = d.findElement(HTML).getAttribute("class");
            boolean dark = cls != null && cls.contains("dark");
            return dark == expectedDark;
        });
    }
}
