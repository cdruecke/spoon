package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.Nutr_def;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table NUTR_DEF.
*/
public class Nutr_defDao extends AbstractDao<Nutr_def, Long> {

    public static final String TABLENAME = "NUTR_DEF";

    /**
     * Properties of entity Nutr_def.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Nutr_no = new Property(0, Long.class, "Nutr_no", true, "NUTR_NO");
        public final static Property Units = new Property(1, String.class, "units", false, "UNITS");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
    };


    public Nutr_defDao(DaoConfig config) {
        super(config);
    }
    
    public Nutr_defDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'NUTR_DEF' (" + //
                "'NUTR_NO' INTEGER PRIMARY KEY ," + // 0: Nutr_no
                "'UNITS' TEXT," + // 1: units
                "'NAME' TEXT);"); // 2: name
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'NUTR_DEF'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Nutr_def entity) {
        stmt.clearBindings();
 
        Long Nutr_no = entity.getNutr_no();
        if (Nutr_no != null) {
            stmt.bindLong(1, Nutr_no);
        }
 
        String units = entity.getUnits();
        if (units != null) {
            stmt.bindString(2, units);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Nutr_def readEntity(Cursor cursor, int offset) {
        Nutr_def entity = new Nutr_def( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // Nutr_no
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // units
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // name
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Nutr_def entity, int offset) {
        entity.setNutr_no(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUnits(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Nutr_def entity, long rowId) {
        entity.setNutr_no(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Nutr_def entity) {
        if(entity != null) {
            return entity.getNutr_no();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}