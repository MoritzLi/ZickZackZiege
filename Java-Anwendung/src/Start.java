import gui.Anmelden;

class Start {
    public static void main(String[] arguments) throws Exception {
        Anmelden gui = new Anmelden();
        //gui.setVisible(true);
        db data = new db();
        data.gebeHighsoreMehr();
    }
}