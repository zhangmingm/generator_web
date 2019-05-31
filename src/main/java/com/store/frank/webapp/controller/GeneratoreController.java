package com.store.frank.webapp.controller;

import com.store.frank.config.AjaxResult;
import com.store.frank.mybatis.base.AutoGenerator;
import com.store.frank.utils.DbTools;
import com.store.frank.utils.PropertiesUtils;
import com.store.frank.utils.StringTools;
import com.store.frank.webapp.module.GlobalProperty;
import com.store.frank.webapp.module.IndexProperty;
import com.store.frank.webapp.module.TableField;
import com.store.frank.webapp.module.TableInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年04月28日 14:50
 * @Description: 生成代码 controller
 */
@Controller
public class GeneratoreController {

    /**
     * 首页。将请求跳转到html页面
     * 如果配置文件中有属性值，将属性值赋值上去。
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    public ModelAndView home() throws IOException {
        ModelAndView mv=new ModelAndView();
        Map<String,String> map=new HashMap<>();
        Properties pro=PropertiesUtils.getProperties();
        // 配置信息有14个属性，如果<14 则初始化属性。
        if(pro.size()<14){
            pro=PropertiesUtils.initProperty();
        }
        for (String key:pro.stringPropertyNames()) {
            map.put(key, (String) pro.get(key));
        }
        mv.addObject("property",map);
        mv.setViewName("/index");
        return mv;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/14 0014 下午 4:52
     * @Description: 根据数据库的配置信息查询该数据库下的视图和表
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult index(IndexProperty baseProperty) throws Exception {
        AjaxResult ajaxResult = new AjaxResult(true);
        Map<String,String> param=new HashMap<>();
        param.put("userName",baseProperty.getUserName());
        param.put("passWord",baseProperty.getPassWord());
        param.put("parentPackage",baseProperty.getParentPackage());
        param.put("mysqlUrl",baseProperty.getMysqlUrl());
        param.put("schema",DbTools.getSchema(baseProperty.getMysqlUrl()));
        param.put("tableName",baseProperty.getTableName());
        param.put("author",baseProperty.getAuthor());
        param.put("outputDir",baseProperty.getOutputDir());
        param.put("autoGetter",baseProperty.getAutoGetter());
        param.put("entityName",StringTools.dbField2UpperCamel(baseProperty.getTableName()));
        param.put("moduleName",StringTools.UnderlineToHump(baseProperty.getTableName()));
        param.put("entityInstanceName",StringTools.getInstanceName(baseProperty.getTableName()));
        PropertiesUtils.addProperties(param);
        return ajaxResult;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/15 0015 下午 3:40
     * @Description: 根据表名称查询对应的字段
     */
    @RequestMapping(value = "/showField", method = RequestMethod.GET)
    public ModelAndView showField() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<TableField> fieldList= new ArrayList<>();
        try {
            fieldList=DbTools.getTableField();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mv.addObject("fieldList",fieldList);
        mv.setViewName("/fieldNext");
        return mv;
    }


    /**
     * @author zhangmingming  braveheart1115@163.com
     * @date 2019/5/16 0016 下午 3:07
     * @Description: 字段选择完的操作
     * 需要将tableInfo设置到GlobalProperty上
     */
    @RequestMapping(value = "/fieldFinish", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult fieldFinish(HttpServletRequest request) throws IllegalAccessException {
        AjaxResult ajaxResult = new AjaxResult(true);
        String queryField=request.getParameter("queryField");
        String updateField=request.getParameter("updateField");
        String itemField=request.getParameter("itemField");
        ModelAndView mv=new ModelAndView();
        GlobalProperty globalProperty=PropertiesUtils.getGlobalProperty();
        TableInfo tableInfo = DbTools.getTableInfo(queryField, updateField, itemField, globalProperty);
        /**
         * 使用这里的tableInfo替换代码中的tableInfo
         */
        AutoGenerator generator=new AutoGenerator();
        generator.execute(tableInfo);
        return ajaxResult;
    }




    @RequestMapping(value = "/finished", method = RequestMethod.GET)
    public ModelAndView finished() throws Exception {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/finish");
        return mv;
    }


































}
