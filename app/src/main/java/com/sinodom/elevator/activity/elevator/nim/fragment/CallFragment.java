package com.sinodom.elevator.activity.elevator.nim.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.CreateRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.RoomCallAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.nim.RoomBean;
import com.sinodom.elevator.bean.nim.RoomCallBean;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallFragment extends BaseFragment implements BaseAdapter.OnItemClickListener {

    @BindView(R.id.etKey)
    EditText etKey;
    @BindView(R.id.lvRecord)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    private ListView mListView;
    private RoomCallAdapter mAdapter;
    private List<RoomCallBean> mList = new ArrayList<>();
    private Gson gson = new Gson();
    private int mPageIndex = 1;
    private String mKeyWords = "";
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_call, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //监听软键盘完成按钮
        etKey.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏软键盘
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    CharSequence keyword = etKey.getText();
                    mKeyWords = keyword.toString().trim();
                    showLoading("加载中...");
                    rLoad();
//                    }
                    return true;
                }
                return false;
            }
        });
        //PullToRefreshListView-start
        pullRefreshList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置刷新标签
        pullRefreshList.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        //设置下拉标签
        pullRefreshList.getLoadingLayoutProxy().setPullLabel("准备刷新");
        //设置释放标签
        pullRefreshList.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新 业务代码
                    rLoad();
                } else if (refreshView.isFooterShown()) {
                    //上拉加载更多 业务代码
                    loadData();
                } else {
                    pullRefreshList.onRefreshComplete();
                }
            }
        });
        mListView = pullRefreshList.getRefreshableView();
        mAdapter = new RoomCallAdapter(context);
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        showLoading("加载中...");
        rLoad();
    }

    private void loadData() {
        Call<ResponBean> call = server.getService().getDeptUserList(userId, mKeyWords);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<RoomCallBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RoomCallBean>>() {
                        }.getType());
                        mPageIndex = mPageIndex + 1;
                        for (RoomCallBean bean : list) {
                            if (bean.getLoginName().equals(NimCache.getAccount())) {
                                list.remove(bean);
                                break;
                            }
                        }
                        mList.addAll(list);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                pullRefreshList.onRefreshComplete();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    pullRefreshList.onRefreshComplete();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    private void createRoom(final String roomId) {
        String toAccids = "";
        for (RoomCallBean bean : mList) {
            if (bean.isSelect()) {
                toAccids = toAccids + bean.getLoginName() + ",";
            }
        }
        toAccids = toAccids.substring(0, toAccids.length() - 1);
        Call<ResponBean> call = server.getService().appSendBatchAttachMsg(NimCache.getAccount(), toAccids, roomId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        createOk(roomId);
                        Logger.json(response.body().getData());
                        List<RoomBean.UserListBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RoomBean.UserListBean>>() {
                        }.getType());
                        RoomBean bean = new RoomBean();
                        bean.setCreator(NimCache.getAccount());
                        bean.setRoomId(roomId);
                        bean.setRoomName(roomId);
                        bean.setUserList(list);
                        //创建房间 调用我们接口 暂时模拟
                        Intent intent = new Intent(context, ChatRoomActivity.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("roomName", roomId);
                        intent.putExtra("creator", NimCache.getAccount());
                        intent.putExtra("isCreate", true);
                        intent.putExtra("bean", bean);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        showToast("视频发起失败");
                        CreateRoomActivity activity = (CreateRoomActivity) getActivity();
                        activity.isCall = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CreateRoomActivity activity = (CreateRoomActivity) getActivity();
                    activity.isCall = false;
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                    CreateRoomActivity activity = (CreateRoomActivity) getActivity();
                    activity.isCall = false;
                }
            }
        });
    }

    private void createOk(String roomId) {
        String userIds = "";
        for (RoomCallBean bean : mList) {
            if (bean.isSelect()) {
                userIds = userIds + bean.getUserID() + ",";
            }
        }
        userIds = userIds + userId;
        Call<ResponBean> call = server.getService().createAPPAlarmRoomRecord(userId, roomId, userIds);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {
        if (!pullRefreshList.isRefreshing()) {
            int i = 0;
            for (RoomCallBean bean : mList) {
                if (bean.isSelect()) {
                    i++;
                }
            }
            if (i == 3 && !mList.get(position).isSelect()) {
                showToast("最多选择3人！");
                return;
            }
            mList.get(position).setSelect(!mList.get(position).isSelect());
            mAdapter.setData(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    //刷新数据
    public void rLoad() {
        mPageIndex = 1;
        mList.clear();
        loadData();
    }

    @OnClick({R.id.bLoad, R.id.tvCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bLoad:
                showLoading("加载中...");
                rLoad();
                break;
            case R.id.tvCall:
                boolean isSelect = false;
                for (RoomCallBean bean : mList) {
                    if (bean.isSelect()) {
                        isSelect = true;
                        break;
                    }
                }
                if (!isSelect) {
                    showToast("请选择接收人");
                    return;
                }
                if (!TextUtils.isEmpty(NimCache.getAccount())) {
                    if (ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
                        ChatRoomActivity activity = ActivityCollector.getActivity(ChatRoomActivity.class);
                        activity.finish();
                    }
                    //权限申请
                    permission();
                } else {
                    try {
                        String account = manager.getSession().getLoginName();
                        if (!TextUtils.isEmpty(account)) {
                            nimLogin(account, "123456");
                        } else {
                            showToast("获取视频账号失败！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("获取视频账号失败！");
                    }
                }
                break;
        }
    }

    /**
     * 创建会议频道
     */
    private void createChannel(final String roomId) {
        AVChatManager.getInstance().createRoom(roomId, getString(R.string.app_name), new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                hideLoading();
                avChatChannelInfo.getTimetagMs();
                CreateRoomActivity activity = (CreateRoomActivity) getActivity();
                activity.isCall = true;
                createRoom(roomId);
            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(context, "创建房间失败, code:" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(context, "创建房间异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AbortableFuture<LoginInfo> loginRequest;

    private void nimLogin(final String account, final String token) {
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                onLoginDone();
                NimCache.setAccount(account);
                saveLoginInfo(account, token);
                showToast("登录成功！");
                //权限申请
                permission();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    showToast("帐号或密码错误");
                } else {
                    showToast("登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                showToast("登录视频服务器异常");
            }
        });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void onLoginDone() {
        loginRequest = null;
    }


    public void permission() {
        boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
        boolean permission1 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
        if (permission == true && permission1 == true) {
            showLoading("正在发起视频！");
            createChannel(UUID.randomUUID().toString());
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLoading("正在发起视频！");
                createChannel(UUID.randomUUID().toString());
            } else {
                getPermission("请授权APP使用摄像头和录音权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getPermission(String text) {
        new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(text)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                        startActivity(localIntent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            //更新界面数据，如果数据还在下载中，就显示加载框

        } else {
            //关闭加载框
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        //去服务器下载数据
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
