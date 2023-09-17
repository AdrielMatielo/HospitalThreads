package Entities;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Medic extends Thread {

    private int maxMedicalCareTime;
    private int minMedicalCareTime;
    private boolean active = true;
    private Queue<Patient> queuePriotiry;
    //private Patient currentPatient;
    private Semaphore patientSemaphore = new Semaphore(1);

    Random random = new Random();

    public Medic(int minMedicalCareTime, int maxMedicalCareTime, Queue<Patient> queuePriotiry){
        this.minMedicalCareTime = minMedicalCareTime;
        this.maxMedicalCareTime = maxMedicalCareTime;
        this.queuePriotiry = queuePriotiry;
    }

    public void finish() {
        active = false;
    }
    @Override
    public void run() {

        while (active) {
            if(!queuePriotiry.isEmpty()){
                Patient currentPatient = null;
                try{
                    patientSemaphore.acquire();
                    currentPatient = queuePriotiry.poll();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    patientSemaphore.release();
                }
                if(currentPatient != null){
                    System.out.println("Medical care on patient: "+ currentPatient.getID());
                    int medicalCareTime = random.nextInt(maxMedicalCareTime-minMedicalCareTime+1)+minMedicalCareTime;
                    try{
                        Thread.sleep(medicalCareTime);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    currentPatient.interrupt();
                }
            }

        }
    }
}
