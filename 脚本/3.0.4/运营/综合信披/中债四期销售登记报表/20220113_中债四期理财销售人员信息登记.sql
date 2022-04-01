--总部或分支机构信息登记表
drop table CHB_SALER_INST_SUB;
create table CHB_SALER_INST_SUB(
   REGIST_SEQNO         CHAR(36)
  ,SALER_INST_CODE	VARCHAR2(32)
  ,SALER_INST_NAME	VARCHAR2(400)
  ,SALER_ORG_CODE 	VARCHAR2(70)
  ,SALER_ORG_NAME	VARCHAR2(400)
  ,SALER_ORG_LEVEL	VARCHAR2(2)
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20)
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(1000)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_INST_SUB is '总部或分支机构信息登记表';
comment on column CHB_SALER_INST_SUB.REGIST_SEQNO is '登记序列 ';
comment on column CHB_SALER_INST_SUB.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_INST_SUB.SALER_INST_NAME is '销售机构名称 ';
comment on column CHB_SALER_INST_SUB.SALER_ORG_CODE is '总部或分支机构编码 ';
comment on column CHB_SALER_INST_SUB.SALER_ORG_NAME is '总部或分支机构名称 ';
comment on column CHB_SALER_INST_SUB.SALER_ORG_LEVEL is '机构层级 ';
comment on column CHB_SALER_INST_SUB.STATUS is '数据状态';
comment on column CHB_SALER_INST_SUB.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_INST_SUB.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_INST_SUB.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_INST_SUB.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_INST_SUB.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_INST_SUB.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_INST_SUB.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_INST_SUB.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_INST_SUB.REMARK is '备注 ';
comment on column CHB_SALER_INST_SUB.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_INST_SUB.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_INST_SUB.CREATE_USER is '创建人 ';
comment on column CHB_SALER_INST_SUB.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_INST_SUB.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_INST_SUB.UPDATE_TIME is '更新时间 ';

--总部或分支机构信息登记履历表
drop table CHB_SALER_INST_SUB_HIS;
create table CHB_SALER_INST_SUB_HIS(
   HIS_NO                 CHAR(36)
  ,SALER_INST_CODE	VARCHAR2(32)
  ,SALER_INST_NAME	VARCHAR2(400)
  ,SALER_ORG_CODE 	VARCHAR2(70)
  ,SALER_ORG_NAME	VARCHAR2(400)
  ,SALER_ORG_LEVEL	VARCHAR2(2)
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20) NOT NULL
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(1000)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_INST_SUB_HIS is '总部或分支机构信息登记履历表';
comment on column CHB_SALER_INST_SUB_HIS.HIS_NO is '履历编号 ';
comment on column CHB_SALER_INST_SUB_HIS.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_INST_SUB_HIS.SALER_INST_NAME is '销售机构名称 ';
comment on column CHB_SALER_INST_SUB_HIS.SALER_ORG_CODE is '总部或分支机构编码 ';
comment on column CHB_SALER_INST_SUB_HIS.SALER_ORG_NAME is '总部或分支机构名称 ';
comment on column CHB_SALER_INST_SUB_HIS.SALER_ORG_LEVEL is '机构层级 ';
comment on column CHB_SALER_INST_SUB_HIS.STATUS is '数据状态 ';
comment on column CHB_SALER_INST_SUB_HIS.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_INST_SUB_HIS.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_INST_SUB_HIS.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_INST_SUB_HIS.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_INST_SUB_HIS.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_INST_SUB_HIS.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_INST_SUB_HIS.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_INST_SUB_HIS.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_INST_SUB_HIS.REMARK is '备注 ';
comment on column CHB_SALER_INST_SUB_HIS.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_INST_SUB_HIS.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_INST_SUB_HIS.CREATE_USER is '创建人 ';
comment on column CHB_SALER_INST_SUB_HIS.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_INST_SUB_HIS.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_INST_SUB_HIS.UPDATE_TIME is '更新时间 ';

