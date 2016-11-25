package anvenkat.calpoly.edu.studybuddiesv02;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A fragment representing a single Class detail screen.
 * This fragment is either contained in a {@link ClassListActivity}
 * in two-pane mode (on tablets) or a {@link ClassDetailActivity}
 * on handsets.
 */
public class ClassDetailFragment extends Fragment {
    private Class c;
    private int index;
    private ListView wView;
    private EditText newWork;
    private Button confirmWork;
    ArrayList<Work> workList;
    private ArrayAdapter<Work> arrayAdapter;

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
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(c.getClassName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        return rootView;
    }
}