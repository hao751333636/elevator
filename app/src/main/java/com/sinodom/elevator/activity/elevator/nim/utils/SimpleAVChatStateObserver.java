package com.sinodom.elevator.activity.elevator.nim.utils;

import com.netease.nimlib.sdk.avchat.AVChatStateObserver;
import com.netease.nimlib.sdk.avchat.model.AVChatAudioFrame;
import com.netease.nimlib.sdk.avchat.model.AVChatNetworkStats;
import com.netease.nimlib.sdk.avchat.model.AVChatSessionStats;
import com.netease.nimlib.sdk.avchat.model.AVChatVideoFrame;

import java.util.Map;

/**
 * Created by winnie on 2017/12/8.
 */

public class SimpleAVChatStateObserver implements AVChatStateObserver {

    //截图结果回调
    @Override
    public void onTakeSnapshotResult(String account, boolean success, String file) {

    }

    //音视频录制回调
    @Override
    public void onAVRecordingCompletion(String account, String filePath) {

    }

    //当用户录制语音结束时回调，会通知录制文件路径
    @Override
    public void onAudioRecordingCompletion(String filePath) {

    }

    //当存储空间不足时的警告回调,存储空间低于20M时开始出现警告，出现警告时请及时关闭所有的录制服务，当存储空间低于10M时会自动关闭所有的录制。
    @Override
    public void onLowStorageSpaceWarning(long availableSize) {

    }

    @Override
    public void onAudioMixingProgressUpdated(long progressMs, long durationMs) {

    }

    //伴音事件通知
    @Override
    public void onAudioMixingEvent(int event) {

    }

    //当前音视频服务器连接回调
    @Override
    public void onJoinedChannel(int code, String audioFile, String videoFile, int elapsed) {

    }

    //加入当前音视频频道用户帐号回调
    @Override
    public void onUserJoined(String account) {

    }

    //当前用户离开频道回调
    @Override
    public void onUserLeave(String account, int event) {

    }

    //自己成功离开频道回调
    @Override
    public void onLeaveChannel() {

    }

    //版本协议不兼容回调
    @Override
    public void onProtocolIncompatible(int status) {

    }

    //服务器断开回调
    @Override
    public void onDisconnectServer(int code) {

    }

    //当前通话网络状况回调
    @Override
    public void onNetworkQuality(String user, int quality, AVChatNetworkStats stats) {

    }

    //音视频连接成功建立回调
    @Override
    public void onCallEstablished() {

    }

    //音视频设备状态通知
    @Override
    public void onDeviceEvent(int code, String desc) {

    }

    //本地网络类型发生改变回调
    @Override
    public void onConnectionTypeChanged(int netType) {

    }

    //用户第一帧画面通知
    @Override
    public void onFirstVideoFrameAvailable(String account) {

    }

    //用户第一帧画面绘制后通知
    @Override
    public void onFirstVideoFrameRendered(String user) {

    }

    //用户视频画面分辨率改变通知
    @Override
    public void onVideoFrameResolutionChanged(String user, int width, int height, int rotate) {

    }

    //用户视频帧率汇报
    @Override
    public void onVideoFpsReported(String account, int fps) {

    }

    //采集语音数据回调
    @Override
    public boolean onVideoFrameFilter(AVChatVideoFrame frame, boolean maybeDualInput) {
        return false;
    }

    @Override
    public boolean onAudioFrameFilter(AVChatAudioFrame frame) {
        return false;
    }

    @Override
    public void onAudioDeviceChanged(int device) {

    }

    //语音正在说话用户声音强度通知
    @Override
    public void onReportSpeaker(Map<String, Integer> speakers, int mixedEnergy) {

    }

    @Override
    public void onSessionStats(AVChatSessionStats sessionStats) {

    }

    @Override
    public void onLiveEvent(int event) {

    }
}
