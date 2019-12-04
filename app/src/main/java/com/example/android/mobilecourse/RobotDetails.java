package com.example.android.mobilecourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.util.Objects;

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
        }
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

    private void initViews(){
        robotName = findViewById(R.id.details_name);
        robotCode = findViewById(R.id.details_code);
        address = findViewById(R.id.details_address);
        time = findViewById(R.id.details_time);
        trajectory = findViewById(R.id.details_trajectory);
        imageUrl = findViewById(R.id.details_image_view);
    }
}
