/*
 * Class Name: SlideShowAdapter
 *
 * Version: Version 1.0
 *
 * Date : December 3, 2018
 *
 * Copyright (c) Team 25, CMPUT301, University of Alberta - All Rights Reserved. You may use, distribute, or modify this code under terms and conditions of the Code of Students Behavior at University of Alberta
 */

package com.cmput301f18t25.healthx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
/**
 * This is the adapter for the slide show.
 *
 * @author Sandy
 * @version 1.0
 *
 */
public class SlideShowAdapter extends PagerAdapter {

    private Context mcontext;
    private LayoutInflater layoutInflater;

    private ArrayList<Drawable> newList;
    protected Bitmap bitmap;


    public SlideShowAdapter(Context mcontext, ArrayList<Drawable> alist) {
        this.mcontext = mcontext;
        this.newList =  alist;
    }



        @Override
    public int getCount() {
        return this.newList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slideshow_customlayout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);


        imageView.setImageDrawable(newList.get(position));

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
