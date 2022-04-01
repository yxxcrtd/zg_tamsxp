package com.joyintech.tams.settle.batch.datadeal.dto.step2;

import java.util.ArrayList;
import java.util.List;

import com.joyintech.tams.common.base.entity.*;

/**
 * 清算数据DTO（step2 reader用）
 * 
 * @author 陈卓
 * @date 2020/7/7
 * @see StlDownDataDealOperatorBaseDto
 * @since 1.0
 */
public class StlDownDataDealOperatorBaseDto {
    
	// JSMX清算数据List
	List<SzdJsmx> jsmxList;
	
	// SJSMX清算数据List
	List<SzdSjsmx> sjsmxList;
	
	// SJSJG清算数据List
	List<SzdSjsjg> sjsjgList;
	
	// SJSFX清算数据List
	List<SzdSjsfx> sjsfxList;
	
	// ZQBD清算数据List
	List<SzdZqbd> zqbdList;
	
	// ABCSJ清算数据List
	List<SzdAbcsj> abcsjList;
	
	// ZSMX清算数据List
	List<SzdZsmx> zsmxList;
	
	// SZDZQY清算数据List
	List<SzdZqy> szdZqyList;
	
	// SJSKS清算数据List
	List<SzdSjsks> szdSjsksList;

	// TZXX清算数据List
	List<SzdTzxx> szdTzxxList;

	// SJSGB清算数据List
	List<SzdSjsgb> szdSjsgbList;

	public StlDownDataDealOperatorBaseDto() {
		jsmxList = new ArrayList<SzdJsmx>();
		sjsmxList = new ArrayList<SzdSjsmx>();
		sjsjgList = new ArrayList<SzdSjsjg>();
		sjsfxList = new ArrayList<SzdSjsfx>();
		zqbdList = new ArrayList<SzdZqbd>();
		abcsjList = new ArrayList<SzdAbcsj>();
		zsmxList = new ArrayList<SzdZsmx>();
		szdZqyList = new ArrayList<SzdZqy>();
		szdSjsksList = new ArrayList<SzdSjsks>();
		szdTzxxList=new ArrayList<SzdTzxx>();
		szdSjsgbList=new ArrayList<SzdSjsgb>();
	}
	
	public List<SzdJsmx> getJsmxList() {
		return jsmxList;
	}

	public void setJsmxList(List<SzdJsmx> jsmxList) {
		this.jsmxList = jsmxList;
	}

	public List<SzdSjsmx> getSjsmxList() {
		return sjsmxList;
	}

	public void setSjsmxList(List<SzdSjsmx> sjsmxList) {
		this.sjsmxList = sjsmxList;
	}

	public List<SzdSjsjg> getSjsjgList() {
		return sjsjgList;
	}

	public void setSjsjgList(List<SzdSjsjg> sjsjgList) {
		this.sjsjgList = sjsjgList;
	}

	public List<SzdSjsfx> getSjsfxList() {
		return sjsfxList;
	}

	public void setSjsfxList(List<SzdSjsfx> sjsfxList) {
		this.sjsfxList = sjsfxList;
	}

	public List<SzdZqbd> getZqbdList() {
		return zqbdList;
	}

	public void setZqbdList(List<SzdZqbd> zqbdList) {
		this.zqbdList = zqbdList;
	}

	public List<SzdAbcsj> getAbcsjList() {
		return abcsjList;
	}

	public void setAbcsjList(List<SzdAbcsj> abcsjList) {
		this.abcsjList = abcsjList;
	}

	public List<SzdZsmx> getZsmxList() {
		return zsmxList;
	}

	public void setZsmxList(List<SzdZsmx> zsmxList) {
		this.zsmxList = zsmxList;
	}

	public List<SzdZqy> getSzdZqyList() {
		return szdZqyList;
	}

	public void setSzdZqyList(List<SzdZqy> szdZqyList) {
		this.szdZqyList = szdZqyList;
	}

	public List<SzdSjsks> getSzdSjsksList() {
		return szdSjsksList;
	}

	public void setSzdSjsksList(List<SzdSjsks> szdSjsksList) {
		this.szdSjsksList = szdSjsksList;
	}

	public List<SzdTzxx> getSzdTzxxList() {
		return szdTzxxList;
	}

	public void setSzdTzxxList(List<SzdTzxx> szdTzxxList) {
		this.szdTzxxList = szdTzxxList;
	}

	public List<SzdSjsgb> getSzdSjsgbList() {
		return szdSjsgbList;
	}

	public void setSzdSjsgbList(List<SzdSjsgb> szdSjsgbList) {
		this.szdSjsgbList = szdSjsgbList;
	}
}
