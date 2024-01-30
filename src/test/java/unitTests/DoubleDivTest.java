package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class DoubleDivTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Division of double: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.div(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.div(Double.parseDouble(input[0].toString()), Double.parseDouble(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoubleDiv(double a, double b, double expected) {
        positiveTest(new Double[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testDoubleDivNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    private Object[][] positiveData() {
        return new Object[][]{
                {10.0, 2.0, 5.0},
                {-10.0, 2.0, -5.0},
                {10.0, -2.0, -5.0},
                {-10.0, -2.0, 5.0},
                {0, 1, 0},
                {1, 0, Double.POSITIVE_INFINITY},
                {-1, 0, Double.NEGATIVE_INFINITY},
                {0, 0, Double.NaN}
        };
    }

    @DataProvider
    private Object[][] negativeData() {
        return new Object[][]{
                {"abc", 1, NumberFormatException.class},
                {1, "abc", NumberFormatException.class},
                {null, 1, NullPointerException.class},
                {"N/A", 1, NumberFormatException.class},
                {"3.3.3", 1, NumberFormatException.class},
                {"", 1, NumberFormatException.class}
        };
    }
}