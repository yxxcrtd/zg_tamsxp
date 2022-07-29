update SYS_DATADICT_LIST
   set item_value = '债券买入返售'
 where dict_code = 'SVR_ASSET_TYPE_SECOND_LEVEL'
   and item_code = '1108';


delete from SYS_DATADICT_LIST
 where dict_code = 'SVR_ASSET_TYPE_SECOND_LEVEL'
   and item_code = '2201';