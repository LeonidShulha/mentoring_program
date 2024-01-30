package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class DoubleSubTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Subtraction of double: ";

    @Override
    Function<Double[], Double> getFunction() {
        return input -> calculator.sub(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.sub(Double.parseDouble(input[0].toString()), Double.parseDouble(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testDoubleSub(double a, double b, double expected) {
        positiveTest(new Double[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testLongSubNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {10.5, 5.5, 5.0},
                {0.0, 0.0, 0.0},
                {-1.2, -3.4, 2.2},
                {Double.MAX_VALUE, 0.0, Double.MAX_VALUE},
                {Double.MIN_VALUE, 0.0, Double.MIN_VALUE},
                {1.0, 1.0, 0.0},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"0.1", "abc", NumberFormatException.class},
                {null, "1.0", NullPointerException.class},
                {"", "1.0", NumberFormatException.class},
                {"3..5", "1.0", NumberFormatException.class},
                {"NAN", "1.0", NumberFormatException.class},
        };
    }
}