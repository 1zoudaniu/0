package safebox.yiye.com.safebox.http;

/**
 * Name: CarIndexSingleModel
 * Author: aina
 * Email:
 * Comment: //TODO
 * Date: 2016-11-22 10:20
 */
public class CarIndexSingleModel {
    private String time;
    private String score;
    private double latitude;
    private String status;
    private double longitude;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CarIndexSingleModel{" +
                "time='" + time + '\'' +
                ", score='" + score + '\'' +
                ", latitude=" + latitude +
                ", status='" + status + '\'' +
                ", longitude=" + longitude +
                '}';
    }
}
