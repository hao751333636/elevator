package com.sinodom.elevator.single;

import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.CheckDetailEntity;
import com.sinodom.elevator.bean.business.CheckDetailPaperlessEntity;
import com.sinodom.elevator.bean.property.PropertyAddBean;
import com.sinodom.elevator.bean.repairrecord.RepairAddBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 服务接口
 */
public interface ApiService {
    //登陆
    @POST
    Call<ResponBean> login(@Url String url, @Body Map<String, Object> map);

    /**
     * 电梯维保开始
     */
    //获取检验项列表信息
    @GET("api/Inspect/GetInspectStepList")
    Call<ResponBean> getInspectStepList();

    //获取电梯详情信息
    @GET("api/Inspect/GetInspectByLiftNum")
    Call<ResponBean> getInspectByLiftNum(@HeaderMap Map<String, String> headers);

    //获取电梯检验类型
    @GET("API/Inspect/GetInspectType")
    Call<ResponBean> getInspectType(@HeaderMap Map<String, String> headers);

    //配件管理信息
    @POST("Api/LiftParts/GetLift")
    Call<ResponBean> getLift(@Body Map<String, String> headers);

    //配件管理删除信息
    @GET("Api/LiftParts/DeleteTL_Parts")
    Call<ResponBean> deleteTL_Parts(@Query("PartsId") int PartsId);

    //配件管理信息详情
    @POST("Api/LiftParts/GetPartsType")
    Call<ResponBean> getPartsType(@Body Map<String, String> headers);

    //配件管理信息绑定标签
    @POST("Api/LiftParts/NfcBindParts")
    Call<ResponBean> nfcBindParts(@Body Map<String, Object> headers);

    //配件管理信息 图片上传
    @Multipart
    @POST("Api/Upload/PartsUploadFile")
    Call<ResponBean> partsUploadFile(@Part MultipartBody.Part file);

    //配件管理信息 图片上传
    @Multipart
    @POST("Api/LiftParts/AddLiftParts")
    Call<ResponBean> addLiftParts(@Part("Manufacturer") RequestBody Manufacturer,
                                  @Part("Brand") RequestBody Brand,
                                  @Part("Model") RequestBody Model,
                                  @Part("InstallationTime") RequestBody InstallationTime,
                                  @Part("PartsTypeId") RequestBody PartsTypeId,
                                  @Part("ProductName") RequestBody ProductName,
                                  @Part("LiftId") RequestBody LiftId,
                                  @Part MultipartBody.Part file);

    //获取电梯检验步骤
    @GET("API/Inspect/GetInspectByLiftNumNew")
    Call<ResponBean> getInspectByLiftNumNew(@HeaderMap Map<String, Object> headers);

    //检验完成
    @GET("api/Inspect/UpdateInspectByID")
    Call<ResponBean> updateInspectByID(@HeaderMap Map<String, String> headers, @Query("id") int id);

    //检测完成
    @GET("api/Inspect/UpdateInspectByIDNew")
    Call<ResponBean> updateInspectByIDNew(@HeaderMap Map<String, String> headers, @Query("id") int id);

    //修改检测部门
    @GET("API/Inspect/UpdateInspectDept")
    Call<ResponBean> updateInspectDept(@HeaderMap Map<String, Integer> headers);

    //修改工作形势
    @GET("API/Inspect/UpdateWorkForm")
    Call<ResponBean> updateWorkForm(@HeaderMap Map<String, Integer> headers);

    //提交检验项  参数+地址
    @POST("api/Inspect/SaveInspectDetail")
    Call<ResponBean> saveInspectDetail(@Body Map<String, Object> map);

    //提交检验项  参数+地址
    @POST("api/Inspect/SaveInspectDetailNew")
    Call<ResponBean> saveInspectDetailNew(@Body Map<String, Object> map);

    //上锁解锁
    @POST("api/Inspect/SaveInspectDetailLock")
    Call<ResponBean> saveInspectDetailLock(@HeaderMap Map<String, Object> headers, @Body StepDataBean.InspectDetailsBean bean);

