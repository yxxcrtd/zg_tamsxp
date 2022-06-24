DELETE FROM chb_infomgr_result_config WHERE MGRTABLE_ID IN ('CHB_ASSET_POSITION','CHB_PRD_POSITION',
'CHB_MONTH_FEE_POSITION',
'CHB_MONTH_ASSET_POSITION',
'CHB_MONTH_PRD_POSITION',
'PBC_MONTH_ASSET_POSITION',
'PBC_MONTH_PRD_POSITION');
DELETE FROM CHB_INFOMGR_CONFIG WHERE BASETABLEID IN (
'CHB_MONTH_FEE_POSITION',
'CHB_MONTH_ASSET_POSITION',
'CHB_MONTH_PRD_POSITION',
'PBC_MONTH_ASSET_POSITION',
'PBC_MONTH_PRD_POSITION');
DELETE FROM CHB_MGRTABLE_INFO WHERE MGRTABLE_ID IN (
'CHB_MONTH_FEE_POSITION',
'CHB_MONTH_ASSET_POSITION',
'CHB_MONTH_PRD_POSITION',
'PBC_MONTH_ASSET_POSITION',
'PBC_MONTH_PRD_POSITION');



insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1013cd', 'CHB_ASSET_POSITION', 'CDATE', '生成日', null, 10, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1014cd', 'CHB_ASSET_POSITION', 'CCY', '币种', 'CURRENCY_CHB', 11, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1011cd', 'CHB_ASSET_POSITION', 'ACCUAMT', '剩余金额', null, 8, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1012cd', 'CHB_ASSET_POSITION', 'ACCUQTY', '剩余本金', null, 9, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1017cd', 'CHB_ASSET_POSITION', 'INVEST_VARIETY', '投资品种', 'SVR_INVEST_TRADE_TYPE', 14, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1018cd', 'CHB_ASSET_POSITION', 'ORG_CODE', '机构代码', null, 15, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1015cd', 'CHB_ASSET_POSITION', 'TRADE_TYPE', '交易类型', 'SVR_TRADE_TYPE_INVEST', 12, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1016cd', 'CHB_ASSET_POSITION', 'IS_UNDER_TRADE', '是否底层交易流水', 'SVR_YES_OR_NO', 13, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1008cd', 'CHB_ASSET_POSITION', 'CHL_ID', '通道代码', null, 5, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1007cd', 'CHB_ASSET_POSITION', 'INVEST_TYPE', '投资类型', null, 4, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1021cd', 'CHB_ASSET_POSITION', 'CREATE_TIME', '创建时间', null, 18, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1009cd', 'CHB_ASSET_POSITION', 'DEALAMT', '交易金额', null, 6, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1004cd', 'CHB_ASSET_POSITION', 'POSUUID', '主键', null, 1, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1023cd', 'CHB_ASSET_POSITION', 'UPDATE_TIME', '更新时间', null, 20, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1006cd', 'CHB_ASSET_POSITION', 'ASSET_CODE', '资产代码', null, 3, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1005cd', 'CHB_ASSET_POSITION', 'PROD_ID', '产品代码', null, 2, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1019cd', 'CHB_ASSET_POSITION', 'DEPT_CODE', '部门代码', null, 16, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1022cd', 'CHB_ASSET_POSITION', 'UPDATE_USER', '更新人', null, 19, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1010cd', 'CHB_ASSET_POSITION', 'DEALQTY', '交易份额', null, 7, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('17f7dd71-zctc-4f7c-96ce-7b3f5f1020cd', 'CHB_ASSET_POSITION', 'CREATE_USER', '创建人', null, 17, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('dafd1034-1892-4261-9213-781bfc82bfdf', 'CHB_PRD_POSITION', 'BUY_PLACE', '销售区域', null, 23, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('bcd2cbef-221f-4e17-b343-ebbb33fe5bdd', 'CHB_PRD_POSITION', 'UNLEGAL_TOTAL', '非法人投资者总数', null, 24, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('06909222-7649-4235-898d-5caaf165639c', 'CHB_PRD_POSITION', 'LEGAL_TOTAL', '法人投资者总数', null, 25, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('69as8e7b-f61f-4089-85de-c41addaed38d', 'CHB_PRD_POSITION', 'CUSTQTY', '兑付客户份额', null, 26, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('c4ca4342-2706-4857-a83f-7d03b1da8061', 'CHB_PRD_POSITION', 'BALANCE_QTY', '剩余份额', null, 21, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('b109881a-40a0-46ec-be9f-78496bdffd20', 'CHB_PRD_POSITION', 'TDADD_QTY', '新增份额', null, 22, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('8d2cb512-1db2-448b-b2fb-bf5e6e963330', 'CHB_PRD_POSITION', 'SINGLE_TOTAL', '个人投资者总数', null, 27, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('d8a1c8d3-d7a3-43fa-900c-30395bb9fc5d', 'CHB_PRD_POSITION', 'CREATE_USER', '创建人', null, 13, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('c4ca4482-2706-4857-a83f-7d03b1da8061', 'CHB_PRD_POSITION', 'RAISEAMT', '实际募集金额', null, 19, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('8d2cb5f3-1db2-448b-b2fb-bf5e6e963330', 'CHB_PRD_POSITION', 'CREATE_TIME', '创建时间', null, 14, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('b109881a-40a0-46ec-be9f-78496bdffd80', 'CHB_PRD_POSITION', 'CUSTAMT', '兑付客户金额', null, 18, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('069092c3-7649-4235-898d-5caaf165639c', 'CHB_PRD_POSITION', 'REMARK', '备注', null, 15, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('bcd2cbef-ea1f-4e17-b343-ebbb33fe5bdd', 'CHB_PRD_POSITION', 'CUST_COUNTS', '保有客户数', null, 16, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('dafd1034-189a-4261-9213-781bfc82bfdf', 'CHB_PRD_POSITION', 'CUSTOMER_TYPE2', '客户二级类型', 'SVR_CUSTOMER_TYPE2', 17, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('c4990363-7321-42e3-aefb-a5899c3cd2ac', 'CHB_PRD_POSITION', 'INDENTIFY_MARK', '识别标识', null, 3, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('ecd497eb-4259-49e6-9910-dae41d39e50f', 'CHB_PRD_POSITION', 'ISSUE_PARTY_ID', '发行机构代码', null, 4, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('6f78276f-ef9d-4594-8efe-06b4b3d16666', 'CHB_PRD_POSITION', 'CCY', '币种', 'CURRENCY_CHB', 5, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('69a18e7b-f61f-4089-85de-c41addaed38d', 'CHB_PRD_POSITION', 'CONSIGN_AMT', '代销总金额', null, 20, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('8fb4d6c1-7bd1-4df3-b098-dda2b14c7e16', 'CHB_PRD_POSITION', 'CDATE', '生成日', null, 1, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('1ce73d69-394f-4d5f-9cc1-ec37e4353d43', 'CHB_PRD_POSITION', 'PROD_ID', '产品代码', null, 2, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('1fca1fe1-0627-4085-9882-3f6b3009a649', 'CHB_PRD_POSITION', 'TDADD_AMT', '新增金额', null, 6, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('61259b44-dc5b-4359-941d-64f31a328939', 'CHB_PRD_POSITION', 'TOTAL_INTAMT', '累计收益', null, 10, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('13163756-4bce-47c4-bf8c-5ef5a45beb83', 'CHB_PRD_POSITION', 'ORG_CODE', '机构代码', null, 11, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('fe24532d-5c47-421b-ae4d-b2e0bd1541f4', 'CHB_PRD_POSITION', 'DEPT_CODE', '部门代码', null, 12, 'N', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('da61d2b9-cfbb-47c3-92d8-97647f72fc0b', 'CHB_PRD_POSITION', 'TDADD_COST', '新增成本', null, 7, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('3c3a2277-bbdc-41f9-8fcd-7befd0886639', 'CHB_PRD_POSITION', 'TDADD_INTAMT', '今日新增收益', null, 8, 'Y', null);

insert into chb_infomgr_result_config (RESULT_ID, MGRTABLE_ID, COLUMN_ID, COLUMN_NAME, DICTCODE, ORDER_NO, SHOWFLAG, REAL_COLUMN_ID)
values ('bbb3fce2-bad8-41ae-acda-97b89a3d0e43', 'CHB_PRD_POSITION', 'BALANCE_AMT', '剩余成本', null, 9, 'Y', null);