package com.sinodom.elevator.fragment.elevator.monitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.socket.NetProxy;
import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.view.FlickerTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/30.
 * 设备调试——设备调试
 */

public class DebugFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tvMenu)
    TextView tvMenu;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvConfirm)
    TextView tvConfirm;
    @BindView(R.id.ivUp)
    ImageView ivUp;
    @BindView(R.id.tvDown)
    ImageView tvDown;
    @BindView(R.id.tvLeft)
    ImageView tvLeft;
    @BindView(R.id.tvRight)
    ImageView tvRight;
    @BindView(R.id.tvByte101)
    TextView tvByte101;
    @BindView(R.id.tvByte101_line)
    FlickerTextView tvByte101Line;
    @BindView(R.id.tvByte102)
    TextView tvByte102;
    @BindView(R.id.tvByte102_line)
    FlickerTextView tvByte102Line;
    @BindView(R.id.tvByte103)
    TextView tvByte103;
    @BindView(R.id.tvByte103_line)
    FlickerTextView tvByte103Line;
    @BindView(R.id.tvByte104)
    TextView tvByte104;
    @BindView(R.id.tvByte104_line)
    FlickerTextView tvByte104Line;
    @BindView(R.id.tvByte105)
    TextView tvByte105;
    @BindView(R.id.tvByte105_line)
    FlickerTextView tvByte105Line;
    @BindView(R.id.tvByte106)
    TextView tvByte106;
    @BindView(R.id.tvByte106_line)
    FlickerTextView tvByte106Line;
    @BindView(R.id.tvByte107)
    TextView tvByte107;
    @BindView(R.id.tvByte107_line)
    FlickerTextView tvByte107Line;
    @BindView(R.id.tvByte108)
    TextView tvByte108;
    @BindView(R.id.tvByte108_line)
    FlickerTextView tvByte108Line;
    @BindView(R.id.tvByte109)
    TextView tvByte109;
    @BindView(R.id.tvByte109_line)
    FlickerTextView tvByte109Line;
    @BindView(R.id.tvByte110)
    TextView tvByte110;
    @BindView(R.id.tvByte110_line)
    FlickerTextView tvByte110Line;
    @BindView(R.id.tvByte111)
    TextView tvByte111;
    @BindView(R.id.tvByte111_line)
    FlickerTextView tvByte111Line;
    @BindView(R.id.tvByte112)
    TextView tvByte112;
    @BindView(R.id.tvByte112_line)
    FlickerTextView tvByte112Line;
    @BindView(R.id.tvByte113)
    TextView tvByte113;
    @BindView(R.id.tvByte113_line)
    FlickerTextView tvByte113Line;
    @BindView(R.id.tvByte114)
    TextView tvByte114;
    @BindView(R.id.tvByte114_line)
    FlickerTextView tvByte114Line;
    @BindView(R.id.tvByte115)
    TextView tvByte115;
    @BindView(R.id.tvByte115_line)
    FlickerTextView tvByte115Line;
    @BindView(R.id.tvByte116)
    TextView tvByte116;
    @BindView(R.id.tvByte116_line)
    FlickerTextView tvByte116Line;
    @BindView(R.id.tvByte201)
    TextView tvByte201;
    @BindView(R.id.tvByte201_line)
    FlickerTextView tvByte201Line;
    @BindView(R.id.tvByte202)
    TextView tvByte202;
    @BindView(R.id.tvByte202_line)
    FlickerTextView tvByte202Line;
    @BindView(R.id.tvByte203)
    TextView tvByte203;
    @BindView(R.id.tvByte203_line)
    FlickerTextView tvByte203Line;
    @BindView(R.id.tvByte204)
    TextView tvByte204;
    @BindView(R.id.tvByte204_line)
    FlickerTextView tvByte204Line;
    @BindView(R.id.tvByte205)
    TextView tvByte205;
    @BindView(R.id.tvByte205_line)
    FlickerTextView tvByte205Line;
    @BindView(R.id.tvByte206)
    TextView tvByte206;
    @BindView(R.id.tvByte206_line)
    FlickerTextView tvByte206Line;
    @BindView(R.id.tvByte207)
    TextView tvByte207;
    @BindView(R.id.tvByte207_line)
    FlickerTextView tvByte207Line;
    @BindView(R.id.tvByte208)
    TextView tvByte208;
    @BindView(R.id.tvByte208_line)
    FlickerTextView tvByte208Line;
    @BindView(R.id.tvByte209)
    TextView tvByte209;
    @BindView(R.id.tvByte209_line)
    FlickerTextView tvByte209Line;
    @BindView(R.id.tvByte210)
    TextView tvByte210;
    @BindView(R.id.tvByte210_line)
    FlickerTextView tvByte210Line;
    @BindView(R.id.tvByte211)
    TextView tvByte211;
    @BindView(R.id.tvByte211_line)
    FlickerTextView tvByte211Line;
    @BindView(R.id.tvByte212)
    TextView tvByte212;
    @BindView(R.id.tvByte212_line)
    FlickerTextView tvByte212Line;
    @BindView(R.id.tvByte213)
    TextView tvByte213;
    @BindView(R.id.tvByte213_line)
    FlickerTextView tvByte213Line;
    @BindView(R.id.tvByte214)
    TextView tvByte214;
    @BindView(R.id.tvByte214_line)
    FlickerTextView tvByte214Line;
    @BindView(R.id.tvByte215)
    TextView tvByte215;
    @BindView(R.id.tvByte215_line)
    FlickerTextView tvByte215Line;
    @BindView(R.id.tvByte216)
    TextView tvByte216;
    @BindView(R.id.tvByte216_line)
    FlickerTextView tvByte216Line;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_debug_debug, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
    }

    public void setData(String str) {
        hideLoading();
        String[] string = str.split(" ");
        if (string.length == 36) {
            tvByte101.setText(getString(string[1]));
            tvByte102.setText(getString(string[2]));
            tvByte103.setText(getString(string[3]));
            tvByte104.setText(getString(string[4]));
            tvByte105.setText(getString(string[5]));
            tvByte106.setText(getString(string[6]));
            tvByte107.setText(getString(string[7]));
            tvByte108.setText(getString(string[8]));
            tvByte109.setText(getString(string[9]));
            tvByte110.setText(getString(string[10]));
            tvByte111.setText(getString(string[11]));
            tvByte112.setText(getString(string[12]));
            tvByte113.setText(getString(string[13]));
            tvByte114.setText(getString(string[14]));
            tvByte115.setText(getString(string[15]));
            tvByte116.setText(getString(string[16]));
            tvByte201.setText(getString(string[17]));
            tvByte202.setText(getString(string[18]));
            tvByte203.setText(getString(string[19]));
            tvByte204.setText(getString(string[20]));
            tvByte205.setText(getString(string[21]));
            tvByte206.setText(getString(string[22]));
            tvByte207.setText(getString(string[23]));
            tvByte208.setText(getString(string[24]));
            tvByte209.setText(getString(string[25]));
            tvByte210.setText(getString(string[26]));
            tvByte211.setText(getString(string[27]));
            tvByte212.setText(getString(string[28]));
            tvByte213.setText(getString(string[29]));
            tvByte214.setText(getString(string[30]));
            tvByte215.setText(getString(string[31]));
            tvByte216.setText(getString(string[32]));

            tvByte101Line.setIsStop(true);
            tvByte102Line.setIsStop(true);
            tvByte103Line.setIsStop(true);
            tvByte104Line.setIsStop(true);
            tvByte105Line.setIsStop(true);
            tvByte106Line.setIsStop(true);
            tvByte107Line.setIsStop(true);
            tvByte108Line.setIsStop(true);
            tvByte109Line.setIsStop(true);
            tvByte110Line.setIsStop(true);
            tvByte111Line.setIsStop(true);
            tvByte112Line.setIsStop(true);
            tvByte113Line.setIsStop(true);
            tvByte114Line.setIsStop(true);
            tvByte115Line.setIsStop(true);
            tvByte116Line.setIsStop(true);
            tvByte201Line.setIsStop(true);
            tvByte202Line.setIsStop(true);
            tvByte203Line.setIsStop(true);
            tvByte204Line.setIsStop(true);
            tvByte205Line.setIsStop(true);
            tvByte206Line.setIsStop(true);
            tvByte207Line.setIsStop(true);
            tvByte208Line.setIsStop(true);
            tvByte209Line.setIsStop(true);
            tvByte210Line.setIsStop(true);
            tvByte211Line.setIsStop(true);
            tvByte212Line.setIsStop(true);
            tvByte213Line.setIsStop(true);
            tvByte214Line.setIsStop(true);
            tvByte215Line.setIsStop(true);
            tvByte216Line.setIsStop(true);

            tvByte101Line.setIsShow(false);
            tvByte102Line.setIsShow(false);
            tvByte103Line.setIsShow(false);
            tvByte104Line.setIsShow(false);
            tvByte105Line.setIsShow(false);
            tvByte106Line.setIsShow(false);
            tvByte107Line.setIsShow(false);
            tvByte108Line.setIsShow(false);
            tvByte109Line.setIsShow(false);
            tvByte110Line.setIsShow(false);
            tvByte111Line.setIsShow(false);
            tvByte112Line.setIsShow(false);
            tvByte113Line.setIsShow(false);
            tvByte114Line.setIsShow(false);
            tvByte115Line.setIsShow(false);
            tvByte116Line.setIsShow(false);
            tvByte201Line.setIsShow(false);
            tvByte202Line.setIsShow(false);
            tvByte203Line.setIsShow(false);
            tvByte204Line.setIsShow(false);
            tvByte205Line.setIsShow(false);
            tvByte206Line.setIsShow(false);
            tvByte207Line.setIsShow(false);
            tvByte208Line.setIsShow(false);
            tvByte209Line.setIsShow(false);
            tvByte210Line.setIsShow(false);
            tvByte211Line.setIsShow(false);
            tvByte212Line.setIsShow(false);
            tvByte213Line.setIsShow(false);
            tvByte214Line.setIsShow(false);
            tvByte215Line.setIsShow(false);
            tvByte216Line.setIsShow(false);
            setCursor(string[33], string[34], string[35]);
        } else {
            showToast("错误数据！");
        }
    }

    private String getString(String str) {
        if (str.equals("FF")) {
            str = "■";
        } else if (str.equals("DB")) {
            str = "□";
        } else if (str.equals("00")) {
            str = "↑";
        } else if (str.equals("01")) {
            str = "↓";
        } else {
            str = HexUtils.hexStr2Str(str);
        }
        return str;
    }

    private void setCursor(String cursor, String flash, String index) {
        int posion = Integer.parseInt(index, 16) - 16 * 3;
        if (flash.equals("01")) {
            switch (posion) {
                case 0:
                    new Thread(tvByte101Line).start();
                    tvByte101Line.setIsStop(false);
                    break;
                case 1:
                    new Thread(tvByte102Line).start();
                    tvByte102Line.setIsStop(false);
                    break;
                case 2:
                    new Thread(tvByte103Line).start();
                    tvByte103Line.setIsStop(false);
                    break;
                case 3:
                    new Thread(tvByte104Line).start();
                    tvByte104Line.setIsStop(false);
                    break;
                case 4:
                    new Thread(tvByte105Line).start();
                    tvByte105Line.setIsStop(false);
                    break;
                case 5:
                    new Thread(tvByte106Line).start();
                    tvByte106Line.setIsStop(false);
                    break;
                case 6:
                    new Thread(tvByte107Line).start();
                    tvByte107Line.setIsStop(false);
                    break;
                case 7:
                    new Thread(tvByte108Line).start();
                    tvByte108Line.setIsStop(false);
                    break;
                case 8:
                    new Thread(tvByte109Line).start();
                    tvByte109Line.setIsStop(false);
                    break;
                case 9:
                    new Thread(tvByte110Line).start();
                    tvByte110Line.setIsStop(false);
                    break;
                case 10:
                    new Thread(tvByte111Line).start();
                    tvByte111Line.setIsStop(false);
                    break;
                case 11:
                    new Thread(tvByte112Line).start();
                    tvByte112Line.setIsStop(false);
                    break;
                case 12:
                    new Thread(tvByte113Line).start();
                    tvByte113Line.setIsStop(false);
                    break;
                case 13:
                    new Thread(tvByte114Line).start();
                    tvByte114Line.setIsStop(false);
                    break;
                case 14:
                    new Thread(tvByte115Line).start();
                    tvByte115Line.setIsStop(false);
                    break;
                case 15:
                    new Thread(tvByte116Line).start();
                    tvByte116Line.setIsStop(false);
                    break;
                case 16:
                    new Thread(tvByte201Line).start();
                    tvByte201Line.setIsStop(false);
                    break;
                case 17:
                    new Thread(tvByte202Line).start();
                    tvByte202Line.setIsStop(false);
                    break;
                case 18:
                    new Thread(tvByte203Line).start();
                    tvByte203Line.setIsStop(false);
                    break;
                case 19:
                    new Thread(tvByte204Line).start();
                    tvByte204Line.setIsStop(false);
                    break;
                case 20:
                    new Thread(tvByte205Line).start();
                    tvByte205Line.setIsStop(false);
                    break;
                case 21:
                    new Thread(tvByte206Line).start();
                    tvByte206Line.setIsStop(false);
                    break;
                case 22:
                    new Thread(tvByte207Line).start();
                    tvByte207Line.setIsStop(false);
                    break;
                case 23:
                    new Thread(tvByte208Line).start();
                    tvByte208Line.setIsStop(false);
                    break;
                case 24:
                    new Thread(tvByte209Line).start();
                    tvByte209Line.setIsStop(false);
                    break;
                case 25:
                    new Thread(tvByte210Line).start();
                    tvByte210Line.setIsStop(false);
                    break;
                case 26:
                    new Thread(tvByte211Line).start();
                    tvByte211Line.setIsStop(false);
                    break;
                case 27:
                    new Thread(tvByte212Line).start();
                    tvByte212Line.setIsStop(false);
                    break;
                case 28:
                    new Thread(tvByte213Line).start();
                    tvByte213Line.setIsStop(false);
                    break;
                case 29:
                    new Thread(tvByte214Line).start();
                    tvByte214Line.setIsStop(false);
                    break;
                case 30:
                    new Thread(tvByte215Line).start();
                    tvByte215Line.setIsStop(false);
                    break;
                case 31:
                    new Thread(tvByte216Line).start();
                    tvByte216Line.setIsStop(false);
                    break;
            }
        }
        if (cursor.equals("01")) {
            switch (posion) {
                case 0:
                    tvByte101Line.setIsShow(true);
                    break;
                case 1:
                    tvByte102Line.setIsShow(true);
                    break;
                case 2:
                    tvByte103Line.setIsShow(true);
                    break;
                case 3:
                    tvByte104Line.setIsShow(true);
                    break;
                case 4:
                    tvByte105Line.setIsShow(true);
                    break;
                case 5:
                    tvByte106Line.setIsShow(true);
                    break;
                case 6:
                    tvByte107Line.setIsShow(true);
                    break;
                case 7:
                    tvByte108Line.setIsShow(true);
                    break;
                case 8:
                    tvByte109Line.setIsShow(true);
                    break;
                case 9:
                    tvByte110Line.setIsShow(true);
                    break;
                case 10:
                    tvByte111Line.setIsShow(true);
                    break;
                case 11:
                    tvByte112Line.setIsShow(true);
                    break;
                case 12:
                    tvByte113Line.setIsShow(true);
                    break;
                case 13:
                    tvByte114Line.setIsShow(true);
                    break;
                case 14:
                    tvByte115Line.setIsShow(true);
                    break;
                case 15:
                    tvByte116Line.setIsShow(true);
                    break;
                case 16:
                    tvByte201Line.setIsShow(true);
                    break;
                case 17:
                    tvByte202Line.setIsShow(true);
                    break;
                case 18:
                    tvByte203Line.setIsShow(true);
                    break;
                case 19:
                    tvByte204Line.setIsShow(true);
                    break;
                case 20:
                    tvByte205Line.setIsShow(true);
                    break;
                case 21:
                    tvByte206Line.setIsShow(true);
                    break;
                case 22:
                    tvByte207Line.setIsShow(true);
                    break;
                case 23:
                    tvByte208Line.setIsShow(true);
                    break;
                case 24:
                    tvByte209Line.setIsShow(true);
                    break;
                case 25:
                    tvByte210Line.setIsShow(true);
                    break;
                case 26:
                    tvByte211Line.setIsShow(true);
                    break;
                case 27:
                    tvByte212Line.setIsShow(true);
                    break;
                case 28:
                    tvByte213Line.setIsShow(true);
                    break;
                case 29:
                    tvByte214Line.setIsShow(true);
                    break;
                case 30:
                    tvByte215Line.setIsShow(true);
                    break;
                case 31:
                    tvByte216Line.setIsShow(true);
                    break;
            }
        }
    }


    @OnClick({R.id.tvMenu, R.id.tvBack, R.id.tvConfirm, R.id.ivUp, R.id.tvDown, R.id.tvRight, R.id.tvLeft})
    public void onViewClicked(View view) {
        showLoading("加载中...");
        switch (view.getId()) {
            case R.id.tvMenu:
                NetProxy.send("04");
                break;
            case R.id.tvBack:
                NetProxy.send("02");
                break;
            case R.id.tvConfirm:
                NetProxy.send("80");
                break;
            case R.id.ivUp:
                NetProxy.send("08");
                break;
            case R.id.tvDown:
                NetProxy.send("10");
                break;
            case R.id.tvLeft:
                NetProxy.send("21");
                break;
            case R.id.tvRight:
                NetProxy.send("20");
                break;
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
