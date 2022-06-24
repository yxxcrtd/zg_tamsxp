package com.joyintech.tams.svreport.chb.assetinfodetail.asset;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset.SvrBussFinBondRepInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class DtoChangeUtil {

    private static Logger logger = LoggerFactory.getLogger("DtoChangeUtil");
    /**
     * rep默认的替换字段
     */
    public static String target = "rep";


    /***
     *
     * @param dto
     * @param obj
     * @param target
     */

    /**
     * 实体类转换方法
     * @param dto 转出实体类
     * @param obj 接收实体类
     * @param target 实体类间字段差异比较符号
     * @param flag  true为使用target对转出实体进行处理  flase为处理转入实体类
     */
    public static void SetChbToRep(Object dto , Object obj,String target,Boolean flag) {
        Map<String,String> chbNameMap = new HashMap<>();
        //获取当前类及其父类的所有字段
        for(Field field :getAllFields(dto)){
            //资产基本信息
            //将rep字段切除去chb找
            String menthdName = "";
            if(StringUtil.isNotEmpty(target)&&flag){
                menthdName = field.getName().replace(target, "").toLowerCase();
            }else{
                menthdName = field.getName().toLowerCase();
            }
            chbNameMap.put(menthdName,field.getName());
        }
        Class clazz = obj.getClass();
        //获取当前类及其父类的所有字段
        for(Field field : getAllFields(obj)){
            try {
                //将rep字段切除去chb找
                String menthdName = "";
                if(StringUtil.isNotEmpty(target)&&!flag){
                    menthdName = field.getName().replace(target, "").toLowerCase();
                }else{
                    menthdName = field.getName().toLowerCase();
                }
                //判断是否有相同的字段,不取序列化字段.避免报错
                if (null != chbNameMap.get(menthdName)&&field.getName().indexOf("erialVersionUID")<0) {
                    //接收实体类
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                            obj.getClass());
                    //转出实体类
                    PropertyDescriptor pd1 = new PropertyDescriptor(chbNameMap.get(menthdName),
                            dto.getClass());
                    //接收实体类取写方法
                    Method setMethod = pd.getWriteMethod();
                    //转出实体类取读方法
                    Method getMethod = pd1.getReadMethod();
                    //获取转出实体类的值
                    Object obj1 = getMethod.invoke(dto);
                    //判断值是否为空
                    if (StringUtil.isNotEmpty(obj1)) {
                        //为接收实体赋值
                        setMethod.invoke(obj, obj1);
                    }
                }
            }catch(Exception e){
                logger.error("转换异常,请检查数据类型是否匹配",e);
            }
        }

    }


    /**
     * 获取父类的字典集合(避免出现继承导致部分字段无法赋值)
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

//        public static void main(String[] args) {
//        ChbAssetInfo dto = new ChbAssetInfo();
//        dto.setPartyType1("01");
//        dto.setPartyGrade("02");
//        dto.setPartyType2("02");
//
//        SvrBussFinBondRepInfoDto dto2= new SvrBussFinBondRepInfoDto();
//        try {
//            SetChbToRep(dto, dto2,"rep",false);
//        }catch(Exception e){
//        }
//    }

}
