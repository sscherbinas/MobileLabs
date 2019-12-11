package com.example.android.mobilecourse;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RobotDetails extends AppCompatActivity {

    private TextView robotName;
    private TextView robotCode;
    private TextView address;
    private TextView time;
    private TextView trajectory;
    private ImageView imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_details);

        getIncomingIntent();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("robot_name") &&
                getIntent().hasExtra("robot_code") &&
                getIntent().hasExtra("address") &&
                getIntent().hasExtra("time") &&
                getIntent().hasExtra("trajectory")) {
            String robotNameInfo = getIntent().getStringExtra("robot_name");
            String robotCodeInfo = getIntent().getStringExtra("robot_code");
            String addressInfo = getIntent().getStringExtra("address");
            String timeInfo = getIntent().getStringExtra("time");
            String trajectoryInfo = getIntent().getStringExtra("trajectory");
            String imageUrlInfo = getIntent().getStringExtra("image");

            setInfo(robotNameInfo, robotCodeInfo, addressInfo, timeInfo, trajectoryInfo, imageUrlInfo);
        } else if (getIntent().hasExtra("notification_robot_code") &&
                getIntent().hasExtra("notification_message")) {
            String robotCode = getIntent().getStringExtra("notification_robot_code");
            String message = getIntent().getStringExtra("notification_message");
            showInfoFromNotification(robotCode, message);
        }
    }

    private void showInfoFromNotification(String robotCode, String message) {
        loadRobotsAndShowWithCode(robotCode);
        showMessage(message);
    }

    private void loadRobotsAndShowWithCode(String robotCode) {
        final ApiService apiService = getApplicationEx().getApiService();
        final Call<List<Robot>> call = apiService.getRobots();

        call.enqueue(new Callback<List<Robot>>() {
            @Override
            public void onResponse(@NonNull final Call<List<Robot>> call,
                                   @NonNull final Response<List<Robot>> response) {
                if (response.body() != null) {
                    Robot robot = getRobotByCode(response.body(), robotCode);
                    if (robot != null) {
                        showRobotDetails(robot);
                    } else {
                        Snackbar.make(getWindow().getDecorView(), R.string.failure, Snackbar.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Robot>> call, @NonNull Throwable t) {
                Snackbar.make(getWindow().getDecorView(), R.string.failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void showRobotDetails(Robot robot) {
        setInfo(robot.getRobotName(),
                robot.getRobotCode(),
                robot.getAddress(),
                robot.getTime(),
                robot.getTrajectory(),
                robot.getPhotoUrl());
    }

    private Robot getRobotByCode(List<Robot> robots, String code) {
        for (int i = 0; i < robots.size(); i++) {
            if (robots.get(i).getRobotCode().equals(code)) {
                return robots.get(i);
            }
        }
        return null;
    }

    private void showMessage(String message) {
        if (message != null && !message.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message)
                    .setTitle("Message")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void initViews() {
        robotName = findViewById(R.id.robot_details_name);
        robotCode = findViewById(R.id.robot_details_code);
        address = findViewById(R.id.robot_details_address);
        time = findViewById(R.id.robot_details_time);
        trajectory = findViewById(R.id.robot_details_trajectory);
        imageUrl = findViewById(R.id.robot_details_image_view);
    }

    private void setInfo(String robotNameInfo, String robotCodeInfo,
                         String addressInfo, String timeInfo,
                         String trajectoryInfo, String imageUrlInfo) {
        initViews();
        robotName.setText(robotNameInfo);
        robotCode.setText(robotCodeInfo);
        address.setText(addressInfo);
        time.setText(timeInfo);
        trajectory.setText(trajectoryInfo);
        Picasso.get().load(imageUrlInfo).into(imageUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(RobotDetails.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ApplicationEx getApplicationEx() {
        return (ApplicationEx) this.getApplication();
    }
}
