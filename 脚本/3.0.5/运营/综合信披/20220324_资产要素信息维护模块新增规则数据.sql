-- 导入数据时资产负债类别为2202，“资产代码”字段输入数据长度小于8位，没有提示
delete from CHB_DATACHECK_RULE_INV cdci where CHECKRULEID ='ASSET_INFO_DETAIL_RULE_0000-F74964B2-F8A3-4598-B620-05FDE91B39B2';
INSERT INTO chb_datacheck_rule_inv (CHECKRULEID,PAGE_ID,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS,CHECKCLASS) VALUES ('ASSET_INFO_DETAIL_RULE_0000-F74964B2-F8A3-4598-B620-05FDE91B39B2','AssetInfoRegist002','fixedLength','svrBussRepAssetCreditNonstdInfoDto.repCreditAssetCode','','8','Y','报送信息中的资产代码要素长度为固定8位。','E','com.joyintech.tams.common.svreport.datacheck.service.ChbBaseCheckLib');

delete from CHB_DATACHECK_RULE where CHECKRULEID ='3D8E9C57-177C-4FCA-8BB8-2EF09B458A13';
INSERT INTO chb_datacheck_rule (CHECKRULEID,DATATYPE,CHECKCLASS,CHECKMETHOD,SRCPROPERTY,COMPARETO,OBJVALUE,NULLABLE,REMARK,STATUS) VALUES ('3D8E9C57-177C-4FCA-8BB8-2EF09B458A13','CHB_ASSET_INFO_SUB','com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib','fixedLength','CREDIT_ASSET_CODE','','8','N','资产代码要素不可为空且长度为固定8位。','E');
