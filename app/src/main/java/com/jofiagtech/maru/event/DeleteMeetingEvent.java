package com.jofiagtech.maru.event;

import com.jofiagtech.maru.model.Meeting;

public class DeleteMeetingEvent
{
    public Meeting mMeeting;

    public DeleteMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}
