--系统参数【用户新建是否显示银行间交易员ID】默认值修改为【01：是】
update SYS_PARA set PARAM_VALUE = '01', REMARK = '用户新建是否显示银行间交易员ID（01：是 02：否）' where PARA_CODE = 'IS_SHOW_IBU_TRADER';