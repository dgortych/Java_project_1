public class App {
    public static void main(String[] args) throws Exception {
    System.out.println("MT start");
    Thread thread = new Thread(() -> {
        
        System.out.println("T1 start");
        for (int i = 0; i < 5; i++) {
            System.out.println("T1 " + i);
        }
        System.out.println("T1 stop");
    }
    );
    Thread thread2 = new Thread(() -> {
        
        System.out.println("T2 start");
        for (int i = 0; i < 5; i++) {
            System.out.println("T2 " + i);
        }
        System.out.println("T2 stop");
    }
    );

    thread.start();
    thread.sleep(3000);
    thread.join();
    thread2.start();
    thread2.sleep(3000);
    thread2.join();

    for (int i = 0; i < 5; i++) {
        System.out.println("MT " + i);
    }
    System.out.println("MT stop");
    }
}
