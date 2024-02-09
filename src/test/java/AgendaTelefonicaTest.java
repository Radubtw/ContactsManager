import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/*import p1.AgendaTelefonica;
import p1.Contact;*/

public class AgendaTelefonicaTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
	 @AfterEach
	    public void tearDown() {
	        System.setOut(System.out);
	    }
}