--销售人员身份信息登记表
drop table CHB_SALER_IDEN_SUB;
create table CHB_SALER_IDEN_SUB(
   REGIST_SEQNO         CHAR(36)
  ,SALER_INS_NO	VARCHAR2(120)
  ,SALER_REGIST_NO	VARCHAR2(12)
  ,SALER_INST_CODE 	VARCHAR2(100)
  ,SALER_NAME	VARCHAR2(400)
  ,SALER_SEX	VARCHAR2(2)
  ,SALER_BIRTHDAY DATE
  ,SALER_CREDENT_TYPE	VARCHAR2(2)
  ,SALER_OTHER_CREDENT_NAME  VARCHAR2(60)
  ,SALER_CREDENT_NO VARCHAR2(50)
  ,SALER_AREA_TYPE VARCHAR2(6)
  ,SALER_ORG_CODE  VARCHAR2(70)
  ,SALER_DEPT  VARCHAR2(80)
  ,SALER_POSITION VARCHAR2(80)
  ,SALER_EDU_LEVEL VARCHAR2(2)
  ,SALER_DEGREE VARCHAR2(2)
  ,SALER_WORK_START_YMD VARCHAR2(20)
  ,SALER_FINANCE_START_YMD VARCHAR2(20)
  ,SALER_START_YMD VARCHAR2(20)
  ,SALER_LEVLE VARCHAR2(2)
  ,SALER_CERTIFICATE_TYPE VARCHAR2(100)
  ,SALER_CHB_TRAIN_NO VARCHAR2(50)
  ,SALER_RISK_LEVEL VARCHAR2(50)
  ,SALER_QUALIFICATION VARCHAR2(800)
  ,SALER_TEL VARCHAR2(30)
  ,SALER_MOBILE VARCHAR2(11)
  ,SALER_MAIL VARCHAR2(50)
  ,SALER_STATUS VARCHAR2(2)
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20)
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(1000)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_IDEN_SUB is '销售人员身份信息登记表';
comment on column CHB_SALER_IDEN_SUB.REGIST_SEQNO is '登记序列 ';
comment on column CHB_SALER_IDEN_SUB.SALER_INS_NO is '内部编号';
comment on column CHB_SALER_IDEN_SUB.SALER_REGIST_NO is '销售人员登记编码 ';
comment on column CHB_SALER_IDEN_SUB.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_IDEN_SUB.SALER_NAME is '姓名 ';
comment on column CHB_SALER_IDEN_SUB.SALER_SEX is '性别 ';
comment on column CHB_SALER_IDEN_SUB.SALER_BIRTHDAY is '出生日期 ';
comment on column CHB_SALER_IDEN_SUB.SALER_CREDENT_TYPE is '证件类别 ';
comment on column CHB_SALER_IDEN_SUB.SALER_OTHER_CREDENT_NAME is '其他证件名称 ';
comment on column CHB_SALER_IDEN_SUB.SALER_CREDENT_NO is '证件号码 ';
comment on column CHB_SALER_IDEN_SUB.SALER_AREA_TYPE is '所属区域 ';
comment on column CHB_SALER_IDEN_SUB.SALER_ORG_CODE  is '所属总部或分支机构 ';
comment on column CHB_SALER_IDEN_SUB.SALER_DEPT is '所属部门 ';
comment on column CHB_SALER_IDEN_SUB.SALER_POSITION is '职位 ';
comment on column CHB_SALER_IDEN_SUB.SALER_EDU_LEVEL is '学历 ';
comment on column CHB_SALER_IDEN_SUB.SALER_DEGREE is '学位 ';
comment on column CHB_SALER_IDEN_SUB.SALER_WORK_START_YMD is '首次参加工作时间 ';
comment on column CHB_SALER_IDEN_SUB.SALER_FINANCE_START_YMD is '首次从事金融工作时间 ';
comment on column CHB_SALER_IDEN_SUB.SALER_START_YMD is '首次从事理财销售时间 ';
comment on column CHB_SALER_IDEN_SUB.SALER_LEVLE is '专业技术职称';
comment on column CHB_SALER_IDEN_SUB.SALER_CERTIFICATE_TYPE is '理财专业证书 ';
comment on column CHB_SALER_IDEN_SUB.SALER_CHB_TRAIN_NO is '理财登记培训证书编号 ';
comment on column CHB_SALER_IDEN_SUB.SALER_RISK_LEVEL is '可销售产品风险等级 ';
comment on column CHB_SALER_IDEN_SUB.SALER_QUALIFICATION is '销售资质 ';
comment on column CHB_SALER_IDEN_SUB.SALER_TEL is '办公电话 ';
comment on column CHB_SALER_IDEN_SUB.SALER_MOBILE is '移动电话 ';
comment on column CHB_SALER_IDEN_SUB.SALER_MAIL is '邮箱 ';
comment on column CHB_SALER_IDEN_SUB.SALER_STATUS is '当前状态 ';
comment on column CHB_SALER_IDEN_SUB.STATUS is '数据状态';
comment on column CHB_SALER_IDEN_SUB.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_IDEN_SUB.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_IDEN_SUB.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_IDEN_SUB.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_IDEN_SUB.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_IDEN_SUB.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_IDEN_SUB.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_IDEN_SUB.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_IDEN_SUB.REMARK is '备注 ';
comment on column CHB_SALER_IDEN_SUB.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_IDEN_SUB.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_IDEN_SUB.CREATE_USER is '创建人 ';
comment on column CHB_SALER_IDEN_SUB.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_IDEN_SUB.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_IDEN_SUB.UPDATE_TIME is '更新时间 ';

