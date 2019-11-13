package com.example.apidemo.domain;

public class Sender
{
    private String ip;
    private String phonenumbers;
    private String msg;


    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getPhonenumbers()
    {
        return phonenumbers;
    }

    public void setPhonenumbers(String phonenumbers)
    {
        this.phonenumbers = phonenumbers;
    }
    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg=msg;
    }
    @Override
    public String toString()
    {
        return " ip:"+this.getIp()+"  phonenumber:"+this.getPhonenumbers()+"  msg:"+this.getMsg();
    }
}
