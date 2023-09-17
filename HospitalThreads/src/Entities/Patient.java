package Entities;

import Util.Names;
import Util.Surname;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Patient extends Thread {
    private int numero;
    private int idade;
    private String nome;
    private String sobrenome;
    private int sinalVital;
    private boolean emTratamento;
    private int vezesNebulizadorUtilizado;
    private long tempoTotalAtendimento;

    private Semaphore semaforoNebulizadores;
    private Semaphore semaforoSalaEspera;


    private static boolean simulacaoAtiva = true;
    private static int pacientesAtendidos = 0;
    private static int pacientesMortos = 0;
    private static int pacientesEncaminhados = 0;

    public Patient(int numero, int idade, Semaphore semaforoNebulizadores, Semaphore semaforoSalaEspera) {
        Random random = new Random();
        this.numero = numero;
        this.idade = idade;
        this.nome = String.valueOf(random.nextInt(100));
        this.sobrenome =  String.valueOf(random.nextInt(100));
        this.sinalVital = random.nextInt(3)+8;
        this.emTratamento = false;
        this.vezesNebulizadorUtilizado = 0;
        this.tempoTotalAtendimento = 0;
        this.semaforoNebulizadores = semaforoNebulizadores;
        this.semaforoSalaEspera = semaforoSalaEspera;
    }

    public static void encerrarSimulacao() {
        simulacaoAtiva = false;
    }

    public static int getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public static int getPacientesMortos() {
        return pacientesMortos;
    }

    public static int getPacientesEncaminhados() {
        return pacientesEncaminhados;
    }

    @Override
    public void run() {
        long tempoInicioAtendimento = System.currentTimeMillis();

        while (simulacaoAtiva) {
            // Paciente aguarda na sala de espera se não houver espaço na sala de nebulização
            try {
                semaforoSalaEspera.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Paciente tenta adquirir um nebulizador
            try {
                semaforoNebulizadores.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Paciente inicia o tratamento com o nebulizador
            emTratamento = true;
            vezesNebulizadorUtilizado++;

            // Simula o uso do nebulizador e as mudanças nos sinais vitais
            simularTratamento();

            // Paciente libera o nebulizador e deixa a sala de nebulização
            emTratamento = false;
            semaforoNebulizadores.release();
            semaforoSalaEspera.release();

            // Calcula o tempo total de atendimento
            tempoTotalAtendimento += System.currentTimeMillis() - tempoInicioAtendimento;

            // Verifica o estado do paciente
            if (sinalVital <= 0) {
                pacientesMortos++;
                break;
            }

            if (idade >= 60) {
                pacientesAtendidos++;
                break;
            }

            if (idade < 60 && tempoTotalAtendimento >= 20000) {
                pacientesAtendidos++;
                break;
            }
        }

        // Verifica se o paciente foi encaminhado para outro hospital
        if (!simulacaoAtiva) {
            pacientesEncaminhados++;
        }
    }

    private void simularTratamento() {
        Random random = new Random();
        int intervalo = random.nextInt(4000) + 1000; // Intervalo aleatório entre 1 e 5 segundos

        while (emTratamento) {
            try {
                Thread.sleep(intervalo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (emTratamento) {
                // Simula a melhoria nos sinais vitais durante o tratamento
                sinalVital += random.nextInt(3) + 2; // Aumento de 2 a 4 níveis
                if (sinalVital > 10) {
                    sinalVital = 10;
                }

                // Simula a deterioração dos sinais vitais quando não está em tratamento
                sinalVital -= random.nextInt(2) + 1; // Redução de 1 a 2 níveis
                if (sinalVital <= 0) {
                    sinalVital = 0;
                    break;
                }
            }
        }
    }

    public static synchronized void atendimentoMedico() {
        //notifyAll();
    }

}
