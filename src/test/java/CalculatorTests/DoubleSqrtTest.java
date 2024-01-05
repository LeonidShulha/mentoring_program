package CalculatorTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class DoubleSqrtTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Square root of double: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.sqrt(input[0]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.sqrt(Double.parseDouble(input[0].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoubleSqrt(double a, double expected) {
        positiveTest(new Double[]{a}, expected, getFunction());
    }

    @Test(dataProvider = NEGATIVE_DATA, testName = GENERAL_TEST_TITLE + NEGATIVE)
    public void testDoubleSqrtNegative(Object a, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {4.0, 2.0},
                {0.0, 0.0},
                {25.0, 5.0},
                {1.0, 1.0},
                {0.09, 0.3},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"abc", NumberFormatException.class},
                {null, NullPointerException.class},
                {"N/A", NumberFormatException.class},
                {"3.3.3", NumberFormatException.class},
                {"", NumberFormatException.class},
        };
    }
}