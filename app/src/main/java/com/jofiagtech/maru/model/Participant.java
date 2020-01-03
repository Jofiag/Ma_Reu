package com.jofiagtech.maru.model;

public class Participant
{
    private String email;
    private int id;

    public Participant() {
    }

    public Participant(String email)
    {
        this.email = email;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
