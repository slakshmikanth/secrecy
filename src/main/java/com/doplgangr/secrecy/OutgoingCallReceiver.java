/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.doplgangr.secrecy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.IntentCompat;

import com.doplgangr.secrecy.Settings.Prefs_;
import com.doplgangr.secrecy.Views.DummyViews.LauncherActivity_;
import com.doplgangr.secrecy.Views.MainActivity_;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EReceiver
public class OutgoingCallReceiver extends BroadcastReceiver {
    @Pref
    Prefs_ Pref;

    @Override
    public void onReceive(final Context context, Intent intent) {

        // Gets the intent, check if it matches our secret code
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction()) &&
                intent.getExtras() != null) {
            Intent launcher = new Intent(context, MainActivity_.class);
            //These flags are added to make the new mainActivity in the home stack.
            //i.e. back button returns to home not dialer.
            launcher.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_TASK_ON_HOME);
            String phoneNumber = intent.getExtras().getString(android.content.Intent.EXTRA_PHONE_NUMBER);
            if (Pref.OpenPIN().exists())
                if (("*#" + Pref.OpenPIN().get()).equals(phoneNumber))
                    // Launch the main app!!
                    launchActivity(context, launcher);
        }
    }

    void launchActivity(Context context, Intent launcher) {
        //Try hiding app everytime, prevent reappearance of app icon.
        hideApp(context);
        context.startActivity(launcher);
        // Cancel the call.
        abortBroadcast();
    }

    //Hides the app here.
    //Set the counter to 1, prevent appearance of first time alert in the future
    //Set LauncherActivity to disabled, removing launcher icon.
    void hideApp(final Context context) {

        ComponentName componentToDisable =
                new ComponentName(context.getPackageName(),
                        LauncherActivity_.class.getName());
        if (context.getPackageManager() != null)
            context.getPackageManager()
                    .setComponentEnabledSetting(componentToDisable,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                            PackageManager.DONT_KILL_APP);
    }
}