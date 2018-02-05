import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import utility.Queue;

public class DatabaseConnector {
    private Connection connection;
    private QueryResult currentQueryResult = null;
    private String message = null;

    /**
     * Ein Objekt vom Typ DatabaseConnector wird erstellt, und eine Verbindung zur Datenbank
     * wird aufgebaut. Mit den Parametern pIP und pPort werden die IP-Adresse und die
     * Port-Nummer uebergeben, unter denen die Datenbank mit Namen pDatabase zu erreichen ist.
     * Mit den Parametern pUsername und pPassword werden Benutzername und Passwort fuer die
     * Datenbank uebergeben.
     */
    public DatabaseConnector(String pIP, int pPort, String pDatabase, String pUsername, String pPassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + pIP + ":" + pPort + "/" + pDatabase, pUsername, pPassword);

        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    /**
     * Der Auftrag schickt den im Parameter pSQLStatement enthaltenen SQL-Befehl an die
     * Datenbank ab.
     * Handelt es sich bei pSQLStatement um einen SQL-Befehl, der eine Ergebnismenge
     * liefert, so kann dieses Ergebnis anschließend mit der Methode getCurrentQueryResult
     * abgerufen werden.
     */
    public void executeStatement(String pSQLStatement) {
        currentQueryResult = null;
        message = null;

        try {
            Statement statement = connection.createStatement();

            if (statement.execute(pSQLStatement)) {
                ResultSet resultset = statement.getResultSet();

                int columnCount = resultset.getMetaData().getColumnCount();

                String[] resultColumnNames = new String[columnCount];
                String[] resultColumnTypes = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    resultColumnNames[i] = resultset.getMetaData().getColumnLabel(i + 1);
                    resultColumnTypes[i] = resultset.getMetaData().getColumnTypeName(i + 1);
                }

                Queue<String[]> rows = new Queue<>();

                int rowCount = 0;
                while (resultset.next()) {
                    String[] resultrow = new String[columnCount];
                    for (int s = 0; s < columnCount; s++) {
                        resultrow[s] = resultset.getString(s + 1);
                    }
                    rows.append(resultrow);
                    rowCount = rowCount + 1;
                }

                String[][] resultData = new String[rowCount][columnCount];
                int j = 0;
                while (!rows.isEmpty()) {
                    resultData[j] = rows.getContent();
                    rows.remove();
                    j = j + 1;
                }

                statement.close();
                currentQueryResult = new QueryResult(resultData, resultColumnNames, resultColumnTypes);
            } else {
                statement.close();
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    /**
     * Die Anfrage liefert das Ergebnis des letzten mit der Methode executeStatement an
     * die Datenbank geschickten SQL-Befehls als Ob-jekt vom Typ QueryResult zurueck.
     * Wurde bisher kein SQL-Befehl abgeschickt oder ergab der letzte Aufruf von
     * executeStatement keine Ergebnismenge (z.B. bei einem INSERT-Befehl oder einem
     * Syntaxfehler), so wird null geliefert.
     */
    public QueryResult getCurrentQueryResult() {
        return currentQueryResult;
    }

    /**
     * Die Anfrage liefert null oder eine Fehlermeldung, die sich jeweils auf die letzte zuvor ausgefuehrte
     * Datenbankoperation bezieht.
     */
    public String getErrorMessage() {
        return message;
    }

    /**
     * Die Datenbankverbindung wird geschlossen.
     */
    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
}