package com.joyintech.tams.invest.common.util;

import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;

/**
 * 用来处理与字典值相关的操作
 */
public class DictCodeUtil {

    /**
     * 判断obj 是否与target中的一个相等
     * @param obj
     * @param target
     * @return
     */
    public static boolean equals(String obj, BussinessDictCodeList...target) {
        if(obj == null) return false;
        for(BussinessDictCodeList str : target) {
            if(str !=null && obj.equals(str.getValue())) return true;
        }
        return false;
    }

    /**
     * 判断obj 是否与target中的一个相等
     * @param obj
     * @param target
     * @return
     */
    public static boolean equals(String obj, DictCodeList...target) {
        if(obj == null) return false;
        for(DictCodeList str : target) {
            if(str !=null && obj.equals(str.getValue())) return true;
        }
        return false;
    }
}
