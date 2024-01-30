package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class DoubleMultTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Multiplication of double: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.mult(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.mult(Double.parseDouble(input[0].toString()), Double.parseDouble(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoubleMult(double a, double b, double expected) {
        positiveTest(new Double[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testDoubleMultNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {1, 1, 1},
                {2.5, 2.5, 6.0},
                {0, 1, 0},
                {-1, -1, 1},
                {-1, 1, -1},
                {Double.MAX_VALUE, 0, 0},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"abc", 1, NumberFormatException.class},
                {1, null, NullPointerException.class},
                {"", 1, NumberFormatException.class},
                {"3.3.3", 1, NumberFormatException.class},
                {"NAN", 1, NumberFormatException.class},
        };
    }
}