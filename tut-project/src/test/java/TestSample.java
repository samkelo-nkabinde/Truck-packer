import static org.junit.Assert.*;
import org.junit.Test;

public class TestSample {
    @Test
    public void testSomethingEven() {
        assertTrue(Sample.isEven(10));
    }
    
    @Test
    public void testSomethingOdd() {
        assertFalse(Sample.isEven(9));
    }
}