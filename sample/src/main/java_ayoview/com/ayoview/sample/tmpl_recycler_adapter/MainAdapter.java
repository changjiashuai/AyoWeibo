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
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.AdvertisementAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.CatAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.DogAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.GeckoAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.SnakeListItemAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.model.DisplayableItem;

import org.ayo.app.adapter.AdapterDelegatesManager;
import org.ayo.view.recycler.adapter.AyoViewHolder;

import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class MainAdapter extends RecyclerView.Adapter<AyoViewHolder> {

  private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;
  private List<DisplayableItem> items;

  public MainAdapter(Activity activity, List<DisplayableItem> items) {
    this.items = items;

    // Delegates
    delegatesManager = new AdapterDelegatesManager<>();
    delegatesManager.addDelegate(new AdvertisementAdapterDelegate(activity));
    delegatesManager.addDelegate(new CatAdapterDelegate(activity));
    delegatesManager.addDelegate(new DogAdapterDelegate(activity));
    delegatesManager.addDelegate(new GeckoAdapterDelegate(activity));
    delegatesManager.addDelegate(new SnakeListItemAdapterDelegate(activity));

  }

  @Override public int getItemViewType(int position) {
    return delegatesManager.getItemViewType(items, position);
  }

  @Override public AyoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return delegatesManager.onCreateViewHolder(parent, viewType);
  }

  @Override public void onBindViewHolder(AyoViewHolder holder, int position) {
    delegatesManager.onBindViewHolder(items, position, holder);
  }

  @Override public int getItemCount() {
    return items.size();
  }
}
