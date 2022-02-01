package com.example.spotted.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.spotted.models.Job;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PreferenceManager {
    private static final String SHARED_PREF_NAME = "com.example.spotted.shared_prefs";
    private static final String KEY_LIKES = "likes_json";

    private static PreferenceManager mInstance;
    private static Context mContext;

    private PreferenceManager (Context context) {
        mContext = context;
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenceManager(context);
        }
        return mInstance;
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
    public void addLike(Job like){
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        //Get current likes
        String json = sharedPreferences.getString(KEY_LIKES, "");
        List<Job> jobs = null;


        if(!json.equals("")){
            Type listType = new TypeToken<ArrayList<Job>>(){}.getType();
            jobs = gson.fromJson(json, listType);
            if(jobs.stream().noneMatch(x -> x.getId().equals(like.getId()))){
                jobs.add(like);
            }

        }
        else{
            jobs = new ArrayList<Job>();
            jobs.add(like);
        }

        editor.putString(KEY_LIKES, gson.toJson(jobs));
        editor.apply();
    }
    public void removeLike(Job like){
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        //Get current likes
        String json = sharedPreferences.getString(KEY_LIKES, "");
        List<Job> jobs = null;

        try {
            if (!json.equals("") & like != null) {
                Type listType = new TypeToken<ArrayList<Job>>() {
                }.getType();
                jobs = gson.fromJson(json, listType);
                jobs.removeIf(x -> x.getId().equals(like.getId()));
                editor.putString(KEY_LIKES, gson.toJson(jobs));
                editor.apply();
            }
        }
        catch (JsonSyntaxException ex){
            System.out.println("RemoveLike JsonSyntaxException (" + like.toString() + ")");
        }
        catch (JsonParseException ex){
            System.out.println("RemoveLike JsonParseException (" + like.toString() + ")");
        }

    }
    public List<Job> getLikes(){
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        //Get current likes
        String json = sharedPreferences.getString(KEY_LIKES, "");
        List<Job> jobs = null;

        try {
            if (!json.equals("")) {
                Type listType = new TypeToken<ArrayList<Job>>() {
                }.getType();
                jobs = gson.fromJson(json, listType);
            } else
                jobs = new ArrayList<Job>();

        }
        catch (JsonSyntaxException ex){
            jobs = new ArrayList<Job>();
        }
        catch (JsonParseException ex){
            jobs = new ArrayList<Job>();
        }
        return jobs;
    }
    public void saveLikes(List<Job> likes){
        SharedPreferences sharedPreferences = mContext
                .getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(likes != null){
            Gson gson = new Gson();
            editor.putString(KEY_LIKES, gson.toJson(likes));
            editor.apply();
        }

    }
}
