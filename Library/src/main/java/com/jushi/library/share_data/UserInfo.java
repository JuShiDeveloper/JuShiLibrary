package com.jushi.library.share_data;

import java.util.List;

public class UserInfo {

    /**
     * id : 2
     * name : 永兴管理员
     * avatar : /file/user/avatar/avatareb881731-d66e-4411-acfb-39390f394c51.png
     * idCard : 520102199003078972
     * username : 13348613574
     * phoneNumber : 13348613574
     * state : 1
     * roles : [{"id":5,"name":"永兴管理员","description":"永兴管理员","menus":"44,45,46,47,91,92,93,94,51,95,96,97,98,10,99,11,12,13,14,15,16,17,1503,1701,1,1502,2,1105,1303,1501,3,1104,1302,4,1103,1301,5,1102,6,1101,7,8,9,61,62,63,64,21,65,22,66,23,67,24,68,69,691,71,31,32,33,34,35,1602,1601,101,1204,1402,102,1203,1401,103,1202,104,1201,105,106,81,82,83,84,41,85,42,1604,43,1603","organizationId":1,"delFlag":0,"state":1,"createUserId":null,"createTime":null,"updateUserId":2,"updateTime":"2021-09-16 14:45:22"}]
     * organization : {"id":1,"type":0,"code":"JC0001","name":"永兴检测","socialCreditCode":"JXDA54DASZCX4AS5DAX","legalPerson":"张珊","legalPersonTel":"18312345678","companyAddress":"西部研发基地四号楼","telephone":null,"companyEmail":"11111","companyUrl":"22222","state":0,"delFlag":0,"createUserId":1,"createTime":"2020-11-25 10:26:42","updateUserId":0,"updateTime":"2020-11-25 10:26:42","reChainXid":null,"reChainKey":null,"reChainTime":"2021-09-16 15:03:29","reChainState":null}
     * organizationId : 1
     * organizationName : null
     * menus : [{"id":1,"name":"首页","authority":"检测机构首页","describe":null,"type":null,"path":"/","icon":"fa fa-home","router":"index","state":"1","redirect":"index","component":"home","servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:04:51","updateUserId":null,"updateTime":null,"children":[]},{"id":2,"name":"检测报告","authority":"检测报告","describe":null,"type":null,"path":"/report","icon":"fa fa-file-text-o","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:12:26","updateUserId":null,"updateTime":null,"children":[{"id":21,"name":"检测报告管理","authority":"检测报告管理","describe":null,"type":null,"path":"index","icon":"fa fa-files-o","router":null,"state":"1","redirect":null,"component":"report/index","servers":null,"keepAlive":true,"parentId":2,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:13:21","updateUserId":null,"updateTime":null,"children":[]},{"id":22,"name":"溯源查询","authority":"溯源查询","describe":null,"type":null,"path":"traceability","icon":"fa fa-qrcode","router":null,"state":"1","redirect":null,"component":"report/traceability","servers":null,"keepAlive":true,"parentId":2,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:14:21","updateUserId":null,"updateTime":null,"children":[]},{"id":23,"name":"溯源报告详情","authority":"溯源报告详情","describe":null,"type":"router","path":"traceability/:fileId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"report/traceabilityInfo","servers":null,"keepAlive":false,"parentId":2,"delFlag":0,"createUserId":null,"createTime":"2020-12-14 14:36:25","updateUserId":null,"updateTime":null,"children":[]},{"id":24,"name":"检测报告详情","authority":"检测报告详情","describe":null,"type":"router","path":":fileId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"report/info","servers":null,"keepAlive":false,"parentId":2,"delFlag":0,"createUserId":null,"createTime":"2020-12-14 15:26:45","updateUserId":null,"updateTime":null,"children":[]}]},{"id":3,"name":"项目管理","authority":"项目管理","describe":null,"type":null,"path":"/project","icon":"fa fa-cubes","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:05:46","updateUserId":null,"updateTime":null,"children":[{"id":31,"name":"工程项目列表","authority":"工程项目列表","describe":null,"type":null,"path":"index","icon":"fa fa-th-large","router":null,"state":"1","redirect":null,"component":"project/index","servers":null,"keepAlive":true,"parentId":3,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:06:58","updateUserId":null,"updateTime":null,"children":[]},{"id":32,"name":"新增项目","authority":"新增工程项目","describe":null,"type":"router","path":"new","icon":"fa fa-plus-square-o","router":null,"state":"1","redirect":null,"component":"project/form","servers":null,"keepAlive":false,"parentId":3,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:20:02","updateUserId":null,"updateTime":null,"children":[]},{"id":33,"name":"编辑项目","authority":"编辑工程项目","describe":null,"type":"router","path":":projectId/edit","icon":null,"router":null,"state":"1","redirect":null,"component":"project/form","servers":null,"keepAlive":false,"parentId":3,"delFlag":0,"createUserId":null,"createTime":"2020-12-04 11:29:41","updateUserId":null,"updateTime":null,"children":[]},{"id":34,"name":"项目详情","authority":"工程项目详情","describe":null,"type":"router","path":":projectId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"project/info","servers":null,"keepAlive":false,"parentId":3,"delFlag":0,"createUserId":null,"createTime":"2020-12-04 11:30:30","updateUserId":null,"updateTime":null,"children":[]},{"id":35,"name":"检测项目管理","authority":"检测项目管理","describe":null,"type":null,"path":"testItems","icon":"fa fa-list-ol","router":null,"state":"1","redirect":null,"component":"project/testItems","servers":null,"keepAlive":false,"parentId":3,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:10:09","updateUserId":null,"updateTime":null,"children":[]}]},{"id":4,"name":"设备管理","authority":"设备管理","describe":null,"type":null,"path":"/equipment","icon":"fa fa-hdd-o","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:15:43","updateUserId":null,"updateTime":null,"children":[{"id":41,"name":"设备列表","authority":"设备列表","describe":null,"type":null,"path":"index","icon":"fa fa-th","router":null,"state":"1","redirect":null,"component":"equipment/index","servers":null,"keepAlive":true,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:18:50","updateUserId":null,"updateTime":null,"children":[]},{"id":42,"name":"新增设备","authority":"新增设备","describe":null,"type":"router","path":"new","icon":"fa fa-plus-square-o","router":null,"state":"1","redirect":null,"component":"equipment/form","servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:19:31","updateUserId":null,"updateTime":null,"children":[]},{"id":43,"name":"编辑设备","authority":"编辑设备","describe":null,"type":"router","path":":equipmentId/edit","icon":null,"router":null,"state":"1","redirect":null,"component":"equipment/form","servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-12-02 14:36:09","updateUserId":null,"updateTime":null,"children":[]},{"id":44,"name":"设备详情","authority":"设备详情","describe":null,"type":"router","path":":equipmentId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"equipment/info","servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-12-02 14:36:12","updateUserId":null,"updateTime":null,"children":[]},{"id":45,"name":"设备数据对比","authority":"设备数据对比","describe":null,"type":null,"path":"compare","icon":"fa fa-line-chart","router":null,"state":"1","redirect":null,"component":"equipment/compare","servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-12-04 11:36:42","updateUserId":null,"updateTime":null,"children":[]},{"id":46,"name":"设备目录","authority":"设备目录","describe":null,"type":null,"path":"directory","icon":"fa fa-book","router":null,"state":"1","redirect":null,"component":"equipment/directory","servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-12-04 11:37:29","updateUserId":null,"updateTime":null,"children":[]},{"id":47,"name":"物理量信息保存","authority":"物理量信息保存","describe":null,"type":"button","path":"directory","icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":4,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:04:51","updateUserId":null,"updateTime":null,"children":[]}]},{"id":5,"name":"数据管理","authority":"数据管理","describe":null,"type":null,"path":"/data","icon":"fa fa-database","router":"index","state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:20:46","updateUserId":null,"updateTime":null,"children":[{"id":51,"name":"数据列表","authority":"数据列表","describe":null,"type":null,"path":"index","icon":"fa fa-server","router":null,"state":"1","redirect":null,"component":"data/index","servers":null,"keepAlive":true,"parentId":5,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:22:14","updateUserId":null,"updateTime":null,"children":[]}]},{"id":6,"name":"系统设置","authority":"系统设置","describe":null,"type":null,"path":"/sys","icon":"fa fa-cogs","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:23:21","updateUserId":null,"updateTime":null,"children":[{"id":61,"name":"用户管理","authority":"用户管理","describe":null,"type":null,"path":"user","icon":"fa fa-users","router":null,"state":"1","redirect":null,"component":"sys/user","servers":null,"keepAlive":true,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:24:45","updateUserId":null,"updateTime":null,"children":[]},{"id":62,"name":"角色管理","authority":"角色管理","describe":null,"type":null,"path":"role","icon":"fa fa-vcard-o","router":null,"state":"1","redirect":null,"component":"sys/role","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:25:30","updateUserId":null,"updateTime":null,"children":[]},{"id":63,"name":"菜单管理","authority":"菜单管理","describe":null,"type":null,"path":"menu","icon":"fa fa-sitemap","router":null,"state":"1","redirect":null,"component":"sys/menu","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:26:00","updateUserId":null,"updateTime":null,"children":[]},{"id":64,"name":"新增用户","authority":"新增用户","describe":null,"type":"router","path":"user/new","icon":null,"router":null,"state":"1","redirect":null,"component":"sys/userEdit","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 11:18:54","updateUserId":null,"updateTime":null,"children":[]},{"id":65,"name":"个人中心","authority":"个人中心","describe":null,"type":null,"path":"user/info","icon":"fa fa-user-o","router":null,"state":"1","redirect":null,"component":"sys/userInfo","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 10:27:07","updateUserId":null,"updateTime":null,"children":[]},{"id":66,"name":"编辑用户","authority":"编辑用户","describe":null,"type":"router","path":"user/:userId/edit","icon":null,"router":null,"state":"1","redirect":null,"component":"sys/userEdit","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 11:20:22","updateUserId":null,"updateTime":null,"children":[]},{"id":67,"name":"用户详情","authority":"用户详情","describe":null,"type":"router","path":"user/:userId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"sys/userInfo","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-24 16:32:18","updateUserId":null,"updateTime":null,"children":[]},{"id":68,"name":"审核用户","authority":"审核用户","describe":null,"type":"router","path":"user/:userId/check","icon":null,"router":null,"state":"1","redirect":null,"component":"sys/userInfo","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-25 14:03:04","updateUserId":null,"updateTime":null,"children":[]},{"id":69,"name":"动态参数","authority":"动态参数","describe":null,"type":null,"path":"params","icon":"fa fa-info-circle","router":null,"state":"1","redirect":null,"component":"sys/params","servers":null,"keepAlive":false,"parentId":6,"delFlag":0,"createUserId":null,"createTime":"2020-11-25 14:03:04","updateUserId":null,"updateTime":null,"children":[]}]},{"id":7,"name":"大屏管理","authority":"大屏管理","describe":null,"type":null,"path":"/bigdata","icon":"fa fa-desktop","router":null,"state":"1","redirect":null,"component":"big","servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-12-02 14:39:46","updateUserId":null,"updateTime":null,"children":[{"id":71,"name":"大屏展示","authority":"大屏展示","describe":null,"type":null,"path":"index","icon":"fa fa-desktop","router":null,"state":"1","redirect":null,"component":"bigdata/index","servers":null,"keepAlive":false,"parentId":7,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]}]},{"id":8,"name":"机构管理","authority":"机构管理","describe":null,"type":"","path":"/org","icon":"fa fa-bank","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[{"id":81,"name":"机构列表","authority":"机构列表","describe":null,"type":"","path":"index","icon":"fa fa-list","router":null,"state":"1","redirect":null,"component":"org/index","servers":null,"keepAlive":true,"parentId":8,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":82,"name":"机构详情","authority":"机构详情","describe":null,"type":"router","path":":orgId/info","icon":null,"router":null,"state":"1","redirect":"","component":"org/info","servers":null,"keepAlive":false,"parentId":8,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":83,"name":"新增机构","authority":"新增机构","describe":null,"type":"router","path":"new","icon":"","router":null,"state":"1","redirect":"","component":"org/form","servers":null,"keepAlive":false,"parentId":8,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":84,"name":"编辑机构","authority":"编辑机构","describe":null,"type":"router","path":":orgId/edit","icon":"","router":null,"state":"1","redirect":"","component":"org/form","servers":null,"keepAlive":false,"parentId":8,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":85,"name":"删除机构","authority":"删除机构","describe":null,"type":"button","path":"","icon":null,"router":null,"state":"1","redirect":"","component":"","servers":null,"keepAlive":false,"parentId":8,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]}]},{"id":9,"name":"sim卡流量管理","authority":"sim卡流量管理","describe":null,"type":null,"path":"/simcard","icon":"fa fa-stack-overflow","router":null,"state":"1","redirect":null,"component":"home","servers":null,"keepAlive":true,"parentId":0,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[{"id":91,"name":"流量卡列表","authority":"流量卡列表","describe":null,"type":null,"path":"index","icon":"fa fa-credit-card","router":null,"state":"1","redirect":null,"component":"simcard/index","servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":92,"name":"流量使用情况","authority":"流量使用情况","describe":null,"type":"button","path":"","icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":true,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":93,"name":"流量卡详情","authority":"流量卡详情","describe":null,"type":"router","path":":simtId/info","icon":null,"router":null,"state":"1","redirect":null,"component":"simcard/info","servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":94,"name":"新增流量卡","authority":"新增流量卡","describe":null,"type":"router","path":"new","icon":null,"router":null,"state":"1","redirect":null,"component":"simcard/form","servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":95,"name":"编辑流量卡","authority":"编辑流量卡","describe":null,"type":"router","path":":simtId/edit","icon":null,"router":null,"state":"1","redirect":null,"component":"simcard/form","servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":96,"name":"删除流量卡","authority":"删除流量卡","describe":null,"type":"button","path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":97,"name":"更新流量卡数据","authority":"更新流量卡数据","describe":null,"type":"button","path":"","icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-12-21 15:17:57","updateUserId":null,"updateTime":null,"children":[]},{"id":98,"name":"绑定设备","authority":"绑定设备","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":99,"name":"解绑设备","authority":"解绑设备","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":691,"name":"修改动态参数","authority":"修改动态参数","describe":null,"type":"button","path":"params","icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":9,"delFlag":0,"createUserId":null,"createTime":"2020-11-25 14:03:04","updateUserId":null,"updateTime":null,"children":[]}]},{"id":10,"name":"APP工程项目表","authority":"APP工程项目表","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":101,"name":"工程项目表查询","authority":"工程项目表查询","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":102,"name":"新增工程项目","authority":"新增工程项目","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":103,"name":"查询工程项目详情","authority":"查询工程项目详情","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":104,"name":"删除工程项目","authority":"删除工程项目","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":105,"name":"修改工程项目","authority":"修改工程项目","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":106,"name":"工程项目关联设备查询设备列表","authority":"工程项目关联设备查询设备列表","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":10,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":11,"name":"APP设备管理","authority":"APP设备管理","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1101,"name":"添加设备","authority":"添加设备","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":11,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1102,"name":"设备详情","authority":"设备详情","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":11,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1103,"name":"修改设备","authority":"修改设备","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":11,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1104,"name":"删除设备","authority":"删除设备","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":11,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1105,"name":"设备列表查询","authority":"设备列表查询","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":11,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":12,"name":"APP设备配置","authority":"APP设备配置","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1201,"name":"读取LORA参数","authority":"读取LORA参数","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":12,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1202,"name":"读取网络参数","authority":"读取网络参数","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":12,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1203,"name":"保存LORA参数","authority":"保存LORA参数","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":12,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1204,"name":"保存网络参数","authority":"保存网络参数","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":12,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":13,"name":"APP流量sim卡管理","authority":"APP流量sim卡管理","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1301,"name":"APP流量卡列表","authority":"APP流量卡列表","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":13,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1302,"name":"APP流量卡详情","authority":"APP流量卡详情","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":13,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1303,"name":"APP修改流量卡信息","authority":"APP修改流量卡信息","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":13,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":14,"name":"APP报告管理","authority":"APP报告管理","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1401,"name":"APP检测报告查询列表","authority":"APP检测报告查询列表","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":14,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1402,"name":"APP检测报告详情","authority":"APP检测报告详情","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":14,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":15,"name":"APP我的消息","authority":"APP我的消息","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1501,"name":"消息列表·","authority":"消息列表·","describe":null,"type":null,"path":"user/info","icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":15,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1502,"name":"消息详情","authority":"消息详情","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":15,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1503,"name":"查看数据","authority":"查看数据","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":15,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":16,"name":"APP个人中心","authority":"APP个人中心","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1601,"name":"用户修改头像","authority":"用户修改头像","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":16,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1602,"name":"用户修改基础个人信息","authority":"用户修改基础个人信息","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":16,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1603,"name":"个人密码重置","authority":"个人密码重置","describe":"","type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":16,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]},{"id":1604,"name":"获取已登录用户信息","authority":"获取已登录用户信息","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":16,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]},{"id":17,"name":"APP软件版本","authority":"APP软件版本","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":0,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[{"id":1701,"name":"获取公司信息","authority":"获取公司信息","describe":null,"type":null,"path":null,"icon":null,"router":null,"state":"1","redirect":null,"component":null,"servers":null,"keepAlive":false,"parentId":17,"delFlag":0,"createUserId":null,"createTime":null,"updateUserId":null,"updateTime":null,"children":[]}]}]
     */