--销售人员身份信息登记履历表
drop table CHB_SALER_IDEN_SUB_HIS;
create table CHB_SALER_IDEN_SUB_HIS(
   HIS_NO         CHAR(36)
  ,SALER_INS_NO	VARCHAR2(120)
  ,SALER_REGIST_NO	VARCHAR2(12)
  ,SALER_INST_CODE 	VARCHAR2(100)
  ,SALER_NAME	VARCHAR2(400)
  ,SALER_SEX	VARCHAR2(2)
  ,SALER_BIRTHDAY DATE
  ,SALER_CREDENT_TYPE	VARCHAR2(2)
  ,SALER_OTHER_CREDENT_NAME  VARCHAR2(60)
  ,SALER_CREDENT_NO VARCHAR2(50)
  ,SALER_AREA_TYPE VARCHAR2(6)
  ,SALER_ORG_CODE  VARCHAR2(70)
  ,SALER_DEPT  VARCHAR2(80)
  ,SALER_POSITION VARCHAR2(80)
  ,SALER_EDU_LEVEL VARCHAR2(2)
  ,SALER_DEGREE VARCHAR2(2)
  ,SALER_WORK_START_YMD VARCHAR2(20)
  ,SALER_FINANCE_START_YMD VARCHAR2(20)
  ,SALER_START_YMD VARCHAR2(20)
  ,SALER_LEVLE VARCHAR2(2)
  ,SALER_CERTIFICATE_TYPE VARCHAR2(100)
  ,SALER_CHB_TRAIN_NO VARCHAR2(50)
  ,SALER_RISK_LEVEL VARCHAR2(50)
  ,SALER_QUALIFICATION VARCHAR2(400)
  ,SALER_TEL VARCHAR2(30)
  ,SALER_MOBILE VARCHAR2(11)
  ,SALER_MAIL VARCHAR2(50)
  ,SALER_STATUS VARCHAR2(2)
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20)
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,REMARK	VARCHAR2(1000)
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_IDEN_SUB_HIS is '销售人员身份信息登记履历表';
comment on column CHB_SALER_IDEN_SUB_HIS.HIS_NO is '履历编号 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_INS_NO is '内部编号';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_REGIST_NO is '销售人员登记编码 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_NAME is '姓名 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_SEX is '性别 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_BIRTHDAY is '出生日期 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_CREDENT_TYPE is '证件类别 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_OTHER_CREDENT_NAME is '其他证件名称 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_CREDENT_NO is '证件号码 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_AREA_TYPE is '所属区域 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_ORG_CODE  is '所属总部或分支机构 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_DEPT is '所属部门 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_POSITION is '职位 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_EDU_LEVEL is '学历 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_DEGREE is '学位 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_WORK_START_YMD is '首次参加工作时间 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_FINANCE_START_YMD is '首次从事金融工作时间 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_START_YMD is '首次从事理财销售时间 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_LEVLE is '专业技术职称';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_CERTIFICATE_TYPE is '理财专业证书 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_CHB_TRAIN_NO is '理财登记培训证书编号 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_RISK_LEVEL is '可销售产品风险等级 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_QUALIFICATION is '销售资质 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_TEL is '办公电话 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_MOBILE is '移动电话 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_MAIL is '邮箱 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SALER_STATUS is '当前状态 ';
comment on column CHB_SALER_IDEN_SUB_HIS.STATUS is '数据状态';
comment on column CHB_SALER_IDEN_SUB_HIS.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_IDEN_SUB_HIS.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_IDEN_SUB_HIS.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_IDEN_SUB_HIS.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_IDEN_SUB_HIS.REMARK is '备注 ';
comment on column CHB_SALER_IDEN_SUB_HIS.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_IDEN_SUB_HIS.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_IDEN_SUB_HIS.CREATE_USER is '创建人 ';
comment on column CHB_SALER_IDEN_SUB_HIS.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_IDEN_SUB_HIS.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_IDEN_SUB_HIS.UPDATE_TIME is '更新时间 ';

