package com.joyintech.service.basicdata.assetdeclarationcodemaintenance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.base.intf.DataDictItem;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.service.basicdata.assetdeclarationcodemaintenance.dao.AssetDeclarationCodeMaintenanceMapper;
import com.joyintech.service.basicdata.assetdeclarationcodemaintenance.dto.AssetDeclarationCodeMaintenanceDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;

@Service("backAssetDeclarationCodeMaintenanceService")
public class AssetDeclarationCodeMaintenanceService extends BaseService<AssetDeclarationCodeMaintenanceDto> {

	/**
	 * 汇率行情自动生成mapper
	 */
	@Autowired
	private AssetDeclarationCodeMaintenanceMapper assetDeclarationCodeMaintenanceMapper;

	/**
	 * 汇率行情分页查询 <功能详细描述>
	 * 
	 * @param length
	 *            长度
	 * @param page
	 *            页数
	 * 
	 * @param pageParam
	 *            查询参数
	 * @return 返回查询结果
	 * @throws Exception
	 *             抛出异常
	 * @see
	 * @since 1.0
	 */
	public Page<AssetDeclarationCodeMaintenanceDto> selectForPage(int page, int length, Map<String, Object> pageParam)
			throws Exception {
		return super.queryForPage(page, length, () -> assetDeclarationCodeMaintenanceMapper.selectForPage(pageParam));
	}

    // add by zhangrui1 2020-07-25 资产分页查询 start
    /**
     * 资产代码联想
     *
     * @param page 页码
     * @param length 条/页
     * @param map 检索参数
     * @return SelectOptionDto
     */
    public Page<SelectOptionDto> getFinprodIdPage(int page, int length, Map<String, Object> map) {
        return queryForPageForSelectReturn(page, length, () -> getFinprodIds(map));
    }
    // add by zhangrui1 2020-07-25 资产分页查询 end

	public Page<SelectOptionDto> getFinprodListPage(int page, int length, Map<String, Object> map) {
		return queryForPageForSelectReturn(page, length, () -> getFinprodList(map));
	}


	/**
	 * 资产代码联想
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
	public List<SelectOptionDto> getFinprodList(Map<String, Object> map) {
		List<? extends DataDictItem> dictlist = SysDataUtil.getDataDictItem(DictCode.平安资产类型.getValue());
		List<String> PinAn_Asset_Type = new ArrayList<>();
		if (!dictlist.isEmpty()) {
			for (DataDictItem d : dictlist) {
				PinAn_Asset_Type.add(d.getItemCode());
			}
		}
		map.put("Asset", PinAn_Asset_Type);

		return assetDeclarationCodeMaintenanceMapper.getFinprodList(map);
	}


	/**
	 * 资产代码联想
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
	public List<SelectOptionDto> getFinprodIds(Map<String, Object> map) {
		List<? extends DataDictItem> dictlist = SysDataUtil.getDataDictItem(DictCode.平安资产类型.getValue());
		List<String> PinAn_Asset_Type = new ArrayList<>();
		if (!dictlist.isEmpty()) {
			for (DataDictItem d : dictlist) {
				PinAn_Asset_Type.add(d.getItemCode());
			}
		}
		map.put("Asset", PinAn_Asset_Type);
		// add by dubaojun 2020-07-31 只有上交所和深交所的资产才能查询 start
		map.put("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
		map.put("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
        // add by dubaojun 2020-07-31 只有上交所和深交所的资产才能查询 end
		return assetDeclarationCodeMaintenanceMapper.getFinprodIds(map);
	}

	/**
	 * 根据资产代码+资产申报类型 主键唯一性验证
	 *
	 * @param dto
	 *            检索参数
	 * @return SelectOptionDto
	 */
	public AssetDeclarationCodeMaintenanceDto selectByPrimaryKey(AssetDeclarationCodeMaintenanceDto dto) {
		return assetDeclarationCodeMaintenanceMapper.selectByPrimaryKey(dto);
	}
	
	/**
	 * 根据申报代码获取记录，唯一性验证
	 *
	 * @param dto
	 *            检索参数
	 * @return SelectOptionDto
	 */
	public AssetDeclarationCodeMaintenanceDto selectByUniqueKey(AssetDeclarationCodeMaintenanceDto dto) {
		return assetDeclarationCodeMaintenanceMapper.selectByUniqueKey(dto);
	}
	
	/**
	 * 根据资产代码获取资产类型
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
	public List<SelectOptionDto> getfinprodType(Map<String, Object> map) {
		map.put("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
		map.put("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
		return assetDeclarationCodeMaintenanceMapper.getfinprodType(map);
	}

	/**
	 * 删除操作
	 */
	public int delete(AssetDeclarationCodeMaintenanceDto entity) {
		int res = 0;
		if (entity.getSelectedItem() != null && entity.getSelectedItem().size() > 0) {
			for (AssetDeclarationCodeMaintenanceDto unit : entity.getSelectedItem()) {
				res += assetDeclarationCodeMaintenanceMapper.delete(unit);
			}
		} else {
			res = assetDeclarationCodeMaintenanceMapper.delete(entity);
		}
		return res;
	}

	/**
	 * 插入操作
	 */
	public int insert(AssetDeclarationCodeMaintenanceDto dto) {
		dto.setBranch("0");
		// 将数据插入到资产申报代码维护表
		return assetDeclarationCodeMaintenanceMapper.insert(dto);
	}

	/**
	 * 更新操作
	 */
	public int update(AssetDeclarationCodeMaintenanceDto dto) {
		return assetDeclarationCodeMaintenanceMapper.update(dto);
	}


	/**
	 * 查询信息
	 *
	 * @param param
	 *            查询参数
	 * @return 返回查询结果
	 * @see
	 * @since 1.0
	 */
	public List<AssetDeclarationCodeMaintenanceDto> selectAssetDeclarationList(Map<String, Object> param){
		return assetDeclarationCodeMaintenanceMapper.selectAssetDeclarationList(param);
	}

}
