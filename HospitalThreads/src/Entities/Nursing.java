package Entities;

public class Nursing extends Patient{

    public int getID(Patient patient) {
        return patient.getID();
    }
    public int getAge(Patient patient) {
        return patient.getAge();
    }
    public String getFirstName(Patient patient) {
        return patient.getFirstName();
    }
    public String getSurname(Patient patient) {
        return patient.getSurname();
    }
    public int getHeartRate(Patient patient) {
        return patient.getHeartRate();
    }
    public int getBloodPressureMax(Patient patient) {
        return patient.getBloodPressureMax();
    }
    public int getBloodPressureMin(Patient patient) {
        return patient.getBloodPressureMin();
    }
    public float getBodyTemperature(Patient patient) {
        return patient.getBodyTemperature();
    }
    public int getRespiratoryFrequency(Patient patient) {
        return patient.getRespiratoryFrequency();
    }
    public int getHeartPoint(Patient patient) {
        if(patient.getHeartPoint() < 7)
            headNursing.notify(patient);
        return patient.getHeartPoint();
    }

    private void notifyHeadNursing(Patient patient) {
        HeadNursing.attention(patient);
    }

    public int getNebulizerTimesUsed(Patient patient) {
        return patient.getNebulizerTimesUsed();
    }

    public boolean isPatientAlive(Patient patient){
        return patient.isPatientAlive();
    }

    public String medicalRecord(Patient patient){
        return  " || ID: "+getID(patient)+
                " || NEME: "+getFirstName(patient)+
                " || SURNAME: "+getSurname(patient)+
                " || AGE: "+getAge(patient)+
                " || HEART RATE: "+getHeartRate(patient)+
                " || BLOOD PRESSURE: "+getBloodPressureMax(patient)+"/"+getBloodPressureMin(patient)+
                " || BODY TEMPERATURE: "+getBodyTemperature(patient)+
                " || RESPITAROTY FREQUENCY: "+getRespiratoryFrequency(patient)+
                " || HEART POINT: "+getHeartPoint(patient)+
                " || TIMES USED NEBULIZER: "+getNebulizerTimesUsed(patient)+
                " ||";
    }

}
