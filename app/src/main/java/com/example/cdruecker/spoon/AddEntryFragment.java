package com.example.cdruecker.spoon;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Food_desc;
import greendao.Food_descDao;
import greendao.User_entry;
import greendao.User_entryDao;
import greendao.Weight;
import greendao.WeightDao;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEntryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private List<Food_desc> fetchList;
    private List<Weight> fetchServ;
    private ArrayList<String> foodItems;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEntryFragment newInstance(String param1, String param2) {
        AddEntryFragment fragment = new AddEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AddEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button addEntryButton = (Button) getActivity().findViewById(R.id.sendAddEntry);
        if(addEntryButton!=null) {
            addEntryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO: Bug that happens if you click too fast
                    submitEntry();
                }
            });
        } else {
            System.err.println("Button is magically not existing right now for some reason");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_entry, container, false);

        v.post(new Runnable() {
            public void run() {
                getFoodNames();
            }
        });

        return v;
    }

    private void getFoodNames() {
        // TODO: Figure out how to defer this stuff until after the activity opens
        // Fetch names of food.
        // TODO: Try not to make DaoMasters for every Fragment
        DatabaseOpenHelper helper = new DatabaseOpenHelper(getActivity(), "food-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        Food_descDao fdescDao = daoSession.getFood_descDao();
        QueryBuilder.LOG_VALUES = true;
        QueryBuilder.LOG_SQL = true;

        // TODO: not have a limit of 100.
        // TODO: incorporate better fuzzy matching bc the default one sucks
        // TODO: have the serving amounts come from the selected name

        List<Food_desc> test = fdescDao.queryBuilder()
                .limit(100).list();
        System.out.println(test.size());
        foodItems = new ArrayList<String>();
        for (int i = 0; i<test.size(); i++) {
            foodItems.add(test.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, foodItems);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                getActivity().findViewById(R.id.addFoodNames);
        textView.setAdapter(adapter);

        // Get the name that is selected currently and get its NDB_No
        fetchList = fdescDao.queryBuilder()
                .where(Food_descDao.Properties.NDB_no.eq("1001")).list();
        // Get serving sizes from that info
        WeightDao weightDao = daoSession.getWeightDao();
        fetchServ = weightDao.queryBuilder()
                .where(WeightDao.Properties.NDB_no.eq(
                        fetchList.get(0).getNDB_no())).list();
        ArrayList<String> servItems = new ArrayList<String>();

        for (int i = 0; i<fetchServ.size(); i++) {
            servItems.add(fetchServ.get(i).getName());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, servItems);
        AutoCompleteTextView textView2 = (AutoCompleteTextView) getActivity().findViewById(R.id.addFoodAmounts);
        textView2.setAdapter(adapter2);
    }

    // TODO: Test that user entries are properly added.
    public void submitEntry() {

        Food_descDao fd = daoSession.getFood_descDao();
        WeightDao wd = daoSession.getWeightDao();
        User_entryDao ud = daoSession.getUser_entryDao();

        TextView ft = (TextView) getView().findViewById(R.id.addFoodNames);
        TextView wt = (TextView) getView().findViewById(R.id.addFoodAmounts);
        TextView at = (TextView) getView().findViewById(R.id.addFoodAmount);

        float amt = Integer.parseInt(at.getText().toString());

        Date date = new Date();

        List<Food_desc> listfooddesc = fd.queryBuilder()
                .where(Food_descDao.Properties.Name.eq(
                        ft.getText())).limit(1).list();
        long ndbno = listfooddesc.get(0).getNDB_no();

        List<Weight> listweight = wd.queryBuilder()
                .where(WeightDao.Properties.Name.eq(
                        wt.getText())).limit(1).list();
        long wid = listweight.get(0).getW_id();

        User_entry ue = new User_entry(null, date, amt, wid, ndbno);

        ud.insert(ue);

        getActivity().finish();

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
