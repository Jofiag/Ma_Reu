package com.jofiagtech.maru;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jofiagtech.maru.adapter.RecyclerViewAdapter;
import com.jofiagtech.maru.di.DI;
import com.jofiagtech.maru.event.DeleteMeetingEvent;
import com.jofiagtech.maru.model.Meeting;
import com.jofiagtech.maru.model.Participant;
import com.jofiagtech.maru.service.DummyMeetingGenerator;
import com.jofiagtech.maru.service.MeetingApiServices;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText mMeetingSubject;
    private EditText mMeetingTime;
    private EditText mMeetingPlace;
    private TextView mNumberOfParticipant;
    private EditText mParticipantEmail;
    private Button mAddParticipantButton;
    private Button mSaveMeetingButton;
    private Button mSaveParticipantButton;
    private Button mDateButton;
    private ImageButton mDeleteButton;

    private List<Meeting> mMeetingList;
    private List<Participant> mParticipantList;

    private AlertDialog.Builder mBuilder;
    private AlertDialog mMeetingDialog;
    private AlertDialog mParticipantDialog;

    private int mParticipantCounter = 0;

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private MeetingApiServices mApiServices;

    private Calendar calendar = Calendar.getInstance();
    private final DateFormat dateFormat = DateFormat.getDateTimeInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApiServices = DI.getMeetingApiService();

        mMeetingList = mApiServices.getMeetingList();
        mParticipantList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerViewAdapter = new RecyclerViewAdapter(this, mMeetingList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerViewAdapter.notifyDataSetChanged();

        settingFab();
    }

    private void setUiMeetingReferences(View view){
        mMeetingSubject = view.findViewById(R.id.meeting_subject);
        mMeetingTime = view.findViewById(R.id.meeting_time);
        mMeetingPlace = view.findViewById(R.id.meeting_place);
        mNumberOfParticipant = view.findViewById(R.id.number_of_participant);
        mAddParticipantButton = view.findViewById(R.id.add_prtcp_button);
        mSaveMeetingButton = view.findViewById(R.id.save_meeting_button);
        mDateButton = view.findViewById(R.id.date_button);
    }
    private void setUiParticipantPopupReferences(View view){
        mParticipantEmail = view.findViewById(R.id.participant_email);
        mSaveParticipantButton = view.findViewById(R.id.save_partcp_button);

    }
    private void settingFab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> createMeetingPopup());
    }

    private void createMeetingPopup(){
        mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.meeting_popup, null);

        setUiMeetingReferences(view);
        mDateButton.setText(dateFormat.format(new Date(calendar.getTimeInMillis())));

        mAddParticipantButton.setOnClickListener(this);
        mSaveMeetingButton.setOnClickListener(this);
        mDateButton.setOnClickListener(this);

        mBuilder.setView(view);
        mMeetingDialog = mBuilder.create();// creating our dialog object
        mMeetingDialog.show();// important step!
    }

    private void createParticipantPopup(){
        View view = getLayoutInflater().inflate(R.layout.participant_popup, null);

        setUiParticipantPopupReferences(view);

        mSaveParticipantButton.setOnClickListener(this);

        mBuilder.setView(view);
        mParticipantDialog = mBuilder.create();
        mParticipantDialog.show();
    }

    private Participant saveParticipant(View view) {
        Participant participant = new Participant();

        String email = mParticipantEmail.getText().toString().trim();

        participant.setEmail(email);

        mParticipantCounter++;

        return participant;
    }

    private void saveMeeting(View view){
        Meeting meeting = new Meeting();

        String subject = mMeetingSubject.getText().toString().trim();
        //String time = mMeetingTime.getText().toString().trim();
        String time = calendar.get(calendar.HOUR_OF_DAY) + "h" + calendar.MINUTE;
        String place = mMeetingPlace.getText().toString().trim();

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()).getTime()); // Feb 23, 2020

        meeting.setSubject(subject);
        meeting.setTime(time);
        meeting.setPlace(place);
        meeting.setParticipantList(mParticipantList);
        mParticipantList = new ArrayList<>();
        meeting.setNumberOfParticipant(mParticipantCounter);
        meeting.setDate(formattedDate);

        mMeetingList.add(meeting);
        DummyMeetingGenerator.updateGenerator(mMeetingList);

        Snackbar.make(view, "Meeting saved", Snackbar.LENGTH_SHORT).show();

        //mRecyclerViewAdapter = null;
        mRecyclerViewAdapter = new RecyclerViewAdapter(this, mMeetingList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mParticipantCounter = 0;
                mMeetingDialog.dismiss();
            }
        }, 600);// 0,5 seconde
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.add_prtcp_button:
                    createParticipantPopup();
                break;
            case R.id.delete_button:

                break;
            case R.id.save_meeting_button:
                if (!mMeetingSubject.getText().toString().isEmpty()
                        && !mMeetingTime.getText().toString().isEmpty()
                        && !mMeetingPlace.getText().toString().isEmpty()
                        && mParticipantCounter != 0){
                    saveMeeting(view);
                }
                else
                    Snackbar.make(view, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                break;
            case R.id.save_partcp_button:
                if (!mParticipantEmail.getText().toString().isEmpty()) {
                    mParticipantList.add(saveParticipant(view));

                    mNumberOfParticipant.setText(MessageFormat.format("Nombre de participant: {0}",
                    mParticipantCounter));

                    Snackbar.make(view, "Participant Saved",Snackbar.LENGTH_SHORT)
                    .show();
                    new Handler().postDelayed(() -> mParticipantDialog.dismiss(), 600);//0,5 seconde*/

                }
                else
                    Snackbar.make(view, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();

                break;
            case R.id.date_button:
                DatePickerDialog dateDialog = new DatePickerDialog(this,
                        (datePicker, year, month, dayOfMonth) -> {
                            calendar.set(year, month, dayOfMonth);
                            TimePickerDialog timeDialog = new TimePickerDialog(this,
                                    (timePicker, hourOfDay, minute) -> {
                                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar.set(Calendar.MINUTE, minute);
                                        mDateButton.setText(dateFormat.format(new Date(calendar.getTimeInMillis())));
                                    },
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    true
                            );
                            timeDialog.show();
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dateDialog.show();
        }
    }

    public void initMeetingList(){
        List<Meeting> meetingList = mApiServices.getMeetingList();

        mRecyclerView.setAdapter(new RecyclerViewAdapter(this, meetingList));
    }



    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event){
        mApiServices.deleteMeeting(event.meeting);

        initMeetingList();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
