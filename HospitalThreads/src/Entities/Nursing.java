package Entities;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Nursing extends Thread {
    private Semaphore semaforoNebulizadores;
    private int numNebulizadores;
    private boolean ativo = true;

    public Nursing(Semaphore semaforoNebulizadores, int numNebulizadores) {
        this.semaforoNebulizadores = semaforoNebulizadores;
        this.numNebulizadores = numNebulizadores;
    }

    public void encerrar() {
        ativo = false;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (ativo) {
            try {
                Thread.sleep(random.nextInt(3000) + 1000); // Tempo de nebulização aleatório (1 a 4 segundos)
                semaforoNebulizadores.release(numNebulizadores); // Libera os nebulizadores
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
