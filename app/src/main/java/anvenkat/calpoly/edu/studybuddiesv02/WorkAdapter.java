package anvenkat.calpoly.edu.studybuddiesv02;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by aniru on 11/21/2016.
 */

public class WorkAdapter extends ArrayAdapter<Work> implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private ArrayList<Work> workList;
    private int day = 0, year = 0, month = 0;
    private int second = 0, minute = 0, hour = 0;
    private int id;
    private AlarmManager alarm;
    private TextView workName;
    PendingIntent intentV;
    Intent intent;
    private ImageButton setDT, setAlarm, cancelAlarm;

    public WorkAdapter(Context context, ArrayList<Work> workList){
        super(context, 0, workList);
        this.workList = workList;
    }

    @Override
    public Work getItem(int position){
        return workList.get(position);
    }

    @Override
    public int getCount(){
        return workList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        id = position;
        LayoutInflater inf = LayoutInflater.from(getContext());
        View menuL = inf.inflate(R.layout.class_detail_workview_detail, parent, false);

        workName = (TextView)menuL.findViewById(R.id.workName);
        CheckBox workBox = (CheckBox)menuL.findViewById(R.id.workCompleted);
        setDT = (ImageButton)menuL.findViewById(R.id.setDateAndTime);
        setAlarm = (ImageButton)menuL.findViewById(R.id.alarm);
        cancelAlarm = (ImageButton)menuL.findViewById(R.id.cancel);

        final Work w = getItem(position);
        workName.setText(w.getToDo().toString());

        workBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                w.setCompleted(isChecked);
            }
        });
        workBox.setChecked(w.getCompleted());

        /**
         * calendar to set the date and time for when the assignment is due.
         */
        setDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar date = Calendar.getInstance();
                DatePickerDialog dateChooser = new DatePickerDialog(getContext(), WorkAdapter.this,
                        date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                dateChooser.show();
            }
        });

        /**
         * sets the alarm for the specified time
         */
        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DATE, day);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.YEAR, year);

                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, second);
                Toast.makeText(getContext(), "alarm set to: " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

                intent = new Intent(getContext(), NotificationBroadcaster.class);
                intent.putExtra("nameofwork", workName.getText().toString());
                intentV = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarm = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), intentV);
            }
        });

        /**
         * cancels the specific pending intent
         */
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
            }
        });

        return menuL;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        month = i1;
        year = i;
        day = i2;
        Calendar date = Calendar.getInstance();
        TimePickerDialog timePicker = new TimePickerDialog(getContext(), WorkAdapter.this,
                date.get(Calendar.HOUR), date.get(Calendar.MINUTE), true);
        timePicker.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        minute = i1;
        second = 0;
        hour = i;
    }
}
