package CalculatorTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class LongMultTestBase extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Multiplication of long: ";

    @Override
    Function<Long[], Long> getFunction() {
        return input -> calculator.mult(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.mult(Long.parseLong(input[0].toString()), Long.parseLong(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testLongMult(long a, long b, long expected) {
        positiveTest(new Long[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testDoubleMultNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {5L, 3L, 15L},
                {-10L, 5L, -50L},
                {0L, 5L, 0L},
                {-3L, -3L, 9L},
                {Long.MAX_VALUE, 1L, Long.MAX_VALUE}
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"0.1", 5L, NumberFormatException.class},
                {5L, "0.1", NumberFormatException.class},
                {"abc", 2L, NumberFormatException.class},
                {null, 2L, NullPointerException.class},
        };
    }
}