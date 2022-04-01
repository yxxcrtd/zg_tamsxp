/*
 * 文件名：DictCodeList.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月25日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.contant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.joyintech.background.component.common.pack.common.base.BaseDictCode;
import com.joyintech.background.component.common.pack.common.base.BaseDictCodeList;
import com.joyintech.background.component.common.pack.common.util.StringUtil;

/**
 * 数据字典值
 * 
 * @description
 * @author 王波
 * @version 1.0 2020年9月25日
 * @see DictCodeList
 * @since 1.0
 */
public enum DictCodeList implements BaseDictCodeList {
	
	报表名称_产品申报登记(DictCode.报表名称, "01", "产品申报登记"),
	报表名称_产品发行登记(DictCode.报表名称, "02", "产品发行登记"),
	报表名称_机构和私人银行产品发行登记(DictCode.报表名称, "03", "机构和私人银行产品发行登记"),
	报表名称_募集总量登记(DictCode.报表名称, "04", "募集总量登记"),
	报表名称_资管计划底层持仓信息登记(DictCode.报表名称, "05", "资管计划底层持仓信息登记"),
	报表名称_产品存续期登记(DictCode.报表名称, "06", "产品存续期登记"),
	报表名称_产品终止登记(DictCode.报表名称, "07", "产品终止登记"),
	报表名称_银行月度统计表(DictCode.报表名称, "08", "银行月度统计表"),
	报表名称_从业人员登记(DictCode.报表名称, "09", "从业人员登记"),
	报表名称_资产要素信息登记(DictCode.报表名称, "10", "资产要素信息登记"),
	报表名称_资产交易信息登记(DictCode.报表名称, "11", "资产交易信息登记"),
	报表名称_资产估值信息登记(DictCode.报表名称, "12", "资产估值信息登记"),
	报表名称_分类总量信息登记(DictCode.报表名称, "13", "分类总量信息登记"),
	报表名称_投资者身份信息登记(DictCode.报表名称, "14", "投资者身份信息登记"),
	报表名称_业务明细信息登记(DictCode.报表名称, "15", "业务明细信息登记"),
	报表名称_投资者持有信息登记(DictCode.报表名称, "16", "投资者持有信息登记"),
	报表名称_人行月度统计报表(DictCode.报表名称, "17", "人行月度统计报表"),
	报表名称_资产池基本信息(DictCode.报表名称, "18", "资产池基本信息"),
	报表名称_产品基本信息(DictCode.报表名称, "19", "产品基本信息"),
	报表名称_产品起始募集信息(DictCode.报表名称, "20", "产品起始募集信息"),
	报表名称_产品资金募集信息(DictCode.报表名称, "21", "产品资金募集信息"),
	报表名称_资产池终止信息(DictCode.报表名称, "22", "资产池终止信息"),
	报表名称_产品终止信息(DictCode.报表名称, "23", "产品终止信息"),
	报表名称_资产负债信息(DictCode.报表名称, "24", "资产负债信息"),
	报表名称_银行季度统计表(DictCode.报表名称,"25","银行季度统计表"),
	报表名称_投资者明细信息登记(DictCode.报表名称,"26","投资者明细信息登记"),
	币种全称_中债_人民币(DictCode.币种全称_中债, "01", "人民币(CNY)"),
	币种全称_中债_港元(DictCode.币种全称_中债, "02", "港元(HKD)"),
	币种全称_中债_美元(DictCode.币种全称_中债, "03", "美元(USD)"),
	币种全称_中债_欧元(DictCode.币种全称_中债, "04", "欧元(EUR)"),
	币种全称_中债_日元(DictCode.币种全称_中债, "05", "日元(JPY)"),
	币种全称_中债_澳门元(DictCode.币种全称_中债, "06", "澳门元(MOP)"),
	币种全称_中债_瑞士法郎(DictCode.币种全称_中债, "07", "瑞士法郎(CHF)"),
	币种全称_中债_英镑(DictCode.币种全称_中债, "08", "英镑(GBP)"),
	币种全称_中债_俄罗斯卢布(DictCode.币种全称_中债, "09", "俄罗斯卢布(RUR)"),
	币种全称_中债_加拿大元(DictCode.币种全称_中债, "10", "加拿大元(CAD)"),
	币种全称_中债_丹麦克朗(DictCode.币种全称_中债, "11", "丹麦克朗(DKK)"),
	币种全称_中债_澳大利亚元(DictCode.币种全称_中债, "12", "澳大利亚元(AUD)"),
	币种全称_中债_瑞典克朗(DictCode.币种全称_中债, "13", "瑞典克朗(SEK)"),
	币种全称_中债_新加坡元(DictCode.币种全称_中债, "14", "新加坡元（SGD）"),
	币种全称_中债_新台币(DictCode.币种全称_中债, "15", "新台币（TWD）"),
	币种全称_中债_其他(DictCode.币种全称_中债, "99", "其他"),


