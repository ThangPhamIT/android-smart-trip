package DAO;

import android.content.Context;
import android.database.Cursor;

import Base.DBContext;

/**
 * Created by Linh on 8/1/2015.
 */
public class BaseDAO {
    protected DBContext db;
    protected Context context;
    String table_name;

    public Cursor loadAll(String table,String[] columns) {
        return db.getReadableDatabase().query(table, columns, null, null, null, null, null);
    }

    public void setContext(Context context,String table_name) {
        db = new DBContext(context,table_name);
        this.context = context;
        this.table_name=table_name;
    }

    public Cursor loadAll(String table, String[] columns, String selection, String[] args) {
        return db.getReadableDatabase().query(table, columns, selection, args, null, null, null);
    }

    public Cursor loadAll(String table, String[] columns, String selection, String[] args, String orderBy) {
        return db.getReadableDatabase().query(table,columns,selection,args,null,null,orderBy);
    }

}
