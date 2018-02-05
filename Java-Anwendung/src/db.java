import java.time.LocalDateTime;

public class db {
    String name;
    int punkte;
    int spieler;
    private DatabaseConnector verbinden;

    public db() {
        verbinden = new DatabaseConnector("localhost", 3306, "zickzackziege", "root", "");
        System.out.println(verbinden.getErrorMessage());
    }

    public void botSpiel(String name, int punkte, int punkte2) {
        LocalDateTime s = LocalDateTime.now();
        double punkted = (double) punkte;
        double punkte2d = (double) punkte2;
        double quote = punkted / punkte2d;
        quote = Math.round(100.0 * quote) / 100.0;
        verbinden.executeStatement("INSERT into einzelhighscore VALUES('" + name + "', '" + s + "', '" + punkte + "',  '" + quote + "')");
    }

    public void mehrSpieler(String name, int punkte, int punkte2, int spieler) {
        LocalDateTime s = LocalDateTime.now();
        double punkted = (double) punkte;
        double punkte2d = (double) punkte2;
        double quote = punkted / punkte2d;
        quote = Math.round(100.0 * quote) / 100.0;
        verbinden.executeStatement("INSERT into mehrspielerhighscore VALUES('" + name + "', '" + s + "', '" + punkte + "',  '" + quote + "', '" + spieler + "')");
    }

    public void anmelden(String name, String passwort) {
        String id1;
        verbinden.executeStatement("SELECT id FROM spieler WHERE name = '" + name + "' AND passwort = '" + passwort + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();

        if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {
            System.out.println("Anmeldung war erfolgreich");
            return;

        }
        System.out.println("Anmeldung war nicht erfolgreich");
    }

    public void register(String name, String passwort) {
        if (name.length() == 0) {
            System.out.println("Name darf nicht leer sein!");
            return;
        }
        verbinden.executeStatement("SELECT name FROM spieler WHERE name = '" + name + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();

        if (ergebnis.getColumnCount() == 0 && ergebnis.getRowCount() == 0) {
            verbinden.executeStatement("INSERT into spieler VALUES(null, '" + name + "','" + passwort + "' )");
            System.out.println("Registrierung war erfolgreich");
            return;
        }

        System.out.println("Name schon vorhanden, bitte wähle einen anderen Namen");
    }

    public String[][] gebeHighsoreBot(String attribut) {
        verbinden.executeStatement("SELECT name, punkte, quote FROM einzelhighscore ORDER by '" + attribut + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();

        return Daten;
    }

    public String[][] gebeHighsoreMehr(String attribut) {
        verbinden.executeStatement("SELECT name, punkte, quote FROM einzelhighscore ORDER by '" + attribut + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();

        //if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {

        //}
        return Daten;
    }
}