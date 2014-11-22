package greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.Nutr_data;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table NUTR_DATA.
*/
public class Nutr_dataDao extends AbstractDao<Nutr_data, Void> {

    public static final String TABLENAME = "NUTR_DATA";

    /**
     * Properties of entity Nutr_data.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Nutrient_amt = new Property(0, Float.class, "nutrient_amt", false, "NUTRIENT_AMT");
        public final static Property NDB_no = new Property(1, Long.class, "NDB_no", false, "NDB_NO");
        public final static Property Nutr_no = new Property(2, Long.class, "Nutr_no", false, "NUTR_NO");
    };

    private DaoSession daoSession;


    public Nutr_dataDao(DaoConfig config) {
        super(config);
    }
    
    public Nutr_dataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'NUTR_DATA' (" + //
                "'NUTRIENT_AMT' REAL," + // 0: nutrient_amt
                "'NDB_NO' INTEGER," + // 1: NDB_no
                "'NUTR_NO' INTEGER);"); // 2: Nutr_no
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NUTR_DATA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Nutr_data entity) {
        stmt.clearBindings();
 
        Float nutrient_amt = entity.getNutrient_amt();
        if (nutrient_amt != null) {
            stmt.bindDouble(1, nutrient_amt);
        }
 
        Long NDB_no = entity.getNDB_no();
        if (NDB_no != null) {
            stmt.bindLong(2, NDB_no);
        }
 
        Long Nutr_no = entity.getNutr_no();
        if (Nutr_no != null) {
            stmt.bindLong(3, Nutr_no);
        }
    }

    @Override
    protected void attachEntity(Nutr_data entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public Nutr_data readEntity(Cursor cursor, int offset) {
        Nutr_data entity = new Nutr_data( //
            cursor.isNull(offset + 0) ? null : cursor.getFloat(offset + 0), // nutrient_amt
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // NDB_no
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // Nutr_no
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Nutr_data entity, int offset) {
        entity.setNutrient_amt(cursor.isNull(offset + 0) ? null : cursor.getFloat(offset + 0));
        entity.setNDB_no(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setNutr_no(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(Nutr_data entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(Nutr_data entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getFood_descDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getNutr_defDao().getAllColumns());
            builder.append(" FROM NUTR_DATA T");
            builder.append(" LEFT JOIN FOOD_DESC T0 ON T.'NDB_NO'=T0.'NDB_NO'");
            builder.append(" LEFT JOIN NUTR_DEF T1 ON T.'NUTR_NO'=T1.'NUTR_NO'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Nutr_data loadCurrentDeep(Cursor cursor, boolean lock) {
        Nutr_data entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Food_desc food_desc = loadCurrentOther(daoSession.getFood_descDao(), cursor, offset);
        entity.setFood_desc(food_desc);
        offset += daoSession.getFood_descDao().getAllColumns().length;

        Nutr_def nutr_def = loadCurrentOther(daoSession.getNutr_defDao(), cursor, offset);
        entity.setNutr_def(nutr_def);

        return entity;    
    }

    public Nutr_data loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Nutr_data> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Nutr_data> list = new ArrayList<Nutr_data>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Nutr_data> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Nutr_data> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}