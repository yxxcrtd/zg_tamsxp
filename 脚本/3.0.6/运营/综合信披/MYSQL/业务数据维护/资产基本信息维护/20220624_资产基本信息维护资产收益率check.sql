
delete from chb_datacheck_rule_inv where CHECKRULEID in ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000503','ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000505');


INSERT INTO chb_datacheck_rule_inv (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000503', 'AssetInfoRegist002', 'compareValueAsNumber', 'svrBussRepNobleMetalInfoDto.assetYield', '>=', '0', 'Y', '资产收益率%必须大于等于0', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
INSERT INTO chb_datacheck_rule_inv (CHECKRULEID, PAGE_ID, CHECKMETHOD, SRCPROPERTY, COMPARETO, OBJVALUE, NULLABLE, REMARK, STATUS, CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-0000-0000-0000-0000-0000-0000-000505', 'AssetInfoRegist002', 'compareValueAsNumber', 'svrBussRepOthKindInfoDto.assetYield', '>=', '0', 'Y', '资产收益率%必须大于等于0', 'E', 'com.joyintech.service.chb.common.service.ChbBaseCheckLib');
