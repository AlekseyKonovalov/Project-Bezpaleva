package com.example.bzp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import static ru.yandex.core.CoreApplication.getActivity;


public class LoginActivity extends FragmentActivity {

    private User user=new User();
    private int LogUserId=0;
    private int mapRadius=100;

    final String SAVED_ID = "0";
    int LOAD_ID=0;

    SharedPreferences sPref;


    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            LogUserId=getIntent().getExtras().getInt("LogUserId");
        }
        catch (Exception e){};
        try{
           mapRadius=getIntent().getExtras().getInt("radius");

        }
        catch (Exception e){
            mapRadius=100;
        };
        try{
            loadID();
            Log.i("bzp1", " loadID " + Integer.toString(LOAD_ID));
        }

        catch (Exception e){};

        setContentView(R.layout.activity_start);
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch (res) {
                    case LoggedOut:
                        showLogin();
                        break;
                    case LoggedIn:
                        showLogout();
                        break;
                    case Pending:
                        break;
                    case Unknown:
                        break;
                    }
                }

            @Override
            public void onError(VKError error) {

            }
        });

    }

    private void showLogout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LogoutFragment())
                .commit();
    }

    private void showLogin() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new LoginFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (VKSdk.isLoggedIn()) {
            showLogout();
        } else {
            showLogin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                VKRequest request = VKApi.users().get();
                request.executeWithListener(mRequestListener);
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback) ) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static class LoginFragment extends android.support.v4.app.Fragment {
        public LoginFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_login, container, false);
            v.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.login(getActivity(), sMyScope);
                }
            });

            v.findViewById(R.id.LogInbutMap).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startMapNotAuthActivity();
                }
            });

            v.findViewById(R.id.LogInbutInfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startInformationActivityy();
                }
            });

            v.findViewById(R.id.LogInbutContacts).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startContactsActivity();
                }
            });

            v.findViewById(R.id.LogInbutSettings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startSettingsActivity();
                }
            });


            return v;
        }
    }

    public static class LogoutFragment extends android.support.v4.app.Fragment {
        public LogoutFragment() {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_logout, container, false);
            v.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VKSdk.logout();
                    ((LoginActivity) getActivity()).cleanID();
                    if (!VKSdk.isLoggedIn()) {
                        ((LoginActivity) getActivity()).showLogin();
                    }
                }
            });


            v.findViewById(R.id.LogOutbutMap).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startMapActivity();
                }
            });

            v.findViewById(R.id.LogOutbutInfo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startInformationActivityy();
                }
            });

            v.findViewById(R.id.LogOutbutContacts).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startContactsActivity();
                }
            });

            v.findViewById(R.id.LogOutbutSettings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((LoginActivity) getActivity()).startSettingsActivity();
                }
            });


            return v;
        }
    }

    private void startMapActivity() {

        if(user.getId()==0) {
            if (LogUserId != 0) {
                user.setId(LogUserId);
            } else {
                if (LOAD_ID != 0) {
                    user.setId(LOAD_ID);
                }
            }
        }

        saveID(user.getId());

        Intent intent=new Intent(this, MapActivity.class);
        intent.putExtra("MapUserID", user.getId());
        intent.putExtra("MapRadius", mapRadius);
        startActivity(intent);
    }

    private void startMapNotAuthActivity() {
        Intent intent=new Intent(this, MapNotAuthActivity.class);
        intent.putExtra("MapRadius", mapRadius);
        startActivity(intent);
    }

    private void startInformationActivityy() {
        startActivity(new Intent(this, InformationActivityy.class));
    }

    private void startSettingsActivity() {

        Intent intent=new Intent(this, SettingsActivity.class);
        intent.putExtra("radius", mapRadius);
        startActivity(intent);
    }

    private void startContactsActivity() {
        startActivity(new Intent(this, ContactsActivity.class));
    }

    private VKRequest.VKRequestListener mRequestListener = new VKRequest.VKRequestListener() {
        @Override
        public void onComplete(VKResponse response) {
            try {
                VKList<VKApiUserFull> list = (VKList<VKApiUserFull>) response.parsedModel;
                VKApiUserFull uservk = list.get(0);

                user.setVkId(uservk.id);
                user.setFirstName(uservk.first_name);
                user.setLastName(uservk.last_name);
                user.setId(-1);

                HandlerUser.compareUser(user);


                String temp= "Здравствуйте, " + user.getFirstName() + " " + user.getLastName();
                Toast.makeText(
                        getApplicationContext(),
                        temp,
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
            }
        }
    };

    private void saveID(int id) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_ID, Integer.toString(id));
        ed.commit();
    }
    private void loadID() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_ID, "");
        LOAD_ID=Integer.parseInt(savedText);
    }
    private void cleanID(){
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_ID, Integer.toString(0));
        ed.commit();
    }

}