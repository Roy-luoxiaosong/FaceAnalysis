package com.roy.faceanalysis;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by Roy on 2016/6/27.
 */
public class FaceppDetect {
    public interface CallBack{
        void succeed(JSONObject result);
        void error(FaceppParseException exception);
    }
    public static void detect(final Bitmap bm,final CallBack callBack){
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    HttpRequests requests = new HttpRequests(Constant.KEY,Constant.SECRET,true,true);
                    Bitmap bmSmall = Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight());
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bmSmall.compress(Bitmap.CompressFormat.JPEG,100,bos);
                    byte[] arrays = bos.toByteArray();
                    PostParameters params = new PostParameters();
                    params.setImg(arrays);
                    JSONObject jsonObject = requests.detectionDetect(params);

                    //Log.d("TAG",jsonObject.toString());

                    if(callBack!=null){
                        callBack.succeed(jsonObject);
                    }
                } catch (FaceppParseException e) {
                    e.printStackTrace();
                    if(callBack!=null){
                        callBack.error(e);
                    }
                }
            }
        }).start();
    }
}
