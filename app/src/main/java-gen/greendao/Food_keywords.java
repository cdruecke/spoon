package greendao;

import greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table FOOD_KEYWORDS.
 */
public class Food_keywords {

    private Long NDB_no;
    private Long Lang_no;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient Food_keywordsDao myDao;

    private Food_desc food_desc;
    private Long food_desc__resolvedKey;

    private Keywords keywords;
    private Long keywords__resolvedKey;


    public Food_keywords() {
    }

    public Food_keywords(Long NDB_no, Long Lang_no) {
        this.NDB_no = NDB_no;
        this.Lang_no = Lang_no;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFood_keywordsDao() : null;
    }

    public Long getNDB_no() {
        return NDB_no;
    }

    public void setNDB_no(Long NDB_no) {
        this.NDB_no = NDB_no;
    }

    public Long getLang_no() {
        return Lang_no;
    }

    public void setLang_no(Long Lang_no) {
        this.Lang_no = Lang_no;
    }

    /** To-one relationship, resolved on first access. */
    public Food_desc getFood_desc() {
        Long __key = this.NDB_no;
        if (food_desc__resolvedKey == null || !food_desc__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Food_descDao targetDao = daoSession.getFood_descDao();
            Food_desc food_descNew = targetDao.load(__key);
            synchronized (this) {
                food_desc = food_descNew;
            	food_desc__resolvedKey = __key;
            }
        }
        return food_desc;
    }

    public void setFood_desc(Food_desc food_desc) {
        synchronized (this) {
            this.food_desc = food_desc;
            NDB_no = food_desc == null ? null : food_desc.getNDB_no();
            food_desc__resolvedKey = NDB_no;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Keywords getKeywords() {
        Long __key = this.Lang_no;
        if (keywords__resolvedKey == null || !keywords__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            KeywordsDao targetDao = daoSession.getKeywordsDao();
            Keywords keywordsNew = targetDao.load(__key);
            synchronized (this) {
                keywords = keywordsNew;
            	keywords__resolvedKey = __key;
            }
        }
        return keywords;
    }

    public void setKeywords(Keywords keywords) {
        synchronized (this) {
            this.keywords = keywords;
            Lang_no = keywords == null ? null : keywords.getLang_no();
            keywords__resolvedKey = Lang_no;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}