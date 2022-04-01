--大宗交易指令菜单名称修改成和需求一致
update SYS_MENU set REMARK = '大宗交易指令' where PAGE_ID = 'EtrExchangeBlockTrade';
update SYS_PAGE set REMARK = '大宗交易指令' where PAGE_ID = 'EtrExchangeBlockTrade';