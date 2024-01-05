package CalculatorTests;

import com.epam.tat.module4.Calculator;
import org.testng.annotations.BeforeMethod;

import java.util.function.Function;

import static org.testng.Assert.assertEquals;


public abstract class CalculatorTestBase {
    protected final static String UNEXPECTED_EXCEPTION_MESSAGE = "Expected exception of type %s but was %s.";
    protected final static String UNEXPECTED_RESULT_MESSAGE = "The function returned an unexpected result for the input: %s";
    protected final static String POSITIVE = "Positive";
    protected final static String NEGATIVE = "Negative";
    protected final static String POSITIVE_DATA = "positiveData";
    protected final static String NEGATIVE_DATA = "negativeData";
    protected Calculator calculator;

    protected <T, R> void positiveTest(T input, R expected, Function<T, R> function) {
        R result = function.apply(input);
        assertEquals(result, expected, String.format(UNEXPECTED_RESULT_MESSAGE, input));
    }

    void negativeTest(Object[] arguments, Class<? extends Throwable> expectedException, Function<Object[], ?> function) {
        try {
            function.apply(arguments);
        } catch (Exception e) {
            assertEquals(e.getClass(), expectedException,
                    UNEXPECTED_EXCEPTION_MESSAGE.formatted(expectedException, e.getClass()));
        }
    }

    @BeforeMethod
    public void setUp() {
        calculator = new Calculator();
    }

    abstract Function getFunction();

    abstract Function<Object[], ?> getNegativeFunction();
}
