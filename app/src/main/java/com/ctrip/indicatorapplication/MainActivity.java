package com.ctrip.indicatorapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ctrip.indicatorapplication.indicator.IndicatorView;

/**
 * @author Zhenhua on 2018/1/24.
 * @email zhshan@ctrip.com ^.^
 */

public class MainActivity extends Activity {
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final IndicatorView indicatorView = findViewById(R.id.indicator);
        indicatorView.init(5);
        TextView click = (TextView) findViewById(R.id.test);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                indicatorView.smoothSlideTo(index % 5);
            }
        });
    }
}
