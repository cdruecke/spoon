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

import greendao.User_entry;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USER_ENTRY.
*/
public class User_entryDao extends AbstractDao<User_entry, Long> {

    public static final String TABLENAME = "USER_ENTRY";

    /**
     * Properties of entity User_entry.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Ue_id = new Property(0, Long.class, "ue_id", true, "UE_ID");
        public final static Property Date = new Property(1, java.util.Date.class, "date", false, "DATE");
        public final static Property Serv_amt = new Property(2, Float.class, "serv_amt", false, "SERV_AMT");
        public final static Property W_id = new Property(3, Long.class, "w_id", false, "W_ID");
        public final static Property NDB_no = new Property(4, Long.class, "NDB_no", false, "NDB_NO");
    };

    private DaoSession daoSession;


    public User_entryDao(DaoConfig config) {
        super(config);
    }
    
    public User_entryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USER_ENTRY' (" + //
                "'UE_ID' INTEGER PRIMARY KEY ASC AUTOINCREMENT ," + // 0: ue_id
                "'DATE' INTEGER," + // 1: date
                "'SERV_AMT' REAL," + // 2: serv_amt
                "'W_ID' INTEGER," + // 3: w_id
                "'NDB_NO' INTEGER);"); // 4: NDB_no
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USER_ENTRY'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, User_entry entity) {
        stmt.clearBindings();
 
        Long ue_id = entity.getUe_id();
        if (ue_id != null) {
            stmt.bindLong(1, ue_id);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(2, date.getTime());
        }
 
        Float serv_amt = entity.getServ_amt();
        if (serv_amt != null) {
            stmt.bindDouble(3, serv_amt);
        }
 
        Long w_id = entity.getW_id();
        if (w_id != null) {
            stmt.bindLong(4, w_id);
        }
 
        Long NDB_no = entity.getNDB_no();
        if (NDB_no != null) {
            stmt.bindLong(5, NDB_no);
        }
    }

    @Override
    protected void attachEntity(User_entry entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public User_entry readEntity(Cursor cursor, int offset) {
        User_entry entity = new User_entry( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ue_id
            cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)), // date
            cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2), // serv_amt
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // w_id
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4) // NDB_no
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, User_entry entity, int offset) {
        entity.setUe_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDate(cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)));
        entity.setServ_amt(cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2));
        entity.setW_id(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setNDB_no(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(User_entry entity, long rowId) {
        entity.setUe_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(User_entry entity) {
        if(entity != null) {
            return entity.getUe_id();
        } else {
            return null;
        }
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
            SqlUtils.appendColumns(builder, "T0", daoSession.getWeightDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getFood_descDao().getAllColumns());
            builder.append(" FROM USER_ENTRY T");
            builder.append(" LEFT JOIN WEIGHT T0 ON T.'W_ID'=T0.'W_ID'");
            builder.append(" LEFT JOIN FOOD_DESC T1 ON T.'NDB_NO'=T1.'NDB_NO'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected User_entry loadCurrentDeep(Cursor cursor, boolean lock) {
        User_entry entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Weight weight = loadCurrentOther(daoSession.getWeightDao(), cursor, offset);
        entity.setWeight(weight);
        offset += daoSession.getWeightDao().getAllColumns().length;

        Food_desc food_desc = loadCurrentOther(daoSession.getFood_descDao(), cursor, offset);
        entity.setFood_desc(food_desc);

        return entity;    
    }

    public User_entry loadDeep(Long key) {
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
    public List<User_entry> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<User_entry> list = new ArrayList<User_entry>(count);
        
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
    
    protected List<User_entry> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<User_entry> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}