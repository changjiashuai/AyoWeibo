package com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayoview.sample.tmpl_recycler_adapter.model.DisplayableItem;
import com.ayoview.sample.tmpl_recycler_adapter.model.Snake;
import com.cowthan.sample.R;

import org.ayo.app.adapter.AbsListItemAdapterDelegate;
import org.ayo.view.recycler.adapter.AyoViewHolder;

import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class SnakeListItemAdapterDelegate extends
        AbsListItemAdapterDelegate<Snake, DisplayableItem> {

  private LayoutInflater inflater;

  public SnakeListItemAdapterDelegate(Activity activity) {
    inflater = activity.getLayoutInflater();
  }

  @Override
  protected boolean isForViewType(@NonNull DisplayableItem item, List<DisplayableItem> items,
                                  int position) {
    return item instanceof Snake;
  }

  @NonNull @Override
  public AyoViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
    return new AyoViewHolder(
        inflater.inflate(R.layout.adapter_delegate_item_snake, parent, false));
  }

  @Override protected void onBindViewHolder(@NonNull Snake snake,
      @NonNull AyoViewHolder vh) {
    TextView name;
    TextView race;
    name = (TextView) vh.findViewById(R.id.name);
    race = (TextView) vh.findViewById(R.id.race);
    name.setText(snake.getName());
    race.setText(snake.getRace());
  }

  static class SnakeViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView race;

    public SnakeViewHolder(View itemView) {
      super(itemView);
      name = (TextView) itemView.findViewById(R.id.name);
      race = (TextView) itemView.findViewById(R.id.race);
    }
  }
}