--销售人员奖惩投诉记录信息登记表创建
drop table CHB_SALER_REW_PENA_SUB;
create table CHB_SALER_REW_PENA_SUB(
   REGIST_SEQNO         CHAR(36)
  ,SALER_REW_PENA_NO	VARCHAR2(32)
  ,SALER_INS_NO	VARCHAR2(120) NOT NULL
  ,SALER_INST_CODE 	VARCHAR2(32)
  ,SALER_REW_PENA_TYEP	VARCHAR2(2)
  ,SALER_REW_PENA_NAME	VARCHAR2(200)
  ,SALER_REW_PENA_COMMENTS VARCHAR2(800)
  ,SALER_REW_PENA_INST	VARCHAR2(2)
  ,SALER_REW_PENA_INST_NAME VARCHAR2(400)
  ,SALER_REW_PENA_START	DATE
  ,SALER_REW_PENA_END	DATE
  ,SALER_COMPLAIN_DETAIL VARCHAR2(800)
  ,SALER_COMPLAIN_RESULT VARCHAR2(800)
  ,SALER_COMPLAIN_DATE	DATE
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20)
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_REW_PENA_SUB is '销售人员奖惩投诉记录信息登记表';
comment on column CHB_SALER_REW_PENA_SUB.REGIST_SEQNO is '登记序列 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_NO is '奖惩编号 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_INS_NO is '内部编号 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_TYEP is '记录分类 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_NAME is '奖惩名称 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_COMMENTS is '奖惩说明 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_INST is '奖惩单位 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_INST_NAME is '奖惩单位具体名称 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_START is '奖惩起始日期 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_REW_PENA_END is '奖惩结束日期 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_COMPLAIN_DETAIL is '投诉详情 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_COMPLAIN_RESULT is '投诉处理结果 ';
comment on column CHB_SALER_REW_PENA_SUB.SALER_COMPLAIN_DATE is '投诉日期 ';
comment on column CHB_SALER_REW_PENA_SUB.STATUS is '数据状态';
comment on column CHB_SALER_REW_PENA_SUB.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_REW_PENA_SUB.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_REW_PENA_SUB.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_REW_PENA_SUB.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_REW_PENA_SUB.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_REW_PENA_SUB.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_REW_PENA_SUB.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_REW_PENA_SUB.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_REW_PENA_SUB.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_REW_PENA_SUB.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_REW_PENA_SUB.CREATE_USER is '创建人 ';
comment on column CHB_SALER_REW_PENA_SUB.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_REW_PENA_SUB.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_REW_PENA_SUB.UPDATE_TIME is '更新时间 ';

