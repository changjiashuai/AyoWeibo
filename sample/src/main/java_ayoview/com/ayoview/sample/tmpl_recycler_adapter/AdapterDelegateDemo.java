package com.ayoview.sample.tmpl_recycler_adapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.AdvertisementAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.CatAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.DogAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.GeckoAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.adapterdelegates.SnakeListItemAdapterDelegate;
import com.ayoview.sample.tmpl_recycler_adapter.model.Advertisement;
import com.ayoview.sample.tmpl_recycler_adapter.model.Cat;
import com.ayoview.sample.tmpl_recycler_adapter.model.DisplayableItem;
import com.ayoview.sample.tmpl_recycler_adapter.model.Dog;
import com.ayoview.sample.tmpl_recycler_adapter.model.Gecko;
import com.ayoview.sample.tmpl_recycler_adapter.model.Snake;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.app.base.ActivityAttacher;
import org.ayo.lang.Lists;
import org.ayo.view.recycler.AyoAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterDelegateDemo extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.adapter_delegate_activity_main);

    RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));

    AyoAdapter adapter = new AyoAdapter(getActivity(), null, Lists.newArrayList(
            new AdvertisementAdapterDelegate(getActivity()),
            new CatAdapterDelegate(getActivity()),
            new DogAdapterDelegate(getActivity()),
            new GeckoAdapterDelegate(getActivity()),
            new SnakeListItemAdapterDelegate(getActivity())
    ));
    rv.setAdapter(adapter);

    adapter.notifyDataSetChanged(getAnimals());

    findViewById(R.id.reptielsActivity).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ActivityAttacher.startActivity(getActivity(), ReptilesActivity.class);
      }
    });
  }

  private List<DisplayableItem> getAnimals() {
    List<DisplayableItem> animals = new ArrayList<>();

    animals.add(new Cat("American Curl"));
    animals.add(new Cat("Baliness"));
    animals.add(new Cat("Bengal"));
    animals.add(new Cat("Corat"));
    animals.add(new Cat("Manx"));
    animals.add(new Cat("Nebelung"));
    animals.add(new Dog("Aidi"));
    animals.add(new Dog("Chinook"));
    animals.add(new Dog("Appenzeller"));
    animals.add(new Dog("Collie"));
    animals.add(new Snake("Mub Adder", "Adder"));
    animals.add(new Snake("Texas Blind Snake", "Blind snake"));
    animals.add(new Snake("Tree Boa", "Boa"));
    animals.add(new Gecko("Fat-tailed", "Hemitheconyx"));
    animals.add(new Gecko("Stenodactylus", "Dune Gecko"));
    animals.add(new Gecko("Leopard Gecko", "Eublepharis"));
    animals.add(new Gecko("Madagascar Gecko", "Phelsuma"));
    animals.add(new Advertisement());
    animals.add(new Advertisement());
    animals.add(new Advertisement());
    animals.add(new Advertisement());
    animals.add(new Advertisement());

    Collections.shuffle(animals);
    return animals;
  }
}
