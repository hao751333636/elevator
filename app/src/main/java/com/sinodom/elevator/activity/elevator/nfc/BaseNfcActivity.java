package com.sinodom.elevator.activity.elevator.nfc;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dk.bleNfc.BleManager.BleManager;
import com.dk.bleNfc.BleManager.Scanner;
import com.dk.bleNfc.BleManager.ScannerCallback;
import com.dk.bleNfc.BleNfcDeviceService;
import com.dk.bleNfc.DeviceManager.BleNfcDevice;
import com.dk.bleNfc.DeviceManager.ComByteManager;
import com.dk.bleNfc.DeviceManager.DeviceManager;
import com.dk.bleNfc.DeviceManager.DeviceManagerCallback;
import com.dk.bleNfc.Exception.CardNoResponseException;
import com.dk.bleNfc.Exception.DeviceNoResponseException;
import com.dk.bleNfc.Tool.StringTool;
import com.dk.bleNfc.card.Ntag21x;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.MyAlertDialog;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class BaseNfcActivity extends BaseActivity {

    protected NfcAdapter mNfcAdapter;//nfc适配器对象
    protected PendingIntent mPendingIntent;//延迟Intent
    protected Tag mTag;//nfc标签对象

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    BleNfcDeviceService mBleNfcDeviceService;
    private BleNfcDevice bleNfcDevice;
    private Scanner mScanner;
    private EditText msgText = null;
    private ProgressDialog readWriteDialog = null;

    public StringBuffer msgBuffer;
    private BluetoothDevice mNearestBle = null;
    private Lock mNearestBleLock = new ReentrantLock();// 锁对象
    private int lastRssi = -100;

    //启动activity,界面可见时
    @Override
    protected void onStart() {
        super.onStart();
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {//判断设备是否支持NFC功能
            new AlertDialog.Builder(context)
                    .setTitle("您的手机不支持NFC功能，请确认手持设备已开机！")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msgBuffer = new StringBuffer();
                            //ble_nfc服务初始化
                            Intent gattServiceIntent = new Intent(BaseNfcActivity.this, BleNfcDeviceService.class);
                            bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                            dialog.dismiss();
                        }
                    })
                    .show();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {//判断设备NFC功能是否打开
            MyAlertDialog myAlertDialog = new MyAlertDialog(context, R.style.DialogStyle1);
            myAlertDialog.setTitle("请前往设置界面，打开NFC功能！");
            myAlertDialog.setBCancelGone();
            myAlertDialog.setOnConfirmListener(new MyAlertDialog.OnClickListener() {
                @Override
                public void onClick(MyAlertDialog dialog, View v) {
                    dialog.dismiss();
                    startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
                }
            });
            myAlertDialog.show();
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);//创建PendingIntent对象,当检测到一个Tag标签就会执行此Intent
    }

    public void onDevice(String str) {
    }

    public void onCard(String str) {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);//获取到Tag标签对象
        String[] techList = mTag.getTechList();
        boolean isType = false;
        for (String tech : techList) {
            if (tech.equals("android.nfc.tech.Ndef")) {
                isType = true;
                break;
            }
        }
        if (isType) {
            cry("phone");
        } else {
            showToast("不支持该类型NFC标签！");
        }
    }

    public abstract void cry(String type);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //页面获取到焦点
    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null && mPendingIntent != null) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);//打开前台发布系统，使页面优于其它nfc处理.当检测到一个Tag标签就会执行mPendingItent
        }
        if (mBleNfcDeviceService != null) {
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        }
    }

    //页面失去焦点
    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null && mPendingIntent != null) {
            mNfcAdapter.disableForegroundDispatch(this);//关闭前台发布系统
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(mServiceConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            BleNfcDeviceService mBleNfcDeviceService = ((BleNfcDeviceService.LocalBinder) service).getService();
            bleNfcDevice = mBleNfcDeviceService.bleNfcDevice;
            mScanner = mBleNfcDeviceService.scanner;
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
            mBleNfcDeviceService.setScannerCallback(scannerCallback);

            //开始搜索设备
            searchNearestBleDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBleNfcDeviceService = null;
        }
    };

    //Scanner 回调
    private ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onReceiveScanDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
            super.onReceiveScanDevice(device, rssi, scanRecord);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //StringTool.byteHexToSting(scanRecord.getBytes())
                System.out.println("Activity搜到设备：" + device.getName()
                        + " 信号强度：" + rssi
                        + " scanRecord：" + StringTool.byteHexToSting(scanRecord));
            }

            //搜索蓝牙设备并记录信号强度最强的设备
            if ((scanRecord != null) && (StringTool.byteHexToSting(scanRecord).contains("017f5450"))) {  //从广播数据中过滤掉其它蓝牙设备
                if (rssi < -55) {
                    return;
                }
                msgBuffer.append("搜到设备：").append(device.getName()).append(" 信号强度：").append(rssi).append("\r\n");
                handler.sendEmptyMessage(0);
                if (mNearestBle != null) {
                    if (rssi > lastRssi) {
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = device;
                        } finally {
                            mNearestBleLock.unlock();
                        }
                    }
                } else {
                    mNearestBleLock.lock();
                    try {
                        mNearestBle = device;
                    } finally {
                        mNearestBleLock.unlock();
                    }
                    lastRssi = rssi;
                }
            }
        }

        @Override
        public void onScanDeviceStopped() {
            super.onScanDeviceStopped();
        }
    };

    //设备操作类回调
    private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
        @Override
        public void onReceiveConnectBtDevice(boolean blnIsConnectSuc) {
            super.onReceiveConnectBtDevice(blnIsConnectSuc);
            if (blnIsConnectSuc) {
                System.out.println("Activity设备连接成功");
                msgBuffer.delete(0, msgBuffer.length());
                msgBuffer.append("设备连接成功!");
//                if (mNearestBle != null) {
//                    msgBuffer.append("设备名称：").append(bleNfcDevice.getDeviceName()).append("\r\n");
//                }
//                msgBuffer.append("信号强度：").append(lastRssi).append("dB\r\n");
//                msgBuffer.append("SDK版本：" + BleNfcDevice.SDK_VERSIONS + "\r\n");

                //连接上后延时500ms后再开始发指令
                try {
                    Thread.sleep(500L);
                    handler.sendEmptyMessage(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onReceiveDisConnectDevice(boolean blnIsDisConnectDevice) {
            super.onReceiveDisConnectDevice(blnIsDisConnectDevice);
            System.out.println("Activity设备断开链接");
            msgBuffer.delete(0, msgBuffer.length());
            msgBuffer.append("设备断开链接!");
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onReceiveConnectionStatus(boolean blnIsConnection) {
            super.onReceiveConnectionStatus(blnIsConnection);
            System.out.println("Activity设备链接状态回调");
        }

        @Override
        public void onReceiveInitCiphy(boolean blnIsInitSuc) {
            super.onReceiveInitCiphy(blnIsInitSuc);
        }

        @Override
        public void onReceiveDeviceAuth(byte[] authData) {
            super.onReceiveDeviceAuth(authData);
        }

        @Override
        //寻到卡片回调
        public void onReceiveRfnSearchCard(boolean blnIsSus, int cardType, byte[] bytCardSn, byte[] bytCarATS) {
            super.onReceiveRfnSearchCard(blnIsSus, cardType, bytCardSn, bytCarATS);
            if (!blnIsSus || cardType == BleNfcDevice.CARD_TYPE_NO_DEFINE) {
                return;
            }

            System.out.println("Activity接收到激活卡片回调：UID->" + StringTool.byteHexToSting(bytCardSn) + " ATS->" + StringTool.byteHexToSting(bytCarATS));

            final int cardTypeTemp = cardType;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isReadWriteCardSuc;
                    try {
                        if (bleNfcDevice.isAutoSearchCard()) {
                            //如果是自动寻卡的，寻到卡后，先关闭自动寻卡
                            bleNfcDevice.stoptAutoSearchCard();
                            isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);

                            //读卡结束，重新打开自动寻卡
                            startAutoSearchCard();
                        } else {
                            isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);

                            //如果不是自动寻卡，读卡结束,关闭天线
                            bleNfcDevice.closeRf();
                        }

                        //打开蜂鸣器提示读卡完成
                        if (isReadWriteCardSuc) {
                            bleNfcDevice.openBeep(50, 50, 3);  //读写卡成功快响3声
                        } else {
                            bleNfcDevice.openBeep(100, 100, 2); //读写卡失败慢响2声
                        }
                    } catch (DeviceNoResponseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onReceiveRfmSentApduCmd(byte[] bytApduRtnData) {
            super.onReceiveRfmSentApduCmd(bytApduRtnData);

            System.out.println("Activity接收到APDU回调：" + StringTool.byteHexToSting(bytApduRtnData));
        }

        @Override
        public void onReceiveRfmClose(boolean blnIsCloseSuc) {
            super.onReceiveRfmClose(blnIsCloseSuc);
        }

        @Override
        //按键返回回调
        public void onReceiveButtonEnter(byte keyValue) {
            if (keyValue == DeviceManager.BUTTON_VALUE_SHORT_ENTER) { //按键短按
                System.out.println("Activity接收到按键短按回调");
                msgBuffer.append("按键短按\r\n");
                handler.sendEmptyMessage(0);
            } else if (keyValue == DeviceManager.BUTTON_VALUE_LONG_ENTER) { //按键长按
                System.out.println("Activity接收到按键长按回调");
                msgBuffer.append("按键长按\r\n");
                handler.sendEmptyMessage(0);
            }
        }
    };

    //搜索最近的设备并连接
    private void searchNearestBleDevice() {
        msgBuffer.delete(0, msgBuffer.length());
        msgBuffer.append("正在搜索设备...");
        handler.sendEmptyMessage(0);
        if (!mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        mScanner.startScan(0);
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = null;
                        } finally {
                            mNearestBleLock.unlock();
                        }
                        lastRssi = -100;

                        int searchCnt = 0;
                        while ((mNearestBle == null)
                                && (searchCnt < 10000)
                                && (mScanner.isScanning())
                                && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            searchCnt++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mScanner.stopScan();
                            mNearestBleLock.lock();
                            try {
                                if (mNearestBle != null) {
                                    mScanner.stopScan();
                                    msgBuffer.delete(0, msgBuffer.length());
                                    msgBuffer.append("正在连接设备...");
                                    handler.sendEmptyMessage(0);
                                    bleNfcDevice.requestConnectBleDevice(mNearestBle.getAddress());
                                } else {
                                    msgBuffer.delete(0, msgBuffer.length());
                                    msgBuffer.append("未找到设备！");
                                    handler.sendEmptyMessage(0);
                                }
                            } finally {
                                mNearestBleLock.unlock();
                            }
                        } else {
                            mScanner.stopScan();
                        }
                    }
                }
            }).start();
        }
    }

    //开始自动寻卡
    private boolean startAutoSearchCard() throws DeviceNoResponseException {
        //打开自动寻卡，200ms间隔，寻M1/UL卡
        boolean isSuc = false;
        int falseCnt = 0;
        do {
            isSuc = bleNfcDevice.startAutoSearchCard((byte) 20, ComByteManager.ISO14443_P4);
        } while (!isSuc && (falseCnt++ < 10));
        if (!isSuc) {
            //msgBuffer.delete(0, msgBuffer.length());
            msgBuffer.append("不支持自动寻卡！\r\n");
            handler.sendEmptyMessage(0);
        }
        return isSuc;
    }

    public Ntag21x ntag21x;

    public String uid;
    public String content;
    public String mText;

    public void setText(String text) {
        mText = text;
    }

    //读写卡Demo
    private boolean readWriteCardDemo(int cardType) {
        switch (cardType) {
            case DeviceManager.CARD_TYPE_ISO4443_B:  //寻到 B cpu卡
                break;
            case DeviceManager.CARD_TYPE_ISO4443_A:   //寻到A CPU卡
                break;
            case DeviceManager.CARD_TYPE_FELICA:  //寻到FeliCa
                break;
            case DeviceManager.CARD_TYPE_ULTRALIGHT: //寻到Ultralight卡
                ntag21x = (Ntag21x) bleNfcDevice.getCard();
                if (ntag21x != null) {
                    msgBuffer.delete(0, msgBuffer.length());
                    uid = ntag21x.uidToString().toUpperCase();
                    try {
                        if (!TextUtil.isEmpty(mText)) {
                            boolean isSuc = ntag21x.NdefTextWrite(mText);
                            if (isSuc) {
                                showToast("写入成功");
                            } else {
                                showToast("写入失败");
                            }
                        }
                        String text = ntag21x.NdefTextRead();
                        msgBuffer.append("读取数据成功！");
                        content = text;
                        handler.sendEmptyMessage(1);
                    } catch (CardNoResponseException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                break;
            case DeviceManager.CARD_TYPE_MIFARE:   //寻到Mifare卡
                break;
            case DeviceManager.CARD_TYPE_ISO15693: //寻到15693卡
                break;
        }
        return true;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("TAG", msgBuffer.toString());
            onDevice(msgBuffer.toString());
            if ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED) || ((bleNfcDevice.isConnection() == BleManager.STATE_CONNECTING))) {
            } else {
            }
            switch (msg.what) {
                case 1:
                    cry("deke");
                    break;
                case 2:
                    break;
                case 3:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                /*获取设备版本号*/
//                                byte versions = bleNfcDevice.getDeviceVersions();
//                                msgBuffer.append("设备版本:").append(String.format("%02x", versions)).append("\r\n");
//                                handler.sendEmptyMessage(0);
//
//                                /*获取设备电池电压*/
                                double voltage = bleNfcDevice.getDeviceBatteryVoltage();
//                                msgBuffer.append("设备电池电压:").append(String.format("%.2f", voltage)).append("\r\n");
                                if (voltage < 3.61) {
                                    msgBuffer.append("设备电池电量低，请及时充电！");
                                } else {
                                    msgBuffer.append("设备电池电量充足！");
                                }
                                handler.sendEmptyMessage(0);

//                                /*设置快速传输参数*/
//                                boolean isSuc = bleNfcDevice.androidFastParams(true);
//                                if (isSuc) {
//                                    msgBuffer.append("\r\n蓝牙快速传输参数设置成功!");
//                                }
//                                else {
//                                    msgBuffer.append("\n不支持快速传输参数设置!");
//                                }
//                                handler.sendEmptyMessage(0);
//
//                                /*修改设备序列号*/
//                                msgBuffer.append("\n修改设备序列号为：00112233445566778899AABBCCDDEEFF00112233445566778899AABBCCDDEEFF");
//                                handler.sendEmptyMessage(0);
//                                byte[] newSerialNumberBytes = new byte[] {
//                                        0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb, (byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff,
//                                        0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb, (byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff
//                                };
//                                isSuc = bleNfcDevice.changeSerialNumber(newSerialNumberBytes);
//                                if (isSuc) {
//                                    msgBuffer.append("\r\n设备序列号修改成功!");
//                                }
//                                else {
//                                    msgBuffer.append("\r\n设备序列号修改失败!");
//                                }
//                                handler.sendEmptyMessage(0);
//
//                                /*获取设备序列号*/
//                                try {
//                                    msgBuffer.append("\r\n开始获取设备序列号...\r\n");
//                                    handler.sendEmptyMessage(0);
//                                    byte[] serialNumberBytes = bleNfcDevice.getSerialNumber();
//                                    msgBuffer.append("设备序列号为：").append(StringTool.byteHexToSting(serialNumberBytes));
//                                }catch (DeviceNoResponseException e) {
//                                    e.printStackTrace();
//                                }
//
//                                /*开启自动寻卡*/
//                                msgBuffer.append("\n开启自动寻卡...\r\n");
//                                handler.sendEmptyMessage(0);
                                //开始自动寻卡
                                startAutoSearchCard();
                            } catch (DeviceNoResponseException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case 7:
                    break;
            }
        }
    };

    //往Ndef标签中写数据
    public boolean writeNdef(String text) {
        if (mTag == null) {
            showToast("不能识别的标签类型！");
            return false;
        }
        Ndef ndef = Ndef.get(mTag);//获取ndef对象
        if (!ndef.isWritable()) {
            showToast("该标签不能写入数据！");
            return false;
        }
        NdefRecord ndefRecord = createTextRecord(text);//创建一个NdefRecord对象
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});//根据NdefRecord数组，创建一个NdefMessage对象
        int size = ndefMessage.getByteArrayLength();
        if (ndef.getMaxSize() < size) {
            showToast("标签容量不足！");
            return false;
        }
        try {
            ndef.connect();//连接
            ndef.writeNdefMessage(ndefMessage);
            showToast("数据写入成功！");
            return true;
        } catch (IOException e) {
            showToast("数据写入失败！请把手机靠近NFC标签！");
            e.printStackTrace();
        } catch (FormatException e) {
            showToast("数据写入失败！请把手机靠近NFC标签！");
            e.printStackTrace();
        } finally {
            try {
                ndef.close();//关闭连接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 创建NDEF文本数据
     *
     * @param text
     * @return
     */
    public static NdefRecord createTextRecord(String text) {
        //生成语言编码的字节数组，中文编码
        byte[] langBytes = Locale.CHINA.getLanguage().getBytes(
                Charset.forName("US-ASCII"));
        //将要写入的文本以UTF_8格式进行编码
        Charset utfEncoding = Charset.forName("UTF-8");
        //由于已经确定文本的格式编码为UTF_8，所以直接将payload的第1个字节的第7位设为0
        byte[] textBytes = text.getBytes(utfEncoding);
        int utfBit = 0;
        //定义和初始化状态字节
        char status = (char) (utfBit + langBytes.length);
        //创建存储payload的字节数组
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        //设置状态字节
        data[0] = (byte) status;
        //设置语言编码
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        //设置实际要写入的文本
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
                textBytes.length);
        //根据前面设置的payload创建NdefRecord对象
        NdefRecord record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(), new byte[]{}, text.getBytes(utfEncoding));
        return record;
    }

    //读取Ndef标签中数据
    public String readNdef() {
        String str = "";
        if (mTag == null) {
            showToast("不能识别的标签类型！");
            return str;
        }
        Ndef ndef = Ndef.get(mTag);//获取ndef对象
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            if (ndefMessage != null) {
                str = parseTextRecord(ndefMessage.getRecords()[0]);
//                showToast("数据读取成功！");
            }
        } catch (IOException e) {
            showToast("数据读取失败！请把手机靠近NFC标签！");
            e.printStackTrace();
        } catch (FormatException e) {
            showToast("数据读取失败！请把手机靠近NFC标签！");
            e.printStackTrace();
        } finally {
            try {
                ndef.close();//关闭链接
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    /**
     * 解析NDEF文本数据，从第三个字节开始，后面的文本数据
     *
     * @param ndefRecord
     * @return
     */
    public static String parseTextRecord(NdefRecord ndefRecord) {
        /**
         * 判断数据是否为NDEF格式
         */
        //判断TNF
        if (ndefRecord.getTnf() != NdefRecord.TNF_MIME_MEDIA) {
            return null;
        }
        //判断可变的长度的类型
        if (!Arrays.equals(ndefRecord.getType(), "text/plain".getBytes())) {
            return null;
        }
        try {
            //获得字节数组，然后进行分析
            byte[] payload = ndefRecord.getPayload();
            //下面开始NDEF文本数据第一个字节，状态字节
            //判断文本是基于UTF-8还是UTF-16的，取第一个字节"位与"上16进制的80，16进制的80也就是最高位是1，
            //其他位都是0，所以进行"位与"运算后就会保留最高位
//            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
            //3f最高两位是0，第六位是1，所以进行"位与"运算后获得第六位
//            int languageCodeLength = payload[0] & 0x3f;
            //下面开始NDEF文本数据第二个字节，语言编码
            //获得语言编码
//            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            //下面开始NDEF文本数据后面的字节，解析出文本
//            String textRecord = new String(payload, languageCodeLength + 1,
//                    payload.length - languageCodeLength - 1, textEncoding);
            String textRecord = new String(payload, 0,
                    payload.length, "UTF-8");
            return textRecord;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

}