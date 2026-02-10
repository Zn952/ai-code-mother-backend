package com.zn.aicodemother.generator;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;

/**
 * @program: ai-code-mother-backend
 * @description: mybatis-flux代码生成器配置
 * @author: Zn
 * @create: 2026-01-17 14:33
 **/
public class MyBatisCodeGenerator {
    //目标表的表名
    public static final String[] TABLE_NAMES = {"chat_history"};

    public static void main(String[] args) {

        Dict dict = YamlUtil.loadByPath("application-dev.yml");
        Map<String, Object> dataSourceConfig = dict.getByPath("spring.datasource");
        String url = String.valueOf(dataSourceConfig.get("url"));
        String username = String.valueOf(dataSourceConfig.get("username"));
        String password = String.valueOf(dataSourceConfig.get("password"));
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    // 详细配置见：https://mybatis-flex.com/zh/others/codegen.html
    public static GlobalConfig createGlobalConfigUseStyle() {

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("com.zn.aicodemother.genresult");

        //设置表前缀和只生成哪些表
        globalConfig.setGenerateTable(TABLE_NAMES);
        globalConfig.setLogicDeleteColumn("isDelete");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(21);

        //设置生成 mapper
        globalConfig.getMapperConfig()
                .setMapperAnnotation(true);
        globalConfig.setMapperGenerateEnable(true);
        //设置生成 xml
        globalConfig.setMapperXmlGenerateEnable(true);
        globalConfig.setMapperXmlPath("src/main/resources/mapper");
        //设置生成 service
        globalConfig.setServiceGenerateEnable(true);
        //设置生成 serviceImpl
        globalConfig.setServiceImplGenerateEnable(true);
        //设置生成 controller
        globalConfig.setControllerGenerateEnable(true);

        //设置生成的注释
        globalConfig.getJavadocConfig()
                .setAuthor("Zn")
                .setSince("2026-01-17");
        return globalConfig;
    }

}
