package com.jofiagtech.maru.di;

import com.jofiagtech.maru.service.DummyMeetingApiService;
import com.jofiagtech.maru.service.MeetingApiServices;

public class DI
{
    private static MeetingApiServices service = new DummyMeetingApiService();


    public static MeetingApiServices getMeetingApiService() {
        return service;
    }

    public static MeetingApiServices getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }
}
