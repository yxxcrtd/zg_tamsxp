delete from SYS_DATADICT_LIST where DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE='27';
insert into SYS_DATADICT_LIST
values ('SVR_REPORT_CODE', '27', '理财子公司月度统计表', '27', '0', 'system', 'null', to_date('2019-03-05', 'yyyy-MM-dd'),
        'system', to_date('2019-03-05', 'yyyy-MM-dd'), null);