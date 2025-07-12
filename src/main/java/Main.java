import java.util.List;

public class Main {
    public static void main(String[] args) {
        PrimesList primesList = new PrimesList();

        List<Integer> primosCargados = FileHandler.readPrimesFromCSV("src/main/resources/primos.csv");
        for (Integer primo : primosCargados) {
            try {
                primesList.add(primo);
            } catch (Exception e) {
                System.out.println("Error al cargar primo del archivo: " + primo);
            }
        }

        Thread productor = new Thread(new PrimesThread(primesList));
        Thread consumidor = new Thread(new ConsumidorThread(primesList));

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (primosCargados.size() > 0) {
            FileHandler.writeEncryptedMessage("mensajes_encriptados.txt", "Hola seguro", primosCargados.get(0));
        }

        System.out.println("Cantidad final de primos: " + primesList.getPrimesCount());
    }
}