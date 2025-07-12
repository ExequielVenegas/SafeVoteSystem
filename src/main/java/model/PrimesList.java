package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrimesList {
    private final List<Integer> primes = new ArrayList<>();
    private final int capacidadMaxima = 10;

    private final Lock lock = new ReentrantLock();
    private final Condition noLleno = lock.newCondition();
    private final Condition noVacio = lock.newCondition();

    public boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public void add(int number) throws InterruptedException {
        lock.lock();
        try {
            while (primes.size() >= capacidadMaxima) {
                System.out.println("Lista llena. Productor espera...");
                noLleno.await();
            }
            if (!isPrime(number)) {
                throw new IllegalArgumentException("El número no es primo.");
            }
            primes.add(number);
            System.out.println("Número agregado: " + number);
            noVacio.signal();
        } finally {
            lock.unlock();
        }
    }

    public int remove() throws InterruptedException {
        lock.lock();
        try {
            while (primes.isEmpty()) {
                System.out.println("Lista vacía. Consumidor espera...");
                noVacio.await();
            }
            int removed = primes.remove(0);
            System.out.println("Número removido: " + removed);
            noLleno.signal();
            return removed;
        } finally {
            lock.unlock();
        }
    }

    public int getPrimesCount() {
        lock.lock();
        try {
            return primes.size();
        } finally {
            lock.unlock();
        }
    }
}