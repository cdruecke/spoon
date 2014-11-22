package com.example.cdruecker.spoon;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.util.DebugUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.*;

public class Root extends Activity {
    Fragment fragmentTab1;
    Fragment fragmentTab2;
    Fragment fragmentTab3;
    Fragment fragmentTab4;
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public static int condition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("App is loading");
        setContentView(R.layout.activity_root);

        // Copy DB into path usable by application
        InputStream is = null;
        try {
            is = getAssets().open("food-db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(is);

        // Open the database
        DatabaseOpenHelper helper = new DatabaseOpenHelper(this, "food-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        // Debug: Testing to check if database has the right stuff
        if(Debug.isDebuggerConnected()) {
            try {
                WeightDao weightDao = daoSession.getWeightDao();
                QueryBuilder.LOG_VALUES = true;
                QueryBuilder.LOG_SQL = true;
                List<Weight> test = weightDao.queryBuilder()
                        .where(WeightDao.Properties.W_id.eq("1")).list();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            daoSession.clear();
        }

        // Setup tab properties
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);


        // Preliminary A/B testing code
        int group = 0;
        switch (group) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                fragmentTab1 = new SummaryActivityFragment();
                fragmentTab2 = new DiaryListFragment();
                fragmentTab3 = new GraphsFragment();
                Tab tab = actionBar.newTab().setText(R.string.summary).setTabListener(new TabListener(fragmentTab1));
                actionBar.addTab(tab);
                tab = actionBar.newTab().setText(R.string.diary_list).setTabListener(new TabListener(fragmentTab2));
                actionBar.addTab(tab);
                tab = actionBar.newTab().setText(R.string.graphs).setTabListener(new TabListener(fragmentTab3));
                actionBar.addTab(tab);
                // Fourth tab
                if(Debug.isDebuggerConnected()) {
                    fragmentTab4 = new AddEntryFragment2();
                    System.out.println("DEBUG TAB IS ON");
                    condition = 1;
                    tab = actionBar.newTab().setText("Debug").setTabListener(new TabListener(fragmentTab4));
                    actionBar.addTab(tab);
                }
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.root, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void write(InputStream is) {
        try {
            OutputStream out = new FileOutputStream(new File("data/data/com.example.cdruecker.spoon/databases/food-db"));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            is.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}