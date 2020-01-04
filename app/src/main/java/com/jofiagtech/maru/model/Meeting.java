package com.jofiagtech.maru.model;

import java.util.List;

public class Meeting
{
    private String subject;
    private String time;
    private String place;
    private String date;
    private List<Participant> participantList;
    private int numberOfParticipant;
    private int id;

    public Meeting() {
    }

    public Meeting(String subject, String time, String place, List<Participant> participantList, int numberOfParticipant)
    {
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.participantList = participantList;
        this.numberOfParticipant = numberOfParticipant;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getNumberOfParticipant()
    {
        return numberOfParticipant;
    }

    public void setNumberOfParticipant(int numberOfParticipant)
    {
        this.numberOfParticipant = numberOfParticipant;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public List<Participant> getParticipantList()
    {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList)
    {
        this.participantList = participantList;
    }
}
