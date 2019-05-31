package com.store.frank.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.CaseFormat;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年04月13日 16:16
 * @Description:
 */
public class StringTools {

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/2 0002 下午 5:58
     * @Description: 下划线命名转为驼峰命名
     */
    public static String UnderlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/2 0002 下午 5:57
     * @Description: 驼峰命名转为下划线命名
     */
    public static String HumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_");
                    temp+=1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }



    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/16 0016 下午 3:19
     * @Description: 将数据库字段转换为对象格式.如 test_data --> TestData
     */
    public static String dbField2UpperCamel(String sources){
        String result=CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, sources);
        return result;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/16 0016 下午 3:19
     * @Description: 将数据库字段转换为对象格式.如 test_data --> testData
     */
    public static String dbField2LowerCamel(String sources){
        String result=CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, sources);
        return result;
    }

    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/16 0016 下午 5:23
     * @param str 需要截取的字符串
     * @param fristChar 2位字符串
     * @param secondChar 1位字符串
     * @Description: 截取str字符串中fristChar与secondChar之间的内容。
     */
    public static String getBetweenCharacterStr(String str,String fristChar,String secondChar){
        String temp=str.substring(str.indexOf(fristChar)+2,str.indexOf(secondChar));
        return temp;
    }


    /**
     *
     * @param entityName
     * @return
     */
    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/4/19 0019 下午 3:43
     * @Description: 根据实体名称获取对象的实例化名称，首字母小写。
     */
    public static String getInstanceName(String tableName){
        String entityName=StringTools.UnderlineToHump(tableName);
        String temp=CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityName);
        return dbField2LowerCamel(temp);
    }

    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    public static String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }

    public static String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }

    public static void main(String [] args){
        String s="user_role";
        System.out.println(dbField2UpperCamel(s));
    }


}
