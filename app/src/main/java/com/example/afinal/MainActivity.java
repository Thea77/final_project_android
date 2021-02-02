package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView register;

    LoginButton loginButton;
    CallbackManager callbackManager;
    FirebaseAuth mAuth;
    String TAG = "Main";
    List<myAuthor> authorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        loginButton = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginButton.setPermissions("email","public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
                APIArticleService apiArticleService;
                apiArticleService = RetrofitInstance.createService(APIArticleService.class);

                //get profile detail in facebook
                Profile profile = Profile.getCurrentProfile();
//                Log.e(TAG,"myProfile: "+profile.getLinkUri());
//                Log.e(TAG,"myProfile: "+profile.getProfilePictureUri(200,200));


                    myAuthor myAuthor = new myAuthor();
                    myAuthor.setName(profile.getName());

                    if (!myAuthor.getName().matches(profile.getName())){
//                        Log.e("TAG","my Name: "+myAuthor);
                        Call<AuthorResponse> call = apiArticleService.createAuthor(new AuthorRequest(
                                profile.getName(),profile.getProfilePictureUri(200,200).toString()

                        ));
                        call.enqueue(new Callback<AuthorResponse>() {
                            @Override
                            public void onResponse(Call<AuthorResponse> call, Response<AuthorResponse> response) {
                                if (response.isSuccessful()){
                                    Log.e("TAG","created User: "+profile.getName());

                                }
                            }
                            @Override
                            public void onFailure(Call<AuthorResponse> call, Throwable t) {

                            }
                        });
                    }


                Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                intent.putExtra("profileName",profile.getName());
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("Error",error.getMessage());
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                startActivity(intent);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });


//    check user login
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null){
                    Log.e("TAG","Logged");
                    Intent intent = new Intent(MainActivity.this,HomePageActivity.class);
                    startActivity(intent);
                }
            }
        };
        accessTokenTracker.startTracking();
    }



    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                Log.d(TAG,"complete: "+user.getDisplayName());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
//

    private void initUI() {
        email = findViewById(R.id.editText);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.txtregister);
        loginButton = findViewById(R.id.login_button);
    }













//get key hash


//    private void getHashKey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    getPackageName(),
//                    PackageManager.GET_SIGNATURES);
//            for(Signature signature : info.signatures){
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        }
//        catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

}