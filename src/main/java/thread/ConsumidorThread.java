package thread;

import model.PrimesList;

public class ConsumidorThread implements Runnable {
    private final PrimesList primesList;

    public ConsumidorThread(PrimesList primesList) {
        this.primesList = primesList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                int removed = primesList.remove();
                System.out.println("Consumidor procesó: " + removed);
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}