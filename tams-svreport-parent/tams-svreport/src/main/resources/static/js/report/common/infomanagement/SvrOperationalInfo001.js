
var tableName;
var assetColList1 = [ //标题栏
	{rowspan: 2, type:'checkbox'},
	{align: 'center', title: '公共信息（必填）', colspan: 5},
	{align: 'center', title: '本行/他行存款||大额存单', colspan: 10},
	{align: 'center', title: '债券类资产/理财直接融资工具/同业存单', colspan: 11},
	{align: 'center', title: '拆放同业及债券买入返售/同业拆入及卖出回购', colspan: 8},
	//中债五月份资产大类名称修改
	{align: 'center', title: '非标准化债权类资产/新增可投资资产', colspan: 67},
	{align: 'center', title: '票据类/信用证类资产', colspan: 14},
	{align: 'center', title: '权益类资产', colspan: 11},
	{align: 'center', title: '金融衍生品', colspan: 4},
	{align: 'center', title: 'QDII债券资产', colspan: 14},
	{align: 'center', title: 'QDII拆出/逆回购资产', colspan: 6},
	{align: 'center', title: 'QDII股票/基金类资产', colspan: 5},
	{align: 'center', title: 'QDII结构性票据类资产', colspan: 17},
	{align: 'center', title: '资产管理产品', colspan: 25},
	{align: 'center', title: '贵金属/商品', colspan: 5},
	{align: 'center', title: '非标准化债权类/权益类/代客境外理财投资QDII/新增可投资资产', colspan: 30},
	{align: 'center', title: '另类资产', colspan: 6},
	{align: 'center', title: '公募基金/私募基金', colspan: 16},
	{align: 'center', title: '委外投资——协议方式', colspan: 20},
	{align: 'center', title: '其他（负债）类资产', colspan: 6},
	// 附加
	{rowspan: 2, field: 'REMARK', align: 'center', title: '备注', width: 150}
];

