import classes.Appuntamento;

public class main {

    public static void main(String[] args) {
        Appuntamento a = new Appuntamento("03-02-1998", "10:45", 15, "Luca", "Ospedale");
        System.out.println(a.toString());
    }
}
