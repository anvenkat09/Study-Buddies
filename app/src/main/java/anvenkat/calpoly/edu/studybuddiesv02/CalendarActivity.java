package anvenkat.calpoly.edu.studybuddiesv02;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;

import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Calendar Class. Allows for specific day to be selected on calendar.
 * @Author: Kian
 */

public class CalendarActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);

        Bundle extras = getIntent().getExtras();

        ArrayList<Class> classes = extras.getParcelableArrayList("classes");
        Log.e("?", classes.get(0).getClassName());

        //classes.get(0).getWork().get(0).g

        ExtendedCalendarView calendar = (ExtendedCalendarView)findViewById(R.id.calendar);
        ContentValues values = new ContentValues();
        values.put(CalendarProvider.COLOR, Event.COLOR_RED);

        values.put(CalendarProvider.DESCRIPTION, "Some Description");
        values.put(CalendarProvider.LOCATION, "Some location");
        values.put(CalendarProvider.EVENT, "Event name");
        Calendar cal = Calendar.getInstance();
        cal.set(2016,9,16);
        values.put(CalendarProvider.START, cal.getTimeInMillis());
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2016,9,16);
        TimeZone tz = TimeZone.getDefault();

        int startDay = Time.getJulianDay(cal.getTimeInMillis(),TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        Date time = gc.getTime();

        values.put(CalendarProvider.START_DAY, startDay);



        int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

        values.put(CalendarProvider.END, cal.getTimeInMillis());
        values.put(CalendarProvider.END_DAY, endDayJulian);

        Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);

    }
}