package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

// This class represents the Reference page in the application.
public class ReferencePage extends BasePage {

    private static final By REFERENCE_ROOT =
            By.xpath("//main//h1[normalize-space(.)='React Reference Overview']");
    private static final By HOOKS_LINK =
            By.xpath("//main//a[@href='/reference/react/hooks' and not(ancestor::nav)]");

    private static final By FOOTER = By.tagName("footer");

    // Constructor that initializes the ReferencePage with the WebDriver
    public ReferencePage(WebDriver driver) {
        super(driver);
    }

    // This method returns the locator that indicates the Reference page is loaded
    @Override
    protected By pageLoadedLocator() {
        return REFERENCE_ROOT;
    }

    // Opens the Reference page by navigating to the URL and ensuring the page is loaded
    public void goToHooks() {
        wait.until(ExpectedConditions.elementToBeClickable(HOOKS_LINK)).click();
        HooksPage hooks = new HooksPage(driver);
        hooks.ensurePageLoaded();
    }

    // Checks if footer is displayed on the Reference page
    public boolean isFooterDisplayed() {
        return driver.findElement(FOOTER).isDisplayed();
    }

    // Checks if a specific footer link is working by clicking it and verifying the URL
    public boolean isFooterLinkWorking(String linkText) {
        WebElement link = driver.findElement(
                By.xpath("//footer//a[normalize-space()='" + linkText + "']")
        );
        String href = link.getAttribute("href");
        link.click();
        return driver.getCurrentUrl().contains(href);
    }

}


