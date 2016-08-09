package com.heim.waterballprogress;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.nineoldandroids.animation.ObjectAnimator;

public class MainActivity extends AppCompatActivity {

    private WaterBallView mWbv;
    private ImageView mIv;
    private DisplayMetrics metrics=new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  initView();
        initAnimation();
    }

    private void initAnimation() {
        mIv = (ImageView) findViewById(R.id.iv);

        Path path = new Path();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        path.moveTo(widthPixels/2,heightPixels);

        path.rQuadTo(widthPixels/4,(heightPixels*4)/5,-widthPixels,-heightPixels);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIv, "translationY", 0, -800);
                    objectAnimator.setDuration(5000);
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
            objectAnimator.start();

       




    }
}
