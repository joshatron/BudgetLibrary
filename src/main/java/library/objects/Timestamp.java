package library.objects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Class to store information about timestamp
 * Will be able to import and export in several formats
 */
public class Timestamp {
    private long timestamp;

    public Timestamp() {
        timestamp = -1;
    }

    public Timestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp(String timestamp) {
        this.timestamp = stringToLong(timestamp);
    }

    public long getTimestampLong() {
        return this.timestamp;
    }

    public String getTimestampString() {
        return longToString(this.timestamp);
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = stringToLong(timestamp);
    }

    //Expects format YYYY-MM-DD HH:MM:SS
    private static long stringToLong(String timestamp) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = dateFormat.parse(timestamp);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Outputs format YYYY-MM-DD HH:MM:SS
    private static String longToString(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp * 1000L);
        return sdf.format(date);
    }
}
