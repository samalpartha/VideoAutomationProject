package com.videoautomation.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomConsoleListener implements ITestListener {
    private long startTime;
    private long endTime;

    @Override
    public void onStart(ITestContext context) {
        startTime = System.currentTimeMillis();
        System.out.println("\n=== Video Test Suite Started: " + context.getName() + " ===");
        System.out.println("Total Tests to Run: " + context.getAllTestMethods().length);
    }

    @Override
    public void onFinish(ITestContext context) {
        endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;

        int total = context.getAllTestMethods().length;
        int passed = context.getPassedTests().size();
        int failed = context.getFailedTests().size();
        int skipped = context.getSkippedTests().size();

        System.out.println("\n=== Video Test Execution Summary ===");
        System.out.println("Total Duration: " + duration + " seconds");
        System.out.println("Total Tests: " + total);
        System.out.println("Passed: " + passed + " (" + (total > 0 ? (passed * 100 / total) : 0) + "%)");
        System.out.println("Failed: " + failed + " (" + (total > 0 ? (failed * 100 / total) : 0) + "%)");
        System.out.println("Skipped: " + skipped);
        System.out.println("====================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logTestResult(result, "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logTestResult(result, "FAILED");
        System.out.println("   Reason: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logTestResult(result, "SKIPPED");
    }

    private void logTestResult(ITestResult result, String status) {
        long duration = result.getEndMillis() - result.getStartMillis();
        System.out.println(String.format("[%s] %s - Duration: %dms",
                status,
                result.getMethod().getMethodName(),
                duration));
    }
}
