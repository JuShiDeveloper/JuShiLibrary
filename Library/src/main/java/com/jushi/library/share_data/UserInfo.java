package com.jushi.library.share_data;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    /**
     * address :
     * auditOpinion :
     * avatar :
     * buyerAudit : 0
     * certificates : [{"code":"","createdBy":0,"createdTime":"","cuserId":0,"delFlag":0,"id":0,"name":"","picturePaths":"","updatedBy":0,"updatedTime":""}]
     * cityCode :
     * companyCode :
     * companyName :
     * countyCode :
     * createdBy : 0
     * createdTime :
     * delFlag : 0
     * email :
     * employeeAudit : 0
     * id : 0
     * idCard :
     * lastLoginTime :
     * legalPerson :
     * legalPersonIdCard :
     * nickName :
     * passSalt :
     * password :
     * phone :
     * provinceCode :
     * realName :
     * sellerAudit : 0
     * sex :
     * shopDesc :
     * shopName :
     * streetCode :
     * totalCostAmt : 0
     * updatedBy : 0
     * updatedTime :
     * userCode :
     * userEmployeeRoles : [{"roles":[{"roleName":"","roleValue":[{"canonicalKeyPropertyListString":"","canonicalName":"","domain":"","domainPattern":true,"keyPropertyList":{},"keyPropertyListString":"","pattern":true,"propertyListPattern":true,"propertyPattern":true,"propertyValuePattern":true}]}],"shop":{"address":"","auditOpinion":"","avatar":"","buyerAudit":0,"certificates":[{"code":"","createdBy":0,"createdTime":"","cuserId":0,"delFlag":0,"id":0,"name":"","picturePaths":"","updatedBy":0,"updatedTime":""}],"cityCode":"","companyCode":"","companyName":"","countyCode":"","createdBy":0,"createdTime":"","delFlag":0,"email":"","employeeAudit":0,"id":0,"idCard":"","lastLoginTime":"","legalPerson":"","legalPersonIdCard":"","nickName":"","passSalt":"","password":"","phone":"","provinceCode":"","realName":"","sellerAudit":0,"sex":"","shopDesc":"","shopName":"","streetCode":"","totalCostAmt":0,"updatedBy":0,"updatedTime":"","userCode":"","userIntro":"","userName":"","userScore":0,"userStatus":0,"userType":0}}]
     * userIntro :
     * userName :
     * userScore : 0
     * userStatus : 0
     * userType : 0
     */

    private String address;
    private String auditOpinion;
    private String avatar;
    private int buyerAudit;
    private String cityCode;
    private String companyCode;
    private String companyName;
    private String countyCode;
    private int createdBy;
    private String createdTime;
    private int delFlag;
    private String email;
    private int employeeAudit;
    private int id;
    private String idCard;
    private String lastLoginTime;
    private String legalPerson;
    private String legalPersonIdCard;
    private String nickName;
    private String passSalt;
    private String password;
    private String phone;
    private String provinceCode;
    private String realName;
    private int sellerAudit;
    private String sex;
    private String shopDesc;
    private String shopName;
    private String streetCode;
    private int totalCostAmt;
    private int updatedBy;
    private String updatedTime;
    private String userCode;
    private String userIntro;
    private String userName;
    private int userScore;
    private int userStatus;
    private int userType;
    private String token;
    private List<CertificatesBean> certificates;
    private List<UserEmployeeRolesBean> userEmployeeRoles;
    private String employeeAuditOpinion;
    private String sellerAuditOpinion;
    private String buyerAuditOpinion;
    private UserInfo.UserEmployeeRolesBean currentShop;//当前所在店铺

    public UserEmployeeRolesBean getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(UserEmployeeRolesBean currentShop) {
        this.currentShop = currentShop;
    }

    public String getEmployeeAuditOpinion() {
        return employeeAuditOpinion;
    }

    public void setEmployeeAuditOpinion(String employeeAuditOpinion) {
        this.employeeAuditOpinion = employeeAuditOpinion;
    }

    public String getSellerAuditOpinion() {
        return sellerAuditOpinion;
    }

    public void setSellerAuditOpinion(String sellerAuditOpinion) {
        this.sellerAuditOpinion = sellerAuditOpinion;
    }

    public String getBuyerAuditOpinion() {
        return buyerAuditOpinion;
    }

    public void setBuyerAuditOpinion(String buyerAuditOpinion) {
        this.buyerAuditOpinion = buyerAuditOpinion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAvatar() {
        return avatar==null?"":avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBuyerAudit() {
        return buyerAudit;
    }

    public void setBuyerAudit(int buyerAudit) {
        this.buyerAudit = buyerAudit;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmployeeAudit() {
        return employeeAudit;
    }

    public void setEmployeeAudit(int employeeAudit) {
        this.employeeAudit = employeeAudit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonIdCard() {
        return legalPersonIdCard;
    }

    public void setLegalPersonIdCard(String legalPersonIdCard) {
        this.legalPersonIdCard = legalPersonIdCard;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getRealName() {
        return realName==null?"":realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSellerAudit() {
        return sellerAudit;
    }

    public void setSellerAudit(int sellerAudit) {
        this.sellerAudit = sellerAudit;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShopDesc() {
        return shopDesc;
    }

    public void setShopDesc(String shopDesc) {
        this.shopDesc = shopDesc;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public int getTotalCostAmt() {
        return totalCostAmt;
    }

    public void setTotalCostAmt(int totalCostAmt) {
        this.totalCostAmt = totalCostAmt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUserCode() {
        return userCode==null?"":userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<CertificatesBean> getCertificates() {
        return certificates==null?new ArrayList<>():certificates;
    }

    public void setCertificates(List<CertificatesBean> certificates) {
        this.certificates = certificates;
    }

    public List<UserEmployeeRolesBean> getUserEmployeeRoles() {
        return userEmployeeRoles==null?new ArrayList<>():userEmployeeRoles;
    }

    public void setUserEmployeeRoles(List<UserEmployeeRolesBean> userEmployeeRoles) {
        this.userEmployeeRoles = userEmployeeRoles;
    }

    public static class CertificatesBean {
        /**
         * code :
         * createdBy : 0
         * createdTime :
         * cuserId : 0
         * delFlag : 0
         * id : 0
         * name :
         * picturePaths :
         * updatedBy : 0
         * updatedTime :
         */

        private String code;
        private int createdBy;
        private String createdTime;
        private int cuserId;
        private int delFlag;
        private int id;
        private String name;
        private String picturePaths;
        private int updatedBy;
        private String updatedTime;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getCuserId() {
            return cuserId;
        }

        public void setCuserId(int cuserId) {
            this.cuserId = cuserId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicturePaths() {
            return picturePaths;
        }

        public void setPicturePaths(String picturePaths) {
            this.picturePaths = picturePaths;
        }

        public int getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(int updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }
    }

    public static class UserEmployeeRolesBean {
        /**
         * roles : [{"roleName":"","roleValue":[{"canonicalKeyPropertyListString":"","canonicalName":"","domain":"","domainPattern":true,"keyPropertyList":{},"keyPropertyListString":"","pattern":true,"propertyListPattern":true,"propertyPattern":true,"propertyValuePattern":true}]}]
         * shop : {"address":"","auditOpinion":"","avatar":"","buyerAudit":0,"certificates":[{"code":"","createdBy":0,"createdTime":"","cuserId":0,"delFlag":0,"id":0,"name":"","picturePaths":"","updatedBy":0,"updatedTime":""}],"cityCode":"","companyCode":"","companyName":"","countyCode":"","createdBy":0,"createdTime":"","delFlag":0,"email":"","employeeAudit":0,"id":0,"idCard":"","lastLoginTime":"","legalPerson":"","legalPersonIdCard":"","nickName":"","passSalt":"","password":"","phone":"","provinceCode":"","realName":"","sellerAudit":0,"sex":"","shopDesc":"","shopName":"","streetCode":"","totalCostAmt":0,"updatedBy":0,"updatedTime":"","userCode":"","userIntro":"","userName":"","userScore":0,"userStatus":0,"userType":0}
         */

        private ShopBean shop;
        private List<RolesBean> roles;

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public List<RolesBean> getRoles() {
            return roles;
        }

        public void setRoles(List<RolesBean> roles) {
            this.roles = roles;
        }

        public static class ShopBean {
            /**
             * address :
             * auditOpinion :
             * avatar :
             * buyerAudit : 0
             * certificates : [{"code":"","createdBy":0,"createdTime":"","cuserId":0,"delFlag":0,"id":0,"name":"","picturePaths":"","updatedBy":0,"updatedTime":""}]
             * cityCode :
             * companyCode :
             * companyName :
             * countyCode :
             * createdBy : 0
             * createdTime :
             * delFlag : 0
             * email :
             * employeeAudit : 0
             * id : 0
             * idCard :
             * lastLoginTime :
             * legalPerson :
             * legalPersonIdCard :
             * nickName :
             * passSalt :
             * password :
             * phone :
             * provinceCode :
             * realName :
             * sellerAudit : 0
             * sex :
             * shopDesc :
             * shopName :
             * streetCode :
             * totalCostAmt : 0
             * updatedBy : 0
             * updatedTime :
             * userCode :
             * userIntro :
             * userName :
             * userScore : 0
             * userStatus : 0
             * userType : 0
             */

            private String address;
            private String auditOpinion;
            private String avatar;
            private int buyerAudit;
            private String cityCode;
            private String companyCode;
            private String companyName;
            private String countyCode;
            private int createdBy;
            private String createdTime;
            private int delFlag;
            private String email;
            private int employeeAudit;
            private int id;
            private String idCard;
            private String lastLoginTime;
            private String legalPerson;
            private String legalPersonIdCard;
            private String nickName;
            private String passSalt;
            private String password;
            private String phone;
            private String provinceCode;
            private String realName;
            private int sellerAudit;
            private String sex;
            private String shopDesc;
            private String shopName;
            private String streetCode;
            private int totalCostAmt;
            private int updatedBy;
            private String updatedTime;
            private String userCode;
            private String userIntro;
            private String userName;
            private int userScore;
            private int userStatus;
            private int userType;
            private List<CertificatesBeanX> certificates;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAuditOpinion() {
                return auditOpinion;
            }

            public void setAuditOpinion(String auditOpinion) {
                this.auditOpinion = auditOpinion;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getBuyerAudit() {
                return buyerAudit;
            }

            public void setBuyerAudit(int buyerAudit) {
                this.buyerAudit = buyerAudit;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getCompanyCode() {
                return companyCode;
            }

            public void setCompanyCode(String companyCode) {
                this.companyCode = companyCode;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getCountyCode() {
                return countyCode;
            }

            public void setCountyCode(String countyCode) {
                this.countyCode = countyCode;
            }

            public int getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(int createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getEmployeeAudit() {
                return employeeAudit;
            }

            public void setEmployeeAudit(int employeeAudit) {
                this.employeeAudit = employeeAudit;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public String getLegalPerson() {
                return legalPerson;
            }

            public void setLegalPerson(String legalPerson) {
                this.legalPerson = legalPerson;
            }

            public String getLegalPersonIdCard() {
                return legalPersonIdCard;
            }

            public void setLegalPersonIdCard(String legalPersonIdCard) {
                this.legalPersonIdCard = legalPersonIdCard;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPassSalt() {
                return passSalt;
            }

            public void setPassSalt(String passSalt) {
                this.passSalt = passSalt;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getProvinceCode() {
                return provinceCode;
            }

            public void setProvinceCode(String provinceCode) {
                this.provinceCode = provinceCode;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public int getSellerAudit() {
                return sellerAudit;
            }

            public void setSellerAudit(int sellerAudit) {
                this.sellerAudit = sellerAudit;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getShopDesc() {
                return shopDesc;
            }

            public void setShopDesc(String shopDesc) {
                this.shopDesc = shopDesc;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getStreetCode() {
                return streetCode;
            }

            public void setStreetCode(String streetCode) {
                this.streetCode = streetCode;
            }

            public int getTotalCostAmt() {
                return totalCostAmt;
            }

            public void setTotalCostAmt(int totalCostAmt) {
                this.totalCostAmt = totalCostAmt;
            }

            public int getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(int updatedBy) {
                this.updatedBy = updatedBy;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public String getUserIntro() {
                return userIntro;
            }

            public void setUserIntro(String userIntro) {
                this.userIntro = userIntro;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserScore() {
                return userScore;
            }

            public void setUserScore(int userScore) {
                this.userScore = userScore;
            }

            public int getUserStatus() {
                return userStatus;
            }

            public void setUserStatus(int userStatus) {
                this.userStatus = userStatus;
            }

            public int getUserType() {
                return userType;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public List<CertificatesBeanX> getCertificates() {
                return certificates;
            }

            public void setCertificates(List<CertificatesBeanX> certificates) {
                this.certificates = certificates;
            }

            public static class CertificatesBeanX {
                /**
                 * code :
                 * createdBy : 0
                 * createdTime :
                 * cuserId : 0
                 * delFlag : 0
                 * id : 0
                 * name :
                 * picturePaths :
                 * updatedBy : 0
                 * updatedTime :
                 */

                private String code;
                private int createdBy;
                private String createdTime;
                private int cuserId;
                private int delFlag;
                private int id;
                private String name;
                private String picturePaths;
                private int updatedBy;
                private String updatedTime;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(int createdBy) {
                    this.createdBy = createdBy;
                }

                public String getCreatedTime() {
                    return createdTime;
                }

                public void setCreatedTime(String createdTime) {
                    this.createdTime = createdTime;
                }

                public int getCuserId() {
                    return cuserId;
                }

                public void setCuserId(int cuserId) {
                    this.cuserId = cuserId;
                }

                public int getDelFlag() {
                    return delFlag;
                }

                public void setDelFlag(int delFlag) {
                    this.delFlag = delFlag;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPicturePaths() {
                    return picturePaths;
                }

                public void setPicturePaths(String picturePaths) {
                    this.picturePaths = picturePaths;
                }

                public int getUpdatedBy() {
                    return updatedBy;
                }

                public void setUpdatedBy(int updatedBy) {
                    this.updatedBy = updatedBy;
                }

                public String getUpdatedTime() {
                    return updatedTime;
                }

                public void setUpdatedTime(String updatedTime) {
                    this.updatedTime = updatedTime;
                }
            }
        }

        public static class RolesBean {
            /**
             * {
             *                         "id":2,
             *                         "name":"卖货员",
             *                         "remark":null,
             *                         "isSystem":0,
             *                         "state":0,
             *                         "delFlag":0,
             *                         "createdBy":null,
             *                         "createdTime":null,
             *                         "updatedBy":null,
             *                         "updatedTime":null
             *                     }
             */
            private int id;
            private String name;
            private String remark;
            private int isSystem;
            private int delFlag;
            private int state;
            private String createdBy;
            private String createdTime;
            private String updatedBy;
            private String updatedTime;

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getIsSystem() {
                return isSystem;
            }

            public void setIsSystem(int isSystem) {
                this.isSystem = isSystem;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(String updatedBy) {
                this.updatedBy = updatedBy;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
            }
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "address='" + address + '\'' +
                ", auditOpinion='" + auditOpinion + '\'' +
                ", avatar='" + avatar + '\'' +
                ", buyerAudit=" + buyerAudit +
                ", cityCode='" + cityCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", countyCode='" + countyCode + '\'' +
                ", createdBy=" + createdBy +
                ", createdTime='" + createdTime + '\'' +
                ", delFlag=" + delFlag +
                ", email='" + email + '\'' +
                ", employeeAudit=" + employeeAudit +
                ", id=" + id +
                ", idCard='" + idCard + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", legalPerson='" + legalPerson + '\'' +
                ", legalPersonIdCard='" + legalPersonIdCard + '\'' +
                ", nickName='" + nickName + '\'' +
                ", passSalt='" + passSalt + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", realName='" + realName + '\'' +
                ", sellerAudit=" + sellerAudit +
                ", sex='" + sex + '\'' +
                ", shopDesc='" + shopDesc + '\'' +
                ", shopName='" + shopName + '\'' +
                ", streetCode='" + streetCode + '\'' +
                ", totalCostAmt=" + totalCostAmt +
                ", updatedBy=" + updatedBy +
                ", updatedTime='" + updatedTime + '\'' +
                ", userCode='" + userCode + '\'' +
                ", userIntro='" + userIntro + '\'' +
                ", userName='" + userName + '\'' +
                ", userScore=" + userScore +
                ", userStatus=" + userStatus +
                ", userType=" + userType +
                ", certificates=" + certificates +
                ", userEmployeeRoles=" + userEmployeeRoles +
                '}';
    }
}
