package com.test.fantasycricket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.List;

public class MatchListAdaptor extends ArrayAdapter<Match> {
    private static final String TAG = "MessageListAdaptor";
    private Context mContext;
    private int mResource;

    public MatchListAdaptor(Context context, int resource, List<Match> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(getItem(position)!=null) {

            final String team1 = getItem(position).team1;
            final String team2 = getItem(position).team2;
            String timeleft = getItem(position).timeleft;
            Boolean started = getItem(position).started;
            String type = getItem(position).matchtype;
            String winner_team = getItem(position).winner_team;
            String toss_winner_team = getItem(position).toss_winner;

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            TextView team1tv = convertView.findViewById(R.id.tv_team1);
            TextView team2tv = convertView.findViewById(R.id.tv_team2);
            TextView timelefttv = convertView.findViewById(R.id.tv_timeleft);
            TextView matchstatus = convertView.findViewById(R.id.tv_matchstatus);
            TextView matchtype = convertView.findViewById(R.id.tv_matchtype);
            TextView team1fulltv = convertView.findViewById(R.id.tv_team1_full);
            TextView team2fulltv = convertView.findViewById(R.id.tv_team2_full);
            LinearLayout linearLayout = convertView.findViewById(R.id.ll_matchelement_layout);

            team1tv.setText(Constants.getTeamShortName(team1));
            team2tv.setText(Constants.getTeamShortName(team2));
            team1fulltv.setText(team1);
            team2fulltv.setText(team2);
            timelefttv.setText(timeleft);
            matchtype.setText(type);
            if(started)
            {
                matchstatus.setText("Started");
                if(!toss_winner_team.isEmpty())
                {
                    timelefttv.setText(Constants.getTeamShortName(toss_winner_team)+"\nwon the toss.");
                    timelefttv.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                }
                if(!winner_team.isEmpty() && (winner_team.equals(team1)|| winner_team.equals(team2)))
                {
                    timelefttv.setText(Constants.getTeamShortName(winner_team)+"\nwon the match");
            //        timelefttv.setTextColor(0xFFCA4300);
                    timelefttv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    matchstatus.setText("Finished");
                    matchstatus.setTextColor(0xFF00A70B);
                }
            }
            else
            {
                matchstatus.setText("");
            }
            if(!getItem(position).open)
            {
                linearLayout.setBackgroundColor(Color.parseColor("#E0E0E0"));
            }
            else
            {
//                linearLayout.setBackgroundColor(Color.parseColor("#05a2cffe"));
            }



            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getItem(position)!=null)
                    {

                        if(getItem(position).open)
                        {
                            Intent intent;
                            if(MyMatchesActivity.active)
                            {
                                intent = new Intent(getContext(),MyContestsActivity.class);
                            }
                            else
                            {
                                intent = new Intent(getContext(),ContestActivity.class);
                            }
                            intent.putExtra("team1",team1);
                            intent.putExtra("team2",team2);
                            intent.putExtra("matchid",getItem(position).uniqueid);
                            intent.putExtra("started",getItem(position).started);
                            ContestActivity.matchid = getItem(position).uniqueid;
                            mContext.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(mContext,"Contests will open soon for this match!",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });


        }
        return convertView;

    }



}
