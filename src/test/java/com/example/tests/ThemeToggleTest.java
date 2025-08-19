package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThemeToggleTest extends BaseTest {

    @Test
    public void testToggleThereAndBack() {
        HomePage home = openHomePage();

        boolean startDark = home.isDark();

        toggleTheme(home, !startDark);
        verifyThemeIs(home, !startDark, "After first toggle, theme must flip.");

        toggleTheme(home, startDark);
        verifyThemeIs(home, startDark, "After second toggle, theme must return to initial.");
    }

    // This method opens the home page
    private HomePage openHomePage() {
        return new HomePage(driver).open();
    }

    // This method toggles the theme on the home page and waits for it to change
    private void toggleTheme(HomePage home, boolean expectedDark) {
        home.setDark(expectedDark);
        home.waitUntilDarkIs(expectedDark);
    }

    // This method verifies that the theme is as expected
    private void verifyThemeIs(HomePage home, boolean expectedDark, String message) {
        boolean actualDark = home.isDark();
        assertEquals(expectedDark, actualDark, message);
    }
}

