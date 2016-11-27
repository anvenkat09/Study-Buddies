package anvenkat.calpoly.edu.studybuddiesv02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kian on 11/26/2016.
 */

public class CalendarSelectedDayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_on_day);
        ListView lv = (ListView) findViewById(R.id.workDay);
        ArrayList<Work>toAdd = getIntent().getExtras().getParcelableArrayList("toAdd");

        ArrayList<String>temp = new ArrayList<String>();

        for(int i = 0; i < toAdd.size(); i++) {
            temp.add(toAdd.get(i).getToDo());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.work_on_day_item, temp);

        lv.setAdapter(aa);


    }
}
