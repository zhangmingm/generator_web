package com.store.frank.mybatis.base;

import com.store.frank.webapp.module.TableInfo;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
public class AutoGenerator {
    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 生成代码
     */
    public void execute(TableInfo tableInfo) {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        PackageConfig packageInfo=new PackageConfig();
        DataSourceConfig dataSource=new DataSourceConfig();
        StrategyConfig strategy=new StrategyConfig();
        GlobalConfig globalConfig=new GlobalConfig();
        ConfigBuilder config = new ConfigBuilder(packageInfo, dataSource, strategy,globalConfig,tableInfo);
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        // 模板引擎初始化执行文件输出
        AbstractTemplateEngine engine= templateEngine.init(config);
        engine.mkdirs();
        engine.batchOutput();
        logger.debug("==========================文件生成完成！！！==========================");
    }



}
