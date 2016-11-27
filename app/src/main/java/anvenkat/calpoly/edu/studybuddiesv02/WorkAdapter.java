package anvenkat.calpoly.edu.studybuddiesv02;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
 *
 */

public class WorkAdapter extends ArrayAdapter<Work> implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    private ArrayList<Work> workList;
    private int day = 0, year = 0, month = 0;
    private int second = 0, minute = 0, hour = 0;
    private AlarmManager alarm;
    private TextView workName;
    private String workNameTag;
    private int positionTag, cancelTag;
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
        LayoutInflater inf = LayoutInflater.from(getContext());
        View menuL = inf.inflate(R.layout.class_detail_workview_detail, parent, false);

        workName = (TextView)menuL.findViewById(R.id.workName);
        CheckBox workBox = (CheckBox)menuL.findViewById(R.id.workCompleted);

        setDT = (ImageButton)menuL.findViewById(R.id.setDateAndTime);
        setDT.setTag(position);

        cancelAlarm = (ImageButton)menuL.findViewById(R.id.cancel);
        cancelAlarm.setTag(position);

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
                positionTag = (Integer)view.getTag();
                final Work w = getItem(positionTag);
                workNameTag = w.getToDo().toString();
                Calendar date = Calendar.getInstance();
                DatePickerDialog dateChooser = new DatePickerDialog(getContext(), WorkAdapter.this,
                        date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                dateChooser.show();
            }
        });
        /**
         * cancels the specific pending intent
         */
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTag = (Integer)view.getTag();
                intent = new Intent(getContext(), NotificationBroadcaster.class);
                final PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), cancelTag, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent != null) {
                    pendingIntent.cancel();
                }
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

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        workList.get(positionTag).setDay(day);
        workList.get(positionTag).setMonth(month);
        workList.get(positionTag).setYear(year);

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        Toast.makeText(getContext(), "alarm set to: " + hour + ":" + minute, Toast.LENGTH_SHORT).show();

        intent = new Intent(getContext(), NotificationBroadcaster.class);
        intent.putExtra("nameofwork", workNameTag);
        intent.putExtra("id", positionTag);
        intentV = PendingIntent.getBroadcast(getContext(), positionTag, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarm = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), intentV);
        } else {
            alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), intentV);
        }
    }

    public ArrayList<Work> getAlarms(){
        return workList;
    }
}
