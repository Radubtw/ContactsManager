import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Clasa care se ocupa cu generarea si gestionarea interfetei grafice
 */
public class AgendaGUI extends JFrame {

    private DefaultListModel<Contact> agendaTelefonicaModel;
    private DefaultListModel<Contact> contacteFavoriteModel;
    private JList<Contact> agendaTelefonica;
    private JList<Contact> contacteFavorite;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton addToFavoritesButton;
    private JButton removeFromFavoritesButton;
    private JTextField searchField;
    /**
     * Constructorul fara parametri al clasei care se ocupa de crearea interfetei.
     */
    public AgendaGUI() {
		super("Agenda Telefonica");
	    agendaTelefonicaModel = new DefaultListModel<>();
	    contacteFavoriteModel = new DefaultListModel<>();
	    agendaTelefonica = new JList<>(agendaTelefonicaModel);
	    agendaTelefonica.setFont(new Font("Arial", Font.PLAIN, 15));
	    citireDinFisier("src/main/resources/agendaTelefonica.txt");
	    sortAgendaTelefonica();

        addButton = new JButton("Adaugare");
        deleteButton = new JButton("Stergere");
        updateButton = new JButton("Actualizare");
        addToFavoritesButton = new JButton("Adauga la favorite");
        removeFromFavoritesButton = new JButton("Sterge din favorite");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Cautare");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaContact();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stergeContact();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizeazaContact();
            }
        });

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaLaFavorite();
            }
        });

        removeFromFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stergeDinFavorite();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cautaContacteDupaNume(searchField.getText());
            }
        });

        setLayout(new BorderLayout());

        contacteFavoriteModel = new DefaultListModel<>();
        contacteFavorite = new JList<>(contacteFavoriteModel);
        contacteFavorite.setFont(new Font("Arial", Font.PLAIN, 15));
	    citireDinFisier("src/main/resources/contacteFavorite.txt");

        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(new JLabel("Contacte"), BorderLayout.NORTH);
        JScrollPane agendaScrollPane = new JScrollPane(agendaTelefonica);
        agendaScrollPane.setPreferredSize(new Dimension(700, 400)); 
        centralPanel.add(agendaScrollPane, BorderLayout.CENTER);
        add(centralPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        JPanel searchPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(addToFavoritesButton);
        buttonPanel.add(removeFromFavoritesButton);

        searchPanel.add(searchButton);
        searchPanel.add(new JLabel("Nume:"));
        searchPanel.add(searchField);

        add(buttonPanel, BorderLayout.SOUTH);
        add(searchPanel, BorderLayout.NORTH);

        JPanel favoritePanel = new JPanel(new BorderLayout());
        favoritePanel.add(new JLabel("Contactele favorite"), BorderLayout.NORTH);
        JScrollPane favoriteScrollPane = new JScrollPane(contacteFavorite);
        favoriteScrollPane.setPreferredSize(new Dimension(700, 400));
        favoritePanel.add(favoriteScrollPane, BorderLayout.CENTER);
        add(favoritePanel, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Metoda adaugaContact adauga contacte in lista cu input de la utilizator print intermediul unor InputDialog-uri.
     */
    private void adaugaContact() {
        String nume = JOptionPane.showInputDialog(this, "Introduceti numele:");
        String telefon = JOptionPane.showInputDialog(this, "Introduceti numarul de telefon:");
        String adresaEmail = JOptionPane.showInputDialog(this, "Introduceti adresa de email:");
        String notite = JOptionPane.showInputDialog(this, "Introduceti notitele:");
        if (contactDejaExistent(nume)) {
            JOptionPane.showMessageDialog(this, "NUME DEJA EXISTENT! Contactul nu a fost adăugat.", "Eroare", JOptionPane.ERROR_MESSAGE);
        } else {
            Contact contact = new Contact(nume + "  ", telefon + "  ", adresaEmail + "  ", notite);
            try {
                FileWriter writer = new FileWriter("src/main/resources/agendaTelefonica.txt", true);
                writer.write(nume + "\n");
                writer.write(telefon + "\n");
                writer.write(adresaEmail + "\n");
                writer.write(notite + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            agendaTelefonicaModel.addElement(contact);
        }
        sortAgendaTelefonica();
    }	
    /**
     * Metoda stergeContact elimina din lista de contacte contactul selectat si apoi apeleaza metoda care urmeaza sa il stearga din fisier.
     */
    private void stergeContact() {
        Contact contact = agendaTelefonica.getSelectedValue();

        if (contact != null) {
            stergereDinFisier(contact.getNume());
            agendaTelefonicaModel.removeElement(contact);
        } else {
            JOptionPane.showMessageDialog(this, "Selectati un contact pentru a-l sterge.");
        }
    }
    /**
     * Metoda stergereDinFisier scrie intr-un fisier temporar continutul fisierului in afara de datele aferente String ului primit ca parametru,
     * iar la final sterge fisierul original si il redenumeste pe cel temporar. 
     * @param deSters Numele contactului care trebuie sters.
     */
    public void stergereDinFisier(String deSters) {
        String pathAgenda = "src/main/resources/agendaTelefonica.txt";

        try {
            File inputFile = new File(pathAgenda);
            File tempFile = new File("agendaTemp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int linesToRemove = 4;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(deSters.trim())) {
                    for (int i = 0; i < linesToRemove - 1; i++) {
                        reader.readLine();
                    }
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();

            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Error renaming the temporary file.");
                }
            } else {
                System.out.println("Error deleting the original file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metoda actualizeazaContact primeste input de la utilizator prin intermediul InputDialog-urilor, actualizeaza datele contactului in lista si
     * in fisier.
     */
    private void actualizeazaContact() {
        int selectedIndex = agendaTelefonica.getSelectedIndex();

        if (selectedIndex != -1) {
            Contact contact = agendaTelefonica.getSelectedValue();

            String nume = JOptionPane.showInputDialog(this, "Nume nou:", contact.getNume());
            String telefon = JOptionPane.showInputDialog(this, "Numar de telefon nou:", contact.getNumarTelefon());
            String adresaEmail = JOptionPane.showInputDialog(this, "Adresa de email noua:", contact.getAdresaEmail());
            String notite = JOptionPane.showInputDialog(this, "Notite noi:", contact.getNotite());
            stergereDinFisier(contact.getNume());

            contact.setNume(nume + "  ");
            contact.setNumarTelefon(telefon + "  ");
            contact.setAdresaEmail(adresaEmail + "  ");
            contact.setNotite(notite);

            agendaTelefonicaModel.setElementAt(contact, selectedIndex);
            sortAgendaTelefonica();
            try {
                FileWriter writer = new FileWriter("src/main/resources/agendaTelefonica.txt", true);
                writer.write(nume + "\n");
                writer.write(telefon + "\n");
                writer.write(adresaEmail + "\n");
                writer.write(notite + "\n");
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectati un contact pentru a-l actualiza.");
        }
    }
    /**
     * Metoda adaugaLaFavorite adauga contacte in fisierul de contacte favorite
     */
    private void adaugaLaFavorite() {
        Contact contact = agendaTelefonica.getSelectedValue();

        if (contact != null) {
            if (!contact.isFavorit()) {
                contact.setFavorit(true);
                contacteFavoriteModel.addElement(contact);
                try {
                    FileWriter writer = new FileWriter("src/main/resources/contacteFavorite.txt", true);
                    if (!contactDejaInFisier(contact, new File("src/main/resources/contacteFavorite.txt"))) {
                        writer.write(contact.getNume() + "\n");
                        writer.write(contact.getNumarTelefon() + "\n");
                        writer.write(contact.getAdresaEmail() + "\n");
                        writer.write(contact.getNotite() + "\n");
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Contactul este deja în lista de favorite.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selectati un contact pentru a-l adauga la favorite.");
        }
    }
    /**
     * Metoda stergeDinFavorite sterge contactele din fisierul de favorite
     */
    private void stergeDinFavorite() {
        Contact contact = contacteFavorite.getSelectedValue();

        if (contact != null) {
            contact.setFavorit(false);
            contacteFavoriteModel.removeElement(contact);
            stergereDinFisier("src/main/resources/contacteFavorite.txt", contact.getNume());
        } else {
            JOptionPane.showMessageDialog(this, "Selectati un contact pentru a-l sterge din favorite.");
        }
    }
    /**
     * Metoda stergereDinFisier scrie intr-un fisier temporar continutul fisierului in afara de datele aferente String ului primit ca parametru,
     * iar la final sterge fisierul original si il redenumeste pe cel temporar. 
     * @param deSters Numele contactului care trebuie sters.
     * @param fileName numele fisierului
     */
    public void stergereDinFisier(String fileName, String deSters) {
        String pathFisier = fileName;

        try {
            File inputFile = new File(pathFisier);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            int linesToRemove = 4;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.contains(deSters.trim())) {
                    for (int i = 0; i < linesToRemove - 1; i++) {
                        reader.readLine();
                    }
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();

            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Error renaming the temporary file.");
                }
            } else {
                System.out.println("Error deleting the original file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda cautaContacteDupaNume cauta daca exista contacte care au ca substring String-ul primit ca parametru si afiseaza corespunzator.
     * @param deCautat
     */
    private void cautaContacteDupaNume(String deCautat) {

        DefaultListModel<Contact> model = (DefaultListModel<Contact>) agendaTelefonica.getModel();

        List<Contact> rezultateCautare = new ArrayList<>();

        for (int i = 0; i < model.getSize(); i++) {
            Contact contact = model.getElementAt(i);
            if (contact.getNume().toLowerCase().contains(deCautat.toLowerCase())
                    || contact.getNumarTelefon().contains(deCautat)) {
                rezultateCautare.add(contact);
            }
        }

        if (!rezultateCautare.isEmpty()) {
            StringBuilder rezultateText = new StringBuilder("Rezultatele căutării:\n");
            for (Contact contact : rezultateCautare) {
                rezultateText.append(contact.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, rezultateText.toString(), "Rezultate Căutare",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Nu a fost găsit niciun contact.", "Rezultate Căutare",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * verifica daca exista deja un contact in agenda si returneaza corespunzator
     * @param nume
     * @return true sau false
     */
    public boolean contactDejaExistent(String nume) {
        for (int i = 0; i < agendaTelefonicaModel.getSize(); i++) {
            Contact contact = agendaTelefonicaModel.getElementAt(i);
            if (contact.getNume().equals(nume)) {
                return true;
            }
        }
        return false;
    }
    /**
  	 * Metoda citireDinFisier citeste contactele din fisier si le adauga in lista de contacte.
       */
    public void citireDinFisier(String fileName) {
        try {
            File fisierAgenda = new File(fileName);
            if (!fisierAgenda.exists()) {
                fisierAgenda.createNewFile();
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(fisierAgenda));
            int index = 0;
            String nume = null;
            String numar = null;
            String adresaEmail = null;
            String notite = null;
            while (reader.ready()) {
                String informatie = reader.readLine();
                if (index == 0) {
                    nume = informatie + "  ";
                    index++;
                } else if (index == 1) {
                    numar = informatie + "  ";
                    index++;
                } else if (index == 2) {
                    adresaEmail = informatie + "  ";
                    index++;
                } else if (index == 3) {
                    notite = informatie;
                    index = 0;
                    Contact contact = new Contact(nume, numar, adresaEmail, notite);
                    if (fileName.equals("src/main/resources/agendaTelefonica.txt")) {
                        agendaTelefonicaModel.addElement(contact);
                    } else if (fileName.equals("src/main/resources/contacteFavorite.txt")) {
                        contacteFavoriteModel.addElement(contact);
                    }
                    nume = null;
                    numar = null;
                    adresaEmail = null;
                    notite = null;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * metoda sorteaza agendaTelefonica
     */
    public void sortAgendaTelefonica() {
        List<Contact> tempContacts = new ArrayList<>();
        for (int i = 0; i < agendaTelefonicaModel.size(); i++) {
            tempContacts.add(agendaTelefonicaModel.getElementAt(i));
        }

        Collections.sort(tempContacts, new ComparatorContact());
        agendaTelefonicaModel.removeAllElements();

        for (Contact contact : tempContacts) {
            agendaTelefonicaModel.addElement(contact);
        }
    }
/**
 * verifica daca contactul este deja in fisier
 * @param contact
 * @param file
 * @return true sau false
 * @throws IOException
 */
    private boolean contactDejaInFisier(Contact contact, File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals(contact.getNume().trim())) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }


}
