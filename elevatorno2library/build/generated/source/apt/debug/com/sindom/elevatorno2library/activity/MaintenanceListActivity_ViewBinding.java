// Generated code from Butter Knife. Do not modify!
package com.sindom.elevatorno2library.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.flyco.tablayout.SlidingTabLayout;
import com.sindom.elevatorno2library.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MaintenanceListActivity_ViewBinding implements Unbinder {
  private MaintenanceListActivity target;

  @UiThread
  public MaintenanceListActivity_ViewBinding(MaintenanceListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MaintenanceListActivity_ViewBinding(MaintenanceListActivity target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.vp_2, "field 'viewPager'", ViewPager.class);
    target.commonTabLayout = Utils.findRequiredViewAsType(source, R.id.tl_7, "field 'commonTabLayout'", SlidingTabLayout.class);
    target.flContent = Utils.findRequiredViewAsType(source, R.id.flContent, "field 'flContent'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MaintenanceListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.commonTabLayout = null;
    target.flContent = null;
  }
}
