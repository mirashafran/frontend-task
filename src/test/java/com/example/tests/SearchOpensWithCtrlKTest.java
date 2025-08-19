//package com.example.tests;
//
//import com.example.base.BaseTest;
//import com.example.pages.HomePage;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class SearchOpensWithCtrlKTest extends BaseTest {
//
//    private static final By SEARCH_INPUT = By.id("docsearch-input");
//    private static final By MODAL_CONTAINER = By.cssSelector(".DocSearch-Container, .DocSearch-Modal");
//
//    @Test
//    public void searchOpensWithCtrlK_andClosesWithEsc() {
//        new HomePage(driver).open();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        // opening the search modal with Ctrl+K
//        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("k").keyUp(Keys.CONTROL).perform();
//
//        //wait for the modal to be visible
//        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
//        assertTrue(input.isDisplayed(), "Search input should be visible after Ctrl+K");
//
//        // closing the modal with an Esc key
//        input.sendKeys(Keys.ESCAPE);
//
//        // wait for the modal to be closed
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(MODAL_CONTAINER));
//    }
//}

package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchOpensWithCtrlKTest extends BaseTest {

    private static final By SEARCH_INPUT = By.id("docsearch-input");
    private static final By MODAL_CONTAINER = By.cssSelector(".DocSearch-Container, .DocSearch-Modal");
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    @Test
    public void searchOpensWithCtrlK_andClosesWithEsc() {
        openHomePage();

        openSearchModalWithCtrlK();
        verifySearchModalIsOpen();

        closeSearchModalWithEsc();
        verifySearchModalIsClosed();
    }

    // this method opens the home page
    private void openHomePage() {
        new HomePage(driver).open();
    }

    // this method opens the search modal using Ctrl+K
    private void openSearchModalWithCtrlK() {
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("k").keyUp(Keys.CONTROL).perform();
    }

    // this method verifies that the search modal is open by checking if the search input is visible
    private void verifySearchModalIsOpen() {
        WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT));
        assertTrue(input.isDisplayed(), "Search input should be visible after Ctrl+K");
    }

    // this method closes the search modal by sending an Escape key
    private void closeSearchModalWithEsc() {
        WebElement input = driver.findElement(SEARCH_INPUT);
        input.sendKeys(Keys.ESCAPE);
    }

    // this method verifies that the search modal is closed by checking if the modal container is not visible
    private void verifySearchModalIsClosed() {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(MODAL_CONTAINER));
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, TIMEOUT);
    }
}
