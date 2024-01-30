package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class LongDivTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Division of long: ";

    @Override
    Function<Long[], Long> getFunction() {
        return input -> calculator.div(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.div(Long.parseLong(input[0].toString()), Long.parseLong(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testLongDiv(long a, long b, long expected) {
        positiveTest(new Long[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testLongSubNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {10L, 2L, 5L},
                {-10L, 2L, -5L},
                {10L, -2L, -5L},
                {-10L, -2L, 5L},
                {0L, 1L, 0L},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {1L, 0L, NumberFormatException.class},
                {1L, "0.1", NumberFormatException.class},
                {1L, "abc", NumberFormatException.class},
                {1L, null, NullPointerException.class},
        };
    }
}