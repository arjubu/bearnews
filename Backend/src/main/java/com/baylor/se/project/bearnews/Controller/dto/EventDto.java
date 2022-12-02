package com.baylor.se.project.bearnews.Controller.dto;

public class EventDto {

    private String eventTitle;
    private String eventdescription;
    private String eventlocation;


    public String geteventdescription(){
        return eventdescription;
    }

    public void seteventdescription(String eventdescription){
        this.eventdescription=eventdescription;
    }

    public String geteventTitle(){
        return eventTitle;
    }

    public void seteventTitle(String eventTitle){
        this.eventTitle=eventTitle;
    }

    public String geteventlocation(){
        return eventlocation;
    }
    public void seteventlocation(String eventlocation){
        this.eventlocation=eventlocation;
    }

}
