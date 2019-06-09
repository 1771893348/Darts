package com.dart.wgw.util;

import androidx.fragment.app.Fragment;

public class BUManager {
    /**
     * 根据包名创建fragment
     * @param pkg pakage name
     * @return fragment object
     */
    public static Fragment getFragment(String pkg){
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(pkg).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  fragment;
    }
}