--销售人员奖惩投诉记录信息登记履历表创建
drop table CHB_SALER_REW_PENA_SUB_HIS;
create table CHB_SALER_REW_PENA_SUB_HIS(
   HIS_NO         CHAR(36)
  ,SALER_REW_PENA_NO	VARCHAR2(32)
  ,SALER_INS_NO	VARCHAR2(120) NOT NULL
  ,SALER_INST_CODE 	VARCHAR2(32)
  ,SALER_REW_PENA_TYEP	VARCHAR2(2)
  ,SALER_REW_PENA_NAME	VARCHAR2(200)
  ,SALER_REW_PENA_COMMENTS VARCHAR2(800)
  ,SALER_REW_PENA_INST	VARCHAR2(2)
  ,SALER_REW_PENA_INST_NAME VARCHAR2(400)
  ,SALER_REW_PENA_START	DATE
  ,SALER_REW_PENA_END	DATE
  ,SALER_COMPLAIN_DETAIL VARCHAR2(800)
  ,SALER_COMPLAIN_RESULT VARCHAR2(800)
  ,SALER_COMPLAIN_DATE	DATE
  ,STATUS	VARCHAR2(2)
  ,SUMIT_SEQ VARCHAR2(20)
  ,CHB_SUBMIT_DEADLINE TIMESTAMP(6)
  ,SUBMIT_DEADLINE     TIMESTAMP(6)
  ,SEND_STATUS         VARCHAR2(50)
  ,SUBMIT_TIME         TIMESTAMP(6)
  ,SUBMIT_FEEDBACK_STATUS VARCHAR2(50)
  ,SUBMIT_FEEDBACK_MSG  VARCHAR2(2000)
  ,DATA_DATE	DATE
  ,CREATE_DEPT	VARCHAR2(20)
  ,ORG_CODE	VARCHAR2(20)
  ,CREATE_USER	VARCHAR2(20)
  ,CREATE_TIME	TIMESTAMP(6)
  ,UPDATE_USER	VARCHAR2(32)
  ,UPDATE_TIME	TIMESTAMP(6)
);
comment on table CHB_SALER_REW_PENA_SUB_HIS is '销售人员奖惩投诉记录信息登记表';
comment on column CHB_SALER_REW_PENA_SUB_HIS.HIS_NO is '履历编号 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_NO is '奖惩编号 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_INS_NO is '内部编号 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_INST_CODE is '销售机构代码 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_TYEP is '记录分类 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_NAME is '奖惩名称 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_COMMENTS is '奖惩说明 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_INST is '奖惩单位 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_INST_NAME is '奖惩单位具体名称 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_START is '奖惩起始日期 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_REW_PENA_END is '奖惩结束日期 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_COMPLAIN_DETAIL is '投诉详情 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_COMPLAIN_RESULT is '投诉处理结果 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SALER_COMPLAIN_DATE is '投诉日期 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.STATUS is '数据状态';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SUMIT_SEQ is '报送流水号 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.CHB_SUBMIT_DEADLINE is '中债报送截止日 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SUBMIT_DEADLINE is '行内报送截止日 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SEND_STATUS is '报送状态 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SUBMIT_TIME is '报送时间 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SUBMIT_FEEDBACK_STATUS is '反馈状态 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.SUBMIT_FEEDBACK_MSG is '报送反馈消息 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.DATA_DATE is '数据日期 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.CREATE_DEPT is '创建部门 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.ORG_CODE is '机构代码 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.CREATE_USER is '创建人 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.CREATE_TIME is '创建时间 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.UPDATE_USER is '更新人 ';
comment on column CHB_SALER_REW_PENA_SUB_HIS.UPDATE_TIME is '更新时间 ';

