package com.app.snacksstore.dao;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.app.snacksstore.entity.SnacksCart;

import java.util.LinkedList;
import java.util.List;

public class SnacksCartService {
    protected final DBConnection dbConnection;
    protected String tableName = "cart";

    public SnacksCartService(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean insert(SnacksCart snacksCart) {
        SQLiteDatabase db = dbConnection.getConnection();
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
        db.close();
        return res != -1;
    }


    public boolean getCartChecked(int id) {
        SQLiteDatabase db = dbConnection.getConnection();
        // 表名，列名，where约束条件，where中占位符提供具体的值
        Cursor cursor = db.query(
                tableName,
                new String[]{"checked"},
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        boolean checked = false;
        if (cursor.moveToFirst()) {
            String res = cursor.getString(0);
            checked = "1".equals(res);
        }
        cursor.close();
        db.close();
        return checked;
    }

    public boolean updateCartChecked(int id, boolean checked) {
        SQLiteDatabase db = dbConnection.getConnection();
        ContentValues values = new ContentValues();
        values.put("checked", checked ? 1 : 0);
        int res = db.update(
                tableName,
                values,
                "id=?",
                new String[]{String.valueOf(id)});
        db.close();
        return res > 0;
    }

    public boolean setCartCheckAll(boolean check_all) {
        SQLiteDatabase db = dbConnection.getConnection();
        ContentValues values = new ContentValues();
        values.put("checked", check_all ? 1 : 0);
        int res = db.update(
                tableName,
                values,
                null,
                null);
        db.close();
        return res > 0;
    }

    public int getCartNum(int id) {
        SQLiteDatabase db = dbConnection.getConnection();
        // 表名，列名，where约束条件，where中占位符提供具体的值
        Cursor cursor = db.query(
                tableName,
                new String[]{"num"},
                "id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        int nums = 0;
        if (cursor.moveToFirst()) {
            String res = cursor.getString(0);
            nums = Integer.parseInt(res);
        }
        cursor.close();
        db.close();
        return nums;
    }

    public List<SnacksCart> getAllCart() {
        return this.getAllCartSelection(null, null);
    }

    public List<SnacksCart> getAllCartChecked() {
        return this.getAllCartSelection("checked=?", new String[]{"1"});
    }

    public List<SnacksCart> getAllCartSelection(String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbConnection.getConnection();
        Cursor cursor = db.query(
                tableName, null, selection, selectionArgs, null, null, "id"
        );
        List<SnacksCart> snacksCartList = new LinkedList<>();
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            double weight = Double.parseDouble(cursor.getString(cursor.getColumnIndex("weight")));
            double heat = Double.parseDouble(cursor.getString(cursor.getColumnIndex("heat")));
            double price = Double.parseDouble(cursor.getString(cursor.getColumnIndex("price")));
            String expireDate = cursor.getString(cursor.getColumnIndex("expire_date"));
            int num = Integer.parseInt(cursor.getString(cursor.getColumnIndex("num")));
            boolean checked = "1".equals(cursor.getString(cursor.getColumnIndex("checked")));
            snacksCartList.add(new SnacksCart(id, name, weight, heat, price, expireDate, num, checked));
        }
        cursor.close();
        db.close();
        return snacksCartList;
    }

    public boolean deleteByID(int id) {
        SQLiteDatabase db = dbConnection.getConnection();
        int res = db.delete(
                tableName,
                "id=?",
                new String[]{String.valueOf(id)});
        db.close();
        return res > 0;
    }

    public double calSumPrice() {
        double price = 0;
        List<SnacksCart> snacksCartList = getAllCart();
        for (SnacksCart cart : snacksCartList) {
            if (cart.isChecked()) price += cart.getNum() * cart.getPrice();
        }
        return price;
    }

    public boolean deleteAllChecked() {
        List<SnacksCart> snacksCartList = getAllCart();
        boolean success = false;
        for (SnacksCart cart : snacksCartList) {
            if (cart.isChecked()) success |= !deleteByID(cart.getId());
        }
        return !success;
    }

    public boolean appendCartNum(int id, int num) {
        try (SQLiteDatabase db = dbConnection.getConnection()) {
            @SuppressLint("DefaultLocale")
            String sql = "UPDATE " + tableName + " SET num=num" + String.format("%+d", num) + " WHERE id=" + id;
            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean setCartNum(int id, int num) {
        try (SQLiteDatabase db = dbConnection.getConnection()) {
            @SuppressLint("DefaultLocale")
            String sql = "UPDATE " + tableName + " SET num=" + num + " WHERE id=" + id;
            db.execSQL(sql);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected String getTableName() {
        return this.tableName;
    }
}
