
delete from CHB_DATACHECK_RULE_INV where CHECKRULEID in ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000348','ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000354'
,'ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000503');


INSERT INTO chb_datacheck_rule_inv (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000348', 'AssetInfoRegist002', 'isNumber2', 'svrBussRepNobleMetalInfoDto.assetYield', NULL, '8,5', 'Y', '资产收益率%以百分数形式表示时必须为n(8,5)格式。', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO chb_datacheck_rule_inv (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000354', 'AssetInfoRegist002', 'isNumber2', 'svrBussRepOthKindInfoDto.assetYield', NULL, '8,5', 'Y', '资产收益率%以百分数形式表示时必须为n(8,5)格式。', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO chb_datacheck_rule_inv (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000503', 'AssetInfoRegist002', 'compareValueAsNumber', 'svrBussRepNobleMetalInfoDto.assetYield', '>=', '0', 'Y', '资产收益率%必须大于等于0', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');

