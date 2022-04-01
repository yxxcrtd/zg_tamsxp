----------------------------------------------------------------base----------------------------------------------------------------------------------------
----表：sys_menu
 /*场内交易指令菜单*/
delete from sys_menu where PAGE_ID = 'EtrExchangeTradeMain';
insert into sys_menu (MENU_ID, SYSTEM_CODE, ICON, PARENT_MENU_ID, PAGE_ID, ORDER_NO, IS_SPREAD, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('INVEST_MANAGEMENT_001_002_028', 'ETR', null, 'INVEST_MANAGEMENT_001_002', 'EtrExchangeTradeMain', 28, '02', '场内交易指令', 'system', 'system', sysdate, 'system', sysdate);

-----表：sys_page
 /*场内交易页面配置*/
delete from sys_page where PAGE_ID in ('EtrExchangeTradeMain','EtrExchangeTrade001','EtrExchangeTrade002','EtrExchangeTrade003','EtrExchangeTradeRiskcal');
insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTradeMain', 'invest/entrust/etrexchangetrade/EtrExchangeTradeMain', 'INVEST_MANAGEMENT_001_002_028', null, null, '02', '01', '场内交易指令主页面', 'system', null, sysdate, 'system', sysdate);

insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade001', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTrade001', 'invest/entrust/etrexchangetrade/EtrExchangeTrade001', null, null, null, '02', '01', '场内交易指令行情和指令页面', 'system', null, sysdate, 'system', sysdate);

insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTrade002', 'invest/entrust/etrexchangetrade/EtrExchangeTrade002', null, null, null, '02', '01', '场内交易指令持仓页面', 'system', null, sysdate, 'system', sysdate);

insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade003', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTrade003', 'invest/entrust/etrexchangetrade/EtrExchangeTrade003', null, null, null, '02', '01', '场内交易指令指令信息', 'system', null, sysdate, 'system', sysdate);

