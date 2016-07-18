package com.example.lyx.myapplication.ch07;

import android.os.RemoteException;

/**
 * Created by lyx on 2016/7/17.
 */
public class IPersonImpl extends IPerson.Stub {
    private int age;
    private String name;
    @Override
    public String display() throws RemoteException {
        return "name:" + name + ";age:" + age;
    }
    @Override
    public void setAge(int age) throws RemoteException {
        this.age = age;
    }
    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }
}
