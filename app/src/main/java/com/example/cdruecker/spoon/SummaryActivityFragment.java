package com.example.cdruecker.spoon;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import greendao.DaoSession;
import greendao.User_entry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SummaryActivityFragment .OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SummaryActivityFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryActivityFragment extends Fragment {

    public ArrayList<String> flist;
    public List<User_entry> foolist;

    public SummaryActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        showFoodEntries(getView());
    }

    private void showFoodEntries(View v) {
        Root root = (Root) getActivity();
        flist = new ArrayList<String>();
        foolist = root.daoSession.getUser_entryDao().queryBuilder().list();
        //flist.add("Breakfast");
        for(int i = 0; i<foolist.size(); i++) {
            flist.add(foolist.get(i).getFood_desc().getName());
        }
        ListView entryList = (ListView) v.findViewById(R.id.breakfastFoods);
        registerForContextMenu(entryList);

        // Create The Adapter with passing ArrayList as 3rd parameter
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, flist);
        System.err.println("Food Entries: " + flist);
        // Set The Adapter
        entryList.setAdapter(arrayAdapter);
        entryList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_activity_summary, container, false);

        // Transaction party
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if(fragmentManager.findFragmentById(R.id.frame1)!=null) {
            ft.remove(fragmentManager.findFragmentById(R.id.frame1));
        }
        Fragment fragment = new SummaryFragment();
        ft.replace(R.id.frame1, fragment).commit();
        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, R.id.edit, 0, R.string.editEntry);
        menu.add(0, R.id.delete, 0, R.string.deleteEntry);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                editEntry(info.position);
                return true;
            case R.id.delete:
                deleteEntry(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void updateSummary() {
        SummaryFragment fragment = (SummaryFragment) getFragmentManager().findFragmentById(R.id.frame1);
        fragment.countCalories();
    }

    public void editEntry(int id) {
        // TODO: Test edit function
        ListView entryList = (ListView) getView().findViewById(R.id.breakfastFoods);
        ListAdapter arrayAdapter = entryList.getAdapter();
        String entry = (String) arrayAdapter.getItem(id);
        Intent i = new Intent(getActivity(), EditEntry.class);
        i.putExtra("pos", "" + id);
        startActivity(i);
    }


    public void deleteEntry(int id) {
        // TODO: Test delete function
        ListView entryList = (ListView) getView().findViewById(R.id.breakfastFoods);
        ListAdapter arrayAdapter = entryList.getAdapter();
        String entry = (String) arrayAdapter.getItem(id);
        foolist.get(id).delete();
        showFoodEntries(getView());
        updateSummary();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Transaction party
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.frame1);

        ft.remove(fragment).commit();
    }

}
