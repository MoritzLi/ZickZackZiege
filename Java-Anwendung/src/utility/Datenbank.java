package utility;

import java.time.LocalDateTime;

public class Datenbank {
    String name;
    int punkte;
    int spieler;
    private DatabaseConnector verbinden;
    public static String username;

    public Datenbank() {
        verbinden = new DatabaseConnector("localhost", 3306, "zickzackziege", "root", "");
        System.out.println(verbinden.getErrorMessage());
    }

    public void botSpiel(String name, int punkte, int punkte2) {
        LocalDateTime s = LocalDateTime.now();
        double punkted = (double) punkte;
        double punkte2d = (double) punkte2;
        double zusm = punkte2d + punkted;
        double quote = punkted / zusm;
        quote = Math.round(10000.0 * quote) / 10000.0;
        verbinden.executeStatement("INSERT into einzelhighscore VALUES('" + name + "', '" + s + "', '" + punkte + "',  '" + quote + "')");
    }

    public void mehrSpieler(String name, int punkte, int punkte2, int spieler) {
        LocalDateTime s = LocalDateTime.now();
        double punkted = (double) punkte;
        double punkte2d = (double) punkte2;
        double quote = punkted / punkte2d;
        quote = Math.round(10000.0 * quote) / 10000.0;
        verbinden.executeStatement("INSERT into mehrspielerhighscore VALUES('" + name + "', '" + s + "', '" + punkte + "',  '" + quote + "', '" + spieler + "')");
    }

    public boolean anmelden(String name, String passwort) {
        String id1;
        verbinden.executeStatement("SELECT id FROM spieler WHERE name = '" + name + "' AND passwort = '" + passwort + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();

        if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {
            username = name;
            return true;
        }
        return false;
    }

    public String register(String name, String passwort) {
        if (name.length() == 0) {
            return ("Name darf nicht leer sein");
        }
        verbinden.executeStatement("SELECT name FROM spieler WHERE name = '" + name + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();

        if (ergebnis.getColumnCount() == 0 && ergebnis.getRowCount() == 0) {
            verbinden.executeStatement("INSERT into spieler VALUES(null, '" + name + "','" + passwort + "' )");
            username = name;
            return ("Registrierung war erfolgreich");
        }

        return ("Name schon vorhanden, bitte wähle einen anderen Namen");
    }

    public String[][] gebeHighsoreBot(String attribut) {
        verbinden.executeStatement("SELECT name, punkte, quote FROM einzelhighscore ORDER by '" + attribut + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        return ergebnis.getData();
    }

    public String[][] gebeHighsoreMehr(String attribut) {
        verbinden.executeStatement("SELECT name, punkte, quote FROM einzelhighscore ORDER by '" + attribut + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();

        //if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {

        //}
        return ergebnis.getData();
    }
}