package com.jofiagtech.maru.model;

public class Meeting
{
    private String subject;
    private String time;
    private String place;
    private int numberOfParticipant;

    public Meeting() {
    }

    public Meeting(String subject, String time, String place, int numberOfParticipant) {
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public int getNumberOfParticipant()
    {
        return numberOfParticipant;
    }

    public void setNumberOfParticipant(int numberOfParticipant)
    {
        this.numberOfParticipant = numberOfParticipant;
    }
}