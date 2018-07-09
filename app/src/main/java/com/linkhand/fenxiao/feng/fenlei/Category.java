package com.linkhand.fenxiao.feng.fenlei;

/**
 * Created by djz on 2016/11/10.
 */

public class Category {
    private String name;
    private String id;

    public Category(String name, String id)
    {
        this.name = name;
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
