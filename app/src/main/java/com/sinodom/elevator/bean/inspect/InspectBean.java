package com.sinodom.elevator.bean.inspect;

import java.io.Serializable;
import java.util.List;

public class InspectBean implements Serializable{

    /**
     * ID : 155797
     * LiftNum : 585741
     * CertificateNum : 10020030040050060070
     * InstallationAddress : 丽湾国际
     * User : {"ID":3922,"UserName":"admin"}
     * InspectType : [{"ID":1,"TypeName":"制动器试验","TypeCode":"Brake","State":0,"Dept":[{"DeptId":5378,"DeptName":"沈阳特检院"},{"DeptId":5590,"DeptName":"辽宁省安科院"}]},{"ID":2,"TypeName":"限速器校验","TypeCode":"SpeedLimiter","State":0,"Dept":[{"DeptId":5378,"DeptName":"沈阳特检院"},{"DeptId":5590,"DeptName":"辽宁省安科院"}]},{"ID":3,"TypeName":"平衡系数校验","TypeCode":"EquilibriumCoefficient","State":0,"Dept":[{"DeptId":5378,"DeptName":"沈阳特检院"},{"DeptId":5590,"DeptName":"辽宁省安科院"}]}]
     */

    private int ID;
    private String LiftNum;
    private String CertificateNum;
    private String InstallationAddress;
    private UserBean User;
    private List<InspectTypeBean> InspectType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String LiftNum) {
        this.LiftNum = LiftNum;
    }

    public String getCertificateNum() {
        return CertificateNum;
    }

    public void setCertificateNum(String CertificateNum) {
        this.CertificateNum = CertificateNum;
    }

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public List<InspectTypeBean> getInspectType() {
        return InspectType;
    }

    public void setInspectType(List<InspectTypeBean> InspectType) {
        this.InspectType = InspectType;
    }

    public static class UserBean implements Serializable{
        /**
         * ID : 3922
         * UserName : admin
         */

        private int ID;
        private String UserName;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }
    }

    public static class InspectTypeBean implements Serializable{
        /**
         * ID : 1
         * TypeName : 制动器试验
         * TypeCode : Brake
         * State : 0
         * Dept : [{"DeptId":5378,"DeptName":"沈阳特检院"},{"DeptId":5590,"DeptName":"辽宁省安科院"}]
         */

        private int ID;
        private String TypeName;
        private String TypeCode;
        private int State;
        private int DeptState;
        private int WorkFormState;
        private List<DeptBean> Dept;
        private List<WorkFormBean> WorkForm;

        public int getWorkFormState() {
            return WorkFormState;
        }

        public void setWorkFormState(int workFormState) {
            WorkFormState = workFormState;
        }

        public int getDeptState() {
            return DeptState;
        }

        public void setDeptState(int deptState) {
            DeptState = deptState;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public String getTypeCode() {
            return TypeCode;
        }

        public void setTypeCode(String TypeCode) {
            this.TypeCode = TypeCode;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public List<DeptBean> getDept() {
            return Dept;
        }

        public void setDept(List<DeptBean> Dept) {
            this.Dept = Dept;
        }

        public List<WorkFormBean> getWorkForm() {
            return WorkForm;
        }

        public void setWorkForm(List<WorkFormBean> workForm) {
            WorkForm = workForm;
        }

        public static class DeptBean implements Serializable{
            /**
             * DeptId : 5378
             * DeptName : 沈阳特检院
             */

            private int DeptId;
            private String DeptName;

            public int getDeptId() {
                return DeptId;
            }

            public void setDeptId(int DeptId) {
                this.DeptId = DeptId;
            }

            public String getDeptName() {
                return DeptName;
            }

            public void setDeptName(String DeptName) {
                this.DeptName = DeptName;
            }
        }
        public static class WorkFormBean implements Serializable{
            /**
             * DeptId : 5378
             * DeptName : 沈阳特检院
             */

            private int Id;
            private String Name;

            public int getId() {
                return Id;
            }

            public void setId(int id) {
                Id = id;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }
        }
    }
}
