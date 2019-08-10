package io.joshatron.budgetlibrary.dtos;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
@Embeddable
public class Timestamp {
    @Column(nullable = false)
    private long timestamp;

    public Timestamp() {
        timestamp = 0;
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
        Parser parser = new Parser();
        List<DateGroup> list = parser.parse(timestamp);
        return list.get(0).getDates().get(0).getTime() / 1000;
    }

    //Outputs format YYYY-MM-DD HH:MM:SS
    private static String longToString(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timestamp * 1000L);
        return sdf.format(date);
    }

    @Override
    public String toString() {
        return longToString(timestamp);
    }
}
