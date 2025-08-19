package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.SearchModalPage;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FavoriteSearchesTest extends BaseTest {

    private final String TERM = "custom hook";

    @Test
    public void testAddToFavoritesThenRemove() {
        openHomePage();
        SearchModalPage search = openSearchModal();

        searchForTerm(search, TERM);
        String label = addLastHitToFavorites(search);

        verifyFavoriteWasAdded(search, label);

        removeFavorite(search, label);

        verifyFavoriteWasRemoved(search, label);
    }

    // this method opens the home page of the application
    private void openHomePage() {
        new HomePage(driver).open();
    }

    // this method opens the search modal
    private SearchModalPage openSearchModal() {
        SearchModalPage search = new SearchModalPage(driver).openSearch();
        search.waitUntilVisible();
        return search;
    }

    // this method searches for a term in the search modal and opens the first result
    private void searchForTerm(SearchModalPage search, String term) {
        search.searchAndOpenFirstResult(term);
        search.waitUntilResultPageLoaded();
    }

    // this method adds the last hit to favorites and returns its label
    private String addLastHitToFavorites(SearchModalPage search) {
        String label = search.getLastSelectedHitLabel();
        search.favoriteFromRecentByLabel(label);
        search.waitUntilFavoriteAdded(label);
        return label;
    }

    // this method verifies that the favorite was added correctly
    private void verifyFavoriteWasAdded(SearchModalPage search, String label) {
        List<String> favorites = search.getFavorites();
        assertEquals(1, favorites.size(), "Should have exactly 1 favorite search after adding");
        assertTrue(
                favorites.stream().anyMatch(t -> t.equals(label) || t.contains(TERM) || t.toLowerCase().contains("custom")),
                "Favorites should contain the searched term/label"
        );
    }

    // this method removes a favorite by its label
    private void removeFavorite(SearchModalPage search, String label) {
        search.removeFavoriteByLabel(label);
        search.waitUntilFavoriteRemoved(label);
    }

    // this method verifies that the favorite was removed correctly
    private void verifyFavoriteWasRemoved(SearchModalPage search, String label) {
        List<String> after = search.getFavorites();
        assertTrue(after.stream().noneMatch(t -> t.equals(label)), "Removed favorite still present");
        assertEquals(0, after.size(), "Favorites count should decrease by 1");
    }
}
