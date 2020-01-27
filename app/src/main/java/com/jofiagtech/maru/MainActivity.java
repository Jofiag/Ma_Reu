package com.jofiagtech.maru;

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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    }
    private void setUiParticipantPopupReferences(View view){
        mParticipantEmail = view.findViewById(R.id.participant_email);
        mSaveParticipantButton = view.findViewById(R.id.save_partcp_button);

    }
    private void settingFab(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                createMeetingPopup();
            }
        });
    }

    private void createMeetingPopup(){
        mBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.meeting_popup, null);

        setUiMeetingReferences(view);

        mAddParticipantButton.setOnClickListener(this);
        mSaveMeetingButton.setOnClickListener(this);

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
        String time = mMeetingTime.getText().toString().trim();
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
    protected void onStop()
    {
        EventBus.getDefault().register(this);
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mParticipantDialog.dismiss();
                        }
                    }, 600);//0,5 seconde*/

                }
                else
                    Snackbar.make(view, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();

                break;
        }
    }



    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event){
        mApiServices.deleteMeeting(event.mMeeting);
    }
}
