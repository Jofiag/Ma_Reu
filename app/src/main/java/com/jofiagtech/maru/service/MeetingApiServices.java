package com.jofiagtech.maru.service;

import com.jofiagtech.maru.model.Meeting;

import java.util.List;

public interface MeetingApiServices
{
    List<Meeting> getMeetingList();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    void setMeetingList(List<Meeting> meetingList);
}
