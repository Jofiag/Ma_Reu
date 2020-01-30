package com.jofiagtech.maru.adapter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.jofiagtech.maru.R;
import com.jofiagtech.maru.event.DeleteMeetingEvent;
import com.jofiagtech.maru.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private Context mContext;
    private List<Meeting> mMeetingList;

    public RecyclerViewAdapter(Context context, List<Meeting> meetingList) {
        mContext = context;
        mMeetingList = meetingList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DateFormat dateFormat = DateFormat.getDateTimeInstance();
        final Meeting meeting = mMeetingList.get(position);
        String string = "";

        for (int i = 0; i < meeting.getParticipantList().size(); i++) {
            String email = "";

            if (i == meeting.getParticipantList().size() - 1)
                email = meeting.getParticipantList().get(i).getEmail();
            else
                email = meeting.getParticipantList().get(i).getEmail() + ", ";

            string = string + email;
        }

        holder.meetingDetails.setText(String.format("%s - %s - %s", meeting.getSubject(), meeting.getTime(), meeting.getPlace()));
        holder.participantEmail.setText(string);

        String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()).getTime()); // Feb 23, 2020

        if (meeting.getDate() != null){
            if (!meeting.getDate().equals(formattedDate))
                holder.date.setText(meeting.getDate());
        }

        holder.deleteButton.setOnClickListener(v -> {
            EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            /*mMeetingList.remove(meeting);
            new RecyclerViewAdapter(mContext, mMeetingList);*/
            Log.d("TD", "onClick: ");
        });
    }

    @Override
    public int getItemCount()
    {
        return mMeetingList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ConstraintLayout constraintLayout;
        TextView meetingDetails;
        TextView participantEmail;
        TextView date;
        ImageButton deleteButton;
        Button dateButton;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        ViewHolder(@NonNull final View itemView)
        {
            super(itemView);

            meetingDetails = itemView.findViewById(R.id.mlr_title);
            participantEmail = itemView.findViewById(R.id.mlr_participant_email);
            date = itemView.findViewById(R.id.mlr_participant_date);
            deleteButton = itemView.findViewById(R.id.delete_button);
            dateButton = itemView.findViewById(R.id.date_button);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);

            meetingDetails.setOnClickListener(v -> {
                if (meetingDetails.getMaxLines() == 1)
                    meetingDetails.setMaxLines(10);
                else
                    meetingDetails.setMaxLines(1);
            });

            participantEmail.setOnClickListener(v -> {
                if (participantEmail.getMaxLines() == 1)
                    participantEmail.setMaxLines(10);
                else
                    participantEmail.setMaxLines(1);
            });
        }
    }
}