    //上传照片或视频
    @Multipart
    @POST("api/Inspect/UploadFile")
    Call<ResponBean> uploadFile(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    //上传照片或视频
    @Multipart
    @POST("api/Inspect/UploadFile")
    Call<ResponBean> uploadFile(@Part MultipartBody.Part file);

    //获取维保记录列表
    @GET("api/Inspect/GetInspectList")
    Call<ResponBean> getInspectList(@HeaderMap Map<String, String> headers);

    //检验电梯是否存在
    @GET("api/Inspect/GetIsInspectByLiftNum")
    Call<ResponBean> getIsInspectByLiftNum(@HeaderMap Map<String, String> headers);

    //创建电梯
    @POST("api/Inspect/AddLift")
    Call<ResponBean> addLift(@Body Map<String, Object> map);
    /**
     * 电梯维保结束
     */

    /**
     * 电梯云开始
     */
    //获取电梯列表
    @GET("api/Task/GetTaskList")
    Call<ResponBean> getTaskList(@HeaderMap Map<String, Object> headers);

    //获取电梯详情
    @GET("api/Task/GetTask")
    Call<ResponBean> getTaskDetail(@HeaderMap Map<String, Object> headers, @Query("id") int id);

    //获取附近救援人员
    @GET("api/Task/GetLocationList")
    Call<ResponBean> getLocationList(@HeaderMap Map<String, Object> headers, @Query("taskId") int id);

    //获取StatusAction
    @GET("api/Task/GetLastStatusActionList")
    Call<ResponBean> getLastStatusActionList();

    //电梯字典(故障原因?code=FailureCause、解救方法?code=RescueMethod、误报原因?code=MisinformationReason、获取屏蔽列表?code=ShieldingCause)
    @GET("api/dict/GetDictListByRoot")
    Call<ResponBean> getDictListByRoot(@HeaderMap Map<String, Object> headers, @Query("code") String code);

    //电梯流程提交
    @POST("api/Task/SaveTaskStatus")
    Call<ResponBean> saveTaskStatus(@HeaderMap Map<String, Object> headers, @Body Map<String, Object> map);

    //电梯流程评分
    @POST("api/Task/SaveEvaluationScore")
    Call<ResponBean> saveEvaluationScore(@HeaderMap Map<String, Object> headers, @Body List<Map<String, Object>> list);

    //处置记录list
    @GET("api/Task/GetTaskEndList")
    Call<ResponBean> getTaskEndList(@HeaderMap Map<String, Object> headers);

    //投诉建议
    @GET("api/Advice/GetAdviceList")
    Call<ResponBean> getAdviceList(@HeaderMap Map<String, Object> headers);

    //查找电梯
    @GET("api/Lift/GetLiftByLiftNum")
    Call<ResponBean> getLiftByLiftNum(@HeaderMap Map<String, Object> headers, @Query("liftNum") String lift);

    //人工下单
    @POST("api/Task/ArtificialOrder")
    Call<ResponBean> artificialOrder(@Body Map<String, Object> map);

    //获取设备安装列表 Type = 0 联网状态列表 Type = 1  屏蔽管理 Type = 2
    @GET("api/Online/GetOnlineList")
    Call<ResponBean> getOnlineList(@HeaderMap Map<String, Object> headers);

    //获取统计图  Type同列表
//    @GET("api/Online/GetOnlineRatesPic")
    @GET("api/Online/GetOnlineRatesNewPic")
    Call<ResponBean> getOnlineRatesPic(@HeaderMap Map<String, Object> headers);

    //获取城市列表
    @GET("api/Address/GetCityList")
    Call<ResponBean> getCityList(@HeaderMap Map<String, Object> headers, @Query("userId") String userId);

    //获取区列表
    @GET("api/Address/GetAreaList")
    Call<ResponBean> getAreaList(@HeaderMap Map<String, Object> headers, @Query("userId") String userId, @Query("cityId") String cityId);

    //获取使用单位列表
    @GET("api/Dept/GetUseDeptList")
    Call<ResponBean> getUseDeptList(@HeaderMap Map<String, Object> headers);

    //获取维保单位列表
    @GET("api/Dept/GetMaintDeptList")
    Call<ResponBean> getMaintDeptList(@HeaderMap Map<String, Object> headers);

    //检验记录列表   Type  记录 不传，  待检 0，   超期 1
    @GET("api/LiftYearInspection/GetLiftYearInspectionList")
    Call<ResponBean> getLiftYearInspectionList(@HeaderMap Map<String, Object> headers);

    //待检电梯 统计图   Type 同列表
    @GET("api/LiftYearInspection/GetLiftYearInspectionRates")
    Call<ResponBean> getLiftYearInspectionRates(@HeaderMap Map<String, Object> headers);

    //扫码 查询电梯
    @GET("api/Check/GetLiftCheckByLiftNum1")
    Call<ResponBean> getLiftCheckByLiftNum(@HeaderMap Map<String, Object> headers);

    //扫码 物业巡查
    @POST("api/PropertyCheck/GetPropertyStep")
    Call<ResponBean> getPropertyStep(@Body Map<String, Object> map);

    //提交 物业巡查
    @POST("api/PropertyCheck/SaveCheckDetail")
    Call<ResponBean> saveCheckDetail(@Body List<PropertyAddBean> list);

    //维保步骤
    @GET("api/Step/GetStepList")
    Call<ResponBean> getStepList(@HeaderMap Map<String, Object> headers);

    //维保记录 列表 Type 不传    待查记录 列表 Type: 0    漏查记录 列表  Type: 1   维保历史 列表  Type: 2
    @GET("api/Check/GetCheckList")
    Call<ResponBean> getCheckList(@HeaderMap Map<String, Object> headers);

    //待查记录 图形  Type: 0   漏查记录 图形  Type: 1
    @GET("api/Check/GetCheckRates")
    Call<ResponBean> getCheckRates(@HeaderMap Map<String, Object> headers);

    //维保记录详细
    @GET("api/Check/GetCheck")
    Call<ResponBean> getCheck(@HeaderMap Map<String, Object> headers, @Query("id") String userId);

    //维保历史记录详细
    @GET("api/Check/GetCheckHistoryList")
    Call<ResponBean> getCheckHistoryList(@HeaderMap Map<String, Object> headers);

    //维保历史
    @GET("api/Check/GetNewCheckHistoryList")
    Call<ResponBean> getNewCheckHistoryList(@HeaderMap Map<String, Object> headers);

    //使用单位处理维保列表
    @GET("api/Check/GetUseCheckList")
    Call<ResponBean> getUseCheckList(@HeaderMap Map<String, Object> headers);

    //使用单位处理维保提交
    @POST("api/Check/SaveCheck")
    Call<ResponBean> saveCheck(@Body Map<String, Object> map);

    //提交巡查
    @POST("api/Check/SaveCheckDetails")
    Call<ResponBean> saveCheckDetails(@Body List<CheckDetailEntity> list);

    //提交巡查
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("api/Check/SaveCheckDetails")
    Call<ResponBean> saveCheckDetails(@Body RequestBody body);

    //物联监控
    @GET("api/Problem/GetProblemList")
    Call<ResponBean> getProblemList(@HeaderMap Map<String, Object> headers);

    //扫码 维修记录
    @POST("api/Repair/GetRepairNewNum")
    Call<ResponBean> getRepairNewNum(@HeaderMap Map<String, Object> map);

    //提交 维修记录
    @POST("api/Repair/SaveRepairNew")
    Call<ResponBean> saveRepairNew(@Body RepairAddBean list);

    //维修记录
    @GET("api/Repair/Repairs")
    Call<ResponBean> repairs(@HeaderMap Map<String, Object> headers);

    //维修记录详情
    @GET("api/Repair/GetRepair")
    Call<ResponBean> getRepair(@HeaderMap Map<String, Object> headers, @Query("Id") String userId);

    //通知通告
    @GET("api/Notice/GetArticleList")
    Call<ResponBean> getArticleList(@HeaderMap Map<String, Object> headers);

    //通知通告详情
    @GET("api/Notice/GetArticle")
    Call<ResponBean> getArticle(@HeaderMap Map<String, Object> headers, @Query("id") String id, @Query("Userid") String userId);

    //物业巡查
    @GET("api/PropertyCheck/PropertyChecks")
    Call<ResponBean> propertyChecks(@HeaderMap Map<String, Object> headers);

    //心跳连接
    @POST("api/Location/SaveLocation")
    Call<ResponBean> saveLocation(@Body Map<String, Object> map);

    //个人信息
    @GET("api/User/GetUserInfo")
    Call<ResponBean> getUserInfo(@Query("Id") String userId);

    //物业巡查详细
    @GET("api/PropertyCheck/GetPropertyCheck")
    Call<ResponBean> getPropertyCheck(@HeaderMap Map<String, Object> headers, @Query("id") String userId);

    //修改密码
    @POST("api/User/ModifyPwd")
    Call<ResponBean> modifyPwd(@Body Map<String, Object> map);

    //获取NFC维保项
    @GET("API/NFC/GetCheckTermIsNFC")
    Call<ResponBean> getCheckTermIsNFC(@Query("LiftNum") String userId);

    //通过20位编码查询电梯
    @GET("API/Lift/GetLiftByCertificateNum")
    Call<ResponBean> getLiftByCertificateNum(@Query("CertificateNum") String userId);

    //NFC绑定
    @POST("API/NFC/BindCheckNFC")
    Call<ResponBean> bindCheckNFC(@Body Map<String, Object> map);

    //NFC标签读取
    @GET("API/LiftParts/GetTL_LiftPartsNFC")
    Call<ResponBean> getTL_LiftPartsNFC(@Query("NFCNum") String NFCNum);

    //NFC新增
    @POST("API/NFC/SaveCheckNfc")
    Call<ResponBean> saveCheckNfc(@Body Map<String, Object> map);

    //获取设备信息
    @GET("api/Equipments/GetLiftEequipment")
    Call<ResponBean> getLiftEequipment(@Query("deviceNum") String userId);

    //设备绑定
    @GET("api/Equipments/ReplacEequipment")
    Call<ResponBean> replacEequipment(@Query("deviceNum") String certificateNum, @Query("certificateNum") String liftNum);

    //离线-NFC绑定
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("API/NFC/BindCheckNFC")
    Call<ResponBean> bindCheckNFC(@Body RequestBody body);

    //扫码 查询电梯
    @GET("API/Check/GetLiftCheckByLiftNum2")
    Call<ResponBean> getLiftCheckByLiftNum2(@HeaderMap Map<String, Object> headers);

    //无纸化获取维保步骤
    @GET("API/Step/GetStepList2")
    Call<ResponBean> getStepList2(@HeaderMap Map<String, Object> headers);

    //提交无纸化维保
    @POST("API/Check/SaveNFCCheckDetails")
    Call<ResponBean> saveNFCCheckDetails(@Body List<CheckDetailPaperlessEntity> list);

    //离线-提交无纸化维保
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("API/Check/SaveNFCCheckDetails")
    Call<ResponBean> saveNFCCheckDetails(@Body RequestBody body);

    //获取NFC维保项
    @GET("API/NFC/ReplaceTheElevator")
    Call<ResponBean> replaceTheElevator(@Query("LiftNum") String liftNum, @Query("CertificateNum") String certificateNum);

    //获取NFC维保项l
    @GET("API/NFC/LiftSign")
    Call<ResponBean> liftSign(@Query("LiftNum") String liftNum, @Query("CertificateNum") String certificateNum, @Query("Longitude") String Longitude, @Query("Latitude") String Latitude);

    //获取NFC维保项l
    @POST("API/NFC/LiftSign")
    Call<ResponBean> liftSign(@Body Map<String, Object> map);

    //视频机绑定
    @POST("API/NFC/VideoLiftSign")
    Call<ResponBean> videoLiftSign(@Body Map<String, Object> map);

    //四位监控绑定
    @POST("Api/Equipments/EquipmentsBind")
    Call<ResponBean> equipmentsBind(@Body Map<String, Object> map);

    //检验服务签字
    @POST("API/Inspect/SaveInspectSign")
    Call<ResponBean> saveInspectSign(@Body Map<String, Object> map);

    //获取用户列表
    @GET("API/NeteaseMi/GetDeptUserList")
    Call<ResponBean> getDeptUserList(@Query("userID") String userID, @Query("userName") String userName);

    //获取用户列表
    @GET("API/NeteaseMi/AppSendBatchAttachMsg")
    Call<ResponBean> appSendBatchAttachMsg(@Query("fromAccid") String fromAccid, @Query("toAccids") String toAccids, @Query("roomId") String roomId);

    //手机端发送系统自定义消息(视频机)
    @GET("API/NeteaseMi/AppSendVideoMachineMsg")
    Call<ResponBean> appSendVideoMachineMsg(@Query("fromAccid") String fromAccid, @Query("LiftID") String liftID, @Query("roomId") String roomId,@Query("isMonitoring") boolean isMonitoring);

    //获取用户个人发起记录
    @GET("API/NeteaseMi/GetAppAlarmRoomRecord")
    Call<ResponBean> getAppAlarmRoomRecord(@Query("userID") String userID, @Query("userName") String userName);

    //获取房间连接人员
    @GET("API/NeteaseMi/GetAppAlarmRoomRecordUser")
    Call<ResponBean> getAppAlarmRoomRecordUser(@Query("roomID") String roomID);

    //创建房间记录
    @GET("API/NeteaseMi/CreateAPPAlarmRoomRecord")
    Call<ResponBean> createAPPAlarmRoomRecord(@Query("userID") String userID, @Query("roomID") String roomID, @Query("userIds") String userIds);

    //修改房间人员状态
    @GET("API/NeteaseMi/APPAlarmRoomRecordUserUpdateState")
    Call<ResponBean> addAPPAlarmRoomRecordUser(@Query("userID") String userID, @Query("roomID") String roomID);

    //修改房间离开时间
    @GET("API/NeteaseMi/APPAlarmRoomRecordUpdateEndTime")
    Call<ResponBean> aPPAlarmRoomRecordUpdateEndTime(@Query("roomID") String roomID);

    //请求拍照
    @POST("Api/Equipments/RequestEquipmentPhotograph")
    Call<ResponBean> requestEquipmentPhotograph(@Body Map<String, Object> map);

    //获取最新救援照片
    @POST("Api/Equipments/GetEquipmentFile")
    Call<ResponBean> getEquipmentFile(@Body Map<String, Object> map);

    //获取最新救援照片list
    @POST("Api/Equipments/GetEquipmentFileList")
    Call<ResponBean> getEquipmentFileList(@Body Map<String, Object> map);
    /**
     *电梯云结束
     */

}