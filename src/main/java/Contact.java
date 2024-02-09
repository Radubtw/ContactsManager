import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Clasa Contact reprezinta o entitate care stocheaza informatii despre un contact al unei agende telefonice.
 * Aceasta contine drept atribute numele, numarul de telefon, adresa de mail si eventual notite, toate de tipul String.
 * @author Radu Badescu
 */
public class Contact {
	
	
	/** 
	 *Variabila de instanta pentru stocarea numelui persoanei de contact. 
	 */
	@Id
	private String nume;
	/**
     * Variabila de instanta pentru stocarea numarului de telefon al persoanei de contact.
	 */
	private String numarTelefon;
	/**
     * Variabila de instanta pentru stocarea adresei de email a persoanei de contact.
     */
	private String adresaEmail;
	/**
     * Variabila de instanta pentru stocarea notițelor suplimentare legate de persoana de contact.
     */
	private String notite;
	/**
	 * Constructorul fara parametri al clasei Contact.
	 * Inițializeaza obiectul de tip Contact cu valori implicite sau nule pentru toate atributele.
	 */
    private boolean favorit;
	public Contact()
	{
		this.nume = "necunoscut";
		this.numarTelefon = "0";
		this.adresaEmail = "necunoscut";
		this.notite = "";
	}
	/**
     * Constructorul cu parametri al clasei Contact.
     * @param nume Numele persoanei.
     * @param numarTelefon Numarul de telefon al persoanei.
     * @param adresaMail Adresa de email a persoanei.
     * @param notite Notițe suplimentare legate de persoana.
     */
	public Contact(String nume, String numarTelefon, String adresaEmail, String notite)
	{
		this.nume = nume;
		this.numarTelefon = numarTelefon;
		this.adresaEmail = adresaEmail;
		this.notite = notite;
	}
	/**
     * Metoda pentru obținerea numelui persoanei de contact.
     * @return Numele persoanei de contact.
     */
	public String getNume()
	{
		return this.nume;
	}
	/**
     * Metoda pentru obtinerea numarului de telefon al persoanei de contact.
     * @return numarul de telefon al persoanei de contact.
     */
	public String getNumarTelefon()
	{
		return this.numarTelefon;
	}
	/**
     * Metoda pentru obtinerea adresei de email a persoanei de contact.
     * @return adresa de email a persoanei de contact.
     */
	public String getAdresaEmail() {
		return adresaEmail;
	}
	/**
     * Metoda pentru obtinerea notit	elor legate de persoana de contact.
     * @return notitele legate de persoana contact.
     */
	public String getNotite() {
		return notite;
	}
	/**
	 * Seteazt numele persoanei de contact.
	 * @param nume Noul nume al persoanei de contact.
	 */
	public void setNume(String nume) {
		this.nume = nume;
	}
	/**
	 * Seteaza numarul de telefon al persoanei de contact.
	 * @param numarTelefon Noul numar de telefon al persoanei de contact.
	 */
	public void setNumarTelefon(String numarTelefon) {
		this.numarTelefon = numarTelefon;
	}
	/**
	 * Seteaza adresa de email a persoanei de contact.
	 * @param adresaMail Noua adresa de email a persoanei de contact.
	 */
	public void setAdresaEmail(String adresaEmail) {
		this.adresaEmail = adresaEmail;
	}
	/**
	 * Seteaza notitele suplimentare legate de persoana de contact.
	 * @param notite Noile notite ale persoanei de contact.
	 */
	public void setNotite(String notite) {
		this.notite = notite;
	}
	
	public boolean isFavorit() {
        return favorit;
    }

    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }
	/**
	 * Returneaza o reprezentare sub forma de sir de caractere a obiectului Contact.
	 * Aceasta reprezentare conține informații despre nume,
	 * numarul de telefon, adresa de email și notițele asociate persoanei de contact.
	 * @return Șirul de caractere care reprezintă obiectul Contact.
	 */
	public String toString()
	{
		return "Nume: " + this.nume + "\n" + "Telefon: " + this.numarTelefon + "\n" +
				"Adresa E-mail: " + this.adresaEmail + "\n" + "Notite: " + this.notite;
	}
}
