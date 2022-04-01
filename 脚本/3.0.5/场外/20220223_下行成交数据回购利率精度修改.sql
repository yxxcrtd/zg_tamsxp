--下行回购利率实际为4位小数，除以100后要保留6位
alter table interf_cstp_trade modify price number(10,6);