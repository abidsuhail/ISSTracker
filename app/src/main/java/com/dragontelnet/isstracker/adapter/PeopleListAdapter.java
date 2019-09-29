package com.dragontelnet.isstracker.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dragontelnet.isstracker.R;
import com.dragontelnet.isstracker.models.AstroPeople;
import com.dragontelnet.isstracker.ui.MapsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ViewHolder>
         {
    private List<AstroPeople> mPeopleList=null;
    private static final String TAG = "PeopleListAdapter";

    public void onListLoaded(List<AstroPeople> astroPeopleList) {
        mPeopleList=astroPeopleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PeopleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.people_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleListAdapter.ViewHolder holder, int position) {

        AstroPeople astroPeople=mPeopleList.get(position);
        holder.peopleNameTv.setText(astroPeople.getName());
        holder.peopleCraftTv.setText(astroPeople.getCraft());

    }

    @Override
    public int getItemCount() {
        if (mPeopleList!=null)
        {
            return mPeopleList.size();
        }
        else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.people_craft)
        TextView peopleCraftTv;
        @BindView(R.id.people_name)
        TextView peopleNameTv;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
