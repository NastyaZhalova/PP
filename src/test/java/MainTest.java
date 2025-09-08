import org.example.LogCalculator;
import org.example.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class MainTest {

//    private double roundToThree(double value) {
//        return new BigDecimal(value)
//                .setScale(3, RoundingMode.HALF_UP)
//                .doubleValue();
//    }

    @Test
    public void testLogAccuracy() {
        int k = 4;
        double x = 0.5;
        double expected = Main.roundToThree(Math.log(1 - x));
        double actual = Main.roundToThree(LogCalculator.calculate(k, x));

        assertEquals(expected, actual,
                "Ожидаемое значение должно совпадать с округлённым результатом Log()");
    }

    @Test
    public void testLogNearZero() {
        int k = 6;
        double x = 0.0001;
        double expected = Main.roundToThree(Math.log(1 - x));
        double actual = Main.roundToThree(LogCalculator.calculate(k, x));

        assertEquals(expected, actual,
                "Ожидаемое значение должно совпадать с округлённым результатом Log()");
    }

    @Test
    public void testLogAtEdge() {
        int k = 12;
        double x = 0.999;
        double expected = Main.roundToThree(Math.log(1 - x));
        double actual = Main.roundToThree(LogCalculator.calculate(k, x));

        assertEquals(expected, actual,
                "Ожидаемое значение должно совпадать с округлённым результатом Log()");
    }

    @Test
    public void testZeroInput() {
        int k = 3;
        double x = 0.0;
        double expected = Main.roundToThree(Math.log(1 - x));
        double actual = Main.roundToThree(LogCalculator.calculate(k, x));

        assertEquals(expected, actual,
                "Ожидаемое значение должно совпадать с округлённым результатом Log()");
    }
}
