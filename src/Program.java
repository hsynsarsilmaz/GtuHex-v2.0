public class Program{
    public static void main(String args[]){
        Engine a = new Engine(5,1);
        a.getGameboard()[3][3].setStatus(statust.computer);
        System.out.println("A: ");
        a.print();
        Engine b = (Engine)a.Clone();
        System.out.println("B: ");
        b.print();
        if(a == b) System.out.println("A and B pointing to same object");
        else System.out.println("A and B pointing different but equal in terms of data objects");

        StartScreen thegame = new StartScreen();
    }
}