-- 更新机构关联登记银行代码表
-- 添加表字段
ALTER TABLE chb_org_regist_bank_code ADD BELOGCODECBRC VARCHAR(100) COMMENT '所属银保监局';