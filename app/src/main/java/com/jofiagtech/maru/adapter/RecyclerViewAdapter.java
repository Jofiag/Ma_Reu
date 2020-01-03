package com.jofiagtech.maru.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jofiagtech.maru.R;
import com.jofiagtech.maru.model.Meeting;
import com.jofiagtech.maru.model.Participant;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private Context mContext;
    private List<Meeting> mMeetingList;
    private List<Participant> mParticipantList;

    public RecyclerViewAdapter(Context context, List<Meeting> meetingList, List<Participant> participantList) {
        mContext = context;
        mMeetingList = meetingList;
        mParticipantList = participantList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meeting_list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetingList.get(position);

        StringBuilder email = new StringBuilder();

        for (Participant participant : mParticipantList){
            email.append(participant.getEmail()).append(", ");
        }

        holder.subject.setText(meeting.getSubject());
        holder.time.setText(meeting.getTime());
        holder.place.setText(meeting.getPlace());
        holder.participantEmail.setText(email);
        holder.date.setText(meeting.getDate());

    }

    @Override
    public int getItemCount()
    {
        return mMeetingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView subject;
        public TextView time;
        public TextView place;
        public TextView participantEmail;
        public TextView date;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            subject = itemView.findViewById(R.id.mlr_subject);
            time = itemView.findViewById(R.id.mlr_time);
            place = itemView.findViewById(R.id.mlr_place);
            participantEmail = itemView.findViewById(R.id.mlr_participant_email);
            date = itemView.findViewById(R.id.mlr_participant_date);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                }
            });
        }
    }
}
