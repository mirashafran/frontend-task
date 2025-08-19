package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.ReferencePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReferencePageLayoutTest extends BaseTest {

    @Test
    public void testHeaderFooterAndFooterLink() {
        ReferencePage reference = navigateToReferencePage();

        verifyFooterIsDisplayed(reference);
        verifyFooterLinkWorks(reference, "Blog");
    }

    // this method navigates to the Reference page from the Home page
    private ReferencePage navigateToReferencePage() {
        return new HomePage(driver).open().goToReference();
    }

    // this method verifies that the footer is displayed on the Reference page
    private void verifyFooterIsDisplayed(ReferencePage reference) {
        assertTrue(reference.isFooterDisplayed(), "Footer should be visible on Reference page");
    }

    // this method verifies that the footer link works correctly
    private void verifyFooterLinkWorks(ReferencePage reference, String linkText) {
        assertTrue(
                reference.isFooterLinkWorking(linkText),
                "Footer link '" + linkText + "' should navigate to the correct URL"
        );
    }
}