--理财销售人员信息登记获取报送截止日配置
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '42';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_SALER_INST_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','42','总部或分支机构信息登记','42','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_SALER_INST_SUB','总部或分支机构信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '42';
INSERT INTO CHB_REG_TIME_POINTS VALUES('42',null,'42',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '43';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_SALER_IDEN_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','43','销售人员身份信息登记','43','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_SALER_IDEN_SUB','销售人员身份信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '43';
INSERT INTO CHB_REG_TIME_POINTS VALUES('43',null,'43',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_REPORT_CODE' and ITEM_CODE = '44';
DELETE FROM SYS_DATADICT_LIST WHERE DICT_CODE = 'SVR_CHECK_INTERF_TYPE' and ITEM_CODE = 'CHB_SALER_REW_PENA_SUB';
insert into SYS_DATADICT_LIST VALUES ('SVR_REPORT_CODE','44','销售人员奖惩投诉记录信息登记','44','0','system',null,sysdate,'system',sysdate,null);
insert into SYS_DATADICT_LIST VALUES ('SVR_CHECK_INTERF_TYPE','CHB_SALER_REW_PENA_SUB','销售人员奖惩投诉记录信息登记','20','0','system',null,sysdate,'system',sysdate,null);
DELETE FROM CHB_REG_TIME_POINTS WHERE CHB_REG_TIME_POINTS.SEQNO = '44';
INSERT INTO CHB_REG_TIME_POINTS VALUES('44',null,'44',null,'03','03','02','业务发生后N个工作日','1','1','12','4','E',null,null,'qq2',sysdate,'system',sysdate,null);

--登记页面菜单配置
DELETE FROM SYS_PAGE WHERE PAGE_ID in ('SalesStaffManageRegist','SalerIdenInfoRegist001','SalerInstInfoRegist001','SalerRewPenaInfoRegist001');
delete from SYS_MENU where MENU_ID = 'REPORT_REGIST_001_002_011';
INSERT INTO SYS_MENU (MENU_ID,SYSTEM_CODE,ICON,PARENT_MENU_ID,PAGE_ID,ORDER_NO,IS_SPREAD,REMARK,CREATE_USER,CREATE_DEPT,CREATE_TIME,UPDATE_USER,UPDATE_TIME)values('REPORT_REGIST_001_002_011','SVR',null,'REPORT_REGIST_001_002','SalesStaffManageRegist',11,02,'理财销售人员信息登记','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalesStaffManageRegist','REP','/report/submitmanagement/salesstaffmanageregist/SalesStaffManageRegist','report/submitmanagement/salesstaffmanageregist/SalesStaffManageRegist','REPORT_REGIST_001_002_011',null,null,'02','01','理财销售人员信息登记','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalerIdenInfoRegist001','REP','/report/submitmanagement/salerideninforegist/SalerIdenInfoRegist001','report/submitmanagement/salerideninforegist/SalerIdenInfoRegist001',null,null,null,'02','01','总部或分支机构信息','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalerInstInfoRegist001','REP','/report/submitmanagement/salerinstinforegist/SalerInstInfoRegist001','report/submitmanagement/salerinstinforegist/SalerInstInfoRegist001',null,null,null,'02','01','销售人员身份信息','system',null,sysdate,'system',sysdate);
INSERT INTO SYS_PAGE VALUES ('SalerRewPenaInfoRegist001','REP','/report/submitmanagement/salerrewpenainforegist/SalerRewPenaInfoRegist001','report/submitmanagement/salerrewpenainforegist/SalerRewPenaInfoRegist001',null,null,null,'02','01','销售人员奖惩投诉记录信息','system',null,sysdate,'system',sysdate);

--批处理配置
DELETE FROM SYS_BATCH_TASK WHERE TASK_ID = '3062';
insert into SYS_BATCH_TASK (TASK_ID, SYSTEM_CODE, TASK_NAME, FTASK_ID, BEAN_NAME, TASK_PRI, CREATE_USER, CREATE_DEPT, CREATE_TIME, UPDATE_USER, UPDATE_TIME, IS_BATCH_START_TARGET)
values ('3062', 'SVR', '销售人员信息登记报送', null, 'SvrSalesStaffManageTaskService', 3040, 'system', null, sysdate, 'system', sysdate, null);
