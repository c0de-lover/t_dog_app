package com.app.snacksstore.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.app.snacksstore.entity.SnacksCart;

import java.util.List;

public class SnacksInventoryService extends SnacksCartService {
    private final DBConnection dbConnection;
    private String tableName = "inventory";

    public SnacksInventoryService(DBConnection dbConnection) {
        super(dbConnection);
        super.setTableName(tableName);
        this.dbConnection = dbConnection;
    }

    public boolean addFromCart(final List<SnacksCart> cartList) {
        SQLiteDatabase db = dbConnection.getConnection();
        boolean success = true;
        for (SnacksCart cart : cartList) {
            if (isExists(cart.getId(), db)) success &= this.appendInventoryNum(cart.getId(), cart.getNum(), db);
            else success &= this.insert(cart, db);
        }
        db.close();
        return success;
    }

    public boolean appendInventoryNum(int id, int num) {
        SQLiteDatabase db = dbConnection.getConnection();
        boolean success = this.appendInventoryNum(id, num, db);
        db.close();
        return success;
    }

    private boolean insert(SnacksCart snacksCart, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("id", snacksCart.getId());
        values.put("name", snacksCart.getName());
        values.put("weight", snacksCart.getWeight());
        values.put("heat", snacksCart.getPrice());
        values.put("price", snacksCart.getPrice());
        values.put("expire_date", snacksCart.getExpireDate());
        values.put("num", snacksCart.getNum());
        values.put("checked", snacksCart.isChecked() ? 1 : 0);
        long res = db.insert(tableName, null, values);
        return res != -1;
    }

    private boolean isExists(int id, SQLiteDatabase db) {
        Cursor cursor = db.query(
                tableName,
                null,
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    private boolean appendInventoryNum(int id, int num, SQLiteDatabase db) {
        try {
            @SuppressLint("DefaultLocale")
            String sql = "UPDATE " + tableName + " SET num=num" + String.format("%+d", num) + " WHERE id=" + id;
            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
