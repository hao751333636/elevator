package com.sinodom.elevator.bean.notice;

import java.io.Serializable;

/**
 * Created by GUO on 2017/12/28.
 */

public class NoticeBean implements Serializable{

    /**
     * UserId : 34
     * User : null
     * NoticeTypeId : 235
     * NoticeTypeName : {"ParentId":223,"ParentDict":null,"ChildDict":null,"TreeLeve":3,"FullPath":"通知","DictName":"通知 ","DictCode":"TZ","DictDesc":"","DictValue":"","Sort":1,"ID":235,"CreateTime":"2015-11-17T16:26:59.563"}
     * Title : 2017年盘锦市电梯维护保养单位考核结果的公示
     * Content : 1
     * IsTop : false
     * ReadCount : 0
     * IsActive : true
     * FilePath : null
     * IsAll : false
     * RecipientCount : 12704
     * RecipientConfirmCount : 43
     * RecipientIds :
     * ID : 27
     * CreateTime : 2017-06-22T08:44:50.547
     */

    private int UserId;
    private Object User;
    private int NoticeTypeId;
    private NoticeTypeNameBean NoticeTypeName;
    private String Title;
    private String Content;
    private boolean IsTop;
    private int ReadCount;
    private boolean IsActive;
    private Object FilePath;
    private boolean IsAll;
    private int RecipientCount;
    private int RecipientConfirmCount;
    private String RecipientIds;
    private int ID;
    private String CreateTime;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public Object getUser() {
        return User;
    }

    public void setUser(Object User) {
        this.User = User;
    }

    public int getNoticeTypeId() {
        return NoticeTypeId;
    }

    public void setNoticeTypeId(int NoticeTypeId) {
        this.NoticeTypeId = NoticeTypeId;
    }

    public NoticeTypeNameBean getNoticeTypeName() {
        return NoticeTypeName;
    }

    public void setNoticeTypeName(NoticeTypeNameBean NoticeTypeName) {
        this.NoticeTypeName = NoticeTypeName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public boolean isIsTop() {
        return IsTop;
    }

    public void setIsTop(boolean IsTop) {
        this.IsTop = IsTop;
    }

    public int getReadCount() {
        return ReadCount;
    }

    public void setReadCount(int ReadCount) {
        this.ReadCount = ReadCount;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public Object getFilePath() {
        return FilePath;
    }

    public void setFilePath(Object FilePath) {
        this.FilePath = FilePath;
    }

    public boolean isIsAll() {
        return IsAll;
    }

    public void setIsAll(boolean IsAll) {
        this.IsAll = IsAll;
    }

    public int getRecipientCount() {
        return RecipientCount;
    }

    public void setRecipientCount(int RecipientCount) {
        this.RecipientCount = RecipientCount;
    }

    public int getRecipientConfirmCount() {
        return RecipientConfirmCount;
    }

    public void setRecipientConfirmCount(int RecipientConfirmCount) {
        this.RecipientConfirmCount = RecipientConfirmCount;
    }

    public String getRecipientIds() {
        return RecipientIds;
    }

    public void setRecipientIds(String RecipientIds) {
        this.RecipientIds = RecipientIds;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public static class NoticeTypeNameBean implements Serializable {
        /**
         * ParentId : 223
         * ParentDict : null
         * ChildDict : null
         * TreeLeve : 3
         * FullPath : 通知
         * DictName : 通知
         * DictCode : TZ
         * DictDesc :
         * DictValue :
         * Sort : 1
         * ID : 235
         * CreateTime : 2015-11-17T16:26:59.563
         */

        private int ParentId;
        private Object ParentDict;
        private Object ChildDict;
        private int TreeLeve;
        private String FullPath;
        private String DictName;
        private String DictCode;
        private String DictDesc;
        private String DictValue;
        private int Sort;
        private int ID;
        private String CreateTime;

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public Object getParentDict() {
            return ParentDict;
        }

        public void setParentDict(Object ParentDict) {
            this.ParentDict = ParentDict;
        }

        public Object getChildDict() {
            return ChildDict;
        }

        public void setChildDict(Object ChildDict) {
            this.ChildDict = ChildDict;
        }

        public int getTreeLeve() {
            return TreeLeve;
        }

        public void setTreeLeve(int TreeLeve) {
            this.TreeLeve = TreeLeve;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getDictName() {
            return DictName;
        }

        public void setDictName(String DictName) {
            this.DictName = DictName;
        }

        public String getDictCode() {
            return DictCode;
        }

        public void setDictCode(String DictCode) {
            this.DictCode = DictCode;
        }

        public String getDictDesc() {
            return DictDesc;
        }

        public void setDictDesc(String DictDesc) {
            this.DictDesc = DictDesc;
        }

        public String getDictValue() {
            return DictValue;
        }

        public void setDictValue(String DictValue) {
            this.DictValue = DictValue;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
