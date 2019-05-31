package com.store.frank.mybatis.base;

import com.store.frank.utils.Constant;
import lombok.Data;

/**
 * @author zhangmingming  braveheart1115@163.com
 * @date 2019年05月20日 17:37
 * @Description:
 */
@Data
public class TemplateConfig {
    private String entity = Constant.TEMPLATE_ENTITY;

    private String vo = Constant.TEMPLATE_VO;

    private String service = Constant.TEMPLATE_SERVICE;

    private String serviceImpl = Constant.TEMPLATE_SERVICE_IMPL;

    private String mapper = Constant.TEMPLATE_MAPPER;

    private String xml = Constant.TEMPLATE_XML;

    private String controller = Constant.TEMPLATE_CONTROLLER;
}
