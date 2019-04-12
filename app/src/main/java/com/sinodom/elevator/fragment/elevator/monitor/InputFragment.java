package com.sinodom.elevator.fragment.elevator.monitor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.monitor.DebugActivity;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.socket.NetProxy;
import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/30.
 * 设备调试——设备输入
 */

public class InputFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tvEquipment)
    TextView tvEquipment;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.etInput)
    EditText etInput;
    @BindView(R.id.tvAddress)
    public TextView tvAddress;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.tvStateImg)
    TextView tvStateImg;
    @BindView(R.id.llState)
    LinearLayout llState;
    private AlertDialog.Builder mAlertDialog;
    //0维保开始 1维保结束
    private int mType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_input, null);
        unbinder = ButterKnife.bind(this, view);
        mAlertDialog = new AlertDialog.Builder(context);
        return view;
    }

//    public void setE2Data(String str) {
//        hideLoading();
////        String[] string = str.split(" ");
////        if (!HexUtils.checkCode(str).equals(string[string.length - 1])) {
////            showToast("数据出错！");
////            return;
////        }
//        String state = str.substring(3, 5);
//        String code1 = str.substring(6, 23);
//        String code2 = str.substring(24, 83);
//        String address = str.substring(84, 173);
//        String code3 = str.substring(174, 191);
//        showAlertE2(state,
//                HexUtils.toStringHex2(code1.replace(" ", "")),
//                HexUtils.toStringHex2(code2.replace(" ", "")),
//                HexUtils.toStringHex2(address.replace(" ", "")));
//    }

    public void setE2Data(String str) {
//        str = "E2 00 45 30 30 30 30 30 37 38 39 39 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 36 37 38 39 30 XX";
        DebugActivity activity = (DebugActivity) getActivity();
        activity.hideLoading();
        hideLoading();
        str = str.replace(" ", "");
        String state = str.substring(2, 4);
        String code1 = "";
        String code2 = "";
        String address = "";
        String code3 = "";
        if (state.equals("00")) {
            state = "06";
            code1 = str.substring(4, 24);
            code2 = str.substring(24, 64);
            address = str.substring(64, 184);
            code3 = str.substring(184, 186);
            tvEquipment.setText(HexUtils.toStringHex2(code1));
            tvCode.setText(HexUtils.toStringHex2(code2));
            tvAddress.setText(HexUtils.toStringHex2(address));
        } else if (state.equals("01")) {
            state = "08";
            code1 = str.substring(4, 24);
            code2 = str.substring(24, 64);
            address = str.substring(64, 184);
            code3 = str.substring(184, 186);
            showAlertE2(state,
                    HexUtils.toStringHex2(code1),
                    HexUtils.toStringHex2(code2),
                    HexUtils.toStringHex2(address));
        } else if (state.equals("02")) {
            state = str.substring(4, 6);
            code3 = str.substring(6, 8);
            showAlertE2(state,
                    tvEquipment.getText().toString(),
                    tvCode.getText().toString(),
                    tvAddress.getText().toString());
        }
    }

    public void setE3Data(final String msg) {
        hideLoading();
//        String[] string = msg.split(" ");
//        if (!HexUtils.checkCode(msg).equals(string[string.length - 1])) {
//            showToast("数据出错！");
//            return;
//        }
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    public void run() {
                        String[] string = msg.split(" ");
                        showAlertE3(string[1]);
                    }
                });
    }

