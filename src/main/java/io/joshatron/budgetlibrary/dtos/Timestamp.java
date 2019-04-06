package io.joshatron.budgetlibrary.dtos;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Timestamp {
    @Column
    private long timestamp;

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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(timestamp);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //Outputs format YYYY-MM-DD HH:MM:SS
    private static String longToString(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp * 1000L);
        return sdf.format(date);
    }
}
