package anvenkat.calpoly.edu.studybuddiesv02;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A fragment representing a single Class detail screen.
 * This fragment is either contained in a {@link ClassListActivity}
 * in two-pane mode (on tablets) or a {@link ClassDetailActivity}
 * on handsets.
 */
public class ClassDetailFragment extends ContractFragment<ClassDetailFragment.CallMain>  {
    private Class c;
    private int index;
    private ListView wView;
    private EditText newWork;
    private Button confirmWork;
    ArrayList<Work> workList;
    private WorkAdapter arrayAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        c = getArguments().getParcelable("class");
        index = getArguments().getInt("index");
        workList = c.getWork();
        arrayAdapter = new WorkAdapter(getContext(), workList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * prints name of class at top and persists on rotate.
         */
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(c.getClassName());
        }
        View rootView = inflater.inflate(R.layout.class_detail, container, false);
        wView = (ListView) rootView.findViewById(R.id.workView);
        newWork = (EditText) rootView.findViewById(R.id.addNewWork);
        confirmWork = (Button) rootView.findViewById(R.id.confirmWork);
        wView.setAdapter(arrayAdapter);

        confirmWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameOfWork = newWork.getText().toString();
                if (nameOfWork.length() > 0) {
                    Work w = new Work();
                    w.setToDo(nameOfWork);
                    w.setCompleted(false);
                    arrayAdapter.add(w);
                    getContract().setWork(workList, index);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onPause() {
        Log.e("?", "Test");
        ArrayList<Work> alarms = arrayAdapter.getAlarms();
        //This is kind of a back hack because the day/month/year is being done in the work adapter instead of this class
        //There is no other way to communicate between the adapter and list
        //Thanks Obama..
        for(int i = 0; i < alarms.size(); i++) {
            workList.get(i).setDay(alarms.get(i).day);
            workList.get(i).setMonth(alarms.get(i).month);
            workList.get(i).setYear(alarms.get(i).year);
        }

        //Log.i("yo", "2");
        getContract().setWork(workList, index);
        arrayAdapter.notifyDataSetChanged();

        super.onPause();
    }

    public interface CallMain {
        public void setWork(ArrayList<Work> toSet, int index);

    }
}