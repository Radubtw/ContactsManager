import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*import p1.Contact;*/
class ContactTest {

	@Test
	public void testContactConstructorCuParametri() {
		String nume = "Radu Badescu";
        String numarTelefon = "0753678291";
        String adresaEmail = "radu.badescu03@e-uvt.ro";
        String notite = "test test test";

        // Act
        Contact contact = new Contact(nume, numarTelefon, adresaEmail, notite);

        // Assert
        assertEquals(nume, contact.getNume());
        assertEquals(numarTelefon, contact.getNumarTelefon());
        assertEquals(adresaEmail, contact.getAdresaEmail());
        assertEquals(notite, contact.getNotite());
	}
	
	@Test
	public void testContactConstructorFaraParametri() {
		Contact contact = new Contact();
		assertEquals("necunoscut", contact.getNume());
        assertEquals("0", contact.getNumarTelefon());
        assertEquals("necunoscut", contact.getAdresaEmail());
        assertEquals("", contact.getNotite());
	}
	
	@Test
	public void testContactToString() {
		String nume = "Radu Badescu";
        String numarTelefon = "0753678291";
        String adresaEmail = "radu.badescu03@e-uvt.ro";
        String notite = "test test test";
        Contact contact = new Contact(nume, numarTelefon, adresaEmail, notite);
        
        String output = contact.toString();
        String expectedOutput = "Nume: Radu Badescu\nTelefon: 0753678291\nAdresa E-mail: radu.badescu03@e-uvt.ro\nNotite: test test test";
        
        assertEquals(expectedOutput, output);
        
	}

}
