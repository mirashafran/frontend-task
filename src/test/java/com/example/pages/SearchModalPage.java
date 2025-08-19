package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

// This class represents the search modal page in the application.
public class SearchModalPage extends BasePage {

    private static final By BTN_OPEN_SEARCH =
            By.xpath("//button[starts-with(normalize-space(.), 'Search')]");
    private static final By MODAL =
            By.cssSelector("div.DocSearch-Modal, .DocSearch-Container");
    private static final By INPUT =
            By.cssSelector("input[aria-labelledby='docsearch-label'], input.DocSearch-Input");
    private static final By DROPDOWN =
            By.cssSelector(".DocSearch-Dropdown, .DocSearch-Dropdown-Container");
    private static final By FIRST_HIT =
            By.cssSelector(".DocSearch-Hit a, .DocSearch-Hit[role='option']");
    private static final By SECTION_RECENT_TITLE =
            By.xpath("//div[contains(@class,'DocSearch-Hit-source') and normalize-space()='Recent']");
    private static final By SECTION_FAVORITES_TITLE =
            By.xpath("//div[contains(@class,'DocSearch-Hit-source') and contains(normalize-space(),'Favor')]");
    private static final By SECTION_FAVORITES =
            By.xpath("//section[contains(@class,'DocSearch-Hits')][.//div[contains(@class,'DocSearch-Hit-source') and contains(normalize-space(),'Favor')]]");

    private String lastSelectedHitLabel;

    // Constructor that initializes the SearchModalPage with the WebDriver
    public SearchModalPage(WebDriver driver) {
        super(driver);
    }

    // This method returns the locator that indicates the Search modal page is loaded
    @Override
    protected By pageLoadedLocator() {
        return BTN_OPEN_SEARCH;
    }

    // Opens the search modal if it is not already open
    public SearchModalPage openSearch() {
        if (driver.findElements(MODAL).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(BTN_OPEN_SEARCH)).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(INPUT));
        return this;
    }

    // Searches for a query and opens the first result in the search modal
    public void searchAndOpenFirstResult(String query) {
        openSearch();
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(INPUT));
        input.clear();
        input.sendKeys(query);

        wait.until(ExpectedConditions.visibilityOfElementLocated(DROPDOWN));

        WebElement firstHit = wait.until(ExpectedConditions.elementToBeClickable(FIRST_HIT));
        String label = firstHit.getText().trim();
        if (label.isEmpty()) {
            throw new RuntimeException("First hit is empty, cannot proceed with search.");
        }
        this.lastSelectedHitLabel = label;

        firstHit.click();
        openSearch();
    }

    // Searches for a query and returns the first result's label
    private By recentRowByLabel(String label) {
        return By.xpath(
                "//section[.//div[@class='DocSearch-Hit-source' and normalize-space()='Recent']]"
                        + "//li[contains(@class,'DocSearch-Hit')]//a[normalize-space()='" + label + "']"
        );
    }


    // Searches for a query and returns the first result's label
    private By favoriteRowByLabel(String label) {
        return By.xpath(
                "//section[.//div[@class='DocSearch-Hit-source' and contains(normalize-space(),'Favor')]]" +
                        "//li[contains(@class,'DocSearch-Hit')]//a[normalize-space()='" + label + "']"
        );
    }

    // Returns the locator for the save button within a row
    private By saveButtonWithinRow() {
        return By.cssSelector("button.DocSearch-Hit-action-button[title*='Save']");
    }


    // Returns the locator for the remove button within a row
    private By removeButtonWithinRow() {
        return By.cssSelector("button.DocSearch-Hit-action-button[title*='Remove']");
    }


    // adds to favorites from the recent section by label
    public void favoriteFromRecentByLabel(String label) {
        openSearch();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SECTION_RECENT_TITLE));
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(recentRowByLabel(label)));
        WebElement star = row.findElement(saveButtonWithinRow());
        wait.until(ExpectedConditions.elementToBeClickable(star)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(favoriteRowByLabel(label)));
    }

    // get the list of favorite searches
    public List<String> getFavorites() {
        openSearch();
        if (driver.findElements(SECTION_FAVORITES_TITLE).isEmpty()) {
            return List.of();
        }

        WebElement section = driver.findElement(SECTION_FAVORITES);

        // Get all titles from the favorite section
        List<WebElement> titles = section.findElements(
                By.cssSelector("li.DocSearch-Hit .DocSearch-Hit-title")
        );

        return titles.stream()
                .map(e -> e.getText().trim())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    // Get the label of the last selected hit
    public String getLastSelectedHitLabel() {
        return lastSelectedHitLabel;
    }

    // Removes a favorite search by its label
    public void removeFavoriteByLabel(String label) {
        openSearch();
        WebElement li = wait.until(ExpectedConditions.visibilityOfElementLocated(favoriteRowByLabel(label)));
        WebElement removeBtn = li.findElement(removeButtonWithinRow());
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(favoriteRowByLabel(label)));
    }

    // Waits until the search modal is visible
    public void waitUntilVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL));
    }

    // Waits until the search modal is closed
    public void waitUntilResultPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("main")));
    }

    // Waits until the search modal is closed
    public void waitUntilFavoriteAdded(String label) {
        wait.until(d -> getFavorites().contains(label));
    }

    // Waits until the favorite search is removed
    public void waitUntilFavoriteRemoved(String label) {
        wait.until(d -> !getFavorites().contains(label));
    }

}
