package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.SearchModalPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchOpensWithCtrlKTest extends BaseTest {

    @Test
    public void searchOpensWithCtrlK_andClosesWithEsc() {
        openHomePage();
        SearchModalPage modal = new SearchModalPage(driver);

        openSearchModalWithCtrlK();
        verifySearchModalIsOpen(modal);

        closeSearchModalWithEsc(modal);
        verifySearchModalIsClosed(modal);
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
    private void verifySearchModalIsOpen(SearchModalPage modal) {
        modal.waitUntilOpen();
        assertTrue(modal.getInput().isDisplayed(), "Search input should be visible after Ctrl+K");
    }

    // this method closes the search modal by sending an Escape key
    private void closeSearchModalWithEsc(SearchModalPage modal) {
        modal.getInput().sendKeys(Keys.ESCAPE);
    }

    // this method verifies that the search modal is closed by checking if the modal container is not visible
    private void verifySearchModalIsClosed(SearchModalPage modal) {
        modal.waitUntilClosed();
    }
}
