package com.gym.looppicturesdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gym.looppicturesdemo.view.LoopPicture;

public class MainActivity extends AppCompatActivity {

    private LoopPicture loopPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loopPicture = (LoopPicture)this.findViewById(R.id.loopPicture);

        String[] imagesRes = new String[] {
         "http://pic.sc.chinaz.com/files/pic/pic9/201604/apic20400.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201602/apic19022.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201603/fpic430.jpg",
         "http://pics.sc.chinaz.com/files/pic/pic9/201605/apic20631.jpg"};
        loopPicture.setImageUrl(imagesRes, true);
/*        int[] imagesRes = { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d, R.drawable.e };
        loopPicture.setImageRes(imagesRes, true);*/
        String[] titles = {"111111111111111111","2222222222222222",
                "3333333333333","4444444444444","5555555555"};
        loopPicture.setImageTitle(titles);

    }
}
