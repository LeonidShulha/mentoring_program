package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class LongSubTestBase extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Subtraction of long: ";

    @Override
    Function<Long[], Long> getFunction() {
        return input -> calculator.sub(input[0], input[1]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.sub(Long.parseLong(input[0].toString()), Long.parseLong(input[1].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testLongSub(long a, long b, long expected) {
        positiveTest(new Long[]{a, b}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testLongSubNegative(Object a, Object b, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a, b}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {5L, 3L, 2L},
                {-10L, 5L, -15L},
                {0L, 0L, 0L},
                {-35L, -5L, -30L},
                {Long.MAX_VALUE, 1L, Long.MAX_VALUE - 1}
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"0.1", 5, NumberFormatException.class},
                {5, "0.1", NumberFormatException.class},
                {"abc", 2, NumberFormatException.class},
                {null, 2, NullPointerException.class},
        };
    }
}