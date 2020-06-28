// Generated code from Butter Knife. Do not modify!
package com.sindom.elevatorno2library.activity.fund;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.sindom.elevatorno2library.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignActivity_ViewBinding implements Unbinder {
  private SignActivity target;

  private View view7f0c0044;

  @UiThread
  public SignActivity_ViewBinding(SignActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignActivity_ViewBinding(final SignActivity target, View source) {
    this.target = target;

    View view;
    target.signaturePad = Utils.findRequiredViewAsType(source, R.id.signature_pad, "field 'signaturePad'", SignaturePad.class);
    view = Utils.findRequiredView(source, R.id.btnCommit, "method 'click'");
    view7f0c0044 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SignActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.signaturePad = null;

    view7f0c0044.setOnClickListener(null);
    view7f0c0044 = null;
  }
}
