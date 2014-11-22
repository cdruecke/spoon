package com.example.cdruecker.spoon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cdruecker.spoon.R;

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

public class EditEntry extends Activity {
    String uriString;
    public DaoMaster daoMaster;
    private User_entry target;
    public DaoSession daoSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        DatabaseOpenHelper helper = new DatabaseOpenHelper(this, "food-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("Edit Entry");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if(bundle!=null) {
            uriString = bundle.getString("pos");
            System.out.println("POS IS " + uriString);
            QueryBuilder peepl = daoSession.getUser_entryDao().queryBuilder();
            List<User_entry> peep = peepl.where(User_entryDao.Properties.Ue_id.eq((Integer.parseInt(uriString) + 1))).list();
            target = peep.get(0);
        } else {
            System.err.println("bundle isn't happening right now");
        }

        AutoCompleteTextView foodName = (AutoCompleteTextView) findViewById(R.id.addFoodNames);
        if(foodName!=null) {
            foodName.setText(target.getFood_desc().getName());
        }

        EditText servName = (EditText) findViewById(R.id.addFoodAmount);
        if(servName!=null) {
            servName.setText(target.getServ_amt().toString());
        }

        AutoCompleteTextView servAmt = (AutoCompleteTextView) findViewById(R.id.addFoodAmounts);
        if(servAmt!=null) {
            servAmt.setText(target.getWeight().getName());
        }

        Button addEntryButton = (Button) findViewById(R.id.sendAddEntry);
        if(addEntryButton!=null) {
            addEntryButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    submitEntry();
                }
            });
        } else {
            System.err.println("Button is magically not existing right now for some reason");
        }
    }


    public void submitEntry() {


        Food_descDao fd = daoSession.getFood_descDao();
        WeightDao wd = daoSession.getWeightDao();
        User_entryDao ud = daoSession.getUser_entryDao();

        TextView ft = (TextView) findViewById(R.id.addFoodNames);
        TextView wt = (TextView) findViewById(R.id.addFoodAmounts);
        TextView at = (TextView) findViewById(R.id.addFoodAmount);

        float amt = Float.parseFloat(at.getText().toString());

        Date date = new Date();

        List<Food_desc> listfooddesc = fd.queryBuilder()
                .where(Food_descDao.Properties.Name.eq(
                        ft.getText())).limit(1).list();
        long ndbno = listfooddesc.get(0).getNDB_no();

        List<Weight> listweight = wd.queryBuilder()
                .where(WeightDao.Properties.Name.eq(
                        wt.getText())).limit(1).list();
        long wid = listweight.get(0).getW_id();

        target.setDate(date);
        target.setNDB_no(ndbno);
        target.setW_id(wid);
        target.setServ_amt(amt);
        target.update();
        target.refresh();

        daoSession.clear();
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_entry, menu);
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
}
