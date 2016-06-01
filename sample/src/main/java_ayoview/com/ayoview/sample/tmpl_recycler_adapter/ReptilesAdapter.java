/*
 * Copyright (c) 2015 Hannes Dorfmann.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ayoview.sample.tmpl_recycler_adapter;

import android.app.Activity;

import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.GeckoAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.ReptilesFallbackDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.SnakeListItemAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.model.DisplayableItem;

import org.ayo.app.adapter.ListDelegationAdapter;

import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class ReptilesAdapter extends ListDelegationAdapter<List<DisplayableItem>> {

  public ReptilesAdapter(Activity activity, List<DisplayableItem> items) {

    // Delegates
    this.delegatesManager.addDelegate(new GeckoAdapterDelegate(activity));
    this.delegatesManager.addDelegate(new SnakeListItemAdapterDelegate(activity));
    this.delegatesManager.setFallbackDelegate(new ReptilesFallbackDelegate(activity));

    setItems(items);
  }
}
