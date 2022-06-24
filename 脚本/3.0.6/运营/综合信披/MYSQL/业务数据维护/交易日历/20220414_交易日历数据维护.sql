-- STATUS字段是后来加的，表里之前的数据STATUS为null，导致查询不到有效数据，现在更新为有效状态
UPDATE MST_HOLIDAY SET STATUS='E' WHERE STATUS IS NULL;