var assetColList2 = [
	// 公共信息（必填）
	{field: 'ASSET_CODE', align: 'center', title: '*行内资产/负债编码', width: 150},
	{field: 'ISSUE_PARTY_ID', align: 'center', title: '*发行机构代码', width: 150},
	{field: 'ASSET_TYPE2', align: 'center', title: '*资产/负债类别', width: 150},
	{field: 'TRADE_MARKET', align: 'center', title: '*交易流通场所', width: 150},
	{field: 'CCY', align: 'center', title: '*币种', width: 150},
	// 本行/他行存款||大额存单
	{field: 'BANK_NAME01', align: 'center', title: '资金存入银行', width: 150},
	{field: 'ACCOUNT_NO01', align: 'center', title: '*存款账号', width: 150},
	{field: 'AMT01', align: 'center', title: '*存款金额', width: 150},
	{field: 'VDATE01', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE01', align: 'center', title: '*到期日', width: 150},
	{field: 'ASSET_YIELD01', align: 'center', title: '*年利率%', width: 150},
	{field: 'BASIS01', align: 'center', title: '*计息基础', width: 150},
	{field: 'DEPO_TYPE01', align: 'center', title: '存款类型', width: 150},
	{field: 'TARGET_TYPE01', align: 'center', title: '结构性存款挂钩标的类别', width: 150},
	{field: 'TARGET01', align: 'center', title: '结构性存款挂钩标的', width: 150},
	// 债券类资产/理财直接融资工具/同业存单
	{field: 'ASSET_ID03', align: 'center', title: '*代码', width: 150},
	{field: 'ASSET_NAME03', align: 'center', title: '*名称', width: 150},
	{field: 'CHB_SECTYPE03', align: 'center', title: '具体类别', width: 150},
	{field: 'ISSUE_MODE03', align: 'center', title: '*发行方式', width: 150},
	{field: 'SEC_MAIN_GRADE03', align: 'center', title: '*主体评级', width: 150},
	{field: 'PARTY_TYPE103', align: 'center', title: '*发行机构类型（按规模划分）', width: 150},
	{field: 'PARTY_TYPE303', align: 'center', title: '*发行机构类型（按技术领域划分）', width: 150},
	{field: 'PARTY_TYPE203', align: 'center', title: '*发行机构类型（按经济类型划分）', width: 150},
	{field: 'INDUSTRY_LEVEL203', align: 'center', title: '*发行机构所属行业', width: 150},
	{field: 'REGIST_ORG03', align: 'center', title: '*登记托管机构', width: 150},
	{field: 'REGISTORG_COMMENT03', align: 'center', title: '登记托管机构说明', width: 150},
	// 拆放同业及买入返售/同业拆入及卖出回购
	{field: 'VDATE02', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE02', align: 'center', title: '*到期日', width: 150},
	{field: 'RIVAL_PARTY02', align: 'center', title: '*对手方', width: 150},
	{field: 'RIVAL_PARTY_TYPE02', align: 'center', title: '对手方类型', width: 150},
	{field: 'ASSET_YIELD02', align: 'center', title: '*年利率%', width: 150},
	{field: 'BASIS02', align: 'center', title: '*计息基础', width: 150},
	{field: 'TARGET_TYPE102', align: 'center', title: '回购标的类别', width: 150},
	{field: 'TARGET_AMT02', align: 'center', title: '回购标的金额', width: 150},
	//add by yjp 估值穿透开发--监管报送统一数据维护增加对应资管计划代码 start
	/*
                          {field: 'CHECK_CODE', align: 'center', title: '对应资管计划代码', width: 150},
    */
	//add by yjp 估值穿透开发--监管报送统一数据维护增加对应资管计划代码 end
	// 非标准化债权类资产/信贷资产流转项目
	{field: 'PROFIT_TYPE04', align: 'center', title: '收/受益权类型', width: 150},
	{field: 'IS_RESALE04', align: 'center', title: '*是否属于买入返售', width: 150},
	{field: 'ASSET_NAME04', align: 'center', title: '*名称', width: 150},
	{field: 'CREDIT_ASSET_CODE04', align: 'center', title: '资产代码', width: 150},
	{field: 'AMT04', align: 'center', title: '*金额', width: 150},
	{field: 'PRIN_BASEAMT04', align: 'center', title: '*份额面值', width: 150},
	{field: 'VDATE04', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE04', align: 'center', title: '*到期日', width: 150},
	{field: 'FMDATE04', align: 'center', title: '*法定到期日', width: 150},
	{field: 'RATE_FLAG04', align: 'center', title: '*是否有预期收益率', width: 150},
	{field: 'ASSET_YIELD04', align: 'center', title: '项目收益率(利率)%', width: 150},
	{field: 'COUPON_SPECIES04', align: 'center', title: '计息类型', width: 150},
	{field: 'INTEREST_RULE_FLAG04', align: 'center', title: "规则付息标识",width:150},
	{field: 'INTPAY_CYCLE04', align: 'center', title: '*付息频率（个月/次）', width: 150},
	{field: 'INTPAY_RULE04', align: 'center', title: '*利息分布方式', width: 150},
	{field: 'REPAY_CMT04', align: 'center', title: '*还本付息情况说明', width: 150},
	{field: 'BASIS04', align: 'center', title: '*计息基础', width: 150},
	{field: 'BASIS_TYPE04', align: 'center', title: '基准利率种类', width: 150},
	{field: 'FLOAT_FACTOR_FLAG04', align: 'center', title: '是否有浮动因子', width: 150},
	{field: 'FLOAT_FACTOR04', align: 'center', title: '浮动因子（%）', width: 150},
	{field: 'SPREAD_RATE04', align: 'center', title: '利差(BP)', width: 150},
	{ title: "结构档次",width:150,field: 'STRUCT_GRADE04', align: 'center'},
	{ title: "还本方式",width:150, field: 'REPAYMENT_TYPE04', align: 'center'},
	{ title: "分期还本条款标识",width:150, field: 'PERIOD_REPAYMENT_FLAG04', align: 'center'},
	{ title: "基础资产类型",width:150, field: 'BASIC_ASSET_TYPE04', align: 'center'},
	{ title: "超额收益分配比例%",width:150, field: 'EXCESS_PROFIT_RATE04', align: 'center'},
	{field: 'FINANCIE04', align: 'center', title: '*融资人', width: 150},
	{field: 'FCR_GRADE_IN04', align: 'center', title: '*融资人内部信用评级', width: 150},
	{field: 'FCR_GRADE_OUT04', align: 'center', title: '*外部评级机构名称及对融资人评级结果', width: 150},
	{field: 'FINANCIER_TYPE104', align: 'center', title: '*融资人类型（按规模划分）', width: 150},
	{field: 'FINANCIER_TYPE204', align: 'center', title: '*融资人类型（按技术领域划分）', width: 150},
	{field: 'FINANCIER_TYPE304', align: 'center', title: '*融资人类型（按经济类型划分）', width: 150},
	{field: 'FINANCE_PROJ04', align: 'center', title: '*融资项目', width: 150},
	{field: 'INDUSTRY_LEVEL2_TYPE04', align: 'center', title: '*融资人所属行业', width: 150},
	{field: 'INDUSTRY_FLAG04', align: 'center', title: '*项目是否属于重点监控行业和领域', width: 150},
	{field: 'MONIT_TYPE04', align: 'center', title: '重点监控行业和领域类别', width: 150},
	{field: 'MON_TYPE_COMM04', align: 'center', title: '重点监控行业和领域类别说明', width: 150},
	{field: 'GUA_TYPE04', align: 'center', title: '担保方式', width: 150},
	{field: 'GUA_TYPE_REMARK04', align: 'center', title: '担保情况说明', width: 150},
	{ title: "抵质押物类型",width:150,field: 'PLEDGE_TYPE04', align: 'center'},
	{ title: "抵质押物价值（元）",width:150, field: 'PLEDGE_VALUE04', align: 'center'},
	{ title: "担保性质",width:150,field: 'NATURE_GUARANTEE04', align: 'center'},
	{ title: "担保人与融资人关系",width:150, field: 'RELATION_FINAN04', align: 'center'},
	{field: 'FINANCIER_GRADE04', align: 'center', title: '*融资人主体评级', width: 150},
	{field: 'IN_GRADE04', align: 'center', title: '*资产内部评级', width: 150},
	{field: 'OUT_GRADE04', align: 'center', title: '*资产外部评级', width: 150},
	{ title: "含权类型",width:150,field: 'CONTAIN_TYPE04', align: 'center'},
	{ title: "行权方式",width:150,field: 'EXERCISE_TYPE04', align: 'center'},
	{ title: "固定行权日",width:150,field: 'EXERCISE_DATE04', align: 'center'},
	{ title: "首次行权日期",width:150, field: 'FIRST_EXERCISE_DATE04', align: 'center'},
	{ title: "行权周期",width:150, field: 'EXERCISE_PERIOD04', align: 'center'},
	{ title: "行权价格",width:150, field: 'EXERCISE_PRICE04', align: 'center'},
	{ title: "永续条款类型",width:150, field: 'PERPETUAL_CLAUSE_TYPE04', align: 'center'},
	{ title: "利息递延条款类型",width:150, field: 'DEFERRED_INTEREST_TYPE04', align: 'center'},
	{ title: "递延利息是否计息",width:150, field: 'DEFERRED_INTEREST_INT04', align: 'center'},
	{ title: "首次重定价日期",width:150, field: 'FIRST_REPRCE_DATE04', align: 'center'},
	{ title: "重定价周期",width:150, field: 'REPRCE_CYCLE04', align: 'center'},
	{ title: "部分赎回标识",width:150,field: 'PART_REDEM_FLAG04', align: 'center'},
	{ title: "部分赎回比例%",width:200,field: 'PART_REDEM_RATE04', align: 'center'},
	{field: 'CHOSE04', align: 'center', title: '*选择权', width: 150},
	{field: 'CONDITION_CMM04', align: 'center', title: '*行权条件说明', width: 150},
	{field: 'FIN_AREA04', align: 'center', title: '*融资人所属地区', width: 150},
	{field: 'FIN_ORG_CODE04', align: 'center', title: '*融资人组织机构（社会信用）代码', width: 150},
	{ title: "增信机构代码",width:150, align: 'center', field: 'CREDIT_ORG_CODE04'},
	{ title: "增信机构名称",width:150, align: 'center', field: 'CREDIT_ORG_NAME04'},
	{field: 'TOTAL_FEE_RATE04', align: 'center', title: '*融资总费率%', width: 150},
	{field: 'PRO_INDUSTRY_LEVEL204', align: 'center', title: '*融资项目所属行业', width: 150},
	// 票据类/信用证类资产
	{field: 'IS_RIGHT05', align: 'center', title: '*是否为收/受益权', width: 150},
	{field: 'IS_RESALE05', align: 'center', title: '*是否属于买入返售', width: 150},
	{field: 'NOTSTAND_SEC_TYPE05', align: 'center', title: '*类型', width: 150},
	{field: 'IS_BZHPJ', align: 'center', title: '是否标准化票据', width: 150},
	{field: 'BILL_CODE05', align: 'center', title: '票据代码', width: 150},
	{field: 'NOTSTAND_SEC_NUM05', align: 'center', title: '*数量', width: 150},
	{field: 'AMT05', align: 'center', title: '*合计金额', width: 150},
	{field: 'REMAIN_TERM05', align: 'center', title: '*加权剩余期限（天）', width: 150},
	{field: 'MAX_REMAIN_TERM05', align: 'center', title: '*最长剩余期限（天）', width: 150},
	{field: 'MIN_REMAIN_TERM05', align: 'center', title: '*最短剩余期限（天）', width: 150},
	{field: 'VDATE05', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE05', align: 'center', title: '*到期日', width: 150},
	{field: 'INDUSTRY_SHARES205', align: 'center', title: '*行业', width: 150},
	{field: 'ASSET_YIELD05', align: 'center', title: '*贴现利率（%）', width: 150},
	// 权益类资产
	{field: 'ASSET_ID06', align: 'center', title: '股票代码', width: 150},
	{field: 'ASSET_NAME06', align: 'center', title: '*股票/企业名称', width: 150},
	{field: 'SHARES_TYPE06', align: 'center', title: '股票类型', width: 150},
	{field: 'INDUSTRY_SHARES206', align: 'center', title: '*行业', width: 150},
	{field: 'INVEST_STAGE06', align: 'center', title: '投资阶段', width: 150},
	{ title: "股权退出安排",width:150, align: 'center', field: 'EQUITY_QUIT06'},
	{field: 'SHARES_TYPE106', align: 'center', title: '*企业类型（按规模划分）', width: 150},
	{field: 'SHARES_TYPE306', align: 'center', title: '*企业类型（按技术领域划分）', width: 150},
	{field: 'SHARES_TYPE206', align: 'center', title: '*企业类型（按经济类型划分）', width: 150},
	{field: 'IS_PLEDGE06', align: 'center', title: '*是否为质押融资', width: 150},
	{ title: "是否为债转股",width:150,align: 'center', field: 'DEPT_EQUITY06'},
	// 金融衍生品
	{field: 'ASSET_NAME07', align: 'center', title: '*名称', width: 150},
	{field: 'NOM_CAPI07', align: 'center', title: '*名义本金', width: 150},
	{field: 'TARGET_TYPE307', align: 'center', title: '*标的类别', width: 150},
	{field: 'HOLD_PURPOSE07', align: 'center', title: '*持有目的', width: 150},
	// QDII债券资产
	{field: 'COUNTRY_AREA08', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'ASSET_NAME08', align: 'center', title: '*债券名称', width: 150},
	{field: 'ASSET_ID08', align: 'center', title: '*债券代码', width: 150},
	{field: 'ISSUE_PARTY_NAME08', align: 'center', title: '*发行机构', width: 150},
	{field: 'INDUSTRY_LEVEL208', align: 'center', title: '*发行机构所属行业', width: 150},
	{field: 'VDATE08', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE08', align: 'center', title: '*到期日', width: 150},
	{field: 'TERM_MONTH08', align: 'center', title: '*期限（月）', width: 150},
	{field: 'PARTY_GRADE08', align: 'center', title: '*发行机构主体信用评级', width: 150},
	{field: 'SEC_CREDIT_GRADE_TYPE08', align: 'center', title: '*债券信用评级', width: 150},
	{field: 'ASSET_YIELD08', align: 'center', title: '*票面利率%', width: 150},
	{field: 'INTPAY_CYCLE08', align: 'center', title: '*付息频率（个月/次）', width: 150},
	{field: 'GUA_TYPE_REMARK08', align: 'center', title: '*担保情况说明', width: 150},
	{field: 'SPECIAL_CMT08', align: 'center', title: '*是否含权等特殊条款情况说明', width: 150},
	// QDII拆出/逆回购资产
	{field: 'COUNTRY_AREA09', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'VDATE09', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE09', align: 'center', title: '*到期日', width: 150},
	{field: 'RIVAL_PARTY09', align: 'center', title: '*对手方', width: 150},
	{field: 'ASSET_YIELD09', align: 'center', title: '*年利率%', width: 150},
	{field: 'BASIS09', align: 'center', title: '*计息基础', width: 150},
	// QDII股票/基金类资产
	{field: 'COUNTRY_AREA10', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'ASSET_ID10', align: 'center', title: '*股票/基金代码', width: 150},
	{field: 'ASSET_NAME10', align: 'center', title: '*股票/基金名称', width: 150},
	{field: 'ISSUE_PARTY_NAME10', align: 'center', title: '*发行机构', width: 150},
	{field: 'INDUSTRY_SHARES210', align: 'center', title: '*行业', width: 150},
	// QDII结构性票据类资产
	{field: 'COUNTRY_AREA11', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'CONTRACT_NAME11', align: 'center', title: '*合约名称', width: 150},
	{field: 'VDATE11', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE11', align: 'center', title: '*到期	日', width: 150},
	{field: 'ASSET_YIELD11', align: 'center', title: '票面利率%', width: 150},
	{field: 'INTPAY_CYCLE11', align: 'center', title: '付息频率（个月/次）', width: 150},
	{field: 'INCOME_RATIO11', align: 'center', title: '*固定收益部分所占比例(%)', width: 150},
	{field: 'DER_INSTR_RATIO11', align: 'center', title: '*衍生金融工具所占比例(%)', width: 150},
	{field: 'DER_INSTR_INV_TYPE11', align: 'center', title: '*衍生金融工具具体投资方式', width: 150},
	{field: 'DER_INSTR_TARGET11', align: 'center', title: '*衍生金融工具挂钩的标的资产', width: 150},
	{field: 'ACCT_MODE11', align: 'center', title: '*利息结算方式', width: 150},
	{field: 'SPECIAL_CMT111', align: 'center', title: '*含权情况说明', width: 150},
	{field: 'MAX_YIELD11', align: 'center', title: '*结构性票据最高收益率%', width: 150},
	{field: 'MIN_YIELD11', align: 'center', title: '结构性票据最低收益率%', width: 150},
	{field: 'BASIC_PRICE11', align: 'center', title: '挂钩标的资产基准价格', width: 150},
	{field: 'REG_DATE_PRICE11', align: 'center', title: '*挂钩标的资产登记日价格', width: 150},
	{field: 'TRADE_FEE11', align: 'center', title: '*交易费', width: 150},
	// 资产管理产品
	{field: 'ASSET_NAME12', align: 'center', title: '*资管计划名称', width: 150},
	{field: 'FINANCIAL_PRODUCT_FLAG12', align: 'center', title: '是否为银行理财产品', width: 150},
	{field: 'PROD_REGISTER_CODE12', align: 'center', title: '产品登记编码', width: 150},
	{title: "是否由金融资产投资公司发行",width:250,align: 'center', field: 'FINANCIAL_COMISSUE12'},
	{field: 'PARTY_CODE12', align: 'center', title: '*资管计划发起人机构编码', width: 150},
	{field: 'CHECK_CODE12', align: 'center', title: '资管计划登记编码', width: 150},
	{field: 'MANAGER_ID12', align: 'center', title: '*管理人', width: 150},
	{field: 'TRUSTEE_ID12', align: 'center', title: '*托管人', width: 150},
	{field: 'AMT12', align: 'center', title: '*金额', width: 150},
	{field: 'TRU_ACT_TYPE12', align: 'center', title: '*资金实际投向', width: 150},
	{field: 'INVEST_TYPE12', align: 'center', title: '*资金运用方式', width: 150},
	{field: 'CAP_USE_INDU_LEVEL212', align: 'center', title: '*资金运用行业', width: 150},
	{field: 'PLAN_SDATE12', align: 'center', title: '*资管计划成立日期', width: 150},
	{field: 'PLAN_EDATE12', align: 'center', title: '*资管计划终止日期', width: 150},
	{field: 'PRO_PROPERTY12', align: 'center', title: '*资管计划属性', width: 150},
	{field: 'RATE_FLAG12', align: 'center', title: '*是否有预期收益率', width: 150},
	{field: 'MAX_RATE12', align: 'center', title: '预期最高收益率%', width: 150},
	{field: 'MIN_RATE12', align: 'center', title: '预期最低收益率%', width: 150},
	{field: 'BUY_STRUCT12', align: 'center', title: '*购买结构', width: 150},
	{field: 'MANAGE_TYPE12', align: 'center', title: '*管理方式', width: 150},
//                  	{field: 'IS_CTRL_BOTTOM12', align: 'center', title: '*是否掌控计划底层资产详情', width: 150},
	{field: 'MGR_FEE_RATE12', align: 'center', title: '*管理费率%', width: 150},
	{field: 'TRU_FEE_RATE12', align: 'center', title: '*托管费率%', width: 150},
	{field: 'TRADE_FEE_RATE12', align: 'center', title: '*交易相关合计费率%', width: 150},
	{field: 'AGENCY_FEE_RATE12', align: 'center', title: '*中介服务机构合计费率%', width: 150},
	{field: 'OTHER_FEE_RATE12', align: 'center', title: '*其他合计费率%', width: 150},
	// 贵金属/商品
	{field: 'COUNTRY_AREA13', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'ASSET_NAME13', align: 'center', title: '*名称', width: 150},
	{field: 'TERM_DATE13', align: 'center', title: '期限（天）', width: 150},
	{field: 'ASSET_VALUE13', align: 'center', title: '*资产价值', width: 150},
	{field: 'ASSET_YIELD13', align: 'center', title: '资产收益率%', width: 150},
	// 非标准化债权类/权益类/代客境外理财投资QDII/新增可投资资产
	{field: 'ASSET_NAME14', align: 'center', title: '*名称', width: 150},
	{field: 'IN_ASSET_TYPE14', align: 'center', title: '*行内资产类别', width: 150},
	{field: 'IN_ASSET_TYPE_CMM14', align: 'center', title: '行内资产类别说明', width: 150},
	{field: 'AMT14', align: 'center', title: '*金额', width: 150},
	{field: 'VDATE14', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE14', align: 'center', title: '*到期日', width: 150},
	{field: 'COUNTRY_AREA14', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'RATE_FLAG14', align: 'center', title: '*是否有预期收益率', width: 150},
	{field: 'ASSET_YIELD14', align: 'center', title: '项目收益率（利率）%', width: 150},
	{field: 'INTPAY_CYCLE14', align: 'center', title: '*付息频率（个月/次）', width: 150},
	{field: 'FINANCIE14', align: 'center', title: '*融资人', width: 150},
	{field: 'FIN_ORG_CODE14', align: 'center', title: '*融资人组织机构（社会信用）代码', width: 150},
	{field: 'FCR_GRADE_OUT14', align: 'center', title: '外部评级机构名称及对融资人评级结果', width: 150},
	{field: 'FINANCIER_TYPE114', align: 'center', title: '*融资人类型（按规模划分）', width: 150},
	{field: 'FINANCIER_TYPE214', align: 'center', title: '*融资人类型（按技术领域划分）', width: 150},
	{field: 'FINANCIER_TYPE314', align: 'center', title: '*融资人类型（按经济类型划分）', width: 150},
	{field: 'FINANCE_PROJ14', align: 'center', title: '*融资项目', width: 150},
	{field: 'INDUSTRY_LEVEL2_TYPE14', align: 'center', title: '*融资人所属行业', width: 150},
	{field: 'PRO_INDUSTRY_LEVEL214', align: 'center', title: '*融资项目所属行业', width: 150},
	{field: 'INDUSTRY_FLAG14', align: 'center', title: '*项目是否属于重点监控行业和领域', width: 150},
	{field: 'MONIT_TYPE14', align: 'center', title: '重点监控行业和领域类别', width: 150},
	{field: 'MON_TYPE_COMM14', align: 'center', title: '重点监控行业和领域类别说明', width: 150},
	{field: 'OUT_GRADE14', align: 'center', title: '对应资产外部评级', width: 150},
	{ title: "担保方式",width:150,align: 'center', field: 'GUA_TYPE14'},
	{ title: "担保情况说明",width:150,align: 'center', field: 'GUA_TYPE_REMARK14'},
	{ title: "抵质押物类型",width:150,align: 'center', field: 'PLEDGE_TYPE14'},
	{ title: "抵质押物价值（元）",width:150,align: 'center', field: 'PLEDGE_VALUE14'},
	{ title: "担保性质",width:150,align: 'center', field: 'NATURE_GUARANTEE14'},
	{ title: "担保人与融资人关系",width:150,align: 'center', field: 'RELATION_FINAN14'},
	{ title: "是否为债转股",width:150,align: 'center', field: 'DEPT_EQUITY14'},

	// 另类资产
	{field: 'COUNTRY_AREA18', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'ASSET_NAME18', align: 'center', title: '*名称', width: 150},
	{field: 'VDATE18', align: 'center', title: '*起息日', width: 150},
	{field: 'MDATE18', align: 'center', title: '*到期日', width: 150},
	{field: 'ASSET_VALUE18', align: 'center', title: '*资产价值', width: 150},
	{field: 'ASSET_YIELD18', align: 'center', title: '资产收益率%', width: 150},
	// 公募基金/私募基金
	{field: 'ASSET_ID15', align: 'center', title: '*基金代码', width: 150},
	{field: 'ASSET_NAME15', align: 'center', title: '*基金名称', width: 150},
	{title: "是否由金融资产投资公司发行",width:250,align: 'center', field: 'FINANCIAL_COMISSUE15'},
	{field: 'INDUSTRY_SHARES215', align: 'center', title: '*行业', width: 150},
	{field: 'REGIST_ORG115', align: 'center', title: '*登记备案机构', width: 150},
	{field: 'PROFIT_FLAG15', align: 'center', title: '是否为固定收益类', width: 150},
	{field: 'IS_GOVERN_FUND15', align: 'center', title: '是否属于政府投资基金', width: 150},
	{field: 'GOVERN_INVEST_FUND15', align: 'center', title: '政府投资基金投向', width: 150},
	{field: 'FUND_ENPE_NAME15', align: 'center', title: '基金公司名称', width: 150},
	{field: 'MNG_ORG_NAME15', align: 'center', title: '*基金管理机构名称', width: 150},
	{field: 'TRU_ORG_NAME15', align: 'center', title: '*基金托管机构名称', width: 150},
	{field: 'INVEST_STAGE15', align: 'center', title: '投资阶段', width: 150},
	{field: 'SHARES_TYPE115', align: 'center', title: '投资企业类型（按规模划分）', width: 150},
	{field: 'SHARES_TYPE315', align: 'center', title: '投资企业类型（按技术领域划分）', width: 150},
	{field: 'SHARES_TYPE215', align: 'center', title: '投资企业类型（按经济类型划分）', width: 150},
	{field: 'FUND_INVEST_ASSET15', align: 'center', title: '*基金投资资产', width: 150},
	// 委外投资——协议方式
	{field: 'ASSET_NAME16', align: 'center', title: '委外投资协议名称', width: 150},
	{field: 'ASSET_ID16', align: 'center', title: '委外投资协议编号', width: 150},
	{field: 'TRUSTEE16', align: 'center', title: '受托人', width: 150},
	{field: 'MANAGER_ID16', align: 'center', title: '实际管理人', width: 150},
	{field: 'TRUSTEE_ID16', align: 'center', title: '托管人', width: 150},
	{field: 'AMT16', align: 'center', title: '委托投资金额', width: 150},
	{field: 'TRU_ACT_TYPE16', align: 'center', title: '资金实际投向', width: 150},
	{field: 'INVEST_TYPE16', align: 'center', title: '资金运用方式', width: 150},
	{field: 'CAP_USE_INDU_LEVEL216', align: 'center', title: '资金运用行业', width: 150},
	{field: 'PLAN_SDATE16', align: 'center', title: '投资运作起始日期', width: 150},
	{field: 'PLAN_EDATE16', align: 'center', title: '投资运作终止日期', width: 150},
	{field: 'PRO_PROPERTY16', align: 'center', title: '委外投资属性', width: 150},
	{field: 'RATE_FLAG16', align: 'center', title: '是否有预期收益率', width: 150},
	{field: 'MAX_RATE16', align: 'center', title: '预期最高收益率', width: 150},
	{field: 'MIN_RATE16', align: 'center', title: '预期最低收益率', width: 150},
	{field: 'MGR_FEE_RATE16', align: 'center', title: '管理费率%', width: 150},
	{field: 'TRU_FEE_RATE16', align: 'center', title: '托管费率%', width: 150},
	{field: 'TRADE_FEE_RATE16', align: 'center', title: '交易相关合计费率%', width: 150},
	{field: 'AGENCY_FEE_RATE16', align: 'center', title: '中介服务机构合计费率%', width: 150},
	{field: 'OTHER_FEE_RATE16', align: 'center', title: '其他合计费率%', width: 150},
	// 其他（负债）类资产
	{field: 'COUNTRY_AREA17', align: 'center', title: '*所属国家或地区', width: 150},
	{field: 'ASSET_NAME17', align: 'center', title: '*名称', width: 150},
	{field: 'TERM_DATE17', align: 'center', title: '期限（天）', width: 150},
	{field: 'DEBT_SCALE17', align: 'center', title: '*负债规模', width: 150},
	{field: 'ASSET_YIELD17', align: 'center', title: '利率%', width: 150},
	{field: 'IS_DEPOSIT_FLAG17', align: 'center', title: '*是否为同业借款', width: 150},

];

var colList = [];
var headerNum='0';
var tableName='';
// 左侧列表主键:方案Id --单击时使用
var s_id = null;
var indexName = null;
var allLoadFalg = false;
// 右侧表单数据是否加载过
var allLoadFalg = false;

var getSearchParams = function() {
	var columnId = getColumnParams();
	return {
		tableId : $('#baseTableName').val(),
		columnIds : columnId
	}
};

var getColumnParams = function() {
	var tableId = $('#baseTableName').val();
	var colunmList = '';
	$
		.ajax({
			url : basePath
				+ "common/infomanagement/SvrOperationalInfo001/queryColumnTypeByTableId",
			async : false,
			data : {
				tableId : tableId
			},
			success : function(result) {
				var jsonObj = eval("(" + result + ")");
				for ( var key in jsonObj) {
					var columnInfo = key.split(",");
					var columnId = tableId + "-" + columnInfo[0] + "-"
						+ jsonObj[key];
					if ($("#" + columnId).length > 0) {
						var obj = '';
						if (jsonObj[key] == 'T') {
							obj = columnInfo[0] + "+" + "T" + "+"
								+ $("#" + columnId).val();
						} else if (jsonObj[key] == 'S') {
							var sdata = $("#" + columnId).val();
							if (sdata != null) {
								obj = columnInfo[0] + "+" + "S" + "+"
									+ sdata.join(",");
							}

						} else if (jsonObj[key] == 'D') {
							var dates = $("#" + columnId).val();
							if (!(typeof (dates) == "undefined"
								|| dates == null || dates == "")) {
								var dateList = dates.split(" - ");
								obj = columnInfo[0] + "+" + "D" + "+"
									+ dateList[0] + ","
									+ dateList[1];
							}
						}
						colunmList = colunmList + obj + ";";
					}
				}

			}
		});
	return colunmList;
};

//var table = $("#dataTable").joyintable({
//	ajax : {
//		url : 'findPage',
//		type : 'POST',
//		traditional : true,
//		data : function() {
//			return getSearchParams();
//		},
//	},
//	cols : [ colList ],
////		toolbar: true,
////		defaultToolbar: ['filter'],
//	page : true
//});

// 已选择字段列表
var selectedColumns = [];

function getColumns() {
	var tableId = $('#baseTableName').val();
	//根据ID获取表名
	$("#columnType").empty();
	$("#resultColumn").empty();
	$("#selectedColumns").empty();
	selectedColumns=[];
	if (typeof (tableId) == "undefined" || tableId == null || tableId == "") {
		$("#columnType").empty();
		$("#resultColumn").empty();
		$("#selectedColumns").hide();
		$('#selectedColumns').html("");
		selectedColumns = [];
//			$("#dataTable").hide();
//			return;
	}


	var table = layui.table;
	var cols = [];
	var i = 1;
	// $("#dataTable").show();
	Joyin
		.ajax(
			basePath
			+ "common/infomanagement/SvrOperationalInfo001/queryColumnByTableId",
			{
				tableId : tableId
			}, function(result) {


				if (result.lenght == 0) {
					return;
				}
				var jsonObj = eval("(" + result + ")");
				var initData = [];
				for ( var key in jsonObj) {
					var curData = {"id": key, "text": jsonObj[key]};
					initData.push(curData);

				}
				$("#columnType").select2({
					placeholder: '请选择字段',
					multiple: true,
					data: initData,
					allowClear:true,
					language: 'zh-CN'
				});

				if(isFromMain == '01'){
					var valueList = [];
					valueList.push('UPDATE_TIME');
					valueList.push('INTERF_TYPE');
					$("#columnType").val(valueList).trigger('change');
				}

			});

	// 资产要素表特殊处理
	/*if (tableId == "CHB_ASSET_INFO" ) {
        layui.use([ 'table' ], function() {
            var table = layui.table;
            table.render({
                elem : '#dataTable',
                cols : [assetColList1,
                        assetColList2]
            });
        });
        return;
    }*/
	$.ajax({
        url : basePath
            + "common/infomanagement/SvrOperationalInfo001/queryColmnInfo",
        async : false,
        type : 'POST',
        traditional : true,
        data : {
            tableId : tableId,
        },
        success : function(result) {
        	var resultData=eval("(" + result + ")");
        	headerNum=resultData["headerNum"];
        	tableName=resultData["tableName"];
            var jsonObj = JSON.parse(resultData["data"]);
            cols[0] = {
                type : 'checkbox',
                fixed : 'left'
            };
            var resultData = [];
            assetColList1.splice(21);
            for ( var key in jsonObj) {
                cols[i] = {
                    field : key,
                    title : jsonObj[key],
                    width : 150
                };
                var col = {
                    rowspan: 2,
                    field : key,
                    title : jsonObj[key],
                    width : 150
                }
                i++;
                var curData = {"id": key, "text": jsonObj[key]};
                resultData.push(curData);
                assetColList1.push(col);
            }
            $("#resultColumn").select2({
                placeholder: '请选择字段',
                multiple: true,
                data: resultData,
                allowClear:true,
            });
            if (headerNum == "2") {
                layui.use([ 'table' ], function() {
                    var table = layui.table;
                    table.render({
                        elem : '#dataTable',
                        cols : [assetColList1,
                            assetColList2]
                    });
                });

            } else {
                colList = cols;
                layui.use([ 'table' ], function() {
                    var table = layui.table;
                    table.render({
                        elem : '#dataTable',
                        cols : [ cols ]
                    });
                });
            }

        }

    });



}



function showSelectColumns() {
	var tableId = $('#baseTableName').val();
	var columnIds = $('#columnType').val();
	if (typeof (columnIds) == "undefined" || columnIds == null
		|| columnIds == "") {
		$("#selectedColumns").hide();
		$('#selectedColumns').html("");
		selectedColumns = [];
		return;
	};
	// 当前需要操作字段
	var curColumns = [];
	// 更新时间默认值
	var defaultDate = isFromMain=='01'?(searchDataStart+' - '+searchDataEnd):'';
	// 当前操作字段名称
	var curColumnName;
	// 新增选项
	if (columnIds.length > selectedColumns.length) {
		for (var i = 0; i < columnIds.length; i ++) {
			if (selectedColumns.indexOf(columnIds[i]) == -1) {
				curColumns.push(columnIds[i]);
			}
		}
		for (var i = 0; i < curColumns.length; i ++) {
			var	curColumn = curColumns[i];
			$.ajax({
				url : basePath + "common/infomanagement/SvrOperationalInfo001/queryColumnNameByCondition",
				async : false,
				data : {
					tableId : tableId,
					curColumn : curColumn
				},
				success : function(result) {
					curColumnName = result;
				}
			});

			$.ajax({
				url : basePath + "common/infomanagement/SvrOperationalInfo001/queryColumnTypeByCondition",
				async : false,
				data : {
					tableId : tableId,
					curColumn : curColumn
				},
				success : function(result) {
					$("#selectedColumns").show();
					if (result == 'T') {
						$("#selectedColumns")
							.append(
								"<div class=\"layui-inline layui-col-md4\" id=\""
								+ tableId
								+ "-"
								+ curColumn
								+	"\">"
								+ " <label class=\"layui-form-label form-lable\">"
								+ curColumnName
								+ "</label><div class=\"layui-input-inline input-inline\">"
								+ "<input id=\""
								+ tableId
								+ "-"
								+ curColumn
								+ "-T"
								+ "\" class=\"layui-input form-input input-lenovoselect\" >"
								+ "</div>");
					} else if (result == 'S') {
						var colunmInofList = queryColumnInfoList(
							tableId, curColumn);
						var content = "<div class=\"layui-inline layui-col-md4\" id=\""
							+ tableId
							+ "-"
							+ curColumn
							+	"\">"
							+ "<label class=\"layui-form-label form-lable\">"
							+ curColumnName
							+ "</label>"
							+ "<div class=\"layui-input-inline input-inline\">"
							+ "<select type=\"text\" id=\""
							+ tableId
							+ "-"
							+ curColumn
							+ "-S"
							+ "\" name=\"columnType\""
							+ " class=\"layui-input form-input input-select\" "
							+ "allowClear=\"true\" multiple=\"multiple\">"
						for ( var key in colunmInofList) {
							content += "<option value='" + key
								+ "'>" + colunmInofList[key]
								+ "</option>"
						}
						content += "</select></div></div>"
						$("#selectedColumns").append(content);
						$("#selectedColumns").find('select')
							.textSelect();
					} else if (result == 'D') {
						$("#selectedColumns")
							.append(
								"<div class=\"layui-inline layui-col-md4\" id=\""
								+ tableId
								+ "-"
								+ curColumn
								+	"\">"
								+ "<label class=\"layui-form-label form-lable\">"
								+ curColumnName
								+ "</label>"
								+ "<div class=\"layui-input-inline input-inline\" id=\"test1\">"
								+ "<input type=\"text\"  name=\"timeControl\"  "
								+ "class=\"layui-input form-input input-dateRangeSelect\" id=\""
								+ tableId
								+ "-"
								+ curColumn
								+ "-D"
								+ "\" placeholder=\"请选择日期区间\" />"
								// + "</div>"
								+ "<div class=\"time-icon\"><i class=\"iconfont icon-date\"></i></div></div>");
						var laydate = layui.laydate;
						laydate.render({
							elem : $("#" + tableId + "-"
								+ curColumn + "-D")[0],
							range : true,
							value:defaultDate // 设置默认值
						});
					}
				}
			});
		}
		selectedColumns = columnIds;
		// 设置接口类型查询框默认值
		$('#CHB_INTERF_ERRODATA-INTERF_TYPE-T').val(interfType==null?'':interfType);
		// 触发查询按钮
		if(isFromMain=='01'){
			$('#SvrOperationalInfo001_btn_search').trigger('click');
		}
		//$('#SvrOperationalInfo001_btn_search').trigger('click')
	};
	// 删除选项
	if (selectedColumns.length > columnIds.length) {
		for (var i = 0; i < selectedColumns.length; i ++) {
			if (columnIds.indexOf(selectedColumns[i]) == -1) {
				$("#" + tableId + "-" + selectedColumns[i]).remove();
			}
		}
		selectedColumns = columnIds;
	}
}

$(function() {
	// 查询按钮事件
	$('#SvrOperationalInfo001_btn_search').click(function() {
		// table.load();
		showTables();
	});
});

function showTables() {
	var h = $("#selectedColumns").outerHeight(true);
	var divheight = 150 + h;
	var tableId = $('#baseTableName').val();
	if (typeof (tableId) == "undefined" || tableId == null || tableId == "") {
		layer.alert('请选择数据源表', {
			icon : 2,
			skin : 'layer-ext-moon'
		});
		return;
	}
	;
	var columnId = getColumnParams();
	var table = layui.table;
	//从首页进入错误数据默认不查询
	if(!columnId && isFromMain=='01' && $('#baseTableName').val() == 'CHB_INTERF_ERRODATA' ){
		return;
	}
	// 资产要素表特殊处理
	if (headerNum == "2") {
		layui.use(
			[ 'table' ],
			function() {
				var table = layui.table;
				table
					.render({
						elem : '#dataTable',
						type : 'POST',
						where : {
							tableId : $('#baseTableName').val(),
							columnIds : columnId
						},
						url : basePath
							+ "common/infomanagement/SvrOperationalInfo001/findPage",
						page : true,
						limits: [20,100,200,300,400,500,600,700,800,900],
						limit: 20,
						height : 'full-' + divheight,
						cols : [assetColList1,
						        assetColList2]
					});
			});
		return;

	}

	layui.use(
		[ 'table' ],
		function() {
			var table = layui.table;
			table
				.render({
					elem : '#dataTable',
					type : 'POST',
					where : {
						tableId : $('#baseTableName').val(),
						columnIds : columnId
					},
					url : basePath
						+ "common/infomanagement/SvrOperationalInfo001/findPage",
					page : true,
					limits: [20,100,200,300,400,500,600,700,800,900],
					limit: 20,
					height : 'full-' + divheight,
					cols : [ colList ]
				});
		});
}

var queryColumnInfoList = function(tableId, columnId) {
	var colunmList;
	$
		.ajax({
			url : basePath
				+ "common/infomanagement/SvrOperationalInfo001/queryColumnInfoList",
			async : false,
			data : {
				tableId : tableId,
				columnId : columnId
			},
			success : function(result) {
				colunmList = eval("(" + result + ")");

			}
		});
	return colunmList;
}

$(function() {
	// 保存按钮事件
	$('#SvrOperationalInfo001_btn_save')
		.click(
			function() {
				layer
					.open({
						type : 1,
						title : '请输入方案名称',
						area : [ '500px', '155px' ],
						content : '<div id="mydiv"  style="margin-left:35px;margin-top:25px;"><textarea id="planName" style="width:430px;height:25px;resize:none;border-radius:6px;" ></textarea></div>',
						btn : [ '完成', '取消' ],
						yes : function(index) {
							var planName = $('#planName').val();
							var tableId = $('#baseTableName')
								.val();
							savePlan(planName, tableId, index, null);
						}
					})
			});

	// 导出按钮点击事件
	$('#SvrOperationalInfo001_btn_export').click(
		function() {
			var table = layui.table;
			var checkStatus = table.checkStatus('dataTable');
			var selectedData = checkStatus.data;
			/**导出报错的特殊处理  add by  dongxiangyang  20201023*/
			for(var i=0;i<selectedData.length;i++){
				delete selectedData[i].ERR_MSG;
				if(selectedData[i].CUR_ASSETDET_TYPE=="'"){
					delete selectedData[i].CUR_ASSETDET_TYPE;
					}
				//add by wm 针对特殊字符处理  jason 识别“'”字符时转换会有问题，将企转换为“&apos;” start
				if(tableName == "CHB_INTERF_ERRODATA"&&selectedData[i].ORG_DATA.indexOf("'") != -1){
					selectedData[i].ORG_DATA = selectedData[i].ORG_DATA.replace(new RegExp("'", 'gm'),"&apos;");
				}
				//add by wm 针对特殊字符处理  jason 识别“'”字符时转换会有问题，将企转换为“&apos;” end
			}
			/**导出报错的特殊处理  add by  dongxiangyang  20201023*/
			var jasonData = JSON.stringify(selectedData);
			var tableId = $('#baseTableName').val();
			var columnIds = getColumnParams();
			var param = {};
			param.headerNum=headerNum;
			param.tableId = tableId;
			param.tableName = tableName;
			param.columnIds = columnIds;
			param.jasonData = jasonData;
			if (typeof (tableId) == "undefined" || tableId == null
				|| tableId == "") {
				layer.alert('请选择数据源表', {
					icon : 2,
					skin : 'layer-ext-moon'
				});
				return;
			};
			/*if (tableName == "CHB_ASSET_INFO"&& headerNum!='1') {
				// 资产要素表双表头,必须指定导出列
				if (jasonData == "[]") {
					layer.alert('资产要素表请先查询再勾选导出！', {
						icon : 2,
						skin : 'layer-ext-moon'
					});
					return;
				}
			}*/

			if (tableName == "CHB_INTERF_ERRODATA") {
				var searchFlag = false;
				if(columnIds){
					var columnIdError = columnIds.split(";");
					for(var i=0;i<columnIdError.length;i++){
						var columns = columnIdError[i].split("+");
						if(columns.length==3 &&columns[0]=='INTERF_TYPE'&&columns[2]){
							searchFlag = true;
							param.serachInterf = columns[2];
							break;
						}
					}
				}
				// 如果是错误信息表,必须指定导出列
				if (jasonData == "[]"&&!searchFlag) {
					layer.alert('请选择列表或者添加接口类型进行导出！', {
						icon : 2,
						skin : 'layer-ext-moon'
					});
					return;
				}
				// 错误信息导出时,一次性只能导出一张业务信息表的数据
				Joyin.ajax('checkIsSingleTable', param, function(result) {
					if (result) {
						if(searchFlag){
							layer.alert('请填写正确的接口类型', {
								icon : 2,
								skin : 'layer-ext-moon'
							});
						}
						else{
							layer.alert('一次性只能导出一张业务信息表的数据', {
								icon : 2,
								skin : 'layer-ext-moon'
							});
						}
					} else {
						Joyin.exportExcel('export', param);
					}
				});

			} else {
				//对于jasondata进行转码
				Joyin.exportExcel('export', param);
			}

		});

	// 导入按钮点击事件
		$('#SvrOperationalInfo001_btn_import').click(
				function() {
					Joyin.confirm(JoyinUtil.getMessage('CHB_ISIMPORT'), function() {
						Joyin.uploadFile({
							hideTpl : true
					       }, function(fileName) {
							Joyin.ajax('checkConfirmMsgForImportData', {fileName : fileName}, function(result) {
								if (result.length == 0) {
									Joyin.ajax('importData', {fileName : fileName}, function(){
										//Joyin.ajax('linkSvrReport', {fileName : fileName});
									});
								}

							});
						}, function() {
							table.reload();
						});
					});
				});


	// 删除按钮点击事件
	$('#SvrOperationalInfo001_btn_delete').click(
		function() {
			var table = layui.table;
			var checkStatus = table.checkStatus('dataTable');
			var selectedData = checkStatus.data;
			var jasonData = JSON.stringify(selectedData);
			if (typeof (jasonData) == "undefined" || jasonData == null
				|| jasonData == "" || jasonData == "[]") {
				layer.alert('请选择需要删除的数据', {
					icon : 2,
					skin : 'layer-ext-moon'
				});
				return;
			}
			;
			var tableId = $('#baseTableName').val();
			var param = {};
			param.tableId = tableId;
			param.jasonData = jasonData;
			Joyin.confirm("确认删除选定数据？", function() {
				Joyin.ajax('deleteData', {
					jasonData : jasonData,
					tableId : tableId,
					tableName:tableName
				}, function(result) {
					if (result > 0) {
						layer.alert('成功删除' + result + '条数据', {
							icon : 1,
							skin : 'layer-ext-moon'
						});
						showTables();
//						Joyin.ajax('linkSvrReportAfterDelete', {
//							jasonData : jasonData,
//							tableName : tableName
//						}, function(result) {
//
//						});
					} else {
						layer.alert('没有删除数据', {
							icon : 2,
							skin : 'layer-ext-moon'
						});
					}
				});
			});
		});

});

function savePlan(planName, tableId, index, filterId) {
	if (typeof (planName) == "undefined" || planName == null
		|| planName == "") {
		layer.alert('方案名称不能为空', {
			icon : 2,
			skin : 'layer-ext-moon'
		});
		return;
	}
	;

	var param = getSaveParam();
	// 查询方案名称是否已经存在
	Joyin.ajaxJson("checkSave", param, function(result) {
		// 已经存在,提示方案名已存在
		if (result) {
			layer.alert('方案名已存在', {
				icon : 2,
				skin : 'layer-ext-moon'
			});
			return;
		} else {
			// 保存方案
			if (filterId == null) {
				Joyin.ajaxJson("savePlan", param, function(result) {
					if (result) {
						layer.alert('保存成功', {
							icon : 1,
							skin : 'layer-ext-moon',
							yes : function() {
								layer.close(index);
								location.reload();
							}
//								closeBtn: 0
						});

					} else {
						layer.alert('保存失败', {
							icon : 2,
							skin : 'layer-ext-moon',
							yes : function() {
								layer.close(index);
								location.reload();
							}
						});
					}
				});
				// 修改方案名称
			} else {
				param.filterId = filterId;
				Joyin.ajaxJson("updatePlanName", param, function(result) {
					if (result) {
						layer.alert('修改成功', {
							icon : 1,
							skin : 'layer-ext-moon',
							yes : function() {
								layer.close(index);
								location.reload();
							}
						});
					} else {
						layer.alert('修改失败', {
							icon : 2,
							skin : 'layer-ext-moon',
							yes : function() {
								layer.close(index);
								location.reload();
							}
						});
					}
				});
			}

		}
	})

}

var getSaveParam = function() {
	return {
		planName : $('#planName').val(),
		tableId : $('#baseTableName').val(),
		columns : $('#columnType').val()
	}
}

function setData(filterId) {
	var valueList = [];
	var initData = [];
	$("#columnType").val(valueList).trigger("change");
	$.ajax({
		url : basePath
			+ "common/infomanagement/SvrOperationalInfo001/queryPlanByfilterId",
		async : false,
		data : {
			filterId : filterId
		},
		success : function(result) {
			colunmList = eval("(" + result + ")");
			var tableId = colunmList['tableName'];
			// 数据源表已保存配置
			$('#baseTableName').val(tableId).trigger("change");
			for ( var key in colunmList) {
				if ("tableName" != key) {
//						var curData = {"id": key, "text": colunmList[key]};
					valueList.push(key);
				}
			}
			$.ajax({
				url : basePath + "common/infomanagement/SvrOperationalInfo001/queryColumnByTableId",
				async : false,
				data : {
					tableId : tableId
				},
				success : function(result) {
					$("#columnType").empty();
					var jsonObj = eval("(" + result + ")");
					for ( var key in jsonObj) {
						var curData = {"id": key, "text": jsonObj[key]};
						initData.push(curData);
					}
				}

			});
			$("#columnType").select2({
//					 tags: true,
				placeholder: '请选择字段',
				multiple: true,
				data: initData,
				allowClear:true
			});
			$("#columnType").val(valueList).trigger("change");
//				s_id = null;
		}
	});


}

function deleteFilter(filterId) {
	Joyin.confirm("确认删除选定方案？", function() {
		Joyin.ajax('deleteFilter', {
			filterId : filterId
		}, function(result) {
			layer.alert('成功删除方案:' + result, {
				icon : 1,
				skin : 'layer-ext-moon',
				yes : function() {
					location.reload();
				}
			});
		});
	});
}

/**
 * 页面加载完后执行
 */
$(function() {
	indexIdTable.load();//加载左侧方案列表
	indexBind();
	var interfType = $("#interfType").val();
	if (typeof (interfType) == "undefined" || interfType == null || interfType == "") {
		return;
	}
	$('#baseTableName').val(interfType).trigger("change");
	showTables();

});

// 搜索查询
var indexBind = function() {
	$(".iconinput").click(function(){
		indexIdTable.reload();//加载左侧名称列表
		s_id = null;// 重置选择记录
	});
}

var getSearchKey = function() {
	return {
		searchKey: $('#searchKey').val()
	}
};
/**
 * 左侧方案列表加载事件
 */
var indexIdTable=$("#indexIdTable").joyintable({
	ajax: {
		url: 'queryFilterInfoByUserId',
		type: 'POST',
		data: function() {
			return getSearchKey();
		}
	},
	searchOnInit: false,
	cellMinWidth:100 ,
	click:function(table, data){
		$("#selectedColumns").html("");
		setData(data.filterId);
//			setColumn(data.filterId);
		var index=data.LAY_TABLE_INDEX;
		var tt=$('#indexIdTable').next().children(".layui-table-box").find(".layui-table-body").eq(0);
		tt.find("tr").each(function(i){
			$(this).find('td[data-field="filterName"]').each(function(){  // 遍历 tr 的各个 td
				var _text=$(this).text();
				if(index==i){
					$(this).children("div").children("div").remove();
					$(this).children("div").append("<div style='float:right'><i class='iconfont icon-edit scheme' ></i>" +
						"  <i class='iconfont icon-delete scheme' ></i>" );
				}else{
					$(this).children("div").children("div").remove();
				}
			});
		});
		// 修改按钮
		$(".scheme.icon-edit").click(function (event){
			layer.open({
				type : 1,
				title : '请输入方案名称',
				area : [ '500px', '155px' ],
				content : '<div id="mydiv"  style="margin-left:35px;margin-top:25px;"><textarea id="planName" style="width:430px;height:25px;resize:none;border-radius:6px;" ></textarea></div>',
				btn : [ '完成', '取消' ],
				yes : function(index) {
					var planName = $('#planName').val();
					var tableId = $('#baseTableName').val();
					var filterId = data.filterId;
					savePlan(planName, tableId, index, filterId);
					s_id = null;
				}
			})
		});
		// 删除按钮
		$(".scheme.icon-delete").click(function (event){
			var filterId = data.filterId;
			deleteFilter(filterId);
		});
	},
	checkbox:false,
	cols: [
		{field:'filterName', title: '方案名称', sortable: false,},
		{field:'filterId', title: '方案id', sortable: false,visible:false}
	],
	done:function(){
		$('#indexIdTable').next().find("thead").remove();// 删除表头
	}
});

/**
 * 数据源边坡配置事件
 * 跳转至数据源表页面
 */
$('#SvrOperationalInfo001_btn_resource').click(function() {
	var paramObj = {
		pageModel : PAGE_MODEL_NEW
	}
	var pageUrl = basePath + "common/infomanagement/SvrOperationalInfo002/init";
	Joyin.dialog("业务数据维护数据源表配置", pageUrl, '100%', '100%', paramObj, function() {
	});
});

function showResultColumns() {
//    	alert(1);
}