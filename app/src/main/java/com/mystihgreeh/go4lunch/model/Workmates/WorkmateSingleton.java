package com.mystihgreeh.go4lunch.model.Workmates;

public class WorkmateSingleton {

    private Workmate workmate;

    private WorkmateSingleton() {
        workmate = new Workmate();

    }

    private static WorkmateSingleton workmateSingleton;

    public static WorkmateSingleton get(){
        if (workmateSingleton == null){
            workmateSingleton = new WorkmateSingleton();
        }
        return workmateSingleton;
    }
    
}
