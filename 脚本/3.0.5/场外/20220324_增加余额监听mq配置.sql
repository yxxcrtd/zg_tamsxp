INSERT INTO SYS_MQ_EXCHANGE (EXCHANGE_NAME, IS_DURABLE, EXCHANGE_TYPE, REMARK) VALUES('exchange.position.update.balance', '01', '02', NULL);
INSERT INTO SYS_MQ_QUEUE (QUEUE_NAME, IS_DURABLE, IS_AUTO_DELETE, IS_EXCLUSIVE, REMARK) VALUES('queue.position.update.balance', '01', '02', '02', NULL);
INSERT INTO SYS_MQ_ROUTE (ROUTE_NAME, EXCHANGE_NAME, QUEUE_NAME, REMARK) VALUES('exchange.position.use', 'exchange.position.update.balance', 'queue.position.update.balance', NULL);
