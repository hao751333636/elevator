package com.sinodom.elevator.activity.elevator.rescue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.WbTypeAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.rescue.DictBean;
import com.sinodom.elevator.util.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 误报安装调试
 */
public class WbAztsActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvReason)
    TextView tvReason;
    @BindView(R.id.llReason)
    LinearLayout llReason;
    @BindView(R.id.etDescribed)
    EditText etDescribed;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    private PopupWindow mTypePopupWindow;
    private List<DictBean> mList = new ArrayList<>();
    private String mId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_azts);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(Constants.Code.WBAZTS_OK, new Intent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ivBack, R.id.llReason, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(Constants.Code.WBAZTS_OK, new Intent());
                finish();
                break;
            case R.id.llReason:
                if (mList == null || mList.size() == 0) {
                    showLoading("加载中...");
                    loadData();
                } else {
                    showTypePopupWindow(llReason);
                }
                break;
            case R.id.tvCommit:
                CharSequence tvReasonCs = tvReason.getText();
                if (TextUtils.isEmpty(tvReasonCs.toString())) {
                    showToast("请选择误报原因");
                    return;
                }
                CharSequence etDescribedCs = etDescribed.getText();
                if (TextUtils.isEmpty(etDescribedCs.toString())) {
                    showToast("请填写警情描述");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("reasonId", mId);
                intent.putExtra("reasonDesc", etDescribed.getText().toString().trim());
                setResult(Constants.Code.WBAZTS_OK, intent);
                finish();
                break;
        }
    }

    //加载数据
    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID() + "");
        Call<ResponBean> call = server.getService().getDictListByRoot(map, "MisinformationReason");
        mRetrofitManager.call(call,new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<DictBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<DictBean>>() {
                        }.getType());
                        mList.clear();
                        mList.addAll(list);
                        showTypePopupWindow(llReason);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if(!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    private void showTypePopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.popup_wb_type, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        WbTypeAdapter adapter = new WbTypeAdapter(context);
        listView.setAdapter(adapter);
        adapter.setData(mList);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mTypePopupWindow.dismiss();
                mId = mList.get(position).getID();
                tvReason.setText(mList.get(position).getFullPath());
            }
        });
        mTypePopupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 150), true);

        mTypePopupWindow.setTouchable(true);

        mTypePopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        mTypePopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.white));

        // 设置好参数之后再show
        mTypePopupWindow.showAsDropDown(view, 0, DisplayUtil.dip2px(context, 1));
        mTypePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                Drawable nav_up = getResources().getDrawable(R.mipmap.ic_fangchan_choice);
//                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//                tvType.setCompoundDrawables(null, null, nav_up, null);
            }
        });
    }
}
