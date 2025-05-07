package com.moddakir.call.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moddakir.call.Model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.moddakir.call.utils.Perference.Pref_Name;
public class AccountPreference {
    private final static String USER_KEY = "user";
    private final static String USER_LIST_KEY = "user_list";
    private final static String ACCESS_TOKEN_KEY = "accessToken";
    public final static String GOOGLE_AD_ID_KEY = "googleId";
    public static User user = null;
    public static User currentLoggedUser;

    /*   public static User getUser() {// first account is the currently used account
   //        SharedPreferences pref = StudentApp.getInstance().getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
           List<User> users = getSavedAccounts();
           if (users != null && users.size() > 0 && users.get(0).isCurrentlyLogged())
               return users.get(0);
           return null;
       }
   */
    public static User getUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        String user = pref.getString(USER_KEY, "");
        if (user == null || user.isEmpty()) {
            currentLoggedUser = null;
            return null;
        }
        currentLoggedUser = new Gson().fromJson(user, User.class);
        return currentLoggedUser;
    }


    public static ArrayList<User> getSavedAccounts(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        try {
            ArrayList<User> users = new ArrayList<>();
            String serializedObject = pref.getString(USER_LIST_KEY, null);
            if (serializedObject != null && !serializedObject.isEmpty()) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>() {
                }.getType();
                users = gson.fromJson(serializedObject, type);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
//            completeLogout();
        }
        return null;
    }

    public static void registerData(User newUser,Context context) {
        try {
            if (newUser == null) return;
            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
            String oldAccessToken = null;
            ArrayList<User> users = getSavedAccounts(context);
            ArrayList<User> validUsers = new ArrayList<>();
            if (users != null && !users.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {//remove any duplicates accounts
                    users.get(i).setCurrentlyLogged(false);
                    if (newUser.getId() != null && users.get(i) != null && users.get(i).getId() != null &&
                            Objects.equals(users.get(i).getId(), newUser.getId())) {
                        oldAccessToken = users.get(i).getAccessToken();
 //                       users.remove(users.get(i));

//                        i--;
                    } else validUsers.add(users.get(i));
                }
            }
           else if (newUser.getAccessToken() == null || newUser.getAccessToken().isEmpty())
                newUser.setAccessToken(oldAccessToken);
            newUser.setCurrentlyLogged(true);
            validUsers.add(0, newUser);//first one is that already used account
            saveUsersList(validUsers, pref);
            saveCurrentLoggedUser(newUser,context);
            setAccessToken(newUser.getAccessToken(),context);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void saveCurrentLoggedUser(User user,Context context) {
        String userStr = new Gson().toJson(user);
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(USER_KEY, userStr);
        editor.apply();
        currentLoggedUser = user;
    }

    private static void saveUsersList(ArrayList<User> users, SharedPreferences preferences) {
        String usersJson = new Gson().toJson(users);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_LIST_KEY, usersJson);
        editor.apply();

    }

    public static String getAccessToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        return pref.getString(ACCESS_TOKEN_KEY, "");

    }

    public static void setAccessToken(String accessToken,Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }


    public static void removeCurrentLoggedUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(USER_KEY);
        editor.remove(ACCESS_TOKEN_KEY);
        editor.apply();
    }

    public static void removeCurrentAccount(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE);
        ArrayList<User> users = getSavedAccounts(context);
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i) != null && users.get(i).isCurrentlyLogged()) {
//                    Timber.v("Removed account " + users.get(i).getId());
                    users.remove(i);
                    i--;
                }

            }
            removeCurrentLoggedUser(context);
            saveUsersList(users, pref);

        }
    }


}
