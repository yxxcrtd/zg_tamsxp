--净值化产品
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinNetValueProductBuild002' and ELEMENT_ID='bussRepFinProductInfoCommon' and ELEMENT_FIELD='bussRepFinProductInfoDto';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinNetValueProductBuild002', 'bussRepFinProductInfoCommon', 'bussRepFinProductInfoDto', '中债报送信息', '02', '02', '02', '02');

--收益型产品
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinProductBuild002' and ELEMENT_ID='bussRepFinProductInfoCommon' and ELEMENT_FIELD='bussRepFinProductInfoDto';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinProductBuild002', 'bussRepFinProductInfoCommon', 'bussRepFinProductInfoDto', '中债报送信息', '02', '02', '02', '02');

--项目类标的
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinProjectTargetAsset002' and ELEMENT_ID='bussRepProjectInfo' and ELEMENT_FIELD='bussRepProjectInfo';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinProjectTargetAsset002', 'bussRepProjectInfo', 'bussRepProjectInfo', '中债报送信息', '02', '02', '02', null);

--计划货币型
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinPlanAssetC002' and ELEMENT_ID='bussRepPlanInfo' and ELEMENT_FIELD='bussRepAssetMngProdInfo';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinPlanAssetC002', 'bussRepPlanInfo', 'bussRepAssetMngProdInfo', '中债报送信息', '02', '02', '02', null);

--存款类
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinDepositTarget002' and ELEMENT_ID='bussRepDepositInfo' and ELEMENT_FIELD='bussRepDepositInfoDto';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinDepositTarget002', 'bussRepDepositInfo', 'bussRepDepositInfoDto', '中债报送信息', '02', '02', '02', null);

--公募基金
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinFundTarget002' and ELEMENT_ID='bussRepFundInfo' and ELEMENT_FIELD='bussRepFundInfoDto';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinFundTarget002', 'bussRepFundInfo', 'bussRepFundInfoDto', '中债报送信息', '02', '02', '02', null);

--计划类净值型
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinPlanNetAsset002' and ELEMENT_ID='bussRepPlanNetInfo' and ELEMENT_FIELD='bussRepPlanNetInfo';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinPlanNetAsset002', 'bussRepPlanNetInfo', 'bussRepPlanNetInfo', '中债报送信息', '02', '02', '02', null);

--计划利率型
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinPlanProfitAsset002' and ELEMENT_ID='bussRepPlanProInfo' and ELEMENT_FIELD='bussRepPlanProInfo';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinPlanProfitAsset002', 'bussRepPlanProInfo', 'bussRepPlanProInfo', '中债报送信息', '02', '02', '02', null);


--场内基金标的
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinExFundTarget002' and ELEMENT_ID='bussRepExFundInfo' and ELEMENT_FIELD='bussRepFundInfoDto';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinExFundTarget002', 'bussRepExFundInfo', 'bussRepFundInfoDto', '中债报送信息', '02', '02', '02', null);

--债券标的
delete from SYS_ELEMENT_CONTROL where PAGE_ID='FinBondTargetAsset002' and ELEMENT_ID='bussRepBondInfo' and ELEMENT_FIELD='bussRepBondInfo';
INSERT INTO SYS_ELEMENT_CONTROL (PAGE_ID, ELEMENT_ID, ELEMENT_FIELD, ELEMENT_LABLE, IS_SHOW, IS_REQUIRED, IS_HOLD, REQUIRED_CONTROL) VALUES ('FinBondTargetAsset002', 'bussRepBondInfo', 'bussRepBondInfo', '中债报送信息', '02', '02', '02', null);

--控制是否展示中债
delete from SYS_PARA where SYSTEM_CODE='COM' and PARA_CODE='ASSET_INFO_CHB_HIDE';
INSERT INTO SYS_PARA (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE, UPDATE_FLG) VALUES ('COM', 'ASSET_INFO_CHB_HIDE', 'N', null, null, '参数值：Y-显示中债报送信息,N-不显示', 'admin', null, sysdate, 'admin', sysdate, null, '01');