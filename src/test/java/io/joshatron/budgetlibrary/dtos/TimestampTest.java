package io.joshatron.budgetlibrary.dtos;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TimestampTest {

    @Test
    public void testSetTimestampLongGetTimestampString() throws Exception {
        Timestamp timestamp = new Timestamp();
        timestamp.setTimestamp(1504249200);
        Assert.assertEquals(timestamp.getTimestampString(), "2017-09-01");
    }

    @Test
    public void testSetTimestampStringGetTimestampLong() throws Exception {
        Timestamp timestamp = new Timestamp();
        timestamp.setTimestamp("2017-09-01");
        Assert.assertEquals(timestamp.getTimestampLong(), 1504249200);
    }

}
