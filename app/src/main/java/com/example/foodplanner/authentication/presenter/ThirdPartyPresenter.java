package com.example.foodplanner.authentication.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.foodplanner.R;
import com.example.foodplanner.authentication.view.ThirdpartyAuthView;
import com.example.foodplanner.model.local.database.calendar.ScheduledMeal;
import com.example.foodplanner.model.local.database.favorites.DbMeal;
import com.example.foodplanner.model.remote.authentication.AuthCallback;
import com.example.foodplanner.model.remote.server.network.RemoteDataSource;
import com.example.foodplanner.model.repository.AuthRepository;
import com.example.foodplanner.model.repository.DataRepository;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Arrays;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThirdPartyPresenter {
    private ThirdpartyAuthView view;
    private AuthRepository repository;
    private DataRepository dataRepository;
    private CallbackManager callbackManager;
    Context context;
    public ThirdPartyPresenter(ThirdpartyAuthView view, AuthRepository repository, Context _context,DataRepository dataRepository) {
        this.view = view;
        this.repository = repository;
        this.dataRepository=dataRepository;
        context=_context;

    }
    public void facebookLogin(LoginButton facebookButton) {
        callbackManager = CallbackManager.Factory.create();
//        facebookButton.setReadPermissions(Arrays.asList("public_profile", "email"));
//        facebookButton.setFragment((Fragment) view);
//        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        LoginManager.getInstance().logInWithReadPermissions((Fragment) view, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                repository.facebookSignIn(loginResult, new AuthCallback() {
                    @Override
                    public void onSuccess() {
                        retreiveData();
                        view.onThirdPartySignUpSuccessful();

                    }
                    @Override
                    public void onFailure(String errMsg) {
                        view.onThirdPartySignUpFailure(errMsg);
                    }
                });
            }

            @Override
            public void onCancel() {
                view.showError("Facebook Sign-in Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                view.showError("Facebook Sign-in Failed: " + error.getMessage());
            }
        });
    }
    @SuppressLint("CheckResult")
    private void retreiveData() {
        dataRepository.getRmoteCalMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        scheduledMealList-> {
                            for(ScheduledMeal scheduledMeal:scheduledMealList)
                            {
                                loadImageWithGlide(scheduledMeal.getMeal().getMealThumb(),
                                        bitmap -> {
                                            Log.i("TAG", "retreiveData:fetched fav image ");
                                            scheduledMeal.getMeal().setImage(bitmap);
                                            dataRepository.insertLocalCalMeal(scheduledMeal).subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe();;
                                        },
                                        throwable -> {
                                            Log.i("TAG", throwable.getMessage());
                                            throwable.getCause();
                                        });

                            }
                        },
                        throwable ->
                        {
                            throwable.getCause();
                            Log.i("TAG", throwable.getMessage());
                        });
        dataRepository.getRemoteFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        dbMealList-> {
                            for(DbMeal dbMeal:dbMealList)
                            {
                                //dataRepository.insertLocalMeal(dbMeal,Bitmap.createBitmap(10,10,Bitmap.Config.ALPHA_8));
                                //Log.i("TAG", dbMeal.toString());
                                loadImageWithGlide(dbMeal.getMealThumb(),
                                        bitmap -> {
                                            Log.i("TAG", "retreiveData:fetched fav image ");
                                            dbMeal.setImage(bitmap);
                                            dataRepository.insertLocalMeal(dbMeal) .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe();
                                        },
                                        throwable -> {
                                            Log.i("TAG", throwable.getMessage());
                                            throwable.getCause();
                                        });
                            }
                        },
                        throwable ->
                        {
                            throwable.getCause();
                            Log.i("TAG", throwable.getMessage());
                        });

    }
    @SuppressLint("CheckResult")
    public void loadImageWithGlide(String imageUrl, Consumer<Bitmap> onSuccess, Consumer<Throwable> onError) {
        Log.d("GlideDebug", "Image URL: " + imageUrl);
        Single.<Bitmap>create(emitter -> {
                    try {
                        Bitmap bitmap = Glide.with(context)
                                .asBitmap()
                                .load(imageUrl)
                                .submit()
                                .get();

                        emitter.onSuccess(bitmap);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess,onError);
    }

    public void googleSignIn(GoogleSignInAccount account) {
        repository.googleSignIn(account, new AuthCallback() {
            @Override
            public void onSuccess() {
                retreiveData();
                view.onThirdPartySignUpSuccessful();
            }

            @Override
            public void onFailure(String errMsg) {
                view.onThirdPartySignUpFailure(errMsg);
            }
        });
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