insert into sys_page (PAGE_ID, SYSTEM_CODE, PAGE_URL, PAGE_FILE, MENU_ID, HEIGHT, WIDTH, IS_HP_SELECT, IS_NEED_WORKFLOW, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeRiskcal', 'ETR', '/invest/entrust/etrexchangetrade/EtrExchangeTradeRiskcal', 'invest/entrust/etrexchangetrade/EtrExchangeTradeRiskcal', null, null, null, '02', '01', '场内交易指令风险试算', 'system', null, sysdate, 'system', sysdate);

-----表：sys_page_element
 /*场内交易指令页面元素配置*/
delete from sys_page_element where PAGE_ID='EtrExchangeTrade002';
insert into sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', 'EtrExchangeTrade002_btn_search', '01', '01', null, '查询', 'system', null, sysdate, 'system', sysdate);

delete from sys_page_element where page_id = 'EtrExchangeTradeMain' and ELEMENT_ID in('EtrExchangeTrade001_btn_save','EtrExchangeTrade001_btn_submit','EtrExchangeTrade001_btn_reset');
insert into sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeTrade001_btn_save', '01', '01', null, '保存', 'system', null, sysdate, 'system', sysdate);

insert into sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeTrade001_btn_submit', '01', '01', null, '提交', 'system', null, sysdate, 'system', sysdate);

insert into sys_page_element (PAGE_ID, ELEMENT_ID, ELEMENT_TYPE, IS_CONTROL, TRANSFER_PAGE_ID, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeTrade001_btn_reset', '01', '01', null, '重置', 'system', null, sysdate, 'system', sysdate);

-----表：SYS_WFL_BUSINESS_MODULE
 /*场内交易指令配置工作流*/
delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeTradeMain' and ELEMENT_ID = 'EtrExchangeTrade001_btn_save';
insert into SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeTrade001_btn_save', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate);

delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeTradeMain' and ELEMENT_ID = 'EtrExchangeTrade001_btn_submit';
insert into SYS_WFL_BUSINESS_MODULE (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeTrade001_btn_submit', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate);

delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeTradeMain' and ELEMENT_ID = 'EtrExchangeImport__btn_save';
insert into sys_wfl_business_module (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeImport__btn_save', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate);

delete from sys_wfl_business_module where PAGE_ID = 'EtrExchangeTradeMain' and ELEMENT_ID = 'EtrExchangeImport_btn_submit';
insert into sys_wfl_business_module (PAGE_ID, ELEMENT_ID, MODULE_ID, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTradeMain', 'EtrExchangeImport_btn_submit', 'oneApproval', 'system', '0001', sysdate, 'system', sysdate);


-----表：sys_datadict 和 sys_datadict_list
 /*新增字典:价格模式*/
delete from sys_datadict where DICT_CODE='PRICE_MODEL';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('PRICE_MODEL', 'TG', '价格模式', '', '02', 'system', '', sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE='PRICE_MODEL';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM01', '指定价格', 1, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM02', '限价指令', 2, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM03', '市价指令', 3, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM05', '不指定', 5, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM06', '意向申报', 6, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM07', '定价申报', 7, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM08', '点击成交申报', 8, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM09', '成交申报', 9, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM10', '收盘价固定价格申报', 10, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM11', '日均价固定价格申报', 11, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM12', '最优价成交申报', 12, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM13', '可转换成交申报', 13, '0', 'system', '', sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('PRICE_MODEL', 'PRM14', '指定对手方成交申报', 14, '0', 'system', '', sysdate, 'system', sysdate, null);


 /*新增字典明细：共通业务类型 */
delete from sys_datadict_list where DICT_CODE ='COMMON_BUSINESS_TYPE' and ITEM_CODE in ('ETR25','ETR26','ETR27','ETR28','ETR29','ETR30','ETR31','ETR32','ETR33','ETR34','ETR35','ETR36','ETR37','ETR38','ETR39','ETR40');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR25', '股票竞价指令', 423, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR26', '基金竞价指令', 424, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR27', '大宗债券质押式回购指令', 425, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR28', '大宗股票指令', 426, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR29', '大宗基金指令', 427, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR30', '大宗债券指令', 428, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR31', '债券竞价指令', 429, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR32', '上交所固定收益平台现券交易指令', 430, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR33', '债券回售指令', 431, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR34', '配股指令', 432, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR35', '新股申购指令', 433, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR36', '配债指令', 434, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR37', '可转债申购指令', 435, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR38', '标准券出入库指令', 436, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR39', '债券质押式回购竞价指令', 437, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('COMMON_BUSINESS_TYPE', 'ETR40', '债转股指令', 438, '0', 'system', null, sysdate, 'system', sysdate, null);

 /*新增字典明细：指令业务类型 */
delete from sys_datadict where DICT_CODE='ENTRUST_BUSS_TYPE';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ENTRUST_BUSS_TYPE', 'ETR', '指令业务类型', '', '03', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE='ENTRUST_BUSS_TYPE' and ITEM_CODE in ('01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16');
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '01', '股票竞价指令', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '02', '基金竞价指令', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '03', '大宗债券质押式回购指令', 3, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '04', '大宗股票指令', 4, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '05', '大宗基金指令', 5, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '06', '大宗债券指令', 6, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '07', '债券竞价指令', 7, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '08', '上交所固定收益平台现券交易指令', 8, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '09', '债券回售指令', 9, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '10', '配股指令', 10, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '11', '新股申购指令', 11, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '12', '配债指令', 12, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '13', '可转债申购指令', 13, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '14', '标准券出入库指令', 14, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '15', '债券质押式回购竞价指令', 15, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_BUSS_TYPE', '16', '债转股指令', 16, '0', 'system', null, sysdate, 'system', sysdate, null);

 /*新增字典：指令类型*/
delete from sys_datadict where DICT_CODE='ENTRUST_CLASS';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ENTRUST_CLASS', 'ETR', '指令类型', null, '03', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE='ENTRUST_CLASS';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('ENTRUST_CLASS', '01', '单组合单资产指令', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

 /*新增字典：目标类型*/
delete from sys_datadict where DICT_CODE='TARGET_CLASS';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('TARGET_CLASS', 'ETR', '目标类型', null, '01', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE='TARGET_CLASS';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('TARGET_CLASS', '01', '数量', 1, '0', 'system', null, sysdate, 'system', sysdate, null);
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('TARGET_CLASS', '02', '金额', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

 /*新增字典明细:交易方向配债*/
delete from SYS_DATADICT_LIST where DICT_CODE = 'PS' and ITEM_CODE in ('T97');
insert into SYS_DATADICT_LIST values ('PS','T97','配债','108','0','system','',sysdate, 'system',sysdate, null);

 /*新增字典：债券计算模式*/
delete from SYS_DATADICT where DICT_CODE = 'SEC_CALC_MODE';
insert into SYS_DATADICT values ('SEC_CALC_MODE','ETR','债券计算模式','','03','system','',sysdate, 'system',sysdate);

delete from SYS_DATADICT_LIST where DICT_CODE = 'SEC_CALC_MODE';
insert into SYS_DATADICT_LIST values ('SEC_CALC_MODE','01','净价','1','0','system','',sysdate, 'system',sysdate, null);
insert into SYS_DATADICT_LIST values ('SEC_CALC_MODE','02','全价','2','0','system','',sysdate, 'system',sysdate, null);
insert into SYS_DATADICT_LIST values ('SEC_CALC_MODE','03','到期收益率','3','0','system','',sysdate, 'system',sysdate, null);
insert into SYS_DATADICT_LIST values ('SEC_CALC_MODE','04','行权收益率','4','0','system','',sysdate, 'system',sysdate, null);

 /*新增字典：申报类型*/
delete from sys_datadict where DICT_CODE='DECLA_TYPE'; 
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('DECLA_TYPE', 'ETR', '申报类型', null, '01', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE = 'DECLA_TYPE'; 
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM05', '不指定', 5, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM06', '意向申报', 6, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM07', '定价申报', 7, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM08', '点击成交申报', 8, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM09', '成交申报', 9, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM10', '收盘价固定价格申报', 10, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM11', '日均价固定价格申报', 11, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM12', '最优价成交申报', 12, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM13', '可转换成交申报', 13, '0', 'system', null, sysdate, 'system', sysdate, null);

insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('DECLA_TYPE', 'PRM14', '指定对手方成交申报', 14, '0', 'system', null, sysdate, 'system', sysdate, null);

 /*新增字典：交易单位*/
delete from sys_datadict where DICT_CODE = 'UNIT';
insert into sys_datadict (DICT_CODE, SYSTEM_CODE, DICT_CNNAME, DICT_ENNAME, UPDATE_FLG, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('UNIT', 'ETR', '交易单位', null, '02', 'system', null, sysdate, 'system', sysdate);

delete from sys_datadict_list where DICT_CODE = 'UNIT' and ITEM_CODE = '01';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('UNIT', '01', '张', 1, '0', 'system', null, sysdate, 'system', sysdate, null);

delete from sys_datadict_list where DICT_CODE = 'UNIT' and ITEM_CODE = '02';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('UNIT', '02', '份', 2, '0', 'system', null, sysdate, 'system', sysdate, null);

delete from sys_datadict_list where DICT_CODE = 'UNIT' and ITEM_CODE = '03';
insert into sys_datadict_list (DICT_CODE, ITEM_CODE, ITEM_VALUE, ORDER_NO, DEFAULT_VALUE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, INSTITUTION_CODE)
values ('UNIT', '03', '股', 3, '0', 'system', null, sysdate, 'system', sysdate, null);

-----表：SYS_PAGE_DICTLIST
 /* 新增画面与字典关联：指令查询页面，交易方向质押券入库和质押券出库*/
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID = 'EtrInvestAllInfo001' AND DICT_ID = 'PS' AND DICT_ITEM_VAL in ('T80', 'T81');
INSERT INTO SYS_PAGE_DICTLIST(PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrInvestAllInfo001', '999999', 'PS', 'T80', null, null, 'admin', null, sysdate, 'admin', sysdate);
INSERT INTO SYS_PAGE_DICTLIST(PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) VALUES ('EtrInvestAllInfo001', '999999', 'PS', 'T81', null, null, 'admin', null, sysdate, 'admin', sysdate);

 /*新增画面与字典关联：场内交易指令界面资产类型筛选*/
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID = 'EtrExchangeTrade002' AND DICT_ITEM_VAL IN ('F01','F06','F10');
INSERT INTO SYS_PAGE_DICTLIST (PAGE_ID,SUB_PAGE_ID,DICT_ID,DICT_ITEM_VAL,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME) VALUES 
('EtrExchangeTrade002','999999','FINPROD_TYPE_TG','F01',NULL,NULL,'admin',NULL,sysdate,'admin',sysdate);

INSERT INTO SYS_PAGE_DICTLIST (PAGE_ID,SUB_PAGE_ID,DICT_ID,DICT_ITEM_VAL,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME) VALUES 
('EtrExchangeTrade002','999999','FINPROD_TYPE_TG','F06',NULL,NULL,'admin',NULL,sysdate,'admin',sysdate);

INSERT INTO SYS_PAGE_DICTLIST (PAGE_ID,SUB_PAGE_ID,DICT_ID,DICT_ITEM_VAL,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME) VALUES 
('EtrExchangeTrade002','999999','FINPROD_TYPE_TG','F10',NULL,NULL,'admin',NULL,sysdate,'admin',sysdate);

DELETE FROM SYS_PAGE_DICTLIST where PAGE_ID ='EtrExchangeTrade003' AND DICT_ID = 'PINAN_ASSET_TYPE';

Insert into SYS_PAGE_DICTLIST (PAGE_ID,DICT_ID,DICT_ITEM_VAL,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,SUB_PAGE_ID,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT) 
values ('EtrExchangeTrade003','PINAN_ASSET_TYPE','F01','admin',null,sysdate,'admin',sysdate,'999999',null,null);
Insert into SYS_PAGE_DICTLIST (PAGE_ID,DICT_ID,DICT_ITEM_VAL,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,SUB_PAGE_ID,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT) 
values ('EtrExchangeTrade003','PINAN_ASSET_TYPE','F02','admin',null,sysdate,'admin',sysdate,'999999',null,null);
Insert into SYS_PAGE_DICTLIST (PAGE_ID,DICT_ID,DICT_ITEM_VAL,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,SUB_PAGE_ID,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT) 
values ('EtrExchangeTrade003','PINAN_ASSET_TYPE','F07','admin',null,sysdate,'admin',sysdate,'999999',null,null);
Insert into SYS_PAGE_DICTLIST (PAGE_ID,DICT_ID,DICT_ITEM_VAL,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME,SUB_PAGE_ID,DICT_ITEM_VAL_CNNAME,DICT_ITEM_VAL_DEFAULT) 
values ('EtrExchangeTrade003','PINAN_ASSET_TYPE','F11','admin',null,sysdate,'admin',sysdate,'999999',null,null);

 /* 场内交易指令增加流程中有效状态 */
DELETE FROM SYS_PAGE_DICTLIST WHERE PAGE_ID='EtrExchangeTrade003' AND DICT_ID='ENTRUST_STATUS' AND DICT_ITEM_VAL='08';
INSERT INTO SYS_PAGE_DICTLIST (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME) 
VALUES ('EtrExchangeTrade003', '999999', 'ENTRUST_STATUS', '08', null, null, 'system', null, sysdate, 'system', sysdate);

 /* 场内交易指令持仓查询页面下拉框值过滤*/
delete from sys_page_dictlist where page_id = 'EtrExchangeTrade002' and DICT_ID in ('FINPROD_TYPE_TG','TRADE_PLACE');
insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', '999999', 'FINPROD_TYPE_TG', 'F01', null, null, 'admin', null, sysdate, 'admin', sysdate);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', '999999', 'FINPROD_TYPE_TG', 'F06', null, null, 'admin', null, sysdate, 'admin', sysdate);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', '999999', 'FINPROD_TYPE_TG', 'F10', null, null, 'admin', null, sysdate, 'admin', sysdate);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', '999999', 'TRADE_PLACE', '03', null, null, 'admin', null, sysdate, 'admin', sysdate);

insert into sys_page_dictlist (PAGE_ID, SUB_PAGE_ID, DICT_ID, DICT_ITEM_VAL, DICT_ITEM_VAL_CNNAME, DICT_ITEM_VAL_DEFAULT, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('EtrExchangeTrade002', '999999', 'TRADE_PLACE', '04', null, null, 'admin', null, sysdate, 'admin', sysdate);

-----sys_para
 /*新增系统参数：委托指令组合的可用现金不足,持仓不足时提醒或者禁止开关*/
delete from sys_para where SYSTEM_CODE='ETR' and PARA_CODE='etr_check_cash_switch';
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ETR', 'etr_check_cash_switch', '01', '01', '', '委托指令组合的可用现金不足时配置的提醒或禁止 01：提醒 02：禁止', 'system', '', sysdate, 'sf', sysdate);

delete from sys_para where SYSTEM_CODE='ETR' and PARA_CODE='etr_check_pos_switch';
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ETR', 'etr_check_pos_switch', '01', '01', '', '委托指令组合的可用持仓不足时配置的提醒或禁止 01：提醒 02：禁止', 'system', '', sysdate, 'xbb1', sysdate);

 /*新增系统参数：场内交易指令定时刷新时间*/
delete from SYS_PARA WHERE SYSTEM_CODE='TRD' AND PARA_CODE = 'EtrExchangeTrade＿refreshTime';
Insert into SYS_PARA (SYSTEM_CODE,PARA_CODE,PARAM_VALUE,DATA_TYPE,DATA_SCOPE,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME) 
values ('TRD','EtrExchangeTrade＿refreshTime','300000',null,null,'场内交易指令定时刷新时间（毫秒）','system',null,sysdate,'system',sysdate);

 /*新增系统参数:增加是否开启交易时间段check开关*/
delete from sys_para where PARA_CODE = 'etr_trade_time_switch';
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ETR', 'etr_trade_time_switch', 'Y', '开', null, '委托指令交易时间端配置的开关 Y：开 N：关', 'system', null, sysdate, 'system', sysdate);


 /*新增系统参数:场内交易指令自动刷新时间*/
delete from sys_para where PARA_CODE in ('etrExchange_HqArea_refresh_period','etrExchange_posTable_refresh_period','etrExchange_entrustTable_refresh_period');
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrExchange_HqArea_refresh_period', '30000', '01', null, '场内交易指令画面行情区域自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrExchange_posTable_refresh_period', '120000', '01', null, '场内交易指令画面持仓列表自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrExchange_entrustTable_refresh_period', '120000', '01', null, '场内交易指令画面指令列表自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

 /*新增系统参数:交易所质押式协议回购自动刷新时间*/
delete from sys_para where PARA_CODE in ('etrAgreRepTrade_UnOpenQuota_refresh_period', 'etrAgreRepTrade_posTable_refresh_period', 'etrAgreRepTrade_onWayRep_refresh_period');
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrAgreRepTrade_UnOpenQuota_refresh_period', '120000', '01', null, '交易所协议回购指令画面非公开行情区域自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrAgreRepTrade_posTable_refresh_period', '120000', '01', null, '交易所协议回购指令画面持仓列表自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('SYS', 'etrAgreRepTrade_onWayRep_refresh_period', '120000', '01', null, '交易所协议回购指令画面在途回购自动刷新时间（毫秒）', 'system', null, sysdate, 'system', sysdate);

/*新增系统参数:委托指令交易时间端配置的开关*/
delete from sys_para where PARA_CODE = 'etr_trade_time_switch';
insert into sys_para (SYSTEM_CODE, PARA_CODE, PARAM_VALUE, DATA_TYPE, DATA_SCOPE, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('ETR', 'etr_trade_time_switch', 'N', '开', null, '委托指令交易时间端配置的开关 Y：开 N：关', 'system', null, '16-OCT-20 08.00.07.000000 PM', 'yy0', sysdate);



-------------------------------------------------------------- mast----------------------------------------------------------------------------------------

-----表结构变动
----新增 TRD_TEMP_STASEC_FLOW：标准券入出库临时表
drop table TRD_TEMP_STASEC_FLOW cascade constraints;

/*==============================================================*/
/* Table: TRD_TEMP_STASEC_FLOW                                  */
/*==============================================================*/
create table TRD_TEMP_STASEC_FLOW 
(
   SEQNO                VARCHAR(32)          not null,
   ENTRUST_ID           VARCHAR(32),
   PORTFOLIO_ID         VARCHAR(32),
   CDATE                DATE,
   PS                   VARCHAR(50),
   SEC_ACCT_ID          VARCHAR(32),
   REMARK               VARCHAR(1000),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_TRD_TEMP_STASEC_FLOW primary key (SEQNO)
);

comment on table TRD_TEMP_STASEC_FLOW is
'标准券入出库临时表';

comment on column TRD_TEMP_STASEC_FLOW.SEQNO is
'入出库编号';

comment on column TRD_TEMP_STASEC_FLOW.ENTRUST_ID is
'委托代码';

comment on column TRD_TEMP_STASEC_FLOW.PORTFOLIO_ID is
'组合代码';

comment on column TRD_TEMP_STASEC_FLOW.CDATE is
'日期';

comment on column TRD_TEMP_STASEC_FLOW.PS is
'入出库方向，入库、出库';

comment on column TRD_TEMP_STASEC_FLOW.SEC_ACCT_ID is
'证券账户代码';

comment on column TRD_TEMP_STASEC_FLOW.REMARK is
'备注';

comment on column TRD_TEMP_STASEC_FLOW.CREATE_USER is
'创建人';

comment on column TRD_TEMP_STASEC_FLOW.CREATE_DEPT is
'创建部门';

comment on column TRD_TEMP_STASEC_FLOW.CREATE_TIME is
'创建时间';

comment on column TRD_TEMP_STASEC_FLOW.UPDATE_USER is
'更新人';

comment on column TRD_TEMP_STASEC_FLOW.UPDATE_TIME is
'更新时间';

----新增 TRD_TEMP_STASEC_FLOW_DETAIL：标准券入出库明细临时表
drop table TRD_TEMP_STASEC_FLOW_DETAIL cascade constraints;

/*==============================================================*/
/* Table: TRD_TEMP_STASEC_FLOW_DETAIL                           */
/*==============================================================*/
create table TRD_TEMP_STASEC_FLOW_DETAIL 
(
   SEQNO                VARCHAR(32)          not null,
   FINPROD_ID           VARCHAR(50)          not null,
   BRANCH               NUMERIC(10)          not null,
   AMT                  NUMERIC(30, 4),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_TRD_TEMP_STASEC_FLOW_DETAIL primary key (SEQNO, FINPROD_ID, BRANCH)
);

comment on table TRD_TEMP_STASEC_FLOW_DETAIL is
'标准券入出库明细临时表';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.SEQNO is
'入出库编号';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.FINPROD_ID is
'金融产品代码';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.BRANCH is
'分支序号';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.AMT is
'入出库数量';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.CREATE_USER is
'创建人';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.CREATE_DEPT is
'创建部门';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.CREATE_TIME is
'创建时间';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.UPDATE_USER is
'更新人';

comment on column TRD_TEMP_STASEC_FLOW_DETAIL.UPDATE_TIME is
'更新时间';


----新增 TRD_PRODUCT_TEMP_DEAL：金融产品临时交易表
-- Create table
drop table TRD_PRODUCT_TEMP_DEAL;
create table TRD_PRODUCT_TEMP_DEAL
(
  trade_id           VARCHAR2(32) not null,
  busi_type          VARCHAR2(50),
  finprod_id         VARCHAR2(50),
  finprod_type       VARCHAR2(50),
  finprod_type2      VARCHAR2(50),
  branch             NUMBER(10),
  trade_date         DATE,
  vdate              DATE,
  mdate              DATE,
  settle_date        DATE,
  settle_speed       NUMBER(10),
  ccy                VARCHAR2(50),
  cprice_date        DATE,
  unit_cprice        NUMBER(30,14),
  unit_int           NUMBER(30,14),
  unit_fprice        NUMBER(30,14),
  par_value          NUMBER(30,14),
  cprice_amt         NUMBER(30,2),
  share_amt          NUMBER(30,4),
  prin_amt           NUMBER(30,2),
  int_amt            NUMBER(30,2),
  fprice_amt         NUMBER(30,2),
  fee_amt_pay        NUMBER(30,2),
  trade_amt          NUMBER(30,2),
  fee_amt_unpay      NUMBER(30,2),
  fee_amt            NUMBER(30,2),
  ps                 VARCHAR2(50),
  yield              NUMBER(30,14),
  exer_yield         NUMBER(30,14),
  inv_aim            VARCHAR2(50),
  trade_status       VARCHAR2(50),
  is_cancel          VARCHAR2(50) default 'N',
  p_trade_id         VARCHAR2(32),
  is_clean           VARCHAR2(50),
  totle_trade_id     VARCHAR2(33),
  r_trade_id         VARCHAR2(32),
  b_trade_id         VARCHAR2(32),
  chl_id             VARCHAR2(32),
  counter_id         VARCHAR2(32),
  is_out_trade       VARCHAR2(50),
  trade_market       VARCHAR2(50),
  trade_plat         VARCHAR2(50),
  trade_market_mode  VARCHAR2(50),
  trader             VARCHAR2(32),
  counter_trader     VARCHAR2(200),
  counter_contact    VARCHAR2(1000),
  sec_manage_acct_id VARCHAR2(32),
  f_trade_id         VARCHAR2(50),
  cash_id            VARCHAR2(32),
  regist_org         VARCHAR2(50),
  remark             VARCHAR2(4000),
  cancel_remark      VARCHAR2(1000),
  create_user        VARCHAR2(20) not null,
  create_dept        VARCHAR2(32),
  create_time        TIMESTAMP(6) not null,
  update_user        VARCHAR2(20) not null,
  update_time        TIMESTAMP(6) not null,
  delivery_type      VARCHAR2(50),
  pay_date           DATE,
  sale_code          VARCHAR2(32),
  accr_num           NUMBER(30,4),
  tg_busi_type       VARCHAR2(50),
  ps_payment         VARCHAR2(50),
  SEAT_ID            VARCHAR2(50),
  SETTLEMEN_TYPE     VARCHAR2(50),
  R_INV_AIM			 VARCHAR2(50),
  constraint PK_TRD_PRODUCT_TEMP_DEAL primary key (TRADE_ID)
);
-- Add comments to the table 
comment on table TRD_PRODUCT_TEMP_DEAL
  is '金融产品临时交易';
-- Add comments to the columns 
comment on column TRD_PRODUCT_TEMP_DEAL.trade_id
  is '交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.busi_type
  is '业务类型，债券交易、产品交易，估值核算专用';
comment on column TRD_PRODUCT_TEMP_DEAL.finprod_id
  is '金融产品代码';
comment on column TRD_PRODUCT_TEMP_DEAL.finprod_type
  is '金融产品类型（估值核算），债券、理财产品、基金、理财产品模板、理财产品分层';
comment on column TRD_PRODUCT_TEMP_DEAL.finprod_type2
  is '金融产品类型（投管分类），债券、理财产品、基金、理财产品模板、理财产品分层';
comment on column TRD_PRODUCT_TEMP_DEAL.branch
  is '分支序号';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_date
  is '交易日期';
comment on column TRD_PRODUCT_TEMP_DEAL.vdate
  is '起息日';
comment on column TRD_PRODUCT_TEMP_DEAL.mdate
  is '到期日';
comment on column TRD_PRODUCT_TEMP_DEAL.settle_date
  is '交割日期';
comment on column TRD_PRODUCT_TEMP_DEAL.settle_speed
  is '清算速度';
comment on column TRD_PRODUCT_TEMP_DEAL.ccy
  is '交易币种';
comment on column TRD_PRODUCT_TEMP_DEAL.cprice_date
  is '净值日期';
comment on column TRD_PRODUCT_TEMP_DEAL.unit_cprice
  is '单位净价';
comment on column TRD_PRODUCT_TEMP_DEAL.unit_int
  is '单位利息';
comment on column TRD_PRODUCT_TEMP_DEAL.unit_fprice
  is '单位全价';
comment on column TRD_PRODUCT_TEMP_DEAL.par_value
  is '单位面值，债券100或者当前面值(还本前)，其他资产1';
comment on column TRD_PRODUCT_TEMP_DEAL.cprice_amt
  is '净价总额';
comment on column TRD_PRODUCT_TEMP_DEAL.share_amt
  is '交易份额，债券数量等';
comment on column TRD_PRODUCT_TEMP_DEAL.prin_amt
  is '交易本金，债券券面等';
comment on column TRD_PRODUCT_TEMP_DEAL.int_amt
  is '利息总额';
comment on column TRD_PRODUCT_TEMP_DEAL.fprice_amt
  is '全价总额';
comment on column TRD_PRODUCT_TEMP_DEAL.fee_amt_pay
  is '随交易支付费用';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_amt
  is '交易金额';
comment on column TRD_PRODUCT_TEMP_DEAL.fee_amt_unpay
  is '未随交易支付费用';
comment on column TRD_PRODUCT_TEMP_DEAL.fee_amt
  is '总交易费用';
comment on column TRD_PRODUCT_TEMP_DEAL.ps
  is '交易方向，买入、卖出、收息、还本、申购、赎回等';
comment on column TRD_PRODUCT_TEMP_DEAL.yield
  is '到期收益率';
comment on column TRD_PRODUCT_TEMP_DEAL.exer_yield
  is '行权收益率';
comment on column TRD_PRODUCT_TEMP_DEAL.inv_aim
  is '投资目的';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_status
  is '交易状态，初始、撤销';
comment on column TRD_PRODUCT_TEMP_DEAL.is_cancel
  is '是否已撤销，默认“否”，在交易发生撤销时更新成“是”';
comment on column TRD_PRODUCT_TEMP_DEAL.p_trade_id
  is '原交易编号，撤销时存原来的交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.is_clean
  is '是否结清，收息付费时用';
comment on column TRD_PRODUCT_TEMP_DEAL.totle_trade_id
  is '汇总交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.r_trade_id
  is '反向交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.b_trade_id
  is '前置交易编号，记录该交易的前置交易编号。比如申购确认时存申购申请交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.chl_id
  is '渠道/通道代码';
comment on column TRD_PRODUCT_TEMP_DEAL.counter_id
  is '交易对手';
comment on column TRD_PRODUCT_TEMP_DEAL.is_out_trade
  is '是否外部交易，是、否';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_market
  is '交易场所，银行间、上交所、深交所、柜面等';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_plat
  is '交易平台，银行间、上交所固定收益平台、上交所大宗交易平台、深交所大宗平台、深交所综合协议交易平台、其他等';
comment on column TRD_PRODUCT_TEMP_DEAL.trade_market_mode
  is '交易市场，一级市场、二级市场';
comment on column TRD_PRODUCT_TEMP_DEAL.trader
  is '交易员';
comment on column TRD_PRODUCT_TEMP_DEAL.counter_trader
  is '对手方交易员';
comment on column TRD_PRODUCT_TEMP_DEAL.counter_contact
  is '对手方联系方式';
comment on column TRD_PRODUCT_TEMP_DEAL.sec_manage_acct_id
  is '证券管理户代码';
comment on column TRD_PRODUCT_TEMP_DEAL.f_trade_id
  is '关联系统交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL.cash_id
  is '现金流代码，还本、付息、付费等时存对应计划的现金流代码';
comment on column TRD_PRODUCT_TEMP_DEAL.regist_org
  is '登记托管机构';
comment on column TRD_PRODUCT_TEMP_DEAL.remark
  is '备注';
comment on column TRD_PRODUCT_TEMP_DEAL.cancel_remark
  is '撤销说明';
comment on column TRD_PRODUCT_TEMP_DEAL.create_user
  is '创建人';
comment on column TRD_PRODUCT_TEMP_DEAL.create_dept
  is '创建部门';
comment on column TRD_PRODUCT_TEMP_DEAL.create_time
  is '创建时间';
comment on column TRD_PRODUCT_TEMP_DEAL.update_user
  is '更新人';
comment on column TRD_PRODUCT_TEMP_DEAL.update_time
  is '更新时间';
comment on column TRD_PRODUCT_TEMP_DEAL.pay_date
  is '交付日';
comment on column TRD_PRODUCT_TEMP_DEAL.sale_code
  is '销售代码';
comment on column TRD_PRODUCT_TEMP_DEAL.accr_num
  is '计提份额';
comment on column TRD_PRODUCT_TEMP_DEAL.tg_busi_type
  is '投管业务类型';
comment on column TRD_PRODUCT_TEMP_DEAL.ps_payment
  is '收付方向';
comment on column TRD_PRODUCT_TEMP_DEAL.delivery_type
  is '结算方式，券款对付、见券付款、见款付券'; 
comment on column TRD_PRODUCT_TEMP_DEAL.SEAT_ID
  is '席位号';
comment on column TRD_PRODUCT_TEMP_DEAL.SETTLEMEN_TYPE
  is '交收方式'; 
comment on column TRD_PRODUCT_TEMP_DEAL.R_INV_AIM
  is '反向交易投资目的'; 
  
----新增 TRD_PRODUCT_TEMP_DEAL_ADD：金融产品临时交易附属表
-- Create table
drop table TRD_PRODUCT_TEMP_DEAL_ADD;
create table TRD_PRODUCT_TEMP_DEAL_ADD
(
  trade_id            VARCHAR2(32) not null,
  portfolio_id        VARCHAR2(50),
  custom_cash_type    VARCHAR2(50),
  gen_type            VARCHAR2(50),
  pur_cfm_date        DATE,
  red_cfm_date        DATE,
  reg_date            DATE,
  bonus_cfm_date      DATE,
  entrust_id          VARCHAR2(32),
  pay_type            VARCHAR2(50),
  create_user         VARCHAR2(20) not null,
  create_dept         VARCHAR2(32),
  create_time         TIMESTAMP(6) not null,
  update_user         VARCHAR2(20) not null,
  update_time         TIMESTAMP(6) not null,
  red_profit          NUMBER(30,2),
  red_cost            NUMBER(30,2),
  reg_date_amt        NUMBER(30,4),
  mp_finprod_id       VARCHAR2(32),
  mp_branch           NUMBER(10),
  deal_mode           VARCHAR2(50),
  exc_deal_type       VARCHAR2(50),
  cur_pair            VARCHAR2(50),
  term_type           VARCHAR2(50),
  usd_amt             NUMBER(30,2),
  our_role            VARCHAR2(50),
  quot_pre            NUMBER(30,2),
  priced_date         DATE,
  priced_date_rate    NUMBER(30,14),
  dif_pay_amt         NUMBER(30,2),
  dif_pay_ccy         VARCHAR2(50),
  dif_ps              VARCHAR2(50),
  lock_mdate          DATE,
  with_lock_period    VARCHAR2(50),
  ref_rate            VARCHAR2(50),
  deviation           NUMBER(30,14),
  asset_recommand_org VARCHAR2(32),
  exp_con_value       NUMBER(30,2),
  deposit_amt         NUMBER(30,2),
  purch_modle         VARCHAR2(50),
  purch_type          VARCHAR2(50),
  purch_date          DATE,
  pur_pay_date        DATE,
  terminate_rate      NUMBER(30,4),
  penalty_intamt      NUMBER(30,4),
  spo_share_num       NUMBER(30,4),
  first_unit_cprice3  NUMBER(30,14),
  preb                NUMBER(30,14),
  prec                NUMBER(30,14),
  release_status      VARCHAR2(2),
  is_cashset          VARCHAR2(50),
  is_manual_release   VARCHAR2(2),
  r_finprod_id        VARCHAR2(32),
  constraint PK_TRD_PRODUCT_TEMP_DEAL_ADD primary key (TRADE_ID)
);
-- Add comments to the table 
comment on table TRD_PRODUCT_TEMP_DEAL_ADD
  is '金融产品临时交易附属表';
-- Add comments to the columns 
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.trade_id
  is '交易编号';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.portfolio_id
  is '投资组合代码';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.custom_cash_type
  is '自定义现金流类型';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.gen_type
  is '生成方式：接口接入、画面录入、批处理生成';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.pur_cfm_date
  is '预计申购确认日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.red_cfm_date
  is '预计赎回到账日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.reg_date
  is '权益登记日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.bonus_cfm_date
  is '预计分红日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.entrust_id
  is '委托代码';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.pay_type
  is '支付类型，交易方向为分红除权时填写，红利再投、现金分红';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.create_user
  is '创建人';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.create_dept
  is '创建部门';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.create_time
  is '创建时间';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.update_user
  is '更新人';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.update_time
  is '更新时间';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.red_profit
  is '赎回收益';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.red_cost
  is '赎回成本';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.mp_finprod_id
  is '主协议金融产品代码';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.mp_branch
  is '主协议分支序号';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.deal_mode
  is '处理模式';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.exc_deal_type
  is '外汇交易类型';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.cur_pair
  is '货币对';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.term_type
  is '期限品种';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.usd_amt
  is '折美元金额';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.our_role
  is '我方角色';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.quot_pre
  is '报价精度';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.priced_date
  is '定价日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.priced_date_rate
  is '定价日即期汇率';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.dif_pay_amt
  is '差额收付金额';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.dif_pay_ccy
  is '差额收付币种';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.dif_ps
  is '差额收付方向';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.lock_mdate
  is '锁定期截止日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.with_lock_period
  is '是否有锁定期';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.ref_rate
  is '差额交割参考汇率';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.deviation
  is '偏离度';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.asset_recommand_org
  is '资产推荐方';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.exp_con_value
  is '预计转股价值';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.deposit_amt
  is '保证金金额';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.purch_modle
  is '申购方式，网上申购、网下申购';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.purch_type
  is '申购类型，优先配售、普通申购';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.purch_date
  is '申购日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.pur_pay_date
  is '预计申购缴款日';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.terminate_rate
  is '提前终止利率';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.penalty_intamt
  is '罚息金额';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.spo_share_num
  is '零星股数';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.first_unit_cprice3
  is '正股价';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.preb
  is '转股溢价率';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.prec
  is '纯债溢价率';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.release_status
  is '释放状态';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.is_cashset
  is '是否现金交割';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.is_manual_release
  is '是否手工释放';
comment on column TRD_PRODUCT_TEMP_DEAL_ADD.r_finprod_id
  is '转换资产代码';


----新增STL_PAY_TEMP_ORDERS：临时划款指令表
-- Create table
drop table STL_PAY_TEMP_ORDERS;
create table STL_PAY_TEMP_ORDERS
(
  ORDER_ID        VARCHAR2(32) not null,
  ORDER_TYPE      VARCHAR2(50),
  PAY_TYPE        VARCHAR2(50),
  SETTLE_DATE     DATE,
  MY_ACCT_NO      VARCHAR2(50),
  PS              VARCHAR2(50),
  COUNTER_ACCT_NO VARCHAR2(50),
  SETTLE_AMT      NUMBER(30,2),
  CCY             VARCHAR2(50),
  SETTLE_STATUS   VARCHAR2(50),
  PURPOSE         VARCHAR2(1000),
  IS_NEED_SEND    VARCHAR2(50),
  IS_TOTAL_TRADE  VARCHAR2(50),
  TRADE_ID        VARCHAR2(32),
  ORDER_STATUS    VARCHAR2(50),
  P_TRADE_ID      VARCHAR2(32),
  PORTFOLIO_ID    VARCHAR2(32),
  PROD_ID         VARCHAR2(32),
  REMARK          VARCHAR2(1000),
  CREATE_USER     VARCHAR2(20) not null,
  CREATE_DEPT     VARCHAR2(32),
  CREATE_TIME     TIMESTAMP(6) not null,
  UPDATE_USER     VARCHAR2(20) not null,
  UPDATE_TIME     TIMESTAMP(6) not null,
  constraint PK_STL_PAY_TEMP_ORDERS primary key (ORDER_ID)
);
-- Add comments to the table 
comment on table STL_PAY_TEMP_ORDERS
  is '临时划款指令表';
-- Add comments to the columns 
comment on column STL_PAY_TEMP_ORDERS.ORDER_ID
  is '指令编号';
comment on column STL_PAY_TEMP_ORDERS.ORDER_TYPE
  is '指令类型，银证转账、费用支付、债券买入等';
comment on column STL_PAY_TEMP_ORDERS.PAY_TYPE
  is '划款类型，托管划款、清算划款';
comment on column STL_PAY_TEMP_ORDERS.SETTLE_DATE
  is '结算日期';
comment on column STL_PAY_TEMP_ORDERS.MY_ACCT_NO
  is '本方资金账户';
comment on column STL_PAY_TEMP_ORDERS.PS
  is '收付方向';
comment on column STL_PAY_TEMP_ORDERS.COUNTER_ACCT_NO
  is '对方资金账户';
comment on column STL_PAY_TEMP_ORDERS.SETTLE_AMT
  is '结算金额';
comment on column STL_PAY_TEMP_ORDERS.CCY
  is '币种';
comment on column STL_PAY_TEMP_ORDERS.SETTLE_STATUS
  is '结算状态';
comment on column STL_PAY_TEMP_ORDERS.PURPOSE
  is '用途';
comment on column STL_PAY_TEMP_ORDERS.IS_NEED_SEND
  is '是否需要发送';
comment on column STL_PAY_TEMP_ORDERS.IS_TOTAL_TRADE
  is '是否汇总交易';
comment on column STL_PAY_TEMP_ORDERS.TRADE_ID
  is '交易编号，汇总交易时存汇总交易编号';
comment on column STL_PAY_TEMP_ORDERS.ORDER_STATUS
  is '指令状态，初始、撤销';
comment on column STL_PAY_TEMP_ORDERS.P_TRADE_ID
  is '原指令编号，撤销时存被撤销的指令编号';
comment on column STL_PAY_TEMP_ORDERS.PORTFOLIO_ID
  is '投资组合代码';
comment on column STL_PAY_TEMP_ORDERS.PROD_ID
  is '理财产品代码';
comment on column STL_PAY_TEMP_ORDERS.REMARK
  is '备注';
comment on column STL_PAY_TEMP_ORDERS.CREATE_USER
  is '创建人';
comment on column STL_PAY_TEMP_ORDERS.CREATE_DEPT
  is '创建部门';
comment on column STL_PAY_TEMP_ORDERS.CREATE_TIME
  is '创建时间';
comment on column STL_PAY_TEMP_ORDERS.UPDATE_USER
  is '更新人';
comment on column STL_PAY_TEMP_ORDERS.UPDATE_TIME
  is '更新时间';

  

----新增 TRD_TEMP_TRADE_FEE_DETAIL：临时交易费用明细表
drop table TRD_TEMP_TRADE_FEE_DETAIL cascade constraints;

/*==============================================================*/
/* Table: TRD_TEMP_TRADE_FEE_DETAIL                             */
/*==============================================================*/
create table TRD_TEMP_TRADE_FEE_DETAIL 
(
   TRADE_ID             VARCHAR(32)          not null,
   FEE_TYPE             VARCHAR(50)          not null,
   IS_PAY_WITH_TRADE    VARCHAR(50)          not null,
   FEE_AMT              NUMERIC(30, 2),
   CHL_FINPROD_ID       VARCHAR(32),
   FEE_ID               VARCHAR(50),
   FEE_RATE             NUMERIC(30, 14),
   IS_PREPAID           VARCHAR(50),
   CCY                  VARCHAR(50),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_TRD_TEMP_TRADE_FEE_DETAIL primary key (TRADE_ID, FEE_TYPE)
);

comment on table TRD_TEMP_TRADE_FEE_DETAIL is
'临时交易费用明细表';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.TRADE_ID is
'交易编号';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.FEE_TYPE is
'费用类型，结算费、交易手续费、通道费、资产推荐费、增值税、城建税、教育附加税等';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.IS_PAY_WITH_TRADE is
'是否随交易支付';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.FEE_AMT is
'费用金额';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.CHL_FINPROD_ID is
'通道代码，通道费时填该值。';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.FEE_ID is
'费用代码，存费用品种对应的ID';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.FEE_RATE is
'费率';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.IS_PREPAID is
'是否做为待摊，费用支付时用于区分如何记账';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.CCY is
'币种';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.CREATE_USER is
'创建人';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.CREATE_DEPT is
'创建部门';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.CREATE_TIME is
'创建时间';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.UPDATE_USER is
'更新人';

comment on column TRD_TEMP_TRADE_FEE_DETAIL.UPDATE_TIME is
'更新时间';
  
  
----新增 WFL_ETR_ENTRUST：委托指令流程表
drop table WFL_ETR_ENTRUST;
-- Create table
create table WFL_ETR_ENTRUST
(
  entrust_id         VARCHAR2(32) not null,
  portfolio_id       VARCHAR2(32),
  finprod_id         VARCHAR2(50),
  finprod_type2      VARCHAR2(50),
  trade_market       VARCHAR2(50),
  trade_plat         VARCHAR2(50),
  trade_market_mode  VARCHAR2(50),
  ps                 VARCHAR2(50),
  ccy                VARCHAR2(50),
  inv_aim            VARCHAR2(50),
  counter_id         VARCHAR2(32),
  is_out_trade       VARCHAR2(50),
  counter_trader     VARCHAR2(32),
  contact_info       VARCHAR2(1000),
  entrust_date       DATE,
  vdate              DATE,
  mdate              DATE,
  entrust_amt        NUMBER(30,2),
  entrust_num        NUMBER(30,4),
  available_num      NUMBER(30,2),
  pro_available_cash NUMBER(30,2),
  acc_available_cash NUMBER(30,2),
  entrust_status     VARCHAR2(50),
  price              NUMBER(30,14),
  is_accurate        VARCHAR2(50),
  inv_manager_id     VARCHAR2(20),
  calendar_id        VARCHAR2(32),
  remark             VARCHAR2(1000),
  create_user        VARCHAR2(20) not null,
  create_dept        VARCHAR2(32),
  create_time        TIMESTAMP(6) not null,
  update_user        VARCHAR2(20) not null,
  update_time        TIMESTAMP(6) not null,
  chl_id             VARCHAR2(32),
  sale_code          VARCHAR2(32),
  price_model        VARCHAR2(50),
  entrust_type       VARCHAR2(50),
  stock_id           VARCHAR2(32),
  entrust_class      VARCHAR2(50),
  WORKFLOW_ID        VARCHAR2(32),
  WORKFLOW_STATUS    VARCHAR2(10),
  CONSTRAINT PK_WFL_ETR_ENTRUST PRIMARY KEY (ENTRUST_ID,WORKFLOW_STATUS)
);
-- Add comments to the table 
comment on table WFL_ETR_ENTRUST
  is '委托指令流程表';
-- Add comments to the columns 
comment on column WFL_ETR_ENTRUST.entrust_id
  is '委托代码';
comment on column WFL_ETR_ENTRUST.portfolio_id
  is '组合代码';
comment on column WFL_ETR_ENTRUST.finprod_id
  is '金融产品代码';
comment on column WFL_ETR_ENTRUST.finprod_type2
  is '金融产品类型（投管分类），债券、理财产品、基金、理财产品模板、理财产品分层';
comment on column WFL_ETR_ENTRUST.trade_market
  is '交易场所，银行间、上交所、深交所、柜面等';
comment on column WFL_ETR_ENTRUST.trade_plat
  is '交易平台，银行间、上交所固定收益平台、上交所大宗交易平台、深交所大宗平台、深交所综合协议交易平台、其他等';
comment on column WFL_ETR_ENTRUST.trade_market_mode
  is '交易市场，一级市场、二级市场';
comment on column WFL_ETR_ENTRUST.ps
  is '交易方向';
comment on column WFL_ETR_ENTRUST.ccy
  is '币种';
comment on column WFL_ETR_ENTRUST.inv_aim
  is '投资目的';
comment on column WFL_ETR_ENTRUST.counter_id
  is '交易对手';
comment on column WFL_ETR_ENTRUST.is_out_trade
  is '是否外部交易，是、否';
comment on column WFL_ETR_ENTRUST.counter_trader
  is '对手方交易员';
comment on column WFL_ETR_ENTRUST.contact_info
  is '对手方联系方式';
comment on column WFL_ETR_ENTRUST.entrust_date
  is '委托日期';
comment on column WFL_ETR_ENTRUST.vdate
  is '委托开始日';
comment on column WFL_ETR_ENTRUST.mdate
  is '委托结束日';
comment on column WFL_ETR_ENTRUST.entrust_amt
  is '委托额度，交易金额';
comment on column WFL_ETR_ENTRUST.entrust_num
  is '委托数量，数量或者券面总额/100';
comment on column WFL_ETR_ENTRUST.available_num
  is '可用数量';
comment on column WFL_ETR_ENTRUST.pro_available_cash
  is '组合可用现金';
comment on column WFL_ETR_ENTRUST.acc_available_cash
  is '托管户可用现金';
comment on column WFL_ETR_ENTRUST.entrust_status
  is '委托状态';
comment on column WFL_ETR_ENTRUST.price
  is '委托价格，净价、利率等';
comment on column WFL_ETR_ENTRUST.is_accurate
  is '是否精确委托';
comment on column WFL_ETR_ENTRUST.inv_manager_id
  is '投资经理';
comment on column WFL_ETR_ENTRUST.calendar_id
  is '交易日历';
comment on column WFL_ETR_ENTRUST.remark
  is '备注';
comment on column WFL_ETR_ENTRUST.create_user
  is '创建人';
comment on column WFL_ETR_ENTRUST.create_dept
  is '创建部门';
comment on column WFL_ETR_ENTRUST.create_time

  is '创建时间';
comment on column WFL_ETR_ENTRUST.update_user
  is '更新人';
comment on column WFL_ETR_ENTRUST.update_time
  is '更新时间';
comment on column WFL_ETR_ENTRUST.chl_id
  is '通道代码';
comment on column WFL_ETR_ENTRUST.sale_code
  is '销售代码';
comment on column WFL_ETR_ENTRUST.price_model
  is '价格模式';
comment on column WFL_ETR_ENTRUST.entrust_type
  is '委托类型';
comment on column WFL_ETR_ENTRUST.stock_id
  is '证券代码';
comment on column WFL_ETR_ENTRUST.entrust_class
  is '指令类别';
comment on column WFL_ETR_ENTRUST.WORKFLOW_ID
  is '流程id';
comment on column WFL_ETR_ENTRUST.WORKFLOW_STATUS
  is '流程状态';


----新增 WFL_ETR_ENTRUST_ADD：委托指令流程附属表
drop table WFL_ETR_ENTRUST_ADD;
-- Create table
create table WFL_ETR_ENTRUST_ADD
(
  entrust_id         VARCHAR2(32) not null,
  settle_spreed      NUMBER(10),
  exer_yield         NUMBER(30,14),
  mature_yield       NUMBER(30,14),
  vdate              DATE,
  mdate              DATE,
  term_days          NUMBER(10),
  term_days2         NUMBER(10),
  term_type          VARCHAR2(50),
  pay_freq           VARCHAR2(50),
  first_cal_date     DATE,
  asset_type         VARCHAR2(50),
  asset_type1        VARCHAR2(50),
  busi_ps            VARCHAR2(50),
  cash_usedate       DATE,
  sec_acct_id        VARCHAR2(32),
  create_user        VARCHAR2(20) not null,
  create_dept        VARCHAR2(32),
  create_time        TIMESTAMP(6) not null,
  update_user        VARCHAR2(20) not null,
  update_time        TIMESTAMP(6) not null,
  first_unit_cprice  NUMBER(30,14),
  first_unit_cprice2 NUMBER(30,14),
  exchange_pledge    VARCHAR2(50),
  exc_deal_type      VARCHAR2(50),
  cur_pair           VARCHAR2(50),
  cor_ccy            VARCHAR2(50),
  exp_cor_amt        NUMBER(30,2),
  cor_cur_pos        NUMBER(30,2),
  is_dif_squ         VARCHAR2(50),
  deal_cur_acct      VARCHAR2(50),
  cor_cur_acct       VARCHAR2(50),
  cny_acct           VARCHAR2(50),
  vdate2             DATE,
  mdate2             DATE,
  exc_fxs_term_type  VARCHAR2(50),
  pay_type           VARCHAR2(50),
  asset_type_10      VARCHAR2(50),
  asset_type_11      VARCHAR2(50),
  deal_mode          VARCHAR2(50),
  preb               NUMBER(30,14),
  prec               NUMBER(30,14),
  share_price        NUMBER(30,14),
  exp_con_value      NUMBER(30,14),
  act_num            NUMBER(30,4),
  exp_con_num        NUMBER(30,4),
  purch_modle        VARCHAR2(50),
  purch_type         VARCHAR2(50),
  is_agreement_end   VARCHAR2(50),
  our_ent_num        NUMBER(30,4),
  lendin_ent_num     NUMBER(30,4),
  our_able_num       NUMBER(30,4),
  lendin_able_num    NUMBER(30,4),
  sell_able_num      NUMBER(30,4),
  pb_exportstatus    VARCHAR2(50),
  high_hind_price    NUMBER(30,14),
  low_hind_price     NUMBER(30,14),
  high_exerc_price   NUMBER(30,14),
  low_exerc_price    NUMBER(30,14),
  exe_price          NUMBER(30,14),
  knock_price        NUMBER(30,14),
  exercise_type      VARCHAR2(50),
  exec_base_date     DATE,
  opt_fee_pay_type2  VARCHAR2(50),
  opt_fee_pay_type   VARCHAR2(50),
  r_finprod_id       VARCHAR2(50),
  contract_result    VARCHAR2(50),
  contract_no        VARCHAR2(50),
  payment_date       DATE,
  listing_date       DATE,
  main_consignee     VARCHAR2(50),
  warrantor          VARCHAR2(50),
  bidding_range      VARCHAR2(50),
  min_amt            NUMBER(30,14),
  cal_mode           VARCHAR2(50),
  counter_seat       VARCHAR2(32),
  counter_con_no     VARCHAR2(50),
  full_price         NUMBER(30,14),
  target_type        VARCHAR2(50),
  counter_org        VARCHAR2(32),
  trader             VARCHAR2(32),
  WORKFLOW_ID        VARCHAR2(32),
  vpay_DATE			 DATE,
  mpay_date          DATE,
  m_int_amt          NUMBER(30,14),
  m_Trade_amt        NUMBER(30,14),
  cap_days           NUMBER(30,0),
  trade_id           VARCHAR2(50),
  request_id         VARCHAR2(50),
  WORKFLOW_STATUS    VARCHAR2(10),
  REDO_TYPE 		 VARCHAR2(2),
  DERIVATIVE_BS_DIR  VARCHAR2(32),
  DERIVATIVE_OC_FLAG VARCHAR2(32),
  QUOTE_TYPE         VARCHAR2(32),
  CONSTRAINT PK_WFL_ETR_ENTRUST_ADD PRIMARY KEY (ENTRUST_ID,WORKFLOW_STATUS)
);
-- Add comments to the table 
comment on table WFL_ETR_ENTRUST_ADD
  is '委托指令流程附属表';
-- Add comments to the columns 
comment on column WFL_ETR_ENTRUST_ADD.entrust_id
  is '委托代码';
comment on column WFL_ETR_ENTRUST_ADD.settle_spreed
  is '清算速度';
comment on column WFL_ETR_ENTRUST_ADD.exer_yield
  is '行权收益率';
comment on column WFL_ETR_ENTRUST_ADD.mature_yield
  is '到期收益率';
comment on column WFL_ETR_ENTRUST_ADD.vdate
  is '起息日';
comment on column WFL_ETR_ENTRUST_ADD.mdate
  is '到期日';
comment on column WFL_ETR_ENTRUST_ADD.term_days
  is '期限天数，到期日-起息日';
comment on column WFL_ETR_ENTRUST_ADD.term_days2
  is '期限天数2，输入期限范围时使用';
comment on column WFL_ETR_ENTRUST_ADD.term_type
  is '期限品种，1天、7天、14天、21天、1个月、2个月、3个月、4个月、6个月、9个月、1年';
comment on column WFL_ETR_ENTRUST_ADD.pay_freq
  is '付息频率';
comment on column WFL_ETR_ENTRUST_ADD.first_cal_date
  is '首次付息日';
comment on column WFL_ETR_ENTRUST_ADD.asset_type
  is '资产类型，对应金融产品分类的分类8';
comment on column WFL_ETR_ENTRUST_ADD.asset_type1
  is '资产一级分类，委托页面的债券一级分类';
comment on column WFL_ETR_ENTRUST_ADD.busi_ps
  is '回购/拆借方向，正回购、逆回购、拆入、拆出';
comment on column WFL_ETR_ENTRUST_ADD.cash_usedate
  is '资金可用日';
comment on column WFL_ETR_ENTRUST_ADD.sec_acct_id
  is '证券账户代码';
comment on column WFL_ETR_ENTRUST_ADD.create_user
  is '创建人';
comment on column WFL_ETR_ENTRUST_ADD.create_dept
  is '创建部门';
comment on column WFL_ETR_ENTRUST_ADD.create_time
  is '创建时间';
comment on column WFL_ETR_ENTRUST_ADD.update_user
  is '更新人';
comment on column WFL_ETR_ENTRUST_ADD.update_time
  is '更新时间';
comment on column WFL_ETR_ENTRUST_ADD.exchange_pledge
  is '质押券置换安排';
comment on column WFL_ETR_ENTRUST_ADD.exc_deal_type
  is '外汇交易类型';
comment on column WFL_ETR_ENTRUST_ADD.cur_pair
  is '货币对';
comment on column WFL_ETR_ENTRUST_ADD.cor_ccy
  is '对应货币';
comment on column WFL_ETR_ENTRUST_ADD.exp_cor_amt
  is '预计对应货币金额';
comment on column WFL_ETR_ENTRUST_ADD.cor_cur_pos
  is '对应货币可用持仓';
comment on column WFL_ETR_ENTRUST_ADD.is_dif_squ
  is '是否差额清算';
comment on column WFL_ETR_ENTRUST_ADD.deal_cur_acct
  is '交易货币账户';
comment on column WFL_ETR_ENTRUST_ADD.cor_cur_acct
  is '对应货币账户';
comment on column WFL_ETR_ENTRUST_ADD.cny_acct
  is '人民币账户';
comment on column WFL_ETR_ENTRUST_ADD.vdate2
  is '起息日2';
comment on column WFL_ETR_ENTRUST_ADD.mdate2
  is '到期日2';
comment on column WFL_ETR_ENTRUST_ADD.exc_fxs_term_type
  is '掉期期限类型';
comment on column WFL_ETR_ENTRUST_ADD.pay_type
  is '缴款方式';
comment on column WFL_ETR_ENTRUST_ADD.asset_type_10
  is '个性分类三';
comment on column WFL_ETR_ENTRUST_ADD.asset_type_11
  is '个性分类四';
comment on column WFL_ETR_ENTRUST_ADD.deal_mode
  is '交易方式';
comment on column WFL_ETR_ENTRUST_ADD.preb
  is '转股溢价率';
comment on column WFL_ETR_ENTRUST_ADD.prec
  is '纯债溢价率';
comment on column WFL_ETR_ENTRUST_ADD.share_price
  is '正股价';
comment on column WFL_ETR_ENTRUST_ADD.exp_con_value
  is '预计转股价值';
comment on column WFL_ETR_ENTRUST_ADD.act_num
  is '实际持有数量';
comment on column WFL_ETR_ENTRUST_ADD.exp_con_num
  is '预计转换股数';
comment on column WFL_ETR_ENTRUST_ADD.purch_modle
  is '申购方式';
comment on column WFL_ETR_ENTRUST_ADD.purch_type
  is '申购类型';
comment on column WFL_ETR_ENTRUST_ADD.is_agreement_end
  is '是否协议终止，适用于活期存放';
comment on column WFL_ETR_ENTRUST_ADD.our_ent_num
  is '本方券委托数量';
comment on column WFL_ETR_ENTRUST_ADD.lendin_ent_num
  is '融入券委托数量';
comment on column WFL_ETR_ENTRUST_ADD.our_able_num
  is '本方券可用数量';
comment on column WFL_ETR_ENTRUST_ADD.lendin_able_num
  is '融入券可用数量';
comment on column WFL_ETR_ENTRUST_ADD.sell_able_num
  is '卖空券可用数量';
comment on column WFL_ETR_ENTRUST_ADD.pb_exportstatus
  is 'PB是否已导出';
comment on column WFL_ETR_ENTRUST_ADD.high_hind_price
  is '高障碍价';
comment on column WFL_ETR_ENTRUST_ADD.low_hind_price
  is '低障碍价';
comment on column WFL_ETR_ENTRUST_ADD.high_exerc_price
  is '高行权价';
comment on column WFL_ETR_ENTRUST_ADD.low_exerc_price
  is '低行权价';
comment on column WFL_ETR_ENTRUST_ADD.exe_price
  is '执行价';
comment on column WFL_ETR_ENTRUST_ADD.knock_price
  is '敲出价';
comment on column WFL_ETR_ENTRUST_ADD.exercise_type
  is '行权方式';
comment on column WFL_ETR_ENTRUST_ADD.exec_base_date
  is '行权基准日';
comment on column WFL_ETR_ENTRUST_ADD.opt_fee_pay_type2
  is '期权费率';
comment on column WFL_ETR_ENTRUST_ADD.opt_fee_pay_type
  is '期权费支付类型';
comment on column WFL_ETR_ENTRUST_ADD.r_finprod_id
  is '转入资产代码';
comment on column WFL_ETR_ENTRUST_ADD.contract_result
  is '合同签署结果';
comment on column WFL_ETR_ENTRUST_ADD.contract_no
  is '合同签署结果';
comment on column WFL_ETR_ENTRUST_ADD.payment_date
  is '缴款日期';
comment on column WFL_ETR_ENTRUST_ADD.listing_date
  is '上市日期';
comment on column WFL_ETR_ENTRUST_ADD.main_consignee
  is '主承销商';
comment on column WFL_ETR_ENTRUST_ADD.warrantor
  is '担保人';
comment on column WFL_ETR_ENTRUST_ADD.bidding_range
  is '投标区间%';
comment on column WFL_ETR_ENTRUST_ADD.min_amt
  is '最低成交金额';
comment on column WFL_ETR_ENTRUST_ADD.cal_mode
  is '计算方式，净价，全价，到期收益率';
comment on column WFL_ETR_ENTRUST_ADD.counter_seat
  is '席位号';
comment on column WFL_ETR_ENTRUST_ADD.counter_con_no
  is '约定号';
comment on column WFL_ETR_ENTRUST_ADD.full_price
  is '全价';
comment on column WFL_ETR_ENTRUST_ADD.target_type
  is '目标类型';
comment on column WFL_ETR_ENTRUST_ADD.counter_org
  is '对手方交易商';
comment on column WFL_ETR_ENTRUST_ADD.trader
  is '本方交易员';
comment on column WFL_ETR_ENTRUST_ADD.WORKFLOW_ID
  is '流程id';
comment on column WFL_ETR_ENTRUST_ADD.WORKFLOW_STATUS
  is '数据状态';
comment on column WFL_ETR_ENTRUST_ADD.vpay_DATE
  is '首次交收日';
comment on column WFL_ETR_ENTRUST_ADD.mpay_date
  is '到期交收日';
comment on column WFL_ETR_ENTRUST_ADD.m_int_amt
  is '到期应计利息';
comment on column WFL_ETR_ENTRUST_ADD.m_Trade_amt
  is '到期结算金额';
comment on column WFL_ETR_ENTRUST_ADD.cap_days
  is '占款天数';
comment on column WFL_ETR_ENTRUST_ADD.trade_id
  is '成交编号/初始交易编号';
comment on column WFL_ETR_ENTRUST_ADD.request_id
  is '请求号';
COMMENT ON COLUMN "WFL_ETR_ENTRUST_ADD"."REDO_TYPE" 
  IS '续做类型';
comment on column WFL_ETR_ENTRUST_ADD.DERIVATIVE_BS_DIR is 
'衍生品买卖方向';
COMMENT ON COLUMN WFL_ETR_ENTRUST_ADD.DERIVATIVE_OC_FLAG IS
 '衍生品开平仓标志';
 comment on column wfl_etr_entrust_add.QUOTE_TYPE is 
'报价方式';

----新增 WFL_ETR_ENTRUST_BID_PRICE：委托投标标位流程表
DROP TABLE WFL_ETR_ENTRUST_BID_PRICE;
CREATE TABLE WFL_ETR_ENTRUST_BID_PRICE
    (
        ENTRUST_ID VARCHAR2(32) NOT NULL,
        BID_PRICE NUMBER(30,14) NOT NULL,
        BID_PRIN_AMT NUMBER(30,2),
        CREATE_USER VARCHAR2(20) NOT NULL,
        CREATE_DEPT VARCHAR2(32),
        CREATE_TIME TIMESTAMP(6) NOT NULL,
        UPDATE_USER VARCHAR2(20) NOT NULL,
        UPDATE_TIME TIMESTAMP(6) NOT NULL,
        WORKFLOW_ID VARCHAR2(32),
        WORKFLOW_STATUS VARCHAR2(10),
        CONSTRAINT PK_WFL_ETR_ENTRUST_BID_PRICE PRIMARY KEY (ENTRUST_ID, BID_PRICE)
    );
COMMENT ON TABLE WFL_ETR_ENTRUST_BID_PRICE IS 
'委托投标标位流程表';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.ENTRUST_ID IS 
'委托代码';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.BID_PRICE IS 
'投标标位';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.BID_PRIN_AMT IS 
'投标券面';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.CREATE_USER IS 
'创建人';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.CREATE_DEPT IS 
'创建部门';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.CREATE_TIME IS 
'创建时间';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.UPDATE_USER IS 
'更新人';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.UPDATE_TIME IS 
'更新时间';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.WORKFLOW_ID IS 
'流程id';
COMMENT ON COLUMN WFL_ETR_ENTRUST_BID_PRICE.WORKFLOW_STATUS IS 
'数据状态';

----新增 WFL_ETR_PLEDGE：委托指令回购质押品流程表
DROP TABLE WFL_ETR_PLEDGE;
CREATE TABLE WFL_ETR_PLEDGE
    (
        ENTRUST_ID VARCHAR2(32) NOT NULL,
        FINPROD_ID VARCHAR2(50) NOT NULL,
        PLEDGE_FACE_VALUE NUMBER(30,2),
        PLEDGE_NUMBER NUMBER(30,2),
        PLEDGE_RATIO NUMBER(30,14),
        PLEDGE_AMT NUMBER(30,2),
        CREATE_USER VARCHAR2(20) NOT NULL,
        CREATE_DEPT VARCHAR2(32),
        CREATE_TIME TIMESTAMP(6) NOT NULL,
        UPDATE_USER VARCHAR2(20) NOT NULL,
        UPDATE_TIME TIMESTAMP(6) NOT NULL,
        WORKFLOW_ID VARCHAR2(32),
        WORKFLOW_STATUS VARCHAR2(10),
        CONSTRAINT PK_WFL_ETR_PLEDGE PRIMARY KEY (ENTRUST_ID, FINPROD_ID)
    );
COMMENT ON TABLE WFL_ETR_PLEDGE IS 
'委托指令回购质押品流程表';
COMMENT ON COLUMN WFL_ETR_PLEDGE.ENTRUST_ID IS 
'委托代码';
COMMENT ON COLUMN WFL_ETR_PLEDGE.FINPROD_ID IS 
'金融产品代码';
COMMENT ON COLUMN WFL_ETR_PLEDGE.PLEDGE_FACE_VALUE IS 
'质押面值';
COMMENT ON COLUMN WFL_ETR_PLEDGE.PLEDGE_NUMBER IS 
'质押数量';
COMMENT ON COLUMN WFL_ETR_PLEDGE.PLEDGE_RATIO IS 
'质押率';
COMMENT ON COLUMN WFL_ETR_PLEDGE.PLEDGE_AMT IS 
'质押金额';
COMMENT ON COLUMN WFL_ETR_PLEDGE.CREATE_USER IS 
'创建人';
COMMENT ON COLUMN WFL_ETR_PLEDGE.CREATE_DEPT IS 
'创建部门';
COMMENT ON COLUMN WFL_ETR_PLEDGE.CREATE_TIME IS 
'创建时间';
COMMENT ON COLUMN WFL_ETR_PLEDGE.UPDATE_USER IS 
'更新人';
COMMENT ON COLUMN WFL_ETR_PLEDGE.UPDATE_TIME IS 
'更新时间';
COMMENT ON COLUMN WFL_ETR_PLEDGE.WORKFLOW_ID IS 
'流程id';
COMMENT ON COLUMN WFL_ETR_PLEDGE.WORKFLOW_STATUS IS 
'数据状态';

----新增 FIN_PRODUCT_QUTO：交易所品种额度
drop table FIN_PRODUCT_QUTO cascade constraints;

/*==============================================================*/
/* Table: FIN_PRODUCT_QUTO                                      */
/*==============================================================*/
create table FIN_PRODUCT_QUTO 
(
   SEQ_NO               VARCHAR(32)          not null,
   PORTFOLIO_ID         VARCHAR(32),
   SEC_ACCT_ID          VARCHAR(32),
   FINPROD_ID           VARCHAR(32),
   OTH_TYPE             VARCHAR(50),
   CDATE                DATE,
   QUTO                 NUMERIC(30, 14),
   IPO_QUTO             NUMERIC(30, 14),
   BOND_ID              VARCHAR(50),
   TRADE_MARKET         VARCHAR(50),
   CREATE_USER          VARCHAR(20)          not null,
   CREATE_DEPT          VARCHAR(32),
   CREATE_TIME          TIMESTAMP            not null,
   UPDATE_USER          VARCHAR(20)          not null,
   UPDATE_TIME          TIMESTAMP            not null,
   constraint PK_FIN_PRODUCT_QUTO primary key (SEQ_NO)
);

comment on table FIN_PRODUCT_QUTO is
'交易所品种额度';

comment on column FIN_PRODUCT_QUTO.SEQ_NO is
'流水号';

comment on column FIN_PRODUCT_QUTO.PORTFOLIO_ID is
'组合代码';

comment on column FIN_PRODUCT_QUTO.SEC_ACCT_ID is
'证券账户代码';

comment on column FIN_PRODUCT_QUTO.FINPROD_ID is
'金融产品代码';

comment on column FIN_PRODUCT_QUTO.OTH_TYPE is
'业务类型';

comment on column FIN_PRODUCT_QUTO.CDATE is
'日期';

comment on column FIN_PRODUCT_QUTO.QUTO is
'额度';

comment on column FIN_PRODUCT_QUTO.IPO_QUTO is
'科创板额度';

comment on column FIN_PRODUCT_QUTO.BOND_ID is
'股东代码';

comment on column FIN_PRODUCT_QUTO.TRADE_MARKET is
'交易场所';

comment on column FIN_PRODUCT_QUTO.CREATE_USER is
'创建人';

comment on column FIN_PRODUCT_QUTO.CREATE_DEPT is
'创建部门';

comment on column FIN_PRODUCT_QUTO.CREATE_TIME is
'创建时间';

comment on column FIN_PRODUCT_QUTO.UPDATE_USER is
'更新人';

comment on column FIN_PRODUCT_QUTO.UPDATE_TIME is
'更新时间';


----新增 成交明细表：INF_CONTRACT_DETAILFILE
-- Create table
drop table INF_CONTRACT_DETAILFILE;
create table INF_CONTRACT_DETAILFILE
(
  seqno             VARCHAR2(32) not null,
  entrust_id        VARCHAR2(32),
  order_id          VARCHAR2(32),
  order_no          VARCHAR2(32),
  deal_no           VARCHAR2(32),
  portfolio_id      VARCHAR2(32),
  acct_no           VARCHAR2(50),
  bond_id           VARCHAR2(50),
  finprod_id        VARCHAR2(50),
  ps                VARCHAR2(50),
  trade_market      VARCHAR2(50),
  trade_plat        VARCHAR2(50),
  finprod_market_id VARCHAR2(50),
  share_amt         NUMBER(30,4),
  unit_cprice       NUMBER(30,14),
  unit_fprice       NUMBER(30,14),
  trade_amt         NUMBER(30,14),
  trade_date        DATE,
  vdate             DATE,
  mdate             DATE,
  v_trade_id        VARCHAR2(32),
  create_user       VARCHAR2(20) not null,
  create_dept       VARCHAR2(32),
  create_time       TIMESTAMP(6) not null,
  update_user       VARCHAR2(20) not null,
  update_time       TIMESTAMP(6) not null,
  ccy               VARCHAR2(50),
  deal_margin       NUMBER(30,14),
  settle_day        DATE,
  busi_type         VARCHAR2(50),
  constraint PK_INF_CONTRACT_DETAILFILE primary key (seqno)
);
-- Add comments to the table 
comment on table INF_CONTRACT_DETAILFILE
  is '成交明细';
-- Add comments to the columns 
comment on column INF_CONTRACT_DETAILFILE.seqno
  is '流水号';
comment on column INF_CONTRACT_DETAILFILE.entrust_id
  is '委托代码，就是投管的指令';
comment on column INF_CONTRACT_DETAILFILE.order_id
  is '订单代码';
comment on column INF_CONTRACT_DETAILFILE.order_no
  is '交易委托编号，实际发到交易所的编号';
comment on column INF_CONTRACT_DETAILFILE.deal_no
  is '成交编号';
comment on column INF_CONTRACT_DETAILFILE.portfolio_id
  is '组合代码';
comment on column INF_CONTRACT_DETAILFILE.acct_no
  is '资金账号，券商资金账户';
comment on column INF_CONTRACT_DETAILFILE.bond_id
  is '股东代码';
comment on column INF_CONTRACT_DETAILFILE.finprod_id
  is '金融产品代码';
comment on column INF_CONTRACT_DETAILFILE.ps
  is '交易方向，买入、卖出等';
comment on column INF_CONTRACT_DETAILFILE.trade_market
  is '交易场所，银行间、上交所、深交所、柜面等';
comment on column INF_CONTRACT_DETAILFILE.trade_plat
  is '交易平台，银行间、上交所固定收益平台、上交所大宗交易平台、深交所大宗平台、深交所综合协议交易平台、其他等';
comment on column INF_CONTRACT_DETAILFILE.finprod_market_id
  is '市场代码';
comment on column INF_CONTRACT_DETAILFILE.share_amt
  is '成交数量，债券数量等';
comment on column INF_CONTRACT_DETAILFILE.unit_cprice
  is '成交净价';
comment on column INF_CONTRACT_DETAILFILE.unit_fprice
  is '成交全价';
comment on column INF_CONTRACT_DETAILFILE.trade_amt
  is '交易金额';
comment on column INF_CONTRACT_DETAILFILE.trade_date
  is '交易日期';
comment on column INF_CONTRACT_DETAILFILE.vdate
  is '首次结算日';
comment on column INF_CONTRACT_DETAILFILE.mdate
  is '到期结算日';
comment on column INF_CONTRACT_DETAILFILE.v_trade_id
  is '首期交易编号';
comment on column INF_CONTRACT_DETAILFILE.create_user
  is '创建人';
comment on column INF_CONTRACT_DETAILFILE.create_dept
  is '创建部门';
comment on column INF_CONTRACT_DETAILFILE.create_time
  is '创建时间';
comment on column INF_CONTRACT_DETAILFILE.update_user
  is '更新人';
comment on column INF_CONTRACT_DETAILFILE.update_time
  is '更新时间';
comment on column INF_CONTRACT_DETAILFILE.ccy
  is '交易币种';
comment on column INF_CONTRACT_DETAILFILE.deal_margin
  is '交易保证金';
comment on column INF_CONTRACT_DETAILFILE.settle_day
  is '交收日';
comment on column INF_CONTRACT_DETAILFILE.busi_type
  is '业务类别,数据字典DELIVERY_BUS_TYPE;01 上交所固定收益平台债券买入;02 上交所固定收益平台债券卖出';
  
  
----新增 MST_COUNTER_PARTY_INFO：交易对手信息表
-- Create table
drop table MST_COUNTER_PARTY_INFO;
create table MST_COUNTER_PARTY_INFO
(
  seqno             VARCHAR2(32) not null,
  counter_id        VARCHAR2(32) not null,
  counter_seat_id   VARCHAR2(32),
  counter_trader_id VARCHAR2(32),
  create_user       VARCHAR2(20) not null,
  create_dept       VARCHAR2(32),
  create_time       TIMESTAMP(6) not null,
  update_user       VARCHAR2(20) not null,
  update_time       TIMESTAMP(6) not null
);
-- Add comments to the table 
comment on table MST_COUNTER_PARTY_INFO
  is '交易对手信息表';
-- Add comments to the columns 
comment on column MST_COUNTER_PARTY_INFO.seqno
  is '主键';
comment on column MST_COUNTER_PARTY_INFO.counter_id
  is '交易对手代码';
comment on column MST_COUNTER_PARTY_INFO.counter_seat_id
  is '交易对手席位';
comment on column MST_COUNTER_PARTY_INFO.counter_trader_id
  is '对手方交易员';
comment on column MST_COUNTER_PARTY_INFO.create_user
  is '创建人';
comment on column MST_COUNTER_PARTY_INFO.create_dept
  is '创建部门';
comment on column MST_COUNTER_PARTY_INFO.create_time
  is '创建时间';
comment on column MST_COUNTER_PARTY_INFO.update_user
  is '更新人';
comment on column MST_COUNTER_PARTY_INFO.update_time
  is '更新时间';


-----更新ETR_ENTRUST：指令委托表
alter table  ETR_ENTRUST  add (ENTRUST_TYPE  VARCHAR(50));
alter table  ETR_ENTRUST  add (STOCK_ID  VARCHAR(32));
alter table  ETR_ENTRUST  add (ENTRUST_CLASS varchar2(50));

comment on column ETR_ENTRUST.ENTRUST_TYPE is
'委托类型';
comment on column ETR_ENTRUST.STOCK_ID is
'证券代码';
comment on column ETR_ENTRUST.ENTRUST_CLASS is
'指令类别';

----更新ETR_ENTRUST_ADD：指令委托附属表
alter table  ETR_ENTRUST_ADD  add (CAL_MODE  VARCHAR(50));
alter table  ETR_ENTRUST_ADD  add (COUNTER_SEAT  VARCHAR(32));
alter table  ETR_ENTRUST_ADD  add (COUNTER_CON_NO  VARCHAR(50));
alter table  ETR_ENTRUST_ADD  add (FULL_PRICE  NUMERIC(30, 14));
alter table  ETR_ENTRUST_ADD  add (TARGET_TYPE varchar2(50));
alter table  ETR_ENTRUST_ADD  add (COUNTER_ORG varchar2(32));
alter table  ETR_ENTRUST_ADD  add (TRADER varchar2(32));
alter table ETR_ENTRUST_ADD add vpay_DATE DATE;
alter table ETR_ENTRUST_ADD add mpay_date DATE;
alter table ETR_ENTRUST_ADD add m_int_amt NUMBER(30,14);
alter table ETR_ENTRUST_ADD add m_Trade_amt NUMBER(30,14);
alter table ETR_ENTRUST_ADD add cap_days NUMBER(30,0);
alter table ETR_ENTRUST_ADD add trade_id VARCHAR2(50);
alter table ETR_ENTRUST_ADD add request_id VARCHAR2(50);
ALTER TABLE ETR_ENTRUST_ADD ADD REDO_TYPE VARCHAR2(2);
alter table ETR_ENTRUST_ADD add DERIVATIVE_BS_DIR VARCHAR2(32);
alter table ETR_ENTRUST_ADD add DERIVATIVE_OC_FLAG VARCHAR2(32);

comment on column ETR_ENTRUST_ADD.CAL_MODE is
'计算方式，净价，全价，到期收益率';
comment on column ETR_ENTRUST_ADD.COUNTER_SEAT is
'席位号';
comment on column ETR_ENTRUST_ADD.COUNTER_CON_NO is
'约定号';
comment on column ETR_ENTRUST_ADD.FULL_PRICE is
'全价';
comment on column ETR_ENTRUST_ADD.TARGET_TYPE is
'目标类型';
comment on column ETR_ENTRUST_ADD.COUNTER_ORG is 
'对手方交易商';
comment on column ETR_ENTRUST_ADD.TRADER is 
'本方交易员';
comment on column ETR_ENTRUST_ADD.vpay_DATE is 
'首次交收日';
comment on column ETR_ENTRUST_ADD.mpay_date is 
'到期交收日';
comment on column ETR_ENTRUST_ADD.m_int_amt is 
'到期应计利息';
comment on column ETR_ENTRUST_ADD.m_Trade_amt is 
'到期结算金额';
comment on column ETR_ENTRUST_ADD.cap_days is 
'占款天数';
comment on column ETR_ENTRUST_ADD.trade_id is 
'成交编号/初始交易编号';
comment on column ETR_ENTRUST_ADD.request_id is 
'请求号';
COMMENT ON COLUMN "ETR_ENTRUST_ADD"."REDO_TYPE" IS
 '续做类型';
comment on column ETR_ENTRUST_ADD.DERIVATIVE_BS_DIR is 
'衍生品买卖方向';
COMMENT ON COLUMN ETR_ENTRUST_ADD.DERIVATIVE_OC_FLAG IS
 '衍生品开平仓标志';


----新增 TRD_MINPRICE_CONFIG：交易所最小价差配置
-- Create table
drop table TRD_MINPRICE_CONFIG;
create table TRD_MINPRICE_CONFIG
(
  seq_no          VARCHAR2(32) not null,
  trade_market    VARCHAR2(50),
  trade_plat      VARCHAR2(50),
  asset_classify1 VARCHAR2(50),
  asset_classify2 VARCHAR2(50),
  asset_classify3 VARCHAR2(50),
  is_grade        VARCHAR2(50),
  eff_date        DATE,
  batch_seq       VARCHAR2(32),
  remark          VARCHAR2(1000),
  create_user     VARCHAR2(20) not null,
  create_dept     VARCHAR2(32),
  create_time     TIMESTAMP(6) not null,
  update_user     VARCHAR2(20) not null,
  update_time     TIMESTAMP(6) not null,
  constraint PK_TRD_MINPRICE_CONFIG primary key (SEQ_NO)
);
-- Add comments to the table 
comment on table TRD_MINPRICE_CONFIG
  is '交易所最小价差配置';
-- Add comments to the columns 
comment on column TRD_MINPRICE_CONFIG.seq_no
  is '流水号';
comment on column TRD_MINPRICE_CONFIG.trade_market
  is '交易场所，上交所、深交所等';
comment on column TRD_MINPRICE_CONFIG.trade_plat
  is '交易平台，银行间、上交所固定收益平台、上交所大宗交易平台、深交所大宗平台、深交所综合协议交易平台、其他等';
comment on column TRD_MINPRICE_CONFIG.asset_classify1
  is '资产分类一';
comment on column TRD_MINPRICE_CONFIG.asset_classify2
  is '资产分类二';
comment on column TRD_MINPRICE_CONFIG.asset_classify3
  is '资产分类三';
comment on column TRD_MINPRICE_CONFIG.is_grade
  is '是否分档';
comment on column TRD_MINPRICE_CONFIG.eff_date
  is '生效日';
comment on column TRD_MINPRICE_CONFIG.batch_seq
  is '批次号';
comment on column TRD_MINPRICE_CONFIG.remark
  is '备注';
comment on column TRD_MINPRICE_CONFIG.create_user
  is '创建人';
comment on column TRD_MINPRICE_CONFIG.create_dept
  is '创建部门';
comment on column TRD_MINPRICE_CONFIG.create_time
  is '创建时间';
comment on column TRD_MINPRICE_CONFIG.update_user
  is '更新人';
comment on column TRD_MINPRICE_CONFIG.update_time
  is '更新时间';
  
  
---新增 TRD_MINPRICE_CONFIG_DETAIL:交易所最小价差配置明细，如果分档，则存多条数据
-- Create table
drop table TRD_MINPRICE_CONFIG_DETAIL;
create table TRD_MINPRICE_CONFIG_DETAIL
(
  seq_no          VARCHAR2(32) not null,
  config_seq_no   VARCHAR2(32) not null,
  grade_condition VARCHAR2(50),
  grade_amt       NUMBER(30,2),
  mini_price      NUMBER(30,14),
  create_user     VARCHAR2(20) not null,
  create_dept     VARCHAR2(32),
  create_time     TIMESTAMP(6) not null,
  update_user     VARCHAR2(20) not null,
  update_time     TIMESTAMP(6) not null,
  constraint PK_TRD_MINPRICE_CONFIG_DETAIL primary key (SEQ_NO)
);
-- Add comments to the table 
comment on table TRD_MINPRICE_CONFIG_DETAIL
  is '交易所最小价差配置明细，如果分档，则存多条数据';
-- Add comments to the columns 
comment on column TRD_MINPRICE_CONFIG_DETAIL.seq_no
  is '流水号';
comment on column TRD_MINPRICE_CONFIG_DETAIL.config_seq_no
  is '配置流水号';
comment on column TRD_MINPRICE_CONFIG_DETAIL.grade_condition
  is '分档条件';
comment on column TRD_MINPRICE_CONFIG_DETAIL.grade_amt
  is '分档金额';
comment on column TRD_MINPRICE_CONFIG_DETAIL.mini_price
  is '最小价差';
comment on column TRD_MINPRICE_CONFIG_DETAIL.create_user
  is '创建人';
comment on column TRD_MINPRICE_CONFIG_DETAIL.create_dept
  is '创建部门';
comment on column TRD_MINPRICE_CONFIG_DETAIL.create_time
  is '创建时间';
comment on column TRD_MINPRICE_CONFIG_DETAIL.update_user
  is '更新人';
comment on column TRD_MINPRICE_CONFIG_DETAIL.update_time
  is '更新时间';


----更新ETR_DISTRIBUTE：委托指令分发表
alter table ETR_DISTRIBUTE add IS_APPROVAL VARCHAR2(50);

comment on COLUMN ETR_DISTRIBUTE.IS_APPROVAL is 
'是否进入流程审批';

----更新ETR_AUTO_DISPENSE_RULE：委托自动分发规则表
alter table ETR_AUTO_DISPENSE_RULE add IS_ENABLE VARCHAR2(50);

alter table ETR_AUTO_DISPENSE_RULE add IS_APPROVAL VARCHAR2(50);

comment on COLUMN ETR_AUTO_DISPENSE_RULE.IS_ENABLE is 
'是否启用';

comment on COLUMN ETR_AUTO_DISPENSE_RULE.IS_APPROVAL is 
'是否进入流程审批';

----更新 FIN_PRODUCT_ADD：金融产品标的附属表
alter  table   FIN_PRODUCT_ADD ADD  (IS_NET_SETT  VARCHAR2(50 BYTE));
COMMENT ON COLUMN FIN_PRODUCT_ADD.IS_NET_SETT IS '是否纳入净额结算 Y:是 N:否';

alter table FIN_PRODUCT_ADD add IS_PPB VARCHAR2(50 BYTE);
COMMENT ON COLUMN "FIN_PRODUCT_ADD"."IS_PPB" IS '是否私募债 Y:是 N:否';

alter table FIN_PRODUCT_ADD add TRADE_PLAT VARCHAR2(50 BYTE);
COMMENT ON COLUMN "FIN_PRODUCT_ADD"."TRADE_PLAT" IS '交易平台';

----更新 IS_NET_SETT 字段
UPDATE FIN_PRODUCT_ADD 
SET IS_NET_SETT =
CASE WHEN FINPROD_ID in(
SELECT FINPROD_ID FROM (SELECT SUBSTR(FINPROD_MARKET_ID ,1,LENGTH(FINPROD_MARKET_ID)-3) AS stockCode,fpa.FINPROD_ID ,fpa.IS_PPB ,fpt.TYPE_6 
FROM FIN_PRODUCT fp 
LEFT JOIN FIN_PRODUCT_ADD  fpa 
ON fp.FINPROD_ID = fpa.FINPROD_ID
LEFT  JOIN  FIN_PRODUCT_TYPE fpt  
ON fp.FINPROD_ID = fpt.FINPROD_ID 
WHERE
fp.FINPROD_TYPE2 ='F01' AND fp.TRADE_MARKET ='03')
WHERE                 
('139000' <= stockCode and stockCode <= '139999'AND LENGTH (stockCode)=6)
or ('123000' <= stockCode AND stockCode <= '123499'AND LENGTH (stockCode)=6)
OR IS_PPB = 'Y'
OR TYPE_6 = 'B13'
UNION 
SELECT FINPROD_ID FROM (SELECT SUBSTR(FINPROD_MARKET_ID ,1,LENGTH(FINPROD_MARKET_ID)-3) AS stockCode,fpa.FINPROD_ID ,fpa.IS_PPB ,fpt.TYPE_6 
FROM FIN_PRODUCT fp 
LEFT JOIN FIN_PRODUCT_ADD  fpa 
ON fp.FINPROD_ID = fpa.FINPROD_ID
LEFT  JOIN  FIN_PRODUCT_TYPE fpt  
ON fp.FINPROD_ID = fpt.FINPROD_ID 
WHERE
fp.FINPROD_TYPE2 ='F01' AND fp.TRADE_MARKET ='04')
WHERE IS_PPB = 'Y'
OR TYPE_6 = 'B13') THEN 'N'
WHEN FINPROD_ID   NOT LIKE 'F01%' THEN NULL 
ELSE  'Y' 
END;


-----序列
-- Create sequence 
create sequence WTCNJY_SEQUENCE
minvalue 1
maxvalue 9999999999999999999999999999
start with 681
increment by 1
cache 20;

-- Create sequence 
create sequence JYSTOCK_SEQUENCE
minvalue 1
maxvalue 9999999999999999999999999999
start with 721
increment by 1
cache 20;


-----表：MST_BUSINESS_ID_RULE
delete from MST_BUSINESS_ID_RULE t where t.COMMON_BUSINESS_TYPE IN ('ETR25','ETR26','ETR27','ETR28','ETR29','ETR30','ETR31','ETR32','ETR33','ETR34','ETR35','ETR36','ETR37','ETR38','ETR39','ETR40');

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10019', 'ETR25', null, null, null, null, null, null, '04', 'WTSHARE', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10020', 'ETR26', null, null, null, null, null, null, '04', 'WTFUND', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10021', 'ETR27', null, null, null, null, null, null, '04', 'WTREPO', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10022', 'ETR28', null, null, null, null, null, null, '04', 'WTSHARE', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10023', 'ETR29', null, null, null, null, null, null, '04', 'WTFUND', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10024', 'ETR30', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10025', 'ETR31', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10026', 'ETR32', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10027', 'ETR33', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10028', 'ETR34', null, null, null, null, null, null, '04', 'WTSHARE', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10029', 'ETR35', null, null, null, null, null, null, '04', 'WTSHARE', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10030', 'ETR36', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10031', 'ETR37', null, null, null, null, null, null, '04', 'WTKZZSG', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10032', 'ETR38', null, null, null, null, null, null, '04', 'WTBOND', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10033', 'ETR39', null, null, null, null, null, null, '04', 'WTREPO', null, null, null, 'WTCNJY_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10034', 'ETR40', null, null, null, null, null, null, '04', 'WTZZG', null, null, null, 'WTBOND_SEQUENCE', 4, '01', null, null, 'system', null, sysdate, 'system', sysdate);

delete from MST_BUSINESS_ID_RULE where SEQ_NO='0205' and COMMON_BUSINESS_TYPE='TRD60';

insert into MST_BUSINESS_ID_RULE (SEQ_NO, COMMON_BUSINESS_TYPE, TYPE_1, TYPE_2, TYPE_3, TYPE_4, TYPE_5, TYPE_6, BUSINESS_ID_RULE_TYPE, PREFIX_NAME, PREFIX_SEQUENCE_NAME, PREFIX_SEQUENCE_LEN, POSTFIX_NAME, POSTFIX_SEQUENCE_NAME, POSTFIX_SEQUENCE_LEN, DATE_RULE_TYPE, BUSINESS_ID_SERVICE_NAME, BUSINESS_ID_METHOD_NAME, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('0205', 'TRD60', '', '', '', '', '', '', '04', 'JYSTOCK', '', null, '', 'JYSTOCK_SEQUENCE', 4, '01', '', '', 'system', '', sysdate, 'system', sysdate);

----草稿箱模式非对象画面表
delete from SYS_TRASH_NOT_TARGET_PAGE where SEQ_NO = 'A96EA0A5352F043FE0538C5AA8C0280A' and PAGE_ID in ('EtrExchangeTradeMain','EtrExchangeTrade001','EtrExchangeTrade002','EtrExchangeTrade003','EtrExchangeTradeRiskcal');
insert into SYS_TRASH_NOT_TARGET_PAGE (SEQ_NO, PAGE_ID) values ('A96EA0A538D1043FE0538C5AA8C0280A', 'EtrExchangeTradeMain');
insert into SYS_TRASH_NOT_TARGET_PAGE (SEQ_NO, PAGE_ID) values ('A96EA0A538D2043FE0538C5AA8C0280A', 'EtrExchangeTrade001');
insert into SYS_TRASH_NOT_TARGET_PAGE (SEQ_NO, PAGE_ID) values ('A96EA0A538D3043FE0538C5AA8C0280A', 'EtrExchangeTrade002');
insert into SYS_TRASH_NOT_TARGET_PAGE (SEQ_NO, PAGE_ID) values ('A96EA0A538D4043FE0538C5AA8C0280A', 'EtrExchangeTrade003');
insert into SYS_TRASH_NOT_TARGET_PAGE (SEQ_NO, PAGE_ID) values ('A96EA0A538D5043FE0538C5AA8C0280A', 'EtrExchangeTradeRiskcal');


----新增 全量：交易所最小价差配置
delete from TRD_MINPRICE_CONFIG;
insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('1', '03', '02', 'F01', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('2', '03', '04', 'F01', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('3', '03', '03', 'F01', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('4', '03', '02', 'F07', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, '单位为%', 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('5', '03', '04', 'F07', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, '单位为%', 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('6', '03', '03', 'F07', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, '单位为%', 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('7', '03', '02', 'F02', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('8', '03', '04', 'F02', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('9', '03', '02', 'F11', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10', '03', '04', 'F11', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('11', '04', '02', 'F01', 'B01', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('12', '04', '05', 'F01', 'B01', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('13', '04', '02', 'F01', 'B12', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('14', '04', '05', 'F01', 'B12', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('15', '04', '02', 'F01', 'B02', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('16', '04', '05', 'F01', 'B02', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('17', '04', '02', 'F01', 'B06', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('18', '04', '05', 'F01', 'B06', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('19', '04', '02', 'F01', 'B07', '10', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('21', '04', '05', 'F01', 'B07', '10', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('22', '04', '05', 'F01', 'B07', '11', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('23', '04', '05', 'F01', 'B13', '16', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('24', '04', '05', 'F01', 'B05', '05', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('25', '04', '05', 'F01', 'B05', '06', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('26', '04', '02', 'F01', 'B14', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('27', '04', '05', 'F01', 'B14', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('28', '04', '02', 'F01', 'B15', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('29', '04', '05', 'F01', 'B15', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('30', '04', '02', 'F01', 'B16', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('31', '04', '05', 'F01', 'B16', null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('32', '04', '02', 'F07', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, '单位为%', 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('33', '04', '05', 'F07', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, '单位为%', 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('34', '04', '02', 'F02', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('35', '04', '05', 'F02', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('36', '04', '02', 'F11', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('37', '04', '05', 'F11', null, null, 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('40', '12', null, 'F20', 'FU01', '03', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('38', '12', null, 'F20', 'FU01', '01', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('39', '12', null, 'F20', 'FU01', '02', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('41', '12', null, 'F20', 'FU02', '04', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('42', '12', null, 'F20', 'FU02', '05', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG (SEQ_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, IS_GRADE, EFF_DATE, BATCH_SEQ, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('43', '12', null, 'F20', 'FU02', '06', 'N', to_date('1990/01/01','yyyy-mm-dd'), null, null, 'system', null, sysdate, 'system', sysdate);




----新增 全量：交易所最小价差配置明细
delete from TRD_MINPRICE_CONFIG_DETAIL;
insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('2', '2', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('3', '3', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('4', '4', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('5', '5', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('6', '6', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('7', '7', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('8', '8', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('9', '9', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10', '10', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('11', '11', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('12', '12', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('13', '13', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('14', '14', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('15', '15', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('16', '16', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('17', '17', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('18', '18', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('19', '19', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('20', '21', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('21', '22', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('22', '23', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('23', '24', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('24', '25', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('25', '26', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('26', '27', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('27', '28', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('28', '29', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('29', '30', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('30', '31', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('31', '32', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('32', '33', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('33', '34', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('34', '35', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('35', '36', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('36', '37', null, null, 0.01000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('1', '1', null, null, 0.00100000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('37', '38', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('38', '39', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('39', '40', null, null, 0.00500000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('40', '41', null, null, 0.20000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('41', '42', null, null, 0.20000000000000, 'system', null, sysdate, 'system', sysdate);

insert into TRD_MINPRICE_CONFIG_DETAIL (SEQ_NO, CONFIG_SEQ_NO, GRADE_CONDITION, GRADE_AMT, MINI_PRICE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('42', '43', null, null, 0.20000000000000, 'system', null, sysdate, 'system', sysdate);

----新增 TRD_ATTRIBUTE_CONFIG：交易所交易属性配置
-- Create table
drop table TRD_ATTRIBUTE_CONFIG;
create table TRD_ATTRIBUTE_CONFIG
(
  config_no           VARCHAR2(32) not null,
  trade_market        VARCHAR2(50),
  trade_plat          VARCHAR2(50),
  asset_classify1     VARCHAR2(50),
  asset_classify2     VARCHAR2(50),
  asset_classify3     VARCHAR2(50),
  ps                  VARCHAR2(50),
  market_type         VARCHAR2(50),
  min_num             NUMBER(30,14),
  min_num_unit        VARCHAR2(50),
  min_amt             NUMBER(30,2),
  max_num             NUMBER(30,14),
  max_num_unit        VARCHAR2(50),
  max_amt             NUMBER(30,14),
  min_step            NUMBER(30,14),
  min_step_unit       VARCHAR2(50),
  min_num_market      NUMBER(30,14),
  min_num_market_unit VARCHAR2(50),
  max_num_market      NUMBER(30,14),
  max_num_market_unit VARCHAR2(50),
  remark              VARCHAR2(1000),
  eff_date            DATE,
  create_user         VARCHAR2(20) not null,
  create_dept         VARCHAR2(32),
  create_time         TIMESTAMP(6) not null,
  update_user         VARCHAR2(20) not null,
  update_time         TIMESTAMP(6) not null,
  constraint PK_TRD_ATTRIBUTE_CONFIG primary key (CONFIG_NO)
);
-- Add comments to the table 
comment on table TRD_ATTRIBUTE_CONFIG
  is '交易所交易属性配置';
-- Add comments to the columns 
comment on column TRD_ATTRIBUTE_CONFIG.config_no
  is '配置代码';
comment on column TRD_ATTRIBUTE_CONFIG.trade_market
  is '交易场所，上交所、深交所等';
comment on column TRD_ATTRIBUTE_CONFIG.trade_plat
  is '交易平台，固定收益平台、大宗交易平台、大宗平台、综合协议交易平台';
comment on column TRD_ATTRIBUTE_CONFIG.asset_classify1
  is '资产分类一';
comment on column TRD_ATTRIBUTE_CONFIG.asset_classify2
  is '资产分类二';
comment on column TRD_ATTRIBUTE_CONFIG.asset_classify3
  is '资产分类三';
comment on column TRD_ATTRIBUTE_CONFIG.ps
  is '交易方向';
comment on column TRD_ATTRIBUTE_CONFIG.market_type
  is '主板/中小企业板/创业板/科创板';
comment on column TRD_ATTRIBUTE_CONFIG.min_num
  is '最低交易数量';
comment on column TRD_ATTRIBUTE_CONFIG.min_num_unit
  is '最低交易数量单位';
comment on column TRD_ATTRIBUTE_CONFIG.min_amt
  is '最低交易金额（元）';
comment on column TRD_ATTRIBUTE_CONFIG.max_num
  is '最大交易数量';
comment on column TRD_ATTRIBUTE_CONFIG.max_num_unit
  is '最大交易数量单位';
comment on column TRD_ATTRIBUTE_CONFIG.max_amt
  is '最大交易金额（元）';
comment on column TRD_ATTRIBUTE_CONFIG.min_step
  is '最小交易单位';
comment on column TRD_ATTRIBUTE_CONFIG.min_step_unit
  is '最小交易单元单位';
comment on column TRD_ATTRIBUTE_CONFIG.min_num_market
  is '最低交易数量（市价）';
comment on column TRD_ATTRIBUTE_CONFIG.min_num_market_unit
  is '最低交易数量（市价）单位';
comment on column TRD_ATTRIBUTE_CONFIG.max_num_market
  is '最大交易数量（市价）';
comment on column TRD_ATTRIBUTE_CONFIG.max_num_market_unit
  is '最大交易数量（市价）单位';
comment on column TRD_ATTRIBUTE_CONFIG.remark
  is '备注';
comment on column TRD_ATTRIBUTE_CONFIG.eff_date
  is '生效日';
comment on column TRD_ATTRIBUTE_CONFIG.create_user
  is '创建人';
comment on column TRD_ATTRIBUTE_CONFIG.create_dept
  is '创建部门';
comment on column TRD_ATTRIBUTE_CONFIG.create_time
  is '创建时间';
comment on column TRD_ATTRIBUTE_CONFIG.update_user
  is '更新人';
comment on column TRD_ATTRIBUTE_CONFIG.update_time
  is '更新时间';

delete from TRD_ATTRIBUTE_CONFIG;
insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('1', '03', '04', 'F01', 'B01', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('2', '03', '04', 'F01', 'B01', null, 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('3', '03', '04', 'F01', 'B02', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('4', '03', '04', 'F01', 'B02', null, 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('5', '03', '04', 'F01', 'B05', '01', 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('6', '03', '04', 'F01', 'B05', '01', 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('7', '03', '04', 'F01', 'B07', '10', 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('8', '03', '04', 'F01', 'B07', '10', 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('9', '03', '04', 'F01', 'B06', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('10', '03', '04', 'F01', 'B06', null, 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('11', '03', '04', 'F01', 'B14', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('12', '03', '04', 'F01', 'B14', null, 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('13', '03', '04', 'F01', 'B15', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('14', '03', '04', 'F01', 'B15', null, 'T02', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('15', '03', '04', 'F07', 'H02', null, '03', null, 10000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1000.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('16', '03', '04', 'F07', 'H02', null, '04', null, 10000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1000.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('17', '03', '04', 'F02', null, null, 'T01', null, 2000000.00000000000000, '02', 2000000.00, 999999999.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 2000000.00000000000000, '02', 999999999.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('18', '03', '04', 'F02', null, null, 'T02', null, 2000000.00000000000000, '02', 2000000.00, 999999999.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 2000000.00000000000000, '02', 999999999.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('19', '03', '04', 'F11', null, null, 'T01', 'BD01', 300000.00000000000000, '03', 2000000.00, 999999999.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 300000.00000000000000, '03', 999999999.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('20', '03', '04', 'F11', null, null, 'T02', 'BD01', 300000.00000000000000, '03', 2000000.00, 999999999.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 300000.00000000000000, '03', 999999999.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('21', '03', '03', 'F01', 'B01', null, 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('22', '03', '03', 'F01', 'B01', null, 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('23', '03', '03', 'F01', 'B02', null, 'T01', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('24', '03', '03', 'F01', 'B02', null, 'T02', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('25', '03', '03', 'F01', 'B05', '01', 'T01', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('26', '03', '03', 'F01', 'B05', '01', 'T02', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('27', '03', '03', 'F01', 'B07', '10', 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('28', '03', '03', 'F01', 'B07', '10', 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('29', '03', '03', 'F01', 'B05', '06', 'T01', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('30', '03', '03', 'F01', 'B05', '06', 'T02', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('31', '03', '03', 'F01', 'B07', '11', 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('32', '03', '03', 'F01', 'B07', '11', 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('33', '03', '03', 'F01', 'B06', null, 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('34', '03', '03', 'F01', 'B06', null, 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('35', '03', '03', 'F01', 'B14', null, 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('36', '03', '03', 'F01', 'B14', null, 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('37', '03', '03', 'F01', 'B15', null, 'T01', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('38', '03', '03', 'F01', 'B15', null, 'T02', null, 1000.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('39', '03', '03', 'F01', 'B13', '16', 'T01', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('40', '03', '03', 'F01', 'B13', '16', 'T02', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 1000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('41', '03', '02', 'F01', 'B01', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('42', '03', '02', 'F01', 'B01', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('43', '03', '02', 'F01', 'B02', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('44', '03', '02', 'F01', 'B02', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('45', '03', '02', 'F01', 'B05', '01', 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('46', '03', '02', 'F01', 'B05', '01', 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('47', '03', '02', 'F01', 'B07', '10', 'T01', null, 10.00000000000000, '01', null, 100000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 100000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('48', '03', '02', 'F01', 'B07', '10', 'T02', null, 10.00000000000000, '01', null, 100000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 100000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('49', '03', '02', 'F01', 'B06', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('50', '03', '02', 'F01', 'B06', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('51', '03', '02', 'F01', 'B14', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('52', '03', '02', 'F01', 'B14', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('53', '03', '02', 'F01', 'B15', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('54', '03', '02', 'F01', 'B15', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('55', '03', '02', 'F01', 'B14', null, 'T04', null, 10.00000000000000, '01', null, null, null, null, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('56', '03', '02', 'F01', 'B14', null, 'T49', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('57', '03', '02', 'F01', null, null, 'T03', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('58', '03', '02', 'F01', null, null, 'T68', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('59', '03', '02', 'F01', null, null, 'T69', null, 10.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('60', '03', '02', 'F07', 'H02', null, '03', null, 1000.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 1000.00000000000000, '01', 1000.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('61', '03', '02', 'F07', 'H02', null, '04', null, 1000.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 1000.00000000000000, '01', 1000.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('62', '03', '02', 'F02', null, null, 'T01', null, 100.00000000000000, '02', null, 1000000.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 100.00000000000000, '02', 1000000.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('63', '03', '02', 'F02', null, null, 'T02', null, 100.00000000000000, '02', null, 1000000.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 100.00000000000000, '02', 1000000.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('64', '03', '02', 'F11', null, null, 'T46', 'BD01', 300000.00000000000000, '03', null, null, null, null, 1.00000000000000, '03', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('65', '03', '02', 'F11', null, null, 'T04', 'BD01', 300000.00000000000000, '03', null, null, null, null, 1000.00000000000000, '03', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('66', '03', '02', 'F11', null, null, 'T01', 'BD01', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 100.00000000000000, '03', 1000000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('15645', '03', '04', 'F01', 'B01', null, 'T01', null, 10000.00000000000000, '01', 1000000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 100.00000000000000, '01', 10000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('1111', '03', '02', 'F11', null, null, 'T01', 'BD03', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 500.00000000000000, '03', 500.00000000000000, '03', 100000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('70', '04', '02', 'F01', 'B01', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('71', '04', '02', 'F01', 'B01', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('72', '04', '02', 'F01', 'B12', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('73', '04', '02', 'F01', 'B12', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('74', '04', '02', 'F01', 'B02', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('75', '04', '02', 'F01', 'B02', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('76', '04', '02', 'F01', 'B06', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('77', '04', '02', 'F01', 'B06', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('78', '04', '02', 'F01', 'B07', '10', 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 100000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('79', '04', '02', 'F01', 'B07', '10', 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 100000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('80', '04', '02', 'F01', 'B14', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('81', '04', '02', 'F01', 'B14', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('82', '04', '02', 'F01', 'B15', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('83', '04', '02', 'F01', 'B15', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('84', '04', '02', 'F01', 'B16', null, 'T01', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('85', '04', '02', 'F01', 'B16', null, 'T02', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('86', '04', '02', 'F01', 'B14', null, 'T04', null, 10.00000000000000, '01', null, null, null, null, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('87', '04', '02', 'F01', 'B14', null, 'T49', null, 1.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('88', '04', '02', 'F01', null, null, 'T03', null, 1.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('89', '04', '02', 'F01', null, null, 'T68', null, 1.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('90', '04', '02', 'F01', null, null, 'T69', null, 1.00000000000000, '01', null, 999999999.00000000000000, '01', 999999999.00000000000000, 1.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('91', '04', '02', 'F07', 'H02', null, '03', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('92', '04', '02', 'F07', 'H02', null, '04', null, 10.00000000000000, '01', null, 1000000.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 10.00000000000000, '01', 1000000.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('93', '04', '02', 'F02', null, null, 'T01', null, 100.00000000000000, '02', null, 1000000.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 100.00000000000000, '02', 1000000.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('94', '04', '02', 'F02', null, null, 'T02', null, 100.00000000000000, '02', null, 1000000.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 100.00000000000000, '02', 1000000.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('95', '04', '02', 'F11', null, null, 'T46', 'BD01', 300000.00000000000000, '03', null, null, null, null, 1.00000000000000, '03', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('96', '04', '02', 'F11', null, null, 'T04', 'BD01', 300000.00000000000000, '03', null, null, null, null, 500.00000000000000, '03', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('97', '04', '02', 'F11', null, null, 'T01', 'BD01', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 100.00000000000000, '03', 1000000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('98', '04', '02', 'F11', null, null, 'T02', 'BD01', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 100.00000000000000, '03', 1000000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('99', '04', '05', 'F01', 'B01', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('100', '04', '05', 'F01', 'B01', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('101', '04', '05', 'F01', 'B12', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('102', '04', '05', 'F01', 'B12', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('103', '04', '05', 'F01', 'B02', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('104', '04', '05', 'F01', 'B02', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('105', '04', '05', 'F01', 'B06', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('106', '04', '05', 'F01', 'B06', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', '单位为%', sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('107', '04', '05', 'F01', 'B07', '10', 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('108', '04', '05', 'F01', 'B07', '10', 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('109', '04', '05', 'F01', 'B07', '11', 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('110', '04', '05', 'F01', 'B07', '11', 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('111', '04', '05', 'F01', 'B13', '16', 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('112', '04', '05', 'F01', 'B13', '16', 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('113', '04', '05', 'F01', 'B05', '05', 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('114', '04', '05', 'F01', 'B05', '05', 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('115', '04', '05', 'F01', 'B05', '06', 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('116', '04', '05', 'F01', 'B05', '06', 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('117', '04', '05', 'F01', 'B14', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('118', '04', '05', 'F01', 'B14', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('119', '04', '05', 'F01', 'B15', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('120', '04', '05', 'F01', 'B15', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('121', '04', '05', 'F01', 'B16', null, 'T01', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('122', '04', '05', 'F01', 'B16', null, 'T02', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('123', '04', '05', 'F07', 'H01', null, '03', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('124', '04', '05', 'F07', 'H01', null, '04', null, 5000.00000000000000, '01', 500000.00, 999999999.00000000000000, '01', 999999999.00000000000000, 10.00000000000000, '01', 5000.00000000000000, '01', 999999999.00000000000000, '01', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('125', '04', '05', 'F02', null, null, 'T01', null, 2000000.00000000000000, '02', 2000000.00, 999999999.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 2000000.00000000000000, '02', 999999999.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('126', '04', '05', 'F02', null, null, 'T02', null, 2000000.00000000000000, '02', 2000000.00, 999999999.00000000000000, '02', 999999999.00000000000000, 100.00000000000000, '02', 2000000.00000000000000, '02', 999999999.00000000000000, '02', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('127', '04', '05', 'F11', null, null, 'T01', 'BD01', 300000.00000000000000, '03', 2000000.00, 999999999.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 300000.00000000000000, '03', 999999999.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('128', '04', '05', 'F11', null, null, 'T02', 'BD01', 300000.00000000000000, '03', 2000000.00, 999999999.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 300000.00000000000000, '03', 999999999.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('129', '03', '02', 'F01', null, null, 'T97', null, 10.00000000000000, '01', null, null, null, null, 10.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('130', '04', '02', 'F01', null, null, 'T97', null, 1.00000000000000, '01', null, null, null, null, 1.00000000000000, '01', null, null, null, null, null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('67', '03', '02', 'F11', null, null, 'T02', 'BD01', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 100.00000000000000, '03', 100.00000000000000, '03', 1000000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('68', '03', '02', 'F11', null, null, 'T01', 'BD03', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 200.00000000000000, '03', 200.00000000000000, '03', 100000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('69', '03', '02', 'F11', null, null, 'T02', 'BD03', 300000.00000000000000, '03', null, 1000000.00000000000000, '03', 999999999.00000000000000, 200.00000000000000, '03', 200.00000000000000, '03', 100000.00000000000000, '03', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('131', '12', null, 'F20', 'FU02', '04', 'T100', null, 1.00000000000000, '04', null, 10.00000000000000, '04', null, 1.00000000000000, '04', 3.00000000000000, '04', 30.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('132', '12', null, 'F20', 'FU02', '04', 'T101', null, 1.00000000000000, '04', null, 10.00000000000000, '04', null, 1.00000000000000, '04', 3.00000000000000, '04', 30.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('133', '12', null, 'F20', 'FU02', '04', 'T102', null, 1.00000000000000, '04', null, 10.00000000000000, '04', null, 1.00000000000000, '04', 3.00000000000000, '04', 30.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('134', '12', null, 'F20', 'FU02', '04', 'T103', null, 1.00000000000000, '04', null, 10.00000000000000, '04', null, 1.00000000000000, '04', 3.00000000000000, '04', 30.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('135', '12', null, 'F20', 'FU02', '05', 'T100', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('136', '12', null, 'F20', 'FU02', '05', 'T101', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('137', '12', null, 'F20', 'FU02', '05', 'T102', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('138', '12', null, 'F20', 'FU02', '05', 'T103', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('139', '12', null, 'F20', 'FU02', '06', 'T100', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('140', '12', null, 'F20', 'FU02', '06', 'T101', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('141', '12', null, 'F20', 'FU02', '06', 'T102', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('142', '12', null, 'F20', 'FU02', '06', 'T103', null, 1.00000000000000, '04', null, 5000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 5000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('143', '12', null, 'F20', 'FU01', '01', 'T100', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('144', '12', null, 'F20', 'FU01', '01', 'T101', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('145', '12', null, 'F20', 'FU01', '01', 'T102', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('146', '12', null, 'F20', 'FU01', '01', 'T103', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('147', '12', null, 'F20', 'FU01', '02', 'T100', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('148', '12', null, 'F20', 'FU01', '02', 'T101', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('149', '12', null, 'F20', 'FU01', '02', 'T102', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('150', '12', null, 'F20', 'FU01', '02', 'T103', null, 1.00000000000000, '04', null, 40.00000000000000, '04', null, 1.00000000000000, '04', 5.00000000000000, '04', 50.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('151', '12', null, 'F20', 'FU01', '03', 'T100', null, 1.00000000000000, '04', null, 2000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 2000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('152', '12', null, 'F20', 'FU01', '03', 'T101', null, 1.00000000000000, '04', null, 2000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 2000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('153', '12', null, 'F20', 'FU01', '03', 'T102', null, 1.00000000000000, '04', null, 2000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 2000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);

insert into TRD_ATTRIBUTE_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, PS, MARKET_TYPE, MIN_NUM, MIN_NUM_UNIT, MIN_AMT, MAX_NUM, MAX_NUM_UNIT, MAX_AMT, MIN_STEP, MIN_STEP_UNIT, MIN_NUM_MARKET, MIN_NUM_MARKET_UNIT, MAX_NUM_MARKET, MAX_NUM_MARKET_UNIT, REMARK, EFF_DATE, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME)
values ('154', '12', null, 'F20', 'FU01', '03', 'T103', null, 1.00000000000000, '04', null, 2000.00000000000000, '04', null, 1.00000000000000, '04', 1.00000000000000, '04', 2000.00000000000000, '04', null, sysdate, 'system', null, sysdate, 'system', sysdate);


----新增 TRD_TIME_CONFIG：交易所交易时间配置
-- Create table
drop table TRD_TIME_CONFIG;
create table TRD_TIME_CONFIG
(
  config_no          VARCHAR2(32) not null,
  trade_market       VARCHAR2(50),
  trade_plat         VARCHAR2(50),
  asset_classify1    VARCHAR2(50),
  asset_classify2    VARCHAR2(50),
  asset_classify3    VARCHAR2(50),
  early_open_time    VARCHAR2(50),
  early_close_time   VARCHAR2(50),
  noon_open_time     VARCHAR2(50),
  noon_close_time    VARCHAR2(50),
  night_open_time    VARCHAR2(50),
  night_close_time   VARCHAR2(50),
  remark             VARCHAR2(1000),
  create_user        VARCHAR2(20) not null,
  create_dept        VARCHAR2(32),
  create_time        TIMESTAMP(6) not null,
  update_user        VARCHAR2(20) not null,
  update_time        TIMESTAMP(6) not null,
  declaration_type   VARCHAR2(50),
  is_last_trade_date VARCHAR2(50),
  constraint PK_TRD_TIME_CONFIG primary key (CONFIG_NO)
);
-- Add comments to the table 
comment on table TRD_TIME_CONFIG
  is '交易所交易时间配置';
-- Add comments to the columns 
comment on column TRD_TIME_CONFIG.config_no
  is '配置代码';
comment on column TRD_TIME_CONFIG.trade_market
  is '交易场所，上交所、深交所等';
comment on column TRD_TIME_CONFIG.trade_plat
  is '交易平台';
comment on column TRD_TIME_CONFIG.asset_classify1
  is '资产分类一';
comment on column TRD_TIME_CONFIG.asset_classify2
  is '资产分类二';
comment on column TRD_TIME_CONFIG.asset_classify3
  is '资产分类三';
comment on column TRD_TIME_CONFIG.early_open_time
  is '早盘开市时间';
comment on column TRD_TIME_CONFIG.early_close_time
  is '早盘收市时间';
comment on column TRD_TIME_CONFIG.noon_open_time
  is '午盘开市时间';
comment on column TRD_TIME_CONFIG.noon_close_time
  is '午盘收市时间';
comment on column TRD_TIME_CONFIG.night_open_time
  is '夜盘开市时间';
comment on column TRD_TIME_CONFIG.night_close_time
  is '夜盘收市时间';
comment on column TRD_TIME_CONFIG.remark
  is '备注';
comment on column TRD_TIME_CONFIG.create_user
  is '创建人';
comment on column TRD_TIME_CONFIG.create_dept
  is '创建部门';
comment on column TRD_TIME_CONFIG.create_time
  is '创建时间';
comment on column TRD_TIME_CONFIG.update_user
  is '更新人';
comment on column TRD_TIME_CONFIG.update_time
  is '更新时间';
comment on column TRD_TIME_CONFIG.declaration_type
  is '申报类型';
comment on column TRD_TIME_CONFIG.is_last_trade_date
  is '是否最后交易日';

delete from TRD_TIME_CONFIG;
insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('1', '03', '02', 'F01', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('2', '03', '02', 'F02', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('3', '03', '02', 'F11', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('4', '03', '02', 'F07', null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('5', '03', '03', null, null, null, '9:30:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('6', '03', '04', null, null, null, '9:30:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM06', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('7', '03', '04', null, null, null, '9:30:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM09', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('8', '03', '04', null, null, null, null, null, '15:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM10', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('9', '03', '04', null, null, null, null, null, '15:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM11', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('10', '04', '02', 'F01', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('11', '04', '02', 'F02', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('12', '04', '02', 'F11', null, null, '9:15:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('13', '04', '02', 'F07', null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('14', '04', '05', null, null, null, null, null, '15:05:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM10', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('15', '04', '05', null, null, null, null, null, '15:05:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM11', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('16', '04', '05', null, null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM06', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('17', '04', '05', null, null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM07', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('18', '04', '05', null, null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM08', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('19', '04', '05', null, null, null, '9:15:00', '11:30:00', '13:00:00', '15:30:00', null, null, null, 'system', null, sysdate, 'system', sysdate, 'PRM09', null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('20', '12', null, 'F20', 'FU02', null, '9:25:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, null);

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('21', '12', null, 'F20', 'FU01', null, '9:25:00', '11:30:00', '13:00:00', '15:00:00', null, null, null, 'system', null, sysdate, 'system', sysdate, null, 'N');

insert into TRD_TIME_CONFIG (CONFIG_NO, TRADE_MARKET, TRADE_PLAT, ASSET_CLASSIFY1, ASSET_CLASSIFY2, ASSET_CLASSIFY3, EARLY_OPEN_TIME, EARLY_CLOSE_TIME, NOON_OPEN_TIME, NOON_CLOSE_TIME, NIGHT_OPEN_TIME, NIGHT_CLOSE_TIME, REMARK, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, DECLARATION_TYPE, IS_LAST_TRADE_DATE)
values ('22', '12', null, 'F20', 'FU01', null, '9:25:00', '11:30:00', null, null, null, null, null, 'system', null, sysdate, 'system', sysdate, null, 'Y');


----新增 ETR_DISTRIBUTE_INTERF_TMP:委托指令分发接口临时表
-- Create table
drop table ETR_DISTRIBUTE_INTERF_TMP;
create table ETR_DISTRIBUTE_INTERF_TMP
(
  entrust_id     VARCHAR2(32) not null,
  trader         VARCHAR2(20) not null,
  trading_header VARCHAR2(32),
  create_user    VARCHAR2(20) not null,
  create_dept    VARCHAR2(32),
  create_time    TIMESTAMP(6) not null,
  update_user    VARCHAR2(20) not null,
  update_time    TIMESTAMP(6) not null,
  constraint PK_ETR_DISTRIBUTE_INTERF_TMP primary key (ENTRUST_ID, TRADER)
);
-- Add comments to the table 
comment on table ETR_DISTRIBUTE_INTERF_TMP
  is '委托指令分发接口临时表';
-- Add comments to the columns 
comment on column ETR_DISTRIBUTE_INTERF_TMP.entrust_id
  is '委托代码';
comment on column ETR_DISTRIBUTE_INTERF_TMP.trader
  is '指定交易员';
comment on column ETR_DISTRIBUTE_INTERF_TMP.trading_header
  is '交易主管，交易主管分发指令时记录主管的用户代码';
comment on column ETR_DISTRIBUTE_INTERF_TMP.create_user
  is '创建人';
comment on column ETR_DISTRIBUTE_INTERF_TMP.create_dept
  is '创建部门';
comment on column ETR_DISTRIBUTE_INTERF_TMP.create_time
  is '创建时间';
comment on column ETR_DISTRIBUTE_INTERF_TMP.update_user
  is '更新人';
comment on column ETR_DISTRIBUTE_INTERF_TMP.update_time
  is '更新时间';





