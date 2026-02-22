package com.wbandara.enterprise.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestNG Cucumber Test Runner.
 * Configures Cucumber options and enables parallel execution.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.wbandara.enterprise.stepdefinitions",
                "com.wbandara.enterprise.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "rerun:target/failed_scenarios.txt"
        },
        monochrome = true,
        dryRun = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Override scenarios DataProvider to enable parallel execution.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

