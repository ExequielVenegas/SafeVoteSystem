import java.util.Random;

public class PrimesThread implements Runnable {
    private final PrimesList primesList;
    private final Random random = new Random();

    public PrimesThread(PrimesList primesList) {
        this.primesList = primesList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(100) + 1;
            try {
                primesList.add(number);
                Thread.sleep(100);
            } catch (IllegalArgumentException e) {
                System.out.println("No se agregó: " + number + " (no primo)");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}