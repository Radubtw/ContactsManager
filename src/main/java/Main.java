import javax.swing.SwingUtilities;
/**
 *Clasa Main contine metoda main care initializeaza interfata.
 * @author Radu Badescu
 */
public class Main {
	/**
	 *Metoda main initializeaza interfata grafica a utilizatorului. 
	 *@param args
	 */
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendaGUI());
    }
}