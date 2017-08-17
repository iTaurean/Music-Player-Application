/*
 * Project: KomectWalking
 * 
 * File Created at 2016-5-16
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.android.lvxin.musicplayer.event;

import org.greenrobot.eventbus.EventBus;

/**
 * The type Base event bus.
 */
public class BaseEventBus {

    public String getEventname() {
        return this.getClass().getName();
    }

    public void post() {
        EventBus.getDefault().post(this);
    }

}
