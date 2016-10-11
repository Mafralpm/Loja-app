package br.unifor.retail.database.controller;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.unifor.retail.model.User;

/**
 * Created by vania on 11/10/16.
 */

public class UserDao extends BaseDaoImpl<User, Integer> {

    public UserDao(ConnectionSource connectionSource) throws SQLException {
        super(User.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
