package com.example.proj1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageItem {
    String ImageDes;
    Bitmap OurImage;


    public ImageItem(String imageDes,Bitmap bitmap) {
        this.ImageDes=imageDes;
        this.OurImage=bitmap;
    }

    public String getImageDes(){
        return ImageDes;
    }
    public void setImageDes(String ImageDes){
        this.ImageDes=ImageDes;
    }
    public Bitmap getOurImage(){
        return OurImage;
    }
    public void setOurImage(Bitmap OurImage){
       this.OurImage=OurImage;
    }


}


