package com.example.android.mobilecourse;

public class Robot {
    private final String robotName;
    private final String robotCode;
    private final String address;
    private final String time;
    private final String trajectory;
    private final String photoUrl;

    public Robot(final String robotName, final String robotCode, final String address,
                 final String time, final String trajectory, final String photoUrl) {
        this.robotName = robotName;
        this.robotCode = robotCode;
        this.address = address;
        this.time = time;
        this.trajectory = trajectory;
        this.photoUrl = photoUrl;
    }

    public String getRobotName() {
        return robotName;
    }

    public String getRobotCode() {
        return robotCode;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getTrajectory() {
        return trajectory;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}