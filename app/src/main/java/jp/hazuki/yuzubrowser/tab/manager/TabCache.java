/*
 * Copyright (c) 2017 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package jp.hazuki.yuzubrowser.tab.manager;

import java.util.LinkedHashMap;

class TabCache extends LinkedHashMap<Long, MainTabData> {

    private final OnCacheOverFlowListener mListener;
    private final int mSize;

    TabCache(int cacheSize, OnCacheOverFlowListener listener) {
        super(cacheSize, 0.75f, true);
        mSize = cacheSize;
        mListener = listener;
    }

    @Override
    protected boolean removeEldestEntry(Entry<Long, MainTabData> eldest) {
        boolean result = size() > mSize;
        if (result) {
            mListener.onCacheOverflow(eldest.getValue());
        }
        return result;
    }

    @Override
    public MainTabData put(Long key, MainTabData value) {
        if (value != null)
            return super.put(key, value);
        return null;
    }

    public interface OnCacheOverFlowListener {
        void onCacheOverflow(MainTabData tabData);
    }
}
