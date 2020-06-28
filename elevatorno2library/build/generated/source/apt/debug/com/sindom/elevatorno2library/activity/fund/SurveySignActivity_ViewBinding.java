// Generated code from Butter Knife. Do not modify!
package com.sindom.elevatorno2library.activity.fund;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.allen.library.SuperButton;
import com.sindom.elevatorno2library.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SurveySignActivity_ViewBinding implements Unbinder {
  private SurveySignActivity target;

  private View view7f0c0044;

  private View view7f0c006f;

  private View view7f0c0070;

  private View view7f0c006e;

  private View view7f0c006d;

  @UiThread
  public SurveySignActivity_ViewBinding(SurveySignActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SurveySignActivity_ViewBinding(final SurveySignActivity target, View source) {
    this.target = target;

    View view;
    target.rvList = Utils.findRequiredViewAsType(source, R.id.rvList, "field 'rvList'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnCommit, "field 'btnCommit' and method 'click'");
    target.btnCommit = Utils.castView(view, R.id.btnCommit, "field 'btnCommit'", SuperButton.class);
    view7f0c0044 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.clWuye, "method 'click'");
    view7f0c006f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.clYewei, "method 'click'");
    view7f0c0070 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.clMaintenance, "method 'click'");
    view7f0c006e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.clCompany, "method 'click'");
    view7f0c006d = view;
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
    SurveySignActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvList = null;
    target.btnCommit = null;

    view7f0c0044.setOnClickListener(null);
    view7f0c0044 = null;
    view7f0c006f.setOnClickListener(null);
    view7f0c006f = null;
    view7f0c0070.setOnClickListener(null);
    view7f0c0070 = null;
    view7f0c006e.setOnClickListener(null);
    view7f0c006e = null;
    view7f0c006d.setOnClickListener(null);
    view7f0c006d = null;
  }
}
