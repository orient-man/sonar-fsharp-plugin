/*
 * Sonar F# Plugin :: Core
 * Copyright (C) 2015 Jorge Costa and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.fsharp;

import com.google.common.collect.ImmutableList;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.dotnet.tests.UnitTestConfiguration;
import org.sonar.plugins.dotnet.tests.UnitTestResultsAggregator;
import org.sonar.plugins.dotnet.tests.UnitTestResultsImportSensor;

import java.util.List;

public class FSharpUnitTestResultsProvider {

  private static final String CATEGORY = "F#";
  private static final String SUBCATEGORY = "Unit Tests";

  private static final String VISUAL_STUDIO_TEST_RESULTS_PROPERTY_KEY = "sonar.fs.vstest.reportsPaths";
  private static final String NUNIT_TEST_RESULTS_PROPERTY_KEY = "sonar.fs.nunit.reportsPaths";
  private static final String XUNIT_TEST_RESULTS_PROPERTY_KEY = "sonar.fs.xunit.reportsPaths";

  private static final UnitTestConfiguration UNIT_TEST_CONF =
          new UnitTestConfiguration(VISUAL_STUDIO_TEST_RESULTS_PROPERTY_KEY, NUNIT_TEST_RESULTS_PROPERTY_KEY, XUNIT_TEST_RESULTS_PROPERTY_KEY);

  private FSharpUnitTestResultsProvider() {
  }

  public static List extensions() {
    return ImmutableList.of(
      FSharpUnitTestResultsAggregator.class,
      FSharpUnitTestResultsImportSensor.class,
      PropertyDefinition.builder(VISUAL_STUDIO_TEST_RESULTS_PROPERTY_KEY)
        .name("Visual Studio Test Reports Paths")
        .description("Example: \"report.trx\", \"report1.trx,report2.trx\" or \"C:/report.trx\"")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onlyOnQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build(),
      PropertyDefinition.builder(NUNIT_TEST_RESULTS_PROPERTY_KEY)
        .name("NUnit Test Reports Paths")
        .description("Example: \"TestResult.xml\", \"TestResult1.xml,TestResult2.xml\" or \"C:/TestResult.xml\"")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onlyOnQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build(),
      PropertyDefinition.builder(XUNIT_TEST_RESULTS_PROPERTY_KEY)
        .name("XUnit Test Reports Paths")
        .description("Example: \"TestResult.xml\", \"TestResult1.xml,TestResult2.xml\" or \"C:/TestResult.xml\"")
        .category(CATEGORY)
        .subCategory(SUBCATEGORY)
        .onlyOnQualifiers(Qualifiers.PROJECT, Qualifiers.MODULE)
        .build())    
    ;
  }

  public static class FSharpUnitTestResultsAggregator extends UnitTestResultsAggregator {

    public FSharpUnitTestResultsAggregator(Settings settings) {
      super(UNIT_TEST_CONF, settings);
    }

  }

  public static class FSharpUnitTestResultsImportSensor extends UnitTestResultsImportSensor {

    public FSharpUnitTestResultsImportSensor(FSharpUnitTestResultsAggregator unitTestResultsAggregator) {
      super(unitTestResultsAggregator);
    }

  }

}
