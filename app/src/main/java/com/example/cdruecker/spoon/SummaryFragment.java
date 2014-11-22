package com.example.cdruecker.spoon;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.*;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.Nutr_data;
import greendao.Nutr_dataDao;
import greendao.Nutr_def;
import greendao.Nutr_defDao;
import greendao.User_entry;
import greendao.User_entryDao;

// Fragment that specifies the amount of calories you have eaten today
// OR provides a suggestion for your next food intake.
// Also has a button to add to your diary

/**
 **A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 **{@link SummaryFragment.OnFragmentInteractionListener} interface
 **to handle interaction events.
 * Use the {@link SummaryFragment#newInstance} factory method to
 **create an instance of this fragment.
 **/

public class SummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);

    }

    @Override
    public void onResume() {
        super.onResume();
        setAddEntryListener();
        setSummaryField();
    }

    private void setSummaryField() {
        TextView summary = (TextView) getView().findViewById(R.id.summaryField);
        if (Root.condition==0)
            summary.setText("" + countCalories());
        else if (Root.condition==1)
            summary.setText("" + countMissingNutrients());
        else
            summary.setText("ERROR");
    }

    public int countMissingNutrients() {
        Root root = (Root) getActivity();

        root.daoSession.clear();
        QueryBuilder<User_entry> elist = root.daoSession.getUser_entryDao().queryBuilder();
        LazyList<User_entry> flist = elist.listLazyUncached();
        // TODO: Change this to find minimum nutrient, then display total.
        Query query = root.daoSession.getNutr_dataDao().queryBuilder().where(Nutr_dataDao.Properties.Nutr_no.eq(208), Nutr_dataDao.Properties.NDB_no.eq(0)).build();
        float totalCalories = 0f;
        for (int i = 0; i < flist.size(); i++) {
            query.setParameter(1, flist.get(i).getNDB_no());
            List<Nutr_data> stuff = query.list();
            System.out.println(flist.get(i).getServ_amt());
            totalCalories += stuff.get(0).getNutrient_amt() / 100 * flist.get(i).getServ_amt() * ((float) (flist.get(i).getWeight().getGram_weight()) / flist.get(i).getWeight().getAmount());
        }
        flist.close();
        return (int) totalCalories;
    }
    public int countCalories() {
        Root root = (Root) getActivity();

        root.daoSession.clear();
        QueryBuilder<User_entry> elist = root.daoSession.getUser_entryDao().queryBuilder();
        LazyList<User_entry> flist = elist.listLazyUncached();
        Query query = root.daoSession.getNutr_dataDao().queryBuilder().where(Nutr_dataDao.Properties.Nutr_no.eq(208), Nutr_dataDao.Properties.NDB_no.eq(0)).build();
        float totalCalories = 0f;
        for (int i = 0; i < flist.size(); i++) {
            query.setParameter(1, flist.get(i).getNDB_no());
            List<Nutr_data> stuff = query.list();
            System.out.println(flist.get(i).getServ_amt());
            totalCalories += stuff.get(0).getNutrient_amt() / 100 * flist.get(i).getServ_amt() * ((float) (flist.get(i).getWeight().getGram_weight()) / flist.get(i).getWeight().getAmount());
        }
        flist.close();
        return (int) totalCalories;
    }

    private void openAddEntry() {
        Intent i = new Intent(getActivity(), AddEntry.class);
        startActivity(i);
    }

    private void setAddEntryListener() {
        // TODO: Have this post to view instead
        Button addEntryButton = (Button) getActivity().findViewById(R.id.addDiaryEntry);
        if(addEntryButton!=null) {
            addEntryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openAddEntry();
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
        View v = inflater.inflate(R.layout.fragment_summary, container, false);


        return v;
    }

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    } */

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
        // TODO: Implement OnFragmentInteractionListener
        public void onFragmentInteraction(Uri uri);
    }

}
