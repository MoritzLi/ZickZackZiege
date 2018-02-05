import gui.Anmelden;
import utility.Datenbank;

class Start {
    public static void main(String[] arguments) throws Exception {
        Anmelden gui = new Anmelden();
        //gui.setVisible(true);
        Datenbank data = new Datenbank();
        data.gebeHighsoreMehr("punkte");
    }
}