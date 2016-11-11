package com.renos.medidiet.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.etsy.android.grid.StaggeredGridView;
import com.renos.medidiet.RecipeApplication;
import com.renos.medidiet.RecipeConfig;
import medidiet.R;
import com.renos.medidiet.adapter.AdapterFavorite;
import com.renos.medidiet.adapter.AdapterWorcipeByCategory;
import com.renos.medidiet.item.ItemCategory;
import com.renos.medidiet.item.ItemWorcipe;
import com.renos.medidiet.utility.DatabaseHandler;
import com.renos.medidiet.utility.Day1DatabaseHandler;
import com.renos.medidiet.utility.JsonConfig;
import com.renos.medidiet.utility.JsonUtils;
import com.renos.medidiet.utility.Pojo;
import com.renos.medidiet.utility.Preferences;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Day1Activity extends AppCompatActivity {

    StaggeredGridView gridView;
    Day1DatabaseHandler dbDay1;
    AdapterFavorite adapter;
    TextView textView;
    JsonUtils util;
    List<Pojo> list;
    ArrayList<String> array_worcipe, array_worcipe_cat_name, array_cid, array_cat_id, array_cat_name, array_title, array_image, array_desc, array_cont, array_ingre, array_proteins, array_calories, array_fat, array_carbs, array_time;
    String[] str_worcipe, str_worcipe_cat_name, str_cid, str_cat_id, str_cat_name, str_title, str_image, str_desc, str_cont, str_ingre, str_proteins, str_calories, str_fat, str_carbs, str_time;



    Pojo pojo;
    private Day1DatabaseHandler.DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Preferences.applyTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setupActionBar();


        gridView = (StaggeredGridView) findViewById(R.id.gridView3);
        dbDay1 = new Day1DatabaseHandler(getApplicationContext());
        dbManager = Day1DatabaseHandler.DatabaseManager.INSTANCE;
        dbManager.init(getApplicationContext());
        util = new JsonUtils(getApplicationContext());

        list = dbDay1.getAllData();

        adapter = new AdapterFavorite(Day1Activity.this,
                R.layout.activity_favorite_items, list);
        gridView.setAdapter(adapter);

        renderViewBanner();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                pojo = list.get(position);
                int pos = Integer.parseInt(pojo.getCatId());

                Intent intplay = new Intent(Day1Activity.this, DetailActivity.class);
                intplay.putExtra("POSITION", pos);
                intplay.putExtra("CATEGORY_ITEM_CID", str_cid);
                intplay.putExtra("CATEGORY_ITEM_NAME", str_cat_name);
                intplay.putExtra("CATEGORY_ITEM_CAT_ID", str_cat_id);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEIMAGE", str_image);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEHEADING", str_title);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEDESCRI", str_desc);
                intplay.putExtra("CATEGORY_ITEM_WORCIPECONTENT", str_cont);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEINGRE", str_ingre);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEPROTEINS", str_proteins);
                intplay.putExtra("CATEGORY_ITEM_WORCIPECALORIES", str_calories);
                intplay.putExtra("CATEGORY_ITEM_WORCIPEFAT", str_fat);
                intplay.putExtra("CATEGORY_ITEM_WORCIPECARBS", str_carbs);
                intplay.putExtra("CATEGORY_ITEM_WORCIPETIME", str_time);

                startActivity(intplay);
            }
        });

    }

    public void onDestroy() {
        if (!dbManager.isDatabaseClosed())
            dbManager.closeDatabase();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!dbManager.isDatabaseClosed())
            dbManager.closeDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();



        list = dbDay1.getAllData();
        adapter = new AdapterFavorite(Day1Activity.this,
                R.layout.activity_favorite_items, list);
        gridView.setAdapter(adapter);

        array_worcipe = new ArrayList<String>();
        array_worcipe_cat_name = new ArrayList<String>();
        array_cid = new ArrayList<String>();
        array_cat_id = new ArrayList<String>();
        array_cat_name = new ArrayList<String>();
        array_title = new ArrayList<String>();
        array_image = new ArrayList<String>();
        array_desc = new ArrayList<String>();
        array_cont = new ArrayList<String>();
        array_ingre = new ArrayList<String>();
        array_proteins = new ArrayList<String>();
        array_calories = new ArrayList<String>();
        array_fat = new ArrayList<String>();
        array_carbs = new ArrayList<String>();
        array_time = new ArrayList<String>();


        str_worcipe = new String[array_worcipe.size()];
        str_worcipe_cat_name = new String[array_worcipe_cat_name.size()];
        str_cid = new String[array_cid.size()];
        str_cat_id = new String[array_cat_id.size()];
        str_cat_name = new String[array_cat_name.size()];
        str_title = new String[array_title.size()];
        str_image = new String[array_image.size()];
        str_cont = new String[array_cont.size()];
        str_desc = new String[array_desc.size()];
        str_ingre = new String[array_ingre.size()];
        str_proteins = new String[array_proteins.size()];
        str_calories = new String[array_calories.size()];
        str_fat = new String[array_fat.size()];
        str_carbs = new String[array_carbs.size()];
        str_time = new String[array_time.size()];





        for (int j = 0; j < list.size(); j++) {
            Pojo objAllBean = list.get(j);


            array_cat_id.add(objAllBean.getCatId());
            str_cat_id = array_cat_id.toArray(str_cat_id);

            array_cid.add(String.valueOf(objAllBean.getCId()));
            str_cid = array_cid.toArray(str_cid);

            array_cat_name.add(objAllBean.getCategoryName());
            str_cat_name = array_cat_name.toArray(str_cat_name);

            array_title.add(String.valueOf(objAllBean.getWorcipeHeading()));
            str_title = array_title.toArray(str_title);

            array_image.add(String.valueOf(objAllBean.getWorcipeImage()));
            str_image = array_image.toArray(str_image);

            array_desc.add(String.valueOf(objAllBean.getWorcipeDesc()));
            str_desc = array_desc.toArray(str_desc);

            array_cont.add(String.valueOf(objAllBean.getWorcipeCont()));
            str_cont = array_cont.toArray(str_cont);

            array_ingre.add(String.valueOf(objAllBean.getWorcipeIngre()));
            str_ingre = array_ingre.toArray(str_ingre);

            array_calories.add(String.valueOf(objAllBean.getWorcipeCalories()));
            str_calories = array_calories.toArray(str_calories);

            array_proteins.add(String.valueOf(objAllBean.getWorcipeProteins()));
            str_proteins = array_proteins.toArray(str_proteins);

            array_fat.add(String.valueOf(objAllBean.getWorcipeFat()));
            str_fat = array_fat.toArray(str_fat);

            array_carbs.add(String.valueOf(objAllBean.getWorcipeCarbs()));
            str_carbs = array_carbs.toArray(str_carbs);

            array_time.add(String.valueOf(objAllBean.getWorcipeTime()));
            str_time = array_time.toArray(str_time);


        }

    }






    private void totalcalories (){
        try {
            list = dbDay1.getAllData();
            int temp = 0;
            int sum = 0;
            for (int j = 0; j < list.size(); j++) {
                Pojo objAllBean1 = list.get(j);
                array_calories.add((objAllBean1.getWorcipeCalories()));
                temp = Integer.parseInt(objAllBean1.getWorcipeCalories());
                sum += temp;
            }
            Toast.makeText(getApplicationContext(), "Total calories of Day 1 = " + sum, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Calories might have text in them", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add( Menu.NONE, R.id.menu_calc, Menu.NONE, "Add").setIcon(R.drawable.icon_calories)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

      //  menu.add( Menu.NONE, R.id.menu_calc1, Menu.NONE, "Add").setIcon(R.drawable.icon_calories)
              //  .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Intent intent = null;
                intent = new Intent(Day1Activity.this, MyDietActivity.class);
                return true;

            case R.id.menu_calc:
                totalcalories();
                return true;

            //case R.id.menu_calc1:
                //addRecipes();
               // return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
        bar.setTitle(R.string.MyDietDay1);
    }

    private void renderViewBanner() {
        final AdView adView = (AdView) findViewById(R.id.adView);
        final ViewGroup bannerViewGroup = (ViewGroup) findViewById(R.id.container_admob);

        if (RecipeConfig.ADMOB_RECIPE_BANNER && JsonUtils.isNetworkAvailable(Day1Activity.this)) {

            AdRequest adRequest = new AdRequest.Builder().build();
            bannerViewGroup.setVisibility(View.VISIBLE);
            adView.setVisibility(View.VISIBLE);
            adView.loadAd(adRequest);
        } else {
            adView.setVisibility(View.GONE);
            bannerViewGroup.setVisibility(View.GONE);
        }
    }
}