	报表编码_产品申报登记(DictCode.报表编码, "WMRS.113.001.01", "产品申报登记"),
	报表编码_产品发行登记(DictCode.报表编码, "WMRS.114.001.01", "产品发行登记"),
	报表编码_机构和私人银行产品发行登记(DictCode.报表编码, "WMRS.114.001.01", "机构和私人银行产品发行登记"),
	报表编码_募集总量登记(DictCode.报表编码, "WMRS.104.001.01", "募集总量登记"),
	报表编码_资管计划底层持仓信息登记(DictCode.报表编码, "WMRS.110.001.01", "资管计划底层持仓信息登记"),
	报表编码_产品存续期登记(DictCode.报表编码, "WMRS.105.001.01", "产品存续期登记"),
	报表编码_产品终止登记(DictCode.报表编码, "WMRS.111.001.01", "产品终止登记"),
	报表编码_从业人员登记(DictCode.报表编码, "WMRS.112.001.01", "从业人员登记"),
	报表编码_资产要素信息登记(DictCode.报表编码, "WMRS.106.001.01", "资产要素信息登记"),
	报表编码_资产交易信息登记(DictCode.报表编码, "WMRS.108.001.01", "资产交易信息登记"),
	报表编码_资产估值信息登记(DictCode.报表编码, "WMRS.109.001.01", "资产估值信息登记"),
	报表编码_投资者身份信息登记(DictCode.报表编码, "WMRS.201.001.01", "投资者身份信息登记"),
	报表编码_投资者持有信息登记(DictCode.报表编码, "WMRS.202.001.01", "投资者持有信息登记"),




	接口系统ID_中债直连(DictCode.接口系统ID, "11", "中债直连"),
	接口系统ID_直连结果分析(DictCode.接口系统ID, "12", "直连结果分析"),
	接口系统ID_获取产品登记编码(DictCode.接口系统ID, "13", "获取产品登记编码"),

	是否_中债_是(DictCode.是否_中债,"01","是"),
	是否_中债_否(DictCode.是否_中债,"02","否"),

	维护数据状态_待处理(DictCode.维护数据状态,"A","待处理"),
	维护数据状态_修改(DictCode.维护数据状态,"M","修改"),
	维护数据状态_删除(DictCode.维护数据状态,"D","删除"),
	维护数据状态_有效(DictCode.维护数据状态,"E","有效"),

	报送反馈状态_发送中(DictCode.报送反馈状态,"00","发送中"),
	报送反馈状态_发送成功(DictCode.报送反馈状态,"01","发送成功"),
	报送反馈状态_发送失败(DictCode.报送反馈状态,"02","发送失败"),
	报送反馈状态_中债通过(DictCode.报送反馈状态,"03","中债通过"),
	报送反馈状态_中债退回(DictCode.报送反馈状态,"04","中债退回"),
	报送反馈状态_审核文件下载失败(DictCode.报送反馈状态,"05","审核文件下载失败"),
	报送反馈状态_发送异常(DictCode.报送反馈状态,"06","发送异常"),

