import java.util.Comparator;
/**
 * Clasa comparator implementeaza interfata Comparator
 */
public class ComparatorContact implements Comparator<Contact> {
	/**
	 * Metoda compare compara doua obiecte de tip contact pentru a le sorta si returneaza rezultatul compararii.
	 */
    @Override
    public int compare(Contact contact1, Contact contact2) {
        String nume1 = contact1.getNume();
        String nume2 = contact2.getNume();

        return nume1.compareToIgnoreCase(nume2);
    }
}