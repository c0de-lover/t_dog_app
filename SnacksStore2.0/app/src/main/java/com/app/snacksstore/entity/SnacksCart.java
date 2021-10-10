package com.app.snacksstore.entity;


public class SnacksCart extends Snacks {
    private int num;
    private boolean checked;

    public SnacksCart(Snacks snacks, int num, boolean checked) {
        super(snacks.getId(), snacks.getName(), snacks.getWeight(), snacks.getHeat(),
                snacks.getPrice(), snacks.getExpireDate());
        this.num = num;
        this.checked = checked;
    }

    public SnacksCart(int id, String name, double weight, double heat, double price, String expireDate, int num, boolean checked) {
        super(id, name, weight, heat, price, expireDate);
        this.num = num;
        this.checked = checked;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "SnacksCart{" +
                "num=" + num +
                ", checked=" + checked +
                '}';
    }
}
