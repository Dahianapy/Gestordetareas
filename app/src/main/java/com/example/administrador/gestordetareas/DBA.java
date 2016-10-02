package com.example.administrador.gestordetareas;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.oss.datahelper.DataBaseManager;

import java.sql.SQLException;

/**
 * Created by Administrador on 14/09/2016.
 */
/// para crear la base de datos
public class DBA {
    private static final String DB_NAME = "agenda.sqlite";
    private static final int DB_VERSION = 1;

    public static void  init(Context context){
        DataBaseManager.init(context,DB_NAME,DB_VERSION);
        /// hasta aca
        ConnectionSource sourse = DataBaseManager.getInstance().getHelper().getConnectionSource();

        try {
            TableUtils.createTableIfNotExists(sourse,Contacto.class);
            /// demas tablas
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public  static Dao<Contacto, Integer> getContactoDao(){
        /// le escribimos desde el return y le damos try cacht si algo sale mal return null
        try {
            return DataBaseManager.getInstance().getHelper().getDao(Contacto.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
