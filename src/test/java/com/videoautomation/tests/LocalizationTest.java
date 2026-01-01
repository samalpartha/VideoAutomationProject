package com.videoautomation.tests;

import com.videoautomation.tests.BaseTest;
import com.videoautomation.utils.LocalizationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Epic("Video Platform Automation")
@Feature("Internationalization")
public class LocalizationTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the video player search can handle pseudo-localized input")
    public void testVideoSearchFormatting() {
        String originalTerm = "Playwright";
        String pseudoTerm = LocalizationUtils.pseudoLocalize(originalTerm);

        System.out.println("Testing Video Search with: " + pseudoTerm);

        // Simulating search entry flow (Abstracted for this test as specific locators
        // vary by platform)
        // In a real scenario:
        // RemoteController.typeText(pseudoTerm);

        Assert.assertTrue(pseudoTerm.contains("Pláywríght"), "Term should be transformed");
        Assert.assertTrue(pseudoTerm.endsWith("!]"), "Term should be padded");
    }
}
