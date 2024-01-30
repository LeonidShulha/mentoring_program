package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

public class LongIsNegativeTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Is Long negative: ";

    @Override
    Function<Long[], Boolean> getFunction() {
        return input -> calculator.isNegative(input[0]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.isNegative(Long.parseLong(input[0].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testLongIsPositive(long a, boolean expected) {
        positiveTest(new Long[]{a}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testLongSubNegative(Object a, Class<? extends Throwable> expectedException) {
        negativeTest(new Object[]{a}, expectedException, getNegativeFunction());
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {1L, false},
                {0L, false},
                {-1L, true},
        };
    }

    @DataProvider
    public Object[][] negativeData() {
        return new Object[][]{
                {"abc", NumberFormatException.class},
                {null, NullPointerException.class},
                {"3.3.3", NumberFormatException.class},
                {"", NumberFormatException.class},
                {"N/A", NumberFormatException.class},
        };
    }
}