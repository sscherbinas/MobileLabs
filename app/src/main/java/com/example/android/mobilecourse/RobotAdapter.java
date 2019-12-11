package com.example.android.mobilecourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RobotAdapter extends RecyclerView.Adapter<RobotAdapter.RobotViewHolder> {
    private List<Robot> robotList;
    private DataListFragment.OnRobotClickListener robotClickListener;

    RobotAdapter(DataListFragment.OnRobotClickListener clickListener, List<Robot> robotList) {
        this.robotList = robotList;
        this.robotClickListener = clickListener;
    }

    @NonNull
    @Override
    public RobotAdapter.RobotViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                           final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_robot, parent, false);
        return new RobotViewHolder(itemView);
    }

    public void onBindViewHolder(final RobotViewHolder holder,
                                 final int position) {
        Picasso.get()
                .load(robotList.get(position).getPhotoUrl())
                .into(holder.photoUrl);

        holder.robotName.setText(robotList.get(position).getRobotName());
        holder.robotCode.setText(robotList.get(position).getRobotCode());
        holder.address.setText(robotList.get(position).getAddress());
        holder.time.setText(robotList.get(position).getTime());
        holder.trajectory.setText(robotList.get(position).getTrajectory());

        holder.itemView.setOnClickListener(view -> robotClickListener.onClick(robotList.get(position)));
    }

    @Override
    public int getItemCount() {
        return robotList.size();
    }

    class RobotViewHolder extends RecyclerView.ViewHolder {

        private TextView robotName;
        private TextView robotCode;
        private TextView address;
        private TextView time;
        private TextView trajectory;
        private ImageView photoUrl;

        private RobotViewHolder(final View itemView) {
            super(itemView);

            photoUrl = itemView.findViewById(R.id.item_robot_image_view);
            robotName = itemView.findViewById(R.id.item_robot_name);
            robotCode = itemView.findViewById(R.id.item_robot_code);
            address = itemView.findViewById(R.id.item_robot_address);
            time = itemView.findViewById(R.id.item_robot_time);
            trajectory = itemView.findViewById(R.id.item_robot_trajectory);
        }
    }
}
