package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class DoublePowerTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Power of double positive test: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.pow(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.pow(Double.parseDouble(input[0].toString()), Double.parseDouble(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoublePow(double a, double b, double expected) {
        positiveTest(new Double[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = NEGATIVE_DATA)
    public void testDoublePowNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {2.0, 2.0, 4.0},
                {2.0, 3.0, 8.0},
                {2.0, -3.0, 0.125},
                {-2.0, 3.0, -8.0},
                {0.0, 3.0, 0.0},
                {2.0, 0.0, 1.0},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"abc", 1, NumberFormatException.class},
                {2, "abc", NumberFormatException.class},
                {null, 2, NullPointerException.class},
                {"N/A", 2, NumberFormatException.class},
                {"3.3.3", 2, NumberFormatException.class},
                {"", 2, NumberFormatException.class},
        };
    }
}