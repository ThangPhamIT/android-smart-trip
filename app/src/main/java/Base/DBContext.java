package Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Linh on 8/1/2015.
 */
public class DBContext extends SQLiteOpenHelper {


    public final static int VERSION=1;
    public DBContext(Context context,String DBName){
        super(context,DBName,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
