import Entities.Medic;
import Entities.Nursing;
import Entities.Patient;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java AlaHospitalar <tempo_de_simulacao_em_segundos>");
            System.exit(1);
        }

        int tempoSimulacao = Integer.parseInt(args[0]) * 1000; // Tempo total de simulação em milissegundos

        final int NUM_PACIENTES = 16;
        final int NUM_NEBULIZADORES = 4;
        final int NUM_VAGAS_SALA = 16;

        // Semáforos para controlar o acesso aos recursos
        Semaphore semaforoNebulizadores = new Semaphore(NUM_NEBULIZADORES, true);
        Semaphore semaforoSalaEspera = new Semaphore(NUM_VAGAS_SALA, true);

        // Criação de médicos e equipe de enfermagem
        Medic medico1 = new Medic(semaforoNebulizadores);
        Medic medico2 = new Medic(semaforoNebulizadores);
        Nursing enfermeiroChefe = new Nursing(semaforoNebulizadores, NUM_NEBULIZADORES);

        // Inicialização dos médicos e equipe de enfermagem
        medico1.start();
        medico2.start();
        enfermeiroChefe.start();

        long startTime = System.currentTimeMillis();

        // Simula a chegada de pacientes
        for (int i = 1; i <= NUM_PACIENTES; i++) {
            int idade = new Random().nextInt(80) + 1; // Idade aleatória entre 1 e 80 anos
            Patient paciente = new Patient(i, idade, semaforoNebulizadores, semaforoSalaEspera);
            paciente.start();

            // Tempo aleatório entre chegadas de pacientes (0 a 5 segundos)
            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Aguarda até que o tempo de simulação se esgote
        while (System.currentTimeMillis() - startTime < tempoSimulacao) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Encerra a simulação
        Patient.encerrarSimulacao();
        medico1.encerrar();
        medico2.encerrar();
        enfermeiroChefe.encerrar();

        // Exibe relatório
        System.out.println("Relatório da Simulação:");
        System.out.println("Pacientes atendidos e liberados pelos médicos: " + Patient.getPacientesAtendidos());
        System.out.println("Pacientes que morreram: " + Patient.getPacientesMortos());
        System.out.println("Pacientes que foram encaminhados para outro hospital: " + Patient.getPacientesEncaminhados());
    }
}
