/**新增 接口头寸配置表**/
DROP TABLE INTERF_POSITION_CONFIG;
create table INTERF_POSITION_CONFIG
(
    TRADE_PLAT    VARCHAR2(50)  not null,
    FINPROD_TYPE  VARCHAR2(50)  not null,
    PS            VARCHAR2(50)  not null,
    BUSI_ACT      VARCHAR2(50)  not null,
    POSITION_TYPE VARCHAR2(50)  not null,
    ENTITY        VARCHAR2(500) not null,
    INTERF_TYPE   VARCHAR2(50)  not null,
    CREATE_USER   VARCHAR2(20),
    CREATE_DEPT   VARCHAR2(32),
    CREATE_TIME   TIMESTAMP(6),
    UPDATE_USER   VARCHAR2(20),
    UPDATE_TIME   TIMESTAMP(6),
    REMARK        VARCHAR2(500),
    ENABLE        VARCHAR2(2) default 'Y' not null
)
;

comment on table INTERF_POSITION_CONFIG is '接口头寸配置表'
;

comment on column INTERF_POSITION_CONFIG.TRADE_PLAT is '交易平台'
;

comment on column INTERF_POSITION_CONFIG.FINPROD_TYPE is '业务品种'
;

comment on column INTERF_POSITION_CONFIG.PS is '交易方向'
;

comment on column INTERF_POSITION_CONFIG.BUSI_ACT is '业务动作'
;

comment on column INTERF_POSITION_CONFIG.POSITION_TYPE is '头寸类型'
;

comment on column INTERF_POSITION_CONFIG.ENTITY is '实体类名'
;

comment on column INTERF_POSITION_CONFIG.INTERF_TYPE is '接口类型'
;

comment on column INTERF_POSITION_CONFIG.CREATE_USER is '创建人'
;

comment on column INTERF_POSITION_CONFIG.CREATE_DEPT is '创建部门'
;

comment on column INTERF_POSITION_CONFIG.CREATE_TIME is '创建时间'
;

comment on column INTERF_POSITION_CONFIG.UPDATE_USER is '更新人'
;

comment on column INTERF_POSITION_CONFIG.UPDATE_TIME is '更新时间'
;

comment on column INTERF_POSITION_CONFIG.REMARK is '备注'
;

comment on column INTERF_POSITION_CONFIG.ENABLE is '启用:Y,禁用:N'
;