    private int id;
    private String name;
    private String avatar;
    private String idCard;
    private String username;
    private String phoneNumber;
    private int state;
    private String authorization;
    private OrganizationBean organization;
    private int organizationId;
    private String organizationName;
    private List<RolesBean> roles;
    private List<MenusBean> menus;

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

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public OrganizationBean getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationBean organization) {
        this.organization = organization;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", idCard='" + idCard + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", state=" + state +
                ", authorization='" + authorization + '\'' +
                ", organization=" + organization +
                ", organizationId=" + organizationId +
                ", organizationName=" + organizationName +
                ", roles=" + roles +
                ", menus=" + menus +
                '}';
    }

    public static class OrganizationBean {
        /**
         * id : 1
         * type : 0
         * code : JC0001
         * name : 永兴检测
         * socialCreditCode : JXDA54DASZCX4AS5DAX
         * legalPerson : 张珊
         * legalPersonTel : 18312345678
         * companyAddress : 西部研发基地四号楼
         * telephone : null
         * companyEmail : 11111
         * companyUrl : 22222
         * state : 0
         * delFlag : 0
         * createUserId : 1
         * createTime : 2020-11-25 10:26:42
         * updateUserId : 0
         * updateTime : 2020-11-25 10:26:42
         * reChainXid : null
         * reChainKey : null
         * reChainTime : 2021-09-16 15:03:29
         * reChainState : null
         */

        private int id;
        private int type;
        private String code;
        private String name;
        private String socialCreditCode;
        private String legalPerson;
        private String legalPersonTel;
        private String companyAddress;
        private Object telephone;
        private String companyEmail;
        private String companyUrl;
        private int state;
        private int delFlag;
        private int createUserId;
        private String createTime;
        private int updateUserId;
        private String updateTime;
        private Object reChainXid;
        private Object reChainKey;
        private String reChainTime;
        private Object reChainState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSocialCreditCode() {
            return socialCreditCode;
        }

        public void setSocialCreditCode(String socialCreditCode) {
            this.socialCreditCode = socialCreditCode;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getLegalPersonTel() {
            return legalPersonTel;
        }

        public void setLegalPersonTel(String legalPersonTel) {
            this.legalPersonTel = legalPersonTel;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public String getCompanyEmail() {
            return companyEmail;
        }

        public void setCompanyEmail(String companyEmail) {
            this.companyEmail = companyEmail;
        }

        public String getCompanyUrl() {
            return companyUrl;
        }

        public void setCompanyUrl(String companyUrl) {
            this.companyUrl = companyUrl;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(int createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(int updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Object getReChainXid() {
            return reChainXid;
        }

        public void setReChainXid(Object reChainXid) {
            this.reChainXid = reChainXid;
        }

        public Object getReChainKey() {
            return reChainKey;
        }

        public void setReChainKey(Object reChainKey) {
            this.reChainKey = reChainKey;
        }

        public String getReChainTime() {
            return reChainTime;
        }

        public void setReChainTime(String reChainTime) {
            this.reChainTime = reChainTime;
        }

        public Object getReChainState() {
            return reChainState;
        }

        public void setReChainState(Object reChainState) {
            this.reChainState = reChainState;
        }

        @Override
        public String toString() {
            return "OrganizationBean{" +
                    "id=" + id +
                    ", type=" + type +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", socialCreditCode='" + socialCreditCode + '\'' +
                    ", legalPerson='" + legalPerson + '\'' +
                    ", legalPersonTel='" + legalPersonTel + '\'' +
                    ", companyAddress='" + companyAddress + '\'' +
                    ", telephone=" + telephone +
                    ", companyEmail='" + companyEmail + '\'' +
                    ", companyUrl='" + companyUrl + '\'' +
                    ", state=" + state +
                    ", delFlag=" + delFlag +
                    ", createUserId=" + createUserId +
                    ", createTime='" + createTime + '\'' +
                    ", updateUserId=" + updateUserId +
                    ", updateTime='" + updateTime + '\'' +
                    ", reChainXid=" + reChainXid +
                    ", reChainKey=" + reChainKey +
                    ", reChainTime='" + reChainTime + '\'' +
                    ", reChainState=" + reChainState +
                    '}';
        }
    }

    public static class RolesBean {
        /**
         * id : 5
         * name : 永兴管理员
         * description : 永兴管理员
         * menus : 44,45,46,47,91,92,93,94,51,95,96,97,98,10,99,11,12,13,14,15,16,17,1503,1701,1,1502,2,1105,1303,1501,3,1104,1302,4,1103,1301,5,1102,6,1101,7,8,9,61,62,63,64,21,65,22,66,23,67,24,68,69,691,71,31,32,33,34,35,1602,1601,101,1204,1402,102,1203,1401,103,1202,104,1201,105,106,81,82,83,84,41,85,42,1604,43,1603
         * organizationId : 1
         * delFlag : 0
         * state : 1
         * createUserId : null
         * createTime : null
         * updateUserId : 2
         * updateTime : 2021-09-16 14:45:22
         */

        private int id;
        private String name;
        private String description;
        private String menus;
        private int organizationId;
        private int delFlag;
        private int state;
        private Object createUserId;
        private Object createTime;
        private int updateUserId;
        private String updateTime;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMenus() {
            return menus;
        }

        public void setMenus(String menus) {
            this.menus = menus;
        }

        public int getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(int organizationId) {
            this.organizationId = organizationId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(int updateUserId) {
            this.updateUserId = updateUserId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public String toString() {
            return "RolesBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", menus='" + menus + '\'' +
                    ", organizationId=" + organizationId +
                    ", delFlag=" + delFlag +
                    ", state=" + state +
                    ", createUserId=" + createUserId +
                    ", createTime=" + createTime +
                    ", updateUserId=" + updateUserId +
                    ", updateTime='" + updateTime + '\'' +
                    '}';
        }
    }

    public static class MenusBean {
        /**
         * id : 1
         * name : 首页
         * authority : 检测机构首页
         * describe : null
         * type : null
         * path : /
         * icon : fa fa-home
         * router : index
         * state : 1
         * redirect : index
         * component : home
         * servers : null
         * keepAlive : false
         * parentId : 0
         * delFlag : 0
         * createUserId : null
         * createTime : 2020-11-24 10:04:51
         * updateUserId : null
         * updateTime : null
         * children : []
         */

        private int id;
        private String name;
        private String authority;
        private Object describe;
        private Object type;
        private String path;
        private String icon;
        private String router;
        private String state;
        private String redirect;
        private String component;
        private Object servers;
        private boolean keepAlive;
        private int parentId;
        private int delFlag;
        private Object createUserId;
        private String createTime;
        private Object updateUserId;
        private Object updateTime;
        private List<?> children;

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

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }

        public Object getDescribe() {
            return describe;
        }

        public void setDescribe(Object describe) {
            this.describe = describe;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getRouter() {
            return router;
        }

        public void setRouter(String router) {
            this.router = router;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public Object getServers() {
            return servers;
        }

        public void setServers(Object servers) {
            this.servers = servers;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(Object updateUserId) {
            this.updateUserId = updateUserId;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "MenusBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", authority='" + authority + '\'' +
                    ", describe=" + describe +
                    ", type=" + type +
                    ", path='" + path + '\'' +
                    ", icon='" + icon + '\'' +
                    ", router='" + router + '\'' +
                    ", state='" + state + '\'' +
                    ", redirect='" + redirect + '\'' +
                    ", component='" + component + '\'' +
                    ", servers=" + servers +
                    ", keepAlive=" + keepAlive +
                    ", parentId=" + parentId +
                    ", delFlag=" + delFlag +
                    ", createUserId=" + createUserId +
                    ", createTime='" + createTime + '\'' +
                    ", updateUserId=" + updateUserId +
                    ", updateTime=" + updateTime +
                    ", children=" + children +
                    '}';
        }
    }
}
