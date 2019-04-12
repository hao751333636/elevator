package com.sinodom.elevator.single;

import android.content.Context;

import com.sinodom.elevator.Constants;
import com.sinodom.elevator.db.Inspect;
import com.sinodom.elevator.db.InspectItem;
import com.sinodom.elevator.db.Location;
import com.sinodom.elevator.db.Maintenance;
import com.sinodom.elevator.db.NfcBind;
import com.sinodom.elevator.db.PaperlessMaintenance;
import com.sinodom.elevator.db.Parts;
import com.sinodom.elevator.db.Session;
import com.sinodom.elevator.db.StatusAction;
import com.sinodom.elevator.db.greendao.DaoMaster;
import com.sinodom.elevator.db.greendao.DaoSession;
import com.sinodom.elevator.db.greendao.InspectDao;
import com.sinodom.elevator.db.greendao.InspectItemDao;
import com.sinodom.elevator.db.greendao.LocationDao;
import com.sinodom.elevator.db.greendao.MaintenanceDao;
import com.sinodom.elevator.db.greendao.NfcBindDao;
import com.sinodom.elevator.db.greendao.PaperlessMaintenanceDao;
import com.sinodom.elevator.db.greendao.PartsDao;
import com.sinodom.elevator.db.greendao.SessionDao;
import com.sinodom.elevator.db.greendao.StatusActionDao;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class ElevatorManager {

    private static ElevatorManager single = null;

    private int random = 0;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private Context context;
    private SessionDao sessionDao;
    private StatusActionDao statusActionDao;
    private LocationDao locationDao;
    private InspectDao inspectDao;
    private InspectItemDao inspectItemDao;
    private MaintenanceDao maintenanceDao;
    private PaperlessMaintenanceDao paperlessMaintenanceDao;
    private NfcBindDao nfcBindDao;
    private PartsDao partsDao;
    private Session session;

    private String deviceToken;

    public synchronized static ElevatorManager getInstance() {
        if (single == null) {
            single = new ElevatorManager();
        }
        return single;
    }

    public void init(Context context) {
        if (sessionDao != null) {
            return;
        }
        this.context = context;
        sessionDao = getDaoSession(context).getSessionDao();
        sessionDao = daoSession.getSessionDao();
        statusActionDao = daoSession.getStatusActionDao();
        locationDao = daoSession.getLocationDao();
        inspectDao = daoSession.getInspectDao();
        inspectItemDao = daoSession.getInspectItemDao();
        maintenanceDao = daoSession.getMaintenanceDao();
        paperlessMaintenanceDao = daoSession.getPaperlessMaintenanceDao();
        nfcBindDao = daoSession.getNfcBindDao();
        partsDao = daoSession.getPartsDao();
        //session = sessionDao.queryBuilder().unique();
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.Config.DB_NAME);
            daoMaster = new DaoMaster(helper.getWritableDb());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    //批量新增StatusAction
    public void addStatusActionList(List<StatusAction> list) {
        statusActionDao.deleteAll();
        statusActionDao.insertInTx(list);
    }

    //获取StatusAction by statusId
    public List<StatusAction> getStatusActionList(int statusId) {
        List<StatusAction> list = statusActionDao.queryBuilder()
                .where(StatusActionDao.Properties.StatusId.eq(statusId))
                .list();
        return list;
    }

    //获取StatusAction by StatusId = Argument
    public List<StatusAction> getStatusActionListByArgument(int argument) {
        List<StatusAction> list = statusActionDao.queryBuilder()
                .where(StatusActionDao.Properties.StatusId.eq(argument))
                .list();
        return list;
    }

    public Session getSession() {
        if (session == null) {
            session = sessionDao.queryBuilder().unique();
        }
        return session;
    }

    public Session getoffLineSession() {
        try {
            Session session = sessionDao.queryBuilder().unique();
            return session;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSession(Session session) {
        this.session = session;
        saveSession();
    }

    public void delSession() {
        sessionDao.deleteAll();
        this.session = null;
    }

    public void saveSession() {
        sessionDao.deleteAll();
        sessionDao.insertOrReplace(session);
    }

    //新增定位信息
    public void addLocation(Location location) {
        locationDao.insertOrReplace(location);
    }

    //修改定位信息
    public void updateLocation(List<Location> locationList) {
        locationDao.updateInTx(locationList);
    }

    //删除定位信息
    public void delLocation(String guid) {
        QueryBuilder<Location> qb = locationDao.queryBuilder();
        DeleteQuery<Location> bd = qb.where(LocationDao.Properties.Guid.eq(guid)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    //查询全部定位信息
    public List<Location> getLocationList(int userId) {
        if (userId == 0) {
            return null;
        } else {
            List<Location> locationList = locationDao.queryBuilder().where(LocationDao.Properties.UserId.eq(userId)).list();
            return locationList;
        }
    }

    /************************************************ 离线数据-start ***********************************************/
    //新增-检验分类
    public void addInspectItem(InspectItem inspect) {
        if (getInspectItem(inspect.getInspectId(), inspect.getStepId()) != null) {
            inspectItemDao.delete(getInspectItem(inspect.getInspectId(), inspect.getStepId()));
        }
        inspectItemDao.insertOrReplace(inspect);
    }

    //获取未上传检验分类数据
    public List<InspectItem> getInspectItemList() {
        List<InspectItem> inspectList = inspectItemDao.queryBuilder().list();
        return inspectList;
    }

    //单删-检验分类
    public void delInspectItemByKey(Long key) {
        inspectItemDao.deleteByKey(key);
    }

    //根据InspectId，返回对应InspectId的数据
    public List<InspectItem> getInspectItem(int inspectId) {
        List<InspectItem> inspectList = inspectItemDao.queryBuilder().where(InspectItemDao.Properties.InspectId.eq(inspectId)).list();
        if (inspectList != null && inspectList.size() > 0) {
            return inspectList;
        } else {
            return null;
        }
    }

    //根据InspectId，返回对应InspectId,StepId的数据
    public InspectItem getInspectItem(int inspectId, int stepId) {
        List<InspectItem> inspectList = inspectItemDao.queryBuilder().where(InspectItemDao.Properties.InspectId.eq(inspectId),
                InspectItemDao.Properties.StepId.eq(stepId)).list();
        if (inspectList != null && inspectList.size() > 0) {
            return inspectList.get(inspectList.size() - 1);
        } else {
            return null;
        }
    }

    //新增-检验
    public void addInspect(Inspect inspect) {
        inspectDao.insertOrReplace(inspect);
    }

    //获取未上传检验数据
    public List<Inspect> getInspectList() {
        List<Inspect> inspectList = inspectDao.queryBuilder().list();
        return inspectList;
    }

    //单删-检验
    public void delInspectByKey(Long key) {
        inspectDao.deleteByKey(key);
    }

    //新增-配件
    public void addParts(Parts parts) {
        partsDao.insertOrReplace(parts);
    }

    //获取未上传配件数据
    public List<Parts> getPartsList() {
        List<Parts> partsList = partsDao.queryBuilder().list();
        return partsList;
    }

    //获取未上传配件数据
    public List<Parts> getPartsList(String liftId) {
        List<Parts> partsList = partsDao.queryBuilder()
                .where(PartsDao.Properties.LiftId.eq(liftId))
                .list();
        return partsList;
    }

    //根据liftId和PartsTypeId，返回对应的数据
    public Parts getPartsItem(int liftId, int partsTypeId) {
        Parts parts = partsDao.queryBuilder().where(PartsDao.Properties.LiftId.eq(liftId),
                PartsDao.Properties.PartsTypeId.eq(partsTypeId)).unique();
        return parts;
    }

    //单删-配件
    public void delPartsByKey(Long key) {
        partsDao.deleteByKey(key);
    }

    //新增-维保
    public void addMaintenance(Maintenance maintenance) {
        maintenanceDao.insertOrReplace(maintenance);
    }

    //获取未上传维保数据
    public List<Maintenance> getMaintenanceList() {
        List<Maintenance> maintenanceList = maintenanceDao.queryBuilder().list();
        return maintenanceList;
    }

    //单删-维保
    public void delMaintenanceByKey(Long key) {
        maintenanceDao.deleteByKey(key);
    }

    //新增-无纸化维保
    public void addPaperlessMaintenance(PaperlessMaintenance maintenance) {
        paperlessMaintenanceDao.insertOrReplace(maintenance);
    }

    //获取未上传无纸化维保数据
    public List<PaperlessMaintenance> getPaperlessMaintenanceList() {
        List<PaperlessMaintenance> maintenanceList = paperlessMaintenanceDao.queryBuilder().list();
        return maintenanceList;
    }

    //单删-无纸化维保
    public void delPaperlessMaintenanceByKey(Long key) {
        paperlessMaintenanceDao.deleteByKey(key);
    }

    //新增-nfc绑定
    public void addNfcBind(NfcBind maintenance) {
        nfcBindDao.insertOrReplace(maintenance);
    }

    //获取未上传nfc绑定
    public List<NfcBind> getNfcBindList() {
        List<NfcBind> maintenanceList = nfcBindDao.queryBuilder().list();
        return maintenanceList;
    }

    //单删-nfc绑定
    public void delNfcBindByKey(Long key) {
        nfcBindDao.deleteByKey(key);
    }

    /************************************************ 离线数据-end ***********************************************/

    //验证码
    public int random() {
        random = (int) (Math.random() * (9999 - 1000 + 1)) + 1000;//产生1000-9999的随机数
        return random;
    }

    public int getRandom() {
        if (random == 0) {
            random();
        }
        return random;
    }

    public void clearRandom() {
        random = 0;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}