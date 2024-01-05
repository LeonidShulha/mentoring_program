package CalculatorTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;


public class DoubleSinTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Sine of double: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.sin(input[0]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.sin(Double.parseDouble(input[0].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoubleSin(double a, double expected) {
        positiveTest(new Double[]{a}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testDoubleSinNegative(Object a, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {0.0, 0.0},
                {Math.PI / 2, 1.0},
                {3 * Math.PI / 2, -1.0}
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