	接口类型_开始(DictCode.接口类型, "00", "开始" ),
	接口类型_读文件(DictCode.接口类型, "01", "读文件" ),
	接口类型_文件转置(DictCode.接口类型, "02", "文件转置" ),
	接口类型_上传ftp(DictCode.接口类型, "03", "上传ftp" ),
	接口类型_ftp下载(DictCode.接口类型, "04", "ftp下载 " ),
	接口类型_发送联机交易(DictCode.接口类型, "05", "发送联机交易" ),
	接口类型_登陆接口表(DictCode.接口类型, "06", "登陆接口表" ),
	接口类型_登陆业务表(DictCode.接口类型, "07", "登陆业务表" ),
	接口类型_备份文件(DictCode.接口类型, "08", "备份文件" ),
	接口类型_检索数据(DictCode.接口类型, "09", "检索数据" ),
	接口类型_写文件(DictCode.接口类型, "10", "写文件" ),
	接口类型_启动流程(DictCode.接口类型, "11", "启动流程" ),
	接口类型_调用他系统service(DictCode.接口类型, "12", "调用他系统service" ),
	接口类型_报文转化(DictCode.接口类型, "13", "报文转化"),
	接口类型_结束(DictCode.接口类型, "99", "结束" ),

	预警标示_预警(DictCode.预警标示, "0", "预警" ),
	预警标示_不预警(DictCode.预警标示, "1", "不预警" ),

	执行结果_成功(DictCode.执行结果,"01","成功"),
	执行结果_失败(DictCode.执行结果,"02","失败"),
	执行结果_部分成功(DictCode.执行结果,"03","部分成功"),
	执行结果_报表不完整(DictCode.执行结果,"05","报表不完整"),
	执行结果_已确认报表(DictCode.执行结果,"06","已确认报表"),
	执行结果_无符合要求执行数据(DictCode.执行结果,"07","无符合要求执行数据"),
	执行结果_生成报表失败(DictCode.执行结果,"08","生成报表失败"),

	资产_负债类别_本行_他行存款_大额存单(DictCode.资产_负债类别,"01","本行/他行存款||大额存单"),
    资产_负债类别_拆放同业_买入返售_资产_同业拆入_卖出回购_负债(DictCode.资产_负债类别,"02","拆放同业/买入返售（资产）||同业拆入/卖出回购（负债）"),
    资产_负债类别_债券类资产_理财直接融资工具_同业存单(DictCode.资产_负债类别,"03","债券类资产/理财直接融资工具/同业存单"),
    资产_负债类别_非标准化债权类资产_信贷资产流转项目 (DictCode.资产_负债类别,"04","非标准化债权类资产/信贷资产流转项目 "),
    资产_负债类别_票据类资产_信用证类资产(DictCode.资产_负债类别,"05","票据类资产/信用证类资产"),
    资产_负债类别_权益类资产(DictCode.资产_负债类别,"06","权益类资产"),
    资产_负债类别_金融衍生品(DictCode.资产_负债类别,"07","金融衍生品"),
    资产_负债类别_QDII债券资产(DictCode.资产_负债类别,"08","QDII债券资产"),
    资产_负债类别_QDII拆出_逆回购资产(DictCode.资产_负债类别,"09","QDII拆出/逆回购资产"),
    资产_负债类别_QDII股票_基金类资产(DictCode.资产_负债类别,"10","QDII股票/基金类资产"),
    资产_负债类别_QDII结构性票据类资产(DictCode.资产_负债类别,"11","QDII结构性票据类资产"),
    资产_负债类别_资产管理产品(DictCode.资产_负债类别,"12","资产管理产品"),
    资产_负债类别_商品类资产(DictCode.资产_负债类别,"13","商品类资产"),
    资产_负债类别_各类资产的其他项(DictCode.资产_负债类别,"14","各类资产的其他项"),
    资产_负债类别_公募基金(DictCode.资产_负债类别,"15","公募基金"),
    资产_负债类别_委外投资_协议方式(DictCode.资产_负债类别,"16","委外投资——协议方式"),
    资产_负债类别_其他_负债_类资产(DictCode.资产_负债类别,"17","其他（负债）类资产"),
    资产_负债类别_另类资产(DictCode.资产_负债类别,"18","另类资产"),
    资产_负债类别_私募基金(DictCode.资产_负债类别,"19","私募基金"),

