package unitTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.function.Function;

import static org.testng.Assert.assertEquals;

public class LongIsPositiveTest extends CalculatorTestBase {
    private final String GENERAL_TEST_TITLE = "Is Long positive: ";

    @Override
    Function<Long[], Boolean> getFunction() {
        return input -> calculator.isPositive(input[0]);
    }

    @Override
    Function<Object[], ?> getNegativeFunction() {
        return input -> calculator.isPositive(Long.parseLong(input[0].toString()));
    }

    @Test(testName = GENERAL_TEST_TITLE + POSITIVE, dataProvider = POSITIVE_DATA)
    public void testLongIsPositive(long a, boolean expected) {
        positiveTest(new Long[]{a}, expected, getFunction());
    }

    @Test(testName = GENERAL_TEST_TITLE + NEGATIVE, dataProvider = NEGATIVE_DATA)
    public void testLongSubNegative(Object a, Class<? extends Throwable> expectedException) {
        try {
            getNegativeFunction().apply(new Object[]{a});
        } catch (Exception e) {
            assertEquals(e.getClass(), expectedException,
                    UNEXPECTED_EXCEPTION_MESSAGE.formatted(expectedException, e.getClass()));
        }
    }

    @DataProvider
    public Object[][] positiveData() {
        return new Object[][]{
                {1L, true},
                {0L, false},
                {-1L, false},
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