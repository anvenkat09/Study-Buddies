package anvenkat.calpoly.edu.studybuddiesv02;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by aniru on 11/21/2016.
 */

public class WorkAdapter extends ArrayAdapter<Work> {
    private ArrayList<Work> workList;

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

        TextView workName = (TextView)menuL.findViewById(R.id.workName);
        CheckBox workBox = (CheckBox)menuL.findViewById(R.id.workCompleted);

        final Work w = getItem(position);
        workName.setText(w.getToDo().toString());

        workBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                w.setCompleted(isChecked);
            }
        });
        workBox.setChecked(w.getCompleted());

        return menuL;
    }
}
