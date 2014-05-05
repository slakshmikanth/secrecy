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

package com.doplgangr.secrecy.Views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;

@EActivity
public class LauncherActivity extends ActionBarActivity {
    @AfterInject
    void launch() {
        Intent launcher = new Intent(this, MainActivity_.class);
        startActivity(launcher);
    }
}
