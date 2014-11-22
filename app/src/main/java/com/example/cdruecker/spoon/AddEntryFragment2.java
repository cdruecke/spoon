package com.example.cdruecker.spoon;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition.StringCondition;
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
 * {@link AddEntryFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEntryFragment2#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AddEntryFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int lpos;
    private String mParam1;
    private String mParam2;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private List<Food_desc> fetchList;
    private List<Weight> fetchServ;
    private ArrayList<String> foodItems;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEntryFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEntryFragment2 newInstance(String param1, String param2) {
        AddEntryFragment2 fragment = new AddEntryFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AddEntryFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_entry2, container, false);

        v.post(new Runnable() {
            public void run() {
                Button addEntryButton = (Button) getActivity().findViewById(R.id.sendAddEntry);
                addEntryButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        submitEntry();
                    }
                });
                TextView searchField = (TextView) getActivity().findViewById(R.id.searchForFood);
                searchField.addTextChangedListener(new TextWatcher() {

                    public void afterTextChanged(Editable s) {
                        getFoodNames();
                    }

                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {

                    }
                });
                ListView myList = (ListView) getActivity().findViewById(R.id.list);
                myList.setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                        lpos = position;
                        getFoodWeights();
                    }
                });
            }
        });

        return v;
    }

 /* Removed because I added the listener to v.post
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button addEntryButton = (Button) getActivity().findViewById(R.id.sendAddEntry);
        if(addEntryButton!=null) {
            addEntryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /T/ODO: Bug that happens if you click too fast
                    submitEntry();
                }
            });
        } else {
            System.err.println("Button is magically not existing right now for some reason");
        }

    } */

    private void getFoodNames() {
        // Fetch names of food.
        // TODO: Try not to make DaoMasters for every Fragment
        DatabaseOpenHelper helper = new DatabaseOpenHelper(getActivity(), "food-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        Food_descDao fdescDao = daoSession.getFood_descDao();
        QueryBuilder.LOG_VALUES = true;
        QueryBuilder.LOG_SQL = true;
        // TODO: incorporate better fuzzy matching bc this one sucks
        TextView searchField = (TextView) getActivity().findViewById(R.id.searchForFood);
        QueryBuilder query = fdescDao.queryBuilder();
        String[] keywords = searchField.getText().toString().split(" ");
        for (int i = 0; i<keywords.length; i++) {
            query.where(new StringCondition(" NAME LIKE '%" + keywords[i] + "%' "));
        }
        query.build();

        List<Food_desc> test = query.list();
                //fdescDao.queryBuilder()
                //.where(Food_descDao.Properties.Name.).limit(20).list();

        try {
            foodItems = new ArrayList<String>();
            for (int i = 0; i < test.size(); i++) {
                foodItems.add(test.get(i).getName());
            }
        } catch (NullPointerException e) {
            foodItems.clear();
            System.out.println("NO SUCH FOOD");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, foodItems);
            ListView textView = (ListView)
                    getActivity().findViewById(R.id.list);
            textView.setAdapter(adapter);
        getFoodWeights();

    }

    private void getFoodWeights() {
        System.out.println("Updating weights");
        ArrayList<String> servItems = new ArrayList<String>();
        try {
            Food_descDao fdescDao = daoSession.getFood_descDao();
            ListView textView = (ListView)
                    getActivity().findViewById(R.id.list);
            String selectName = textView.getItemAtPosition(lpos).toString();
            // Get the name that is selected currently and get its Dao object.
            fetchList = fdescDao.queryBuilder()
                    .where(Food_descDao.Properties.Name.eq(selectName)).list();

            WeightDao weightDao = daoSession.getWeightDao();
            fetchServ = weightDao.queryBuilder()
                    .where(WeightDao.Properties.NDB_no.eq(
                            fetchList.get(0).getNDB_no())).list();
            for (int i = 0; i<fetchServ.size(); i++) {
                servItems.add(fetchServ.get(i).getName());
            }

        } catch (NullPointerException e) {
            System.out.println("NO FOOD SELECTED");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("THERE'S NO FOOD AVAILABLE");
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line, servItems);
        Spinner textView2 = (Spinner) getActivity().findViewById(R.id.addFoodAmounts);
        textView2.setAdapter(adapter2);
    }

    // TODO: Test that user entries are properly added.
    // TODO: don't error out if people don't input all fields in
    public void submitEntry() {

        Food_descDao fd = daoSession.getFood_descDao();
        WeightDao wd = daoSession.getWeightDao();
        User_entryDao ud = daoSession.getUser_entryDao();

        try {
            ListView ft = (ListView) getView().findViewById(R.id.list);
            Spinner wt = (Spinner) getView().findViewById(R.id.addFoodAmounts);
            TextView at = (TextView) getView().findViewById(R.id.addFoodAmount);

            float amt = Integer.parseInt(at.getText().toString());

            Date date = new Date();

            List<Food_desc> listfooddesc = fd.queryBuilder()
                    .where(Food_descDao.Properties.Name.eq(
                            ft.getItemAtPosition(lpos).toString())).limit(1).list();

            long ndbno = listfooddesc.get(0).getNDB_no();

            List<Weight> listweight = wd.queryBuilder()
                    .where(WeightDao.Properties.Name.eq(
                            wt.getSelectedItem().toString())).limit(1).list();
            long wid = listweight.get(0).getW_id();

            User_entry ue = new User_entry(null, date, amt, wid, ndbno);

            ud.insert(ue);
        } catch (NumberFormatException e) {
            System.out.println("Lol");
            // TODO: Toast that explains what went wrong
        }

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