	报送状态_未报送(DictCode.报送状态,"01","未报送"),
	报送状态_接口报送(DictCode.报送状态,"02","接口报送"),
	报送状态_手工处理(DictCode.报送状态,"03","手工处理"),

	// add by zhangliang 20210510 中债直连反馈异常处理 start
	中债一二三期文件处理状态_未处理(DictCode.中债一二三期文件处理状态,"01","未处理"),
	中债一二三期文件处理状态_已处理(DictCode.中债一二三期文件处理状态,"02","已处理"),
	// add by zhangliang 20210510 中债直连反馈异常处理 end
	;

	/**
	 * 对应于数据字典code
	 */
	private String value;

	/**
	 * 数据字典枚举
	 */
	private BaseDictCode dictCode;

	/**
	 * 明细名称
	 */
	private String codeName;

	DictCodeList(BaseDictCode dictCode, String value, String codeName) {
		this.dictCode = dictCode;
		this.value = value;
		this.setCodeName(codeName);
	}

	/**
	 * 获得数据字典详细类
	 *
	 * @param value    数据字典值
	 * @param dictCode 数据字典DictCode
	 * @return DictCodeList
	 * @see
	 * @since 1.0
	 */
	public static DictCodeList from(BaseDictCode dictCode, String value) {
		DictCodeList[] values = DictCodeList.values();
		for (DictCodeList code : values) {
			if (StringUtil.equals(value, code.getValue()) && code.getDictCode().equals(dictCode)) {
				return code;
			}
		}
		return null;
	}

	/**
	 * 获取数据字典明细valueList
	 *
	 * @param dictCode DictCode
	 * @return List&lt;SelectOptionDto&gt;
	 * @see
	 * @since 1.0
	 */
	public static List<String> valuesfromDictCode(BaseDictCode dictCode) {
		List<String> result = new ArrayList<>();
		DictCodeList[] values = DictCodeList.values();
		for (DictCodeList code : values) {
			if (code.getDictCode() == dictCode) {
				result.add(code.getValue());
			}
		}
		return result;
	}

	/**
	 * 获取 DictCode
	 *
	 * @return value.
	 */
	@Override
	public BaseDictCode getDictCode() {
		return dictCode;
	}

	/**
	 * 获取 value
	 *
	 * @return value.
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * 设置 value
	 *
	 * @param value value
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取 codeName
	 *
	 * @return codeName.
	 */
	@Override
	public String getCodeName() {
		return codeName;
	}

	/**
	 * 获取 带有引号的名称
	 *
	 * @return 带有引号的名称.
	 */
	public String getQuotesCodeName() {
		return "“" + codeName + "”";
	}

	/**
	 * 获取Integer value
	 *
	 * @return Integer value
	 */
	public Integer getIntegerCodeName() {
		Integer result = null;
		try {
			result = Integer.parseInt(codeName);
		} catch (Exception e) {
			// nothing to do
		}
		return result;
	}

	/**
	 * 获取Integer value
	 *
	 * @return Integer value
	 */
	public BigDecimal getBigDecimalCodeName() {
		BigDecimal result = null;
		try {
			result = new BigDecimal(codeName);
		} catch (Exception e) {
			// nothing to do
		}
		return result;
	}

	/**
	 * 设置 codeName
	 *
	 * @param codeName codeName
	 */
	@Override
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
}
