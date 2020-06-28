// Generated code from Butter Knife. Do not modify!
package com.sindom.elevatorno2library.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.sindom.elevatorno2library.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ElevatorMaintenanceActivity_ViewBinding implements Unbinder {
  private ElevatorMaintenanceActivity target;

  private View view7f0c00d5;

  private View view7f0c00d1;

  private View view7f0c00d7;

  private View view7f0c00d6;

  private View view7f0c00d2;

  private View view7f0c00d3;

  private View view7f0c00d4;

  @UiThread
  public ElevatorMaintenanceActivity_ViewBinding(ElevatorMaintenanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ElevatorMaintenanceActivity_ViewBinding(final ElevatorMaintenanceActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.llToday, "method 'click'");
    view7f0c00d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llOut, "method 'click'");
    view7f0c00d1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llWait, "method 'click'");
    view7f0c00d7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llTodayComplete, "method 'click'");
    view7f0c00d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llPlan, "method 'click'");
    view7f0c00d2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llRecord, "method 'click'");
    view7f0c00d3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.llTemp, "method 'click'");
    view7f0c00d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f0c00d5.setOnClickListener(null);
    view7f0c00d5 = null;
    view7f0c00d1.setOnClickListener(null);
    view7f0c00d1 = null;
    view7f0c00d7.setOnClickListener(null);
    view7f0c00d7 = null;
    view7f0c00d6.setOnClickListener(null);
    view7f0c00d6 = null;
    view7f0c00d2.setOnClickListener(null);
    view7f0c00d2 = null;
    view7f0c00d3.setOnClickListener(null);
    view7f0c00d3 = null;
    view7f0c00d4.setOnClickListener(null);
    view7f0c00d4 = null;
  }
}
