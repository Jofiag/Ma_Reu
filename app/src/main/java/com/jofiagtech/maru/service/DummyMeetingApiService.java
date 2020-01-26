package com.jofiagtech.maru.service;

import com.jofiagtech.maru.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiServices
{
    private List<Meeting> meetingList = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings()
    {
        return meetingList;
    }

    @Override
    public void addMeeting(Meeting meeting)
    {
        meetingList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting)
    {
        meetingList.remove(meeting);
    }
}
