package main;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class prova_week {

    public static void main(String[] args){
        Calendar calendar = new GregorianCalendar();
        Date date = new Date();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        System.out.println("Week number:" + calendar.get(Calendar.WEEK_OF_YEAR));
    }
}
