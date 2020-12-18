package main;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;

import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages("main")
public class TestSuite {

}
