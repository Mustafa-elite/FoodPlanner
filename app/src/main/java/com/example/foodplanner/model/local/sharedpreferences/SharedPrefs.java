package com.example.foodplanner.model.local.sharedpreferences;

import android.content.Context;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

public class SharedPrefs {
    RxSharedPreferences rxSharedPreferences;
    public void setRxSharedPreferencesContext(Context context) {
        rxSharedPreferences = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public SharedPrefs(Context context) {
        this.rxSharedPreferences = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public Preference<String> saveSharedPref(String key,String value)
    {
        Preference<String> usernamePref = rxSharedPreferences.getString(key);
        usernamePref.set(value);
        return usernamePref;
    }
    /*RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(PreferenceManager.getDefaultSharedPreferences(getContext()));
        Preference<String> usernamePref = rxSharedPreferences.getString("username");
        usernamePref.set("Mustafa");
        Disposable subscribe = usernamePref.asObservable()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String username) {
                        makeToast(username);
                    }
                });*/
}
