package win.pcdack.oscsmallclient.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;


/**
 * Created by pcdack on 17-11-11.
 *
 */

public class DatabaseManager {
    private DaoSession daoSession=null;
    private UserProfileDao userProfile=null;
    private DatabaseManager(){

    }
    private static final class Holder{
        private static final DatabaseManager INSTANCE=new DatabaseManager();
    }
    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }

    public void initDao(Context context){
        final ReleaseOpenHelper helper=new ReleaseOpenHelper(context,"fast_ec.db");
        final Database db=helper.getWritableDb();
        daoSession=new DaoMaster(db).newSession();
        userProfile=daoSession.getUserProfileDao();
    }
    public final UserProfileDao getDao(){
        return userProfile;
    }
}
