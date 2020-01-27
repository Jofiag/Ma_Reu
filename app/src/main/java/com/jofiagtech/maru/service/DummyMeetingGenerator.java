package com.jofiagtech.maru.service;

import com.jofiagtech.maru.model.Meeting;
import com.jofiagtech.maru.model.Participant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyMeetingGenerator
{
    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting( "Reunion 1", "15h00", "Salle Elizabethe", Arrays.asList(
                    new Participant("joel@lamzone.fr"),
                    new Participant("alex@lamzone.fr"),
                    new Participant("paul@lamzone.fr")
            )),
            new Meeting( "Reunion 2", "17h00", "Salle Dunnant", Arrays.asList(
                    new Participant("john@lamzone.fr"),
                    new Participant("arnaud@lamzone.fr"),
                    new Participant("dimitri@lamzone.fr")
            )),
            new Meeting( "Reunion 3", "19h00", "Salle Poisson", Arrays.asList(
                    new Participant("castiel@lamzone.fr"),
                    new Participant("sam@lamzone.fr"),
                    new Participant("bobby@lamzone.fr")
            ))
    );

    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETING);
    }

    public static void updateGenerator(List<Meeting> meetingList){
        DUMMY_MEETING = meetingList;
    }
}
