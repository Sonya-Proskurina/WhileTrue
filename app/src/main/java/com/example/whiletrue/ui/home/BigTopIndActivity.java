package com.example.whiletrue.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.whiletrue.R;
import com.example.whiletrue.ui.home.adapter.AdapterReviews;
import com.example.whiletrue.ui.home.model.Reviews;
import com.trex.lib.ThumbIndicator;

import java.util.ArrayList;
import java.util.List;

public class BigTopIndActivity extends AppCompatActivity {
    List<String> mUrls;
    ViewPager mVpMain;
    ThumbIndicator mIndicator;
    private int indexToDelete = -1;
    int likes = 1;

    RecyclerView recyclerView;
    List<Reviews> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_top_ind);
        getSupportActionBar().hide();
        def();
        setup();

        list = new ArrayList<>();
        list.add(new Reviews("Иванова Анна", "Лучшая мусорка города!", "https://bipbap.ru/wp-content/uploads/2015/02/99ce2806d226f18.jpg", 5));
        list.add(new Reviews("Петров Иван", "Я в восторге!!!", "https://i.pinimg.com/originals/af/78/a3/af78a3a834568505e9d73c718402e82b.jpg", 5));
        list.add(new Reviews("Кузнецов Антон", "Не удобно подходить", "https://yt3.ggpht.com/a/AATXAJzWJ8qWAVOOop9dJZPYfyCsjh6cATVejPOtxfYo=s900-c-k-c0xffffffff-no-rj-mo", 4));

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AdapterReviews adapter = new AdapterReviews(list, this);
        recyclerView.setAdapter(adapter);
    }

    public void deleteImageAfterScroll(View v) {
        if (indexToDelete != -1) return;
        int position = mVpMain.getCurrentItem();
        if ((position >= 0) && (position < mUrls.size()) && (mUrls.size() > 1)) {
            if (position == 0) {
                mVpMain.setCurrentItem(1, true);
            } else {
                mVpMain.setCurrentItem(position - 1, true);
            }
            indexToDelete = position;
        }

    }

    private void def() {
        mVpMain = findViewById(R.id.vpMain);
        mIndicator = findViewById(R.id.indicator);
        mUrls = getMockImgs();
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (indexToDelete != -1 && i == ViewPager.SCROLL_STATE_IDLE) {
                    mUrls.remove(indexToDelete);
                    adp.notifyDataSetChanged();
                    mIndicator.notifyDataSetChanged();
                    if (indexToDelete == 0) {
                        mVpMain.setCurrentItem(indexToDelete, false);
                        mIndicator.setCurrentItem(indexToDelete, false);
                    }
                    indexToDelete = -1;
                }
            }
        });
    }


    private List<String> getMockImgs() {
        List<String> tmp = new ArrayList<>();
        tmp.add("https://hozmarket24.ru/uploads/all/59/aa/f6/59aaf6175cf2ed5a3255e170251be780/sx-filter__common-thumbnails-MediumWatermark/emkost-dlya-sbora-batareek.jpg");
        tmp.add("http://lavkaurna.ru/uploads/all/77/41/19/7741193b08a014293b5befb5a1fe3325/sx-filter__common-thumbnails-MediumWatermark/emkost-dlya-sbora-batareek.jpg");
        tmp.add("https://ic.pics.livejournal.com/seyfetdin/76928716/57343/57343_original.jpg");
        tmp.add("https://im0-tub-ru.yandex.net/i?id=51de6aa405f553a39d6f6cae46696dec-l&ref=rim&n=13&w=960&h=1200");
        tmp.add("https://images.ru.prom.st/702822987_w640_h640_urna-dlya-batareek.jpg");
        tmp.add("https://kddm.kzn.ru/upload/iblock/bdc/MMB_0884.jpg");
        return tmp;
    }

    VpAdapter adp;

    private void setup() {
        adp = new VpAdapter();
        mVpMain.setAdapter(adp);
        mIndicator.setupWithViewPager(mVpMain, (ArrayList<String>) mUrls, 70);
    }

    public void like(View view) {
        ImageView like = findViewById(R.id.like);
        if (likes == 1) {
            like.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            likes = 0;
        } else {
            likes = 1;
            like.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }

    class VpAdapter extends PagerAdapter {
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View v = getLayoutInflater().inflate(R.layout.thumb_item, container, false);
            ImageView imgSlider = v.findViewById(R.id.imgSlider);
            Glide.with(container.getContext()).load(mUrls.get(position)).into(imgSlider);
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (mUrls.indexOf(object) == -1)
                return POSITION_NONE;
            else
                return super.getItemPosition(object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }
    }

    public void back(View view) {
        finish();
    }
}