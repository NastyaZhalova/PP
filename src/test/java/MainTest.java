
import org.example.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testLogAccuracy() {
        int k = 4;
        double x = 0.5;
        double expected = Math.log(1 - x);
        double actual = Main.Log(k, x);

        // Разница должна быть меньше ε
        double epsilon = Math.pow(10, -k);
        assertTrue(Math.abs(expected - actual) < epsilon,
                "Ожидаемое значение должно быть близко к Math.log(1 - x)");
    }

    @Test
    public void testLogNearZero() {
        int k = 6;
        double x = 0.0001;
        double expected = Math.log(1 - x);
        double actual = Main.Log(k, x);

        assertTrue(Math.abs(expected - actual) < Math.pow(10, -k));
    }

    @Test
    public void testLogAtEdge() {
        int k = 12;
        double x = 0.999;
        double expected = Math.log(1 - x);
        double actual = Main.Log(k, x);

        assertTrue(Math.abs(expected - actual) < Math.pow(10, -k));
    }

    @Test
    public void testZeroInput() {
        int k = 3;
        double x = 0.0;
        double expected = Math.log(1 - x); // log(1) = 0
        double actual = Main.Log(k, x);

        assertEquals(expected, actual, Math.pow(10, -k));
    }
}