//    //绑定电梯alert
//    private void showAlertE2(final String state, final String code1, final String code2, final String address) {
//        mAlertDialog.setMessage("");
//        switch (state) {
//            case "00":
//                mAlertDialog.setTitle("需要app确认绑定");
//                mAlertDialog.setMessage("设备号：" + code1 + "\n" +
//                        "注册代码：" + code2 + "\n" +
//                        "电梯地址：" + address);
//                break;
//            case "01":
//                tvEquipment.setText(code1);
//                tvCode.setText(code2);
//                tvAddress.setText(address);
//                mAlertDialog.setTitle("已成功绑定");
////                mAlertDialog.setTitle("需要app确认绑定");
////                mAlertDialog.setMessage("设备号：" + code1 + "\n" +
////                        "注册代码：" + code2 + "\n" +
////                        "电梯地址：" + address);
//                break;
//            case "02":
//                mAlertDialog.setTitle("电梯注册码错误");
//                break;
//            case "03":
//                mAlertDialog.setTitle("电梯编码错");
//                break;
//            case "04":
//                mAlertDialog.setTitle("效验错误");
//                break;
//            case "05":
//                mAlertDialog.setTitle("连接不到网络");
//                break;
//        }
//        mAlertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (state) {
//                    case "00":
//                        String request = "E2 01 " + HexUtils.str2HexStr(etInput.getText().toString().trim()) + " " + HexUtils.getHexUserID();
//                        NetProxy.send(request + " " + HexUtils.getCode(request));
//                        break;
//                    case "01":
//                        break;
//                    case "02":
//                        break;
//                    case "03":
//                        break;
//                    case "04":
//                        break;
//                    case "05":
//                        break;
//                }
//            }
//        });
//        mAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        mAlertDialog.setCancelable(false);
//        mAlertDialog.show();
//    }

    //绑定电梯alert
    private void showAlertE2(final String state, final String code1, final String code2, final String address) {
        mAlertDialog.setMessage("");
        switch (state) {
            case "08":
                tvEquipment.setText(code1);
                tvCode.setText(code2);
                tvAddress.setText(address);
                mAlertDialog.setTitle("需要app确认绑定");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                mAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                break;
            case "01":
                tvEquipment.setText(code1);
                tvCode.setText(code2);
                tvAddress.setText(address);
                mAlertDialog.setTitle("已成功绑定");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
//                mAlertDialog.setTitle("需要app确认绑定");
//                mAlertDialog.setMessage("设备号：" + code1 + "\n" +
//                        "注册代码：" + code2 + "\n" +
//                        "电梯地址：" + address);
                break;
            case "02":
                mAlertDialog.setTitle("电梯注册码错误");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
            case "03":
                mAlertDialog.setTitle("电梯编码错");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
            case "04":
                mAlertDialog.setTitle("效验错误");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
            case "05":
                mAlertDialog.setTitle("连接不到网络");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
            case "06":
                mAlertDialog.setTitle("该设备已绑过其他电梯");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
            case "07":
                mAlertDialog.setTitle("该注册码已绑定其他设备");
                mAlertDialog.setMessage("设备编号：" + "\n" + code1 + "\n" +
                        "注册代码：" + "\n" + code2 + "\n" +
                        "电梯地址：" + "\n" + address);
                break;
        }
        mAlertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (state) {
                    case "00":
                    case "01":
                    case "02":
                    case "03":
                    case "04":
                    case "05":
                    case "06":
                    case "07":
                        break;
                    case "08":
                        showLoading("加载中...");
                        String request = "E2 02 " + HexUtils.str2HexStr(etInput.getText().toString().trim()) + " " + HexUtils.getHexUserID();
                        NetProxy.send(request + " " + HexUtils.getCode(request));
                        break;
                }
            }
        });
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }

    //电梯维保alert
    private void showAlertE3(final String state) {
        mAlertDialog.setMessage("");
        switch (state) {
            case "00":
                mAlertDialog.setTitle("维保结果确认成功");
                if (mType == 0) {
                    tvState.setText("正在维保中，请勿离开...");
                    tvStateImg.setBackgroundResource(R.mipmap.ic_monitor_1);
                    llState.setBackgroundResource(R.drawable.btn_yellow_normal);
                } else if (mType == 1) {
                    tvState.setText("维保已结束，谢谢！");
                    tvStateImg.setBackgroundResource(R.mipmap.ic_monitor_2);
                    llState.setBackgroundResource(R.drawable.btn_blue_normal);
                }
                break;
            case "01":
                mAlertDialog.setTitle("维保结果确认失败");
                break;
        }
        mAlertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (state) {
                    case "00":
                        break;
                    case "01":
                        break;
                }
            }
        });
        mAlertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }

    @OnClick({R.id.tvInput, R.id.tvScan, R.id.tvStart, R.id.tvEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvInput:
                CharSequence etInputCs = etInput.getText();
                if (TextUtils.isEmpty(etInputCs.toString())) {
                    showToast("请输入20位电梯注册代码！");
                    return;
                }
                if (etInputCs.length() != 20) {
                    showToast("请输入20位电梯注册代码！");
                    return;
                }
                showLoading("加载中...");
                String request = "E2 01 " + HexUtils.str2HexStr(etInput.getText().toString().trim()) + " " + HexUtils.getHexUserID();
                NetProxy.send(request + " " + HexUtils.getCode(request));
                break;
            case R.id.tvScan:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        requestPermissions(
                                new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SBTS);
                        startActivityForResult(intent, Constants.Code.GO_SCORE);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SBTS);
                        startActivityForResult(intent, Constants.Code.GO_SCORE);
                    } else {
                        getPermission("请授权APP使用相机权限！");
                    }
                }
                break;
            //维保开始
            case R.id.tvStart:
                showLoading("加载中...");
                mType = 0;
                String request1 = "E3 01 " + HexUtils.getHexUserID();
                NetProxy.send(request1 + " " + HexUtils.getCode(request1));
                break;
            //维保结束
            case R.id.tvEnd:
                showLoading("加载中...");
                mType = 1;
                String request2 = "E3 02 " + HexUtils.getHexUserID();
                NetProxy.send(request2 + " " + HexUtils.getCode(request2));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //二维码扫描
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            etInput.setText(data.getStringExtra("liftNum"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_SBTS);
                startActivityForResult(intent, Constants.Code.GO_SCORE);
            } else {
                getPermission("请授权APP使用相机权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
