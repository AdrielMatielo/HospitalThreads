package Entities;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Medic extends Thread {
    private Semaphore semaforoNebulizadores;
    private boolean ativo = true;

    public Medic(Semaphore semaforoNebulizadores) {
        this.semaforoNebulizadores = semaforoNebulizadores;
    }

    public void encerrar() {
        ativo = false;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (ativo) {
            try {
                Thread.sleep(random.nextInt(8000) + 2000); // Tempo de atendimento aleatório (2 a 10 segundos)
                Patient.atendimentoMedico(); // Libera um paciente para atendimento médico
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
