package br.unifor.retail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by vania on 10/10/16.
 */
@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(generatedId = true)
    private Long idUser;

    @DatabaseField(columnName = "email", canBeNull = false)
    private String email;

    @DatabaseField(columnName = "password", canBeNull = false)
    private String password;

    @DatabaseField(columnName = "passwordConfirmation", canBeNull = false)
    private String passwordConfirmation;

}
