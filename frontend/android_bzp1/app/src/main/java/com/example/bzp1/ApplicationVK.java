package com.example.bzp1;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class ApplicationVK extends android.app.Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Toast.makeText(ApplicationVK.this, "AccessToken invalidated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ApplicationVK.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            vkAccessTokenTracker.startTracking();
        }
        catch (Exception e) {
            String message = e.getMessage();
            Log.i("bzp1", message);
        }
        try {
            VKSdk.initialize(this);
        }
        catch (Exception e) {
            String message = e.getMessage();
            Log.i("bzp1", message);
        }
    }
}