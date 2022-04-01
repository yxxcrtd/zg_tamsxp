alter table mst_customer_info_add add IS_SHAREHOLDER_RELATED varchar2(10);
comment on column mst_customer_info_add.IS_SHAREHOLDER_RELATED is '是否股东及其关联方';
--客户信息附属表加入机构类型字段
ALTER TABLE MST_CUSTOMER_INFO_ADD ADD ORG_TYPE VARCHAR2(50);
COMMENT ON COLUMN MST_CUSTOMER_INFO_ADD.ORG_TYPE IS '机构类型(风险)' ;