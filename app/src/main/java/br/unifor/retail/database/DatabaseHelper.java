package br.unifor.retail.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.unifor.retail.R;
import br.unifor.retail.model.User;

/**
 * Created by vania on 11/10/16.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DatabaseName = "retail.db";

    private static final int DatabaseVersion = 1;

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion, R.raw.ormlite_config);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
