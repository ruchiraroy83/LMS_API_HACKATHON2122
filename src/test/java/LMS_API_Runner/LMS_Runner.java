package LMS_API_Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/users.feature",
		"src/test/resources/skillMaster.feature",
		"src/test/resources/userSkills.feature",
		"src/test/resources/userSkillsMap.feature"}, 
glue = { "step_definition" }, 
plugin = { "pretty","io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
		"html:target/LMS-reports/LMS_htmlreports.html", 
		"json:target/LMS-reports/LMS_JSONReports.json",
		"junit:target/LMS-reports/LMS_XMLReports.xml",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"timeline:test-output-thread/"},
monochrome = true)

public class LMS_Runner {

}
