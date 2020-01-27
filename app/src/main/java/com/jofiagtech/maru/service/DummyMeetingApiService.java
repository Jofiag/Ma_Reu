package com.jofiagtech.maru.service;

import com.jofiagtech.maru.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiServices
{
    private List<Meeting> mMeetingList = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetingList()
    {
        return mMeetingList;
    }

    @Override
    public void addMeeting(Meeting meeting)
    {
        mMeetingList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting)
    {
        mMeetingList.remove(meeting);
    }

    @Override
    public void setMeetingList(List<Meeting> meetingList)
    {
        mMeetingList = meetingList;
    }
}
