package Entities;

import Util.Names;
import Util.Surname;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Patient extends Thread {
    private static int nextID = 1;
    private int ID; //> 100 ID patient for tracking | sequential
    private int age;    //18yo ~ 65yo | Random
    private String FirstName;    //enum | Random
    private String surname; // enum | Random
    private int heartRate;  //60~100 bpm | Random
    private int bloodPressureMax;   //90~120mmHg | Random
    private int bloodPressureMin;   //60~80mmHg | Random
    private float bodyTemperature;  //35,5~37,0ºC |Random
    private int respiratoryFrequency;   //12~20 rpm | Random
    private int heartPoint;  //8~10HP (initial)| Random; -1HP (death) || without tratment patient loses 1 or 2 HP randomly || undergoinTratment receives 2 or 4 HP randomly
    private boolean onNebulizer; //Nebulizer in use
    private int nebulizerTimesUsed;  //For register
    private boolean active; //active thread
    private long INTERVAL; //time for increase/decrease HP
    private boolean alive; //if dead need wait Nursin remove of simulation
    private int MAX_NEBULIZE_TIME;
    private int MIN_NEBULIZE_TIME;

    Random random = new Random();

    public Patient(long INTERVAL, int MAX_NEBULIZE_TIME, int MIN_NEBULIZE_TIME) {
        this.ID = nextID;
        nextID++;
        this.age = random.nextInt(48)+18; //18~65yo
        this.FirstName = String.valueOf(random.nextInt(Names.values().length));
        this.surname = String.valueOf(random.nextInt(Surname.values().length));
        this.heartRate = random.nextInt(41)+60;  //60~100 bpm
        this.bloodPressureMax = random.nextInt(31)+90;   //90~120
        this.bloodPressureMin = random.nextInt(21)+60;   //60~80
        this.bodyTemperature= random.nextFloat(2.5F)+35.5F;  //35,5~37,0ºC
        this.respiratoryFrequency = random.nextInt(9)+12;   //12~20rpm
        this.heartPoint = random.nextInt(3)+8;  //8~10HP
        this.onNebulizer = false;
        this.nebulizerTimesUsed = 0;
        this.active = true;
        this.alive = true;

        this.INTERVAL = INTERVAL;
        this.MAX_NEBULIZE_TIME = MAX_NEBULIZE_TIME;
        this.MIN_NEBULIZE_TIME = MIN_NEBULIZE_TIME;

    }
    public Patient() {
        this.ID = nextID;
        nextID++;
    }

    protected int getID() {
        return ID;
    }
    protected int getAge() {
        return age;
    }
    protected String getFirstName() {
        return FirstName;
    }
    protected String getSurname() {
        return surname;
    }
    protected int getHeartRate() {
        return heartRate;
    }
    protected int getBloodPressureMax() {
        return bloodPressureMax;
    }
    protected int getBloodPressureMin() {
        return bloodPressureMin;
    }
    protected float getBodyTemperature() {
        return bodyTemperature;
    }
    protected int getRespiratoryFrequency() {
        return respiratoryFrequency;
    }
    protected int getHeartPoint() {
        return heartPoint;
    }
    protected boolean isUndergoinTreatment() {
        return onNebulizer;
    }
    protected boolean isPatientAlive(){
        return alive;
    }
    protected int getNebulizerTimesUsed() {
        return nebulizerTimesUsed;
    }

    //Principal function
    @Override
    public void run() {

        while (active) {
            if(heartPoint == 0)
                alive = false;

            if(onNebulizer && alive){
                nebulizerTimesUsed++;
                heartPoint += random.nextInt(3) + 2; // increase 2 ~ 4 heartPoint
                if (heartPoint > 10) {
                    heartPoint = 10;
                }
            }else{
                heartPoint -= random.nextInt(2) + 1; // decrease de 1-2 heartPoint
                if (heartPoint < 0) {
                    heartPoint = 0;
                }
            }

            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
