import java.time.LocalDateTime;

public class db {
    String name;
    int punkte;
    int spieler;
    DatabaseConnector verbinden;

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
        verbinden.executeStatement("SELECT id FROM spieler WHERE name LIKE '" + name + "' AND passwort LIKE '" + passwort + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();

        if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {
            System.out.println("Anmeldung war erfolgreich");
            return;

        }
        System.out.println("Anmeldung war nicht erfolgreich");
    }


    public void regist(String name, String passwort) {
        verbinden.executeStatement("SELECT name FROM spieler WHERE name LIKE '" + name + "'");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();
        if (ergebnis.getColumnCount() == 0 && ergebnis.getRowCount() == 0) {
            verbinden.executeStatement("INSERT into spieler VALUES(null, '" + name + "','" + passwort + "' )");
            System.out.println("Registrierung war erfolgreich");
            return;
        }

        System.out.println("Name schon vorhanden, bitte wähle einen anderen Namen");


    }


    public void gebeHighsoreBot() {
        verbinden.executeStatement("SELECT name, punkte, quote FROM einzelhighscore");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();

        if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {
            for (int i = 0; i < ergebnis.getRowCount(); i++) {
                for (int u = 0; u < ergebnis.getColumnCount(); u++) {
                    System.out.print(Daten[i][u] + " ");
                }
                System.out.println();
            }

        }

    }


    public void gebeHighsoreMehr() {
        verbinden.executeStatement("SELECT name, punkte, quote FROM mehrspielerhighscore");
        //verbinden.executeStatement("SELECT name, punkte, quote FROM mehrspielerhighscore ORDER by ...");
        QueryResult ergebnis = verbinden.getCurrentQueryResult();
        String[][] Daten = ergebnis.getData();

        if (ergebnis.getColumnCount() != 0 && ergebnis.getRowCount() != 0) {
            for (int i = 0; i < ergebnis.getRowCount(); i++) {
                for (int u = 0; u < ergebnis.getColumnCount(); u++) {
                    System.out.print(Daten[i][u] + " ");
                }
                System.out.println();
            }

        }

    }


}
