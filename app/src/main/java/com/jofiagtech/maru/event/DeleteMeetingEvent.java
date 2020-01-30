package com.jofiagtech.maru.event;

import com.jofiagtech.maru.model.Meeting;

public class DeleteMeetingEvent
{
    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
