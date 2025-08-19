package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// This class represents the Hooks page in the application.
public class HooksPage extends BasePage {

    // The locator for the Hooks page header to ensure the page is loaded
    private static final By H1_HOOKS = By.xpath("//h1[normalize-space(.)='Built-in React Hooks']");

    // The constructor initializes the HooksPage with the WebDriver
    public HooksPage(WebDriver driver) {
        super(driver);
    }

    // This method returns the locator that indicates the Hooks page is loaded
    @Override
    protected By pageLoadedLocator() {
        return H1_HOOKS;
    }
}
