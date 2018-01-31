package com.example.victor.golfscoreapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HoleAdapter extends RecyclerView.Adapter<HoleAdapter.HoleViewHolder> {

    private Hole[] mHoles;
    private Context mContext;

    public HoleAdapter(Hole[] holes, Context context) {
        mHoles = holes;
        mContext = context;
    }

    @Override
    public HoleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.score_card_list_item, parent, false);

        HoleViewHolder viewHolder = new HoleViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(HoleViewHolder holder, int position) {
        holder.bindHole(mHoles[position]);
    }

    @Override
    public int getItemCount() {
        return mHoles.length;
    }

    public class HoleViewHolder extends RecyclerView.ViewHolder {

        TextView mNameLabel;
        TextView mScoreLabel;
        Button mRaiseScoreButton;
        Button mLowerScoreButton;


        public HoleViewHolder(View itemView) {
            super(itemView);

            mNameLabel = itemView.findViewById(R.id.holeNameTextView);
            mScoreLabel = itemView.findViewById(R.id.scoreTextView);
            mRaiseScoreButton = itemView.findViewById(R.id.raiseScoreButtonView);
            mLowerScoreButton = itemView.findViewById(R.id.lowerScoreButtonView);
        }

        public void bindHole(final Hole hole) {
            mNameLabel.setText(hole.getName());
            mScoreLabel.setText(hole.getScore() + "");
            mRaiseScoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int incScore = hole.getScore() + 1;
                    hole.setScore(incScore++);
                    mScoreLabel.setText(hole.getScore() + "");

                }
            });
            mLowerScoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int score = hole.getScore();

                    if (score <= 0) {
                        return;
                    }

                    int lowScore = hole.getScore() - 1;
                    hole.setScore(lowScore);
                    mScoreLabel.setText(hole.getScore() + "");
                }
            });
        }
    }
}
