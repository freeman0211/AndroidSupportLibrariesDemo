package com.freeman.example.apitest.fragment;

/**
 * Created by freeman on 9/23/15.
 */
public enum TabFragment {

    Android("Android", new AndroidFragment()),
    IOS ("iOS", new IosFragment());

    private String title;
    private FragmentBase fragment;

    TabFragment(String title, FragmentBase fragment) {
        this.title = title;
        this.fragment = fragment;
    }


    public String getTitle () {
        return title;
    }

    public FragmentBase getFragment () {
        return fragment;
    }

}
