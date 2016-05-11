package com.helen.dayjoke.entity;

/**
 * Created by Helen on 2016/5/3.
 *
 */
public class ConstellationEn extends BaseEn{
    public static final int START = 5;

    public static final String TYPE_TODAY = "today";
    public static final String TYPE_TOMORROW = "tomorrow";
    public static final String TYPE_WEEK = "week";
    public static final String TYPE_NEXT_WEEK = "nextweek";
    public static final String TYPE_YEAR = "year";

    private String date;/*日期数值*/
    private String name; /*星座名称*/
    private String QFriend;/*速配星座*/
    private String all; /*综合指数*/
    private String color;/*幸运色*/
    private String datetime;/*日期*/
    private String health; /*健康指数*/
    private String love;/*爱情指数*/
    private String money; /*财运指数*/
    private String number; /*幸运数字*/
    private String summary; /*总结*/
    private String work;/*工作指数*/
    private String resultcode;/*返回状态码 200为成功*/
    private int error_code;/*返回错误码 0为没有错误*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public float getAllFormat(){
        try {
            return Float.parseFloat(all.replace("%",""))*0.01f*START;
        }catch (Exception e){
            //
        }
        return 0f;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public float getLoveFormat(){
        try {
            return Float.parseFloat(love.replace("%",""))*0.01f*START;
        }catch (Exception e){
            //
        }
        return 0f;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public float getMoneyFormat(){
        try {
            return Float.parseFloat(money.replace("%",""))*0.01f*START;
        }catch (Exception e){
            //
        }
        return 0f;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public float getWorkFormat(){
        try {
            return Float.parseFloat(work.replace("%",""))*0.01f*START;
        }catch (Exception e){
            //
        }
        return 0f;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
