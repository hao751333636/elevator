package com.sinodom.elevator.activity.inspect;

import android.os.Bundle;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.fragment.inspect.main.InspectFragment;


public class InspectListActivity extends BaseActivity {

    public InspectFragment newsFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_list);
        newsFragment = new InspectFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tabPager, newsFragment).show(newsFragment).commit();
    }
}
