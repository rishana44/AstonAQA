package test;

import com.myaqa.Factorial;
import org.testng.Assert;
import org.testng.annotations.Test;
public class FactorialCalculatorTestNGTest {
    @Test
    public void testFactorialOfZero() {
        Assert.assertEquals(FactorialCalculator.calculateFactorial(0), 1);
    }
]
    @Test
    public void testFactorialOfOne() {
        Assert.assertEquals(FactorialCalculator.calculateFactorial(1), 1);
    }

    @Test
    public void testFactorialOfPositiveNumber() {
        Assert.assertEquals(FactorialCalculator.calculateFactorial(5), 120);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialOfNegativeNumber() {
        FactorialCalculator.calculateFactorial(-1);
    }
}