package com.helen.dayjoke.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.helen.dayjoke.ui.application.DJApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Helen on 2015/7/23.
 * SharedPreferences Util
 */
public class SPUtil {
    public static SPUtil INSTANCE;
    public SharedPreferences mSharedPre;
    public SharedPreferences.Editor mEditor;


    private SPUtil (Context context){
        mSharedPre = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mEditor = mSharedPre.edit();
    }


    public static SPUtil getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SPUtil(DJApplication.getInstance());
        }
        return INSTANCE;
    }

    /**put、get String start*/
    public SPUtil putString(String key, String value){
        mEditor.putString(key, value);
        return this;
    }

    public String getString(String key , String defValue ){
        return mSharedPre.getString(key, defValue);
    }

    public String getString(String key){
        return getString(key, "");
    }
    /**put、get String end*/

    /**put、get Int start*/
    public SPUtil putInt(String key,int value){
        mEditor.putInt(key, value);
        return this;
    }

    public int getInt(String key , int defValue){
        return mSharedPre.getInt(key, defValue);
    }

    public int getInt(String key){
        return getInt(key, 0);
    }
    /**put、get Int end*/

    /**put、get Boolean start*/
    public SPUtil putBoolean(String key,boolean value){
        mEditor.putBoolean(key, value);
        return this;
    }

    public boolean getBoolean(String key , boolean defValue){
        return mSharedPre.getBoolean(key, defValue);
    }

    public boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    /**put、get Boolean end*/

    /**put、get Long start*/
    public SPUtil putLong(String key,long value){
        mEditor.putLong(key, value);
        return this;
    }

    public long getLong(String key ,long defValue){
        return mSharedPre.getLong(key, defValue);
    }

    public long getLong(String key){
        return getLong(key, 0);
    }

    /**put、get Long end*/

    /**put、get Float start*/
    public SPUtil putFloat(String key,float value){
        mEditor.putFloat(key, value);
        return this;
    }

    public float getFloat(String key , float defValue){
        return mSharedPre.getFloat(key, defValue);
    }

    public float getFloat(String key){
        return getFloat(key, 0.0f);
    }

    /**put、get Float end*/

    /**put、get StringSet start*/
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SPUtil putStringSet(String key,Set<String> value){
        mEditor.putStringSet(key,value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Set<String> getStringSet(String key , Set<String> defValue){
        return mSharedPre.getStringSet(key, defValue);
    }

    public Set<String> getStringSet(String key){
        return getStringSet(key, null);
    }

    /**put、get StringSet end*/

    /**put、get JSONObject start*/
    public SPUtil putJSONObject(String key,JSONObject value){
        return putString(key, value.toString());
    }


    public JSONObject getJSONObject(String key,JSONObject defValue){
        String jsonStr = getString(key,"");
        JSONObject obj;
        try {
            obj = new JSONObject(jsonStr);
        } catch (Exception e) {
            obj =defValue;
        }
        return obj;
    }

    public JSONObject getJSONObject(String key){
        return getJSONObject(key, null);
    }

    /**put、get JSONObject end*/

    /**put、get JSONArray start*/
    public SPUtil putJSONArray(String key,JSONArray value){
        return putString(key, value.toString());
    }

    public JSONArray getJSONArray(String key,JSONArray defValue){
        String jsonStr = getString(key,"");
        JSONArray obj;
        try {
            obj = new JSONArray(jsonStr);
        } catch (Exception e) {
            obj = defValue;
        }
        return obj;
    }


    public JSONArray getJSONArray(String key){
        return getJSONArray(key, null);
    }

    /**put、get JSONArray end*/

    /**put、get StringList start*/
    public SPUtil putStringList(String key , List<String> value){
        StringBuilder sb = new StringBuilder();
        for(String str : value){
            sb.append(str).append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return putString(key,sb.toString());
    }

    public List<String> getStringList(String key , List<String> defValue){
        List<String> obj;
        try {
            String str = getString(key,"");
            obj = Arrays.asList(str.split(","));
        }catch (Exception e){
            obj = defValue;
        }
        return obj;
    }

    public List<String> getStringList(String key){
        return getStringList(key, null);
    }

    /**put、get StringList end*/

    /**put、get StringArray start*/
    public SPUtil putStringArray(String key , String[] value){
        StringBuilder sb = new StringBuilder();
        for(String str : value){
            sb.append(str).append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return putString(key, sb.toString());
    }

    public String[] getStringArray(String key , String[] defValue ){
        String[] obj;
        try {
            String str = getString(key,"");
            obj = str.split(",");
        }catch (Exception e){
            obj = defValue;
        }
        return obj;
    }

    public String[] getStringArray(String key){
        return  getStringArray(key,null);
    }

    /**put、get StringArray end*/

    /**others*/
    public SPUtil remove(String key){
        mEditor.remove(key);
        return this;
    }


    public boolean contains(String key){
        return mSharedPre.contains(key);
    }


    public Map<String,?> getAll(){
        return mSharedPre.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        mSharedPre.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        mSharedPre.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean commit(){
        return mEditor.commit();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void apply(){
        mEditor.apply();
    }

    public void clear(){
        mEditor.clear().commit();
    }
}
