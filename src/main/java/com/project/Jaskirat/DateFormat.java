package com.project.Jaskirat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    public static String formatData(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(date);
    }
}
