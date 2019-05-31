package com.dart.wgw.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author:Admin
 * Time:2019/5/31 10:48
 * 描述：
 */
public class Funs {
    /**
     * 把view保存成图片
     * @param view
     * @param name
     */
    public static void viewSaveToImage(View view,String name) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);

        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);
        File file = new File("/sdcard/wgw/");
        if(!file.exists())
            file.mkdirs();
        FileOutputStream fos=null;
        try {
            fos = new FileOutputStream(file.getPath() +"/"+name+".png");
            cachebmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            System.out.println("saveBmp is here");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //保存到sdk卡
//        FileOutputStream fos;
//        String imagePath = "";
//        try {
//            // 判断手机设备是否有SD卡
//            boolean isHasSDCard = Environment.getExternalStorageState().equals(
//                    android.os.Environment.MEDIA_MOUNTED);
//            if (isHasSDCard) {
//                // SD卡根目录
//                File sdRoot = Environment.getExternalStorageDirectory();
//                File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis()+".png");
//                fos = new FileOutputStream(file);
//                imagePath = file.getAbsolutePath();
//            } else
//                throw new Exception("创建文件失败!");
//
//            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
//
//            fos.flush();
//            fos.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        view.destroyDrawingCache();
    }


    /**
     * 把view转化成bitmap
     * @param v
     * @return
     */
    public static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;

    }

    public static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT> Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
