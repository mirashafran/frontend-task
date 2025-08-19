package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReferenceHooksNavigationTest extends BaseTest {

    private static final String HOOKS_URL = "https://react.dev/reference/react/hooks";

    @Test
    public void testNavigateToHooksFromReference() {
        openHomePage()
                .goToReference()
                .goToHooks();

        verifyOnHooksPage();
    }

    // This method opens the home page
    private HomePage openHomePage() {
        return new HomePage(driver).open();
    }

    // This method verifies that the user is on the Hooks reference page
    private void verifyOnHooksPage() {
        String current = driver.getCurrentUrl();
        assertTrue(
                current.equals(HOOKS_URL),
                "Expected to be on Hooks reference page, but was: " + current
        );
    }
}

