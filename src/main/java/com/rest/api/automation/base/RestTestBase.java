package com.rest.api.automation.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.github.javafaker.Faker;
import com.rest.api.automation.utils.*;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class RestTestBase {
    protected static ExtentReports extent;
    protected static ExtentTest extentLog;
    protected static String testName = "";
    protected static StringWriter requestWriter;
    protected static PrintStream requestCapture;
    protected String methodName;
    protected CsvUtils csv = new CsvUtils();
    protected CommonMethods cm = new CommonMethods();
    protected HeadersUtils headersUtils = new HeadersUtils();
    protected static final Faker FAKER = new Faker();

    @BeforeSuite
    public void setUp() {
        extent = ExtentManager.getExtentReport();
    }

    public String customReport(String message) {
        String format = "<b class='exception' style='display:block; cursor:pointer; user-select:none' onclick='($(\".exception\").click(function(){ $(this).next().toggle()}))'>"
                + message + "</b>" + "<pre style='display:none'></pre>";
        return format;
    }

    public String customReport(String message, String response) {
        String format = "<b class='exception' style='display:block; cursor:pointer; user-select:none' onclick='($(\".exception\").click(function(){ $(this).next().toggle()}))'>"
                + message + "</b>" + "<pre style='display:none'>" + response + "</pre>";
        return format;
    }

    public void checkSuccessResponse(Response res, int statusCode) {
        Assert.assertEquals(res.getStatusCode(), statusCode);
    }

    public void checkErrorResponse(Response res, int statusCode) {
        Assert.assertEquals(res.getStatusCode(), statusCode);
    }

    @BeforeMethod
    public void getMethodName(Method m) {
        methodName = m.getName();
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter), true);
        TestConstants.REQUEST_CAPTURE = requestCapture;
        String packageName = this.getClass().getPackage().getName();
        testName = this.getClass().getSimpleName() + " : " + m.getName();
        extentLog = extent.createTest(testName, m.getAnnotation(Test.class).description())
                .assignCategory(packageName.substring(packageName.lastIndexOf(".") + 1));
    }

    @AfterMethod
    public void reportsUpdate(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            extentLog.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            extentLog.log(Status.FAIL,
                    MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentLog.log(Status.SKIP,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
            extentLog.log(Status.SKIP,
                    MarkupHelper.createLabel(result.getThrowable() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentLog.log(Status.PASS,
                    MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
        }
    }

    @AfterSuite
    public void afterSuite(ITestContext context) {
        System.out.println("In after suite************************************************");
        extent.flush();
    }
}