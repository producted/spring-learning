package com.haohuo.code;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created By zhangpk On 2019/6/14
 **/
public class Generator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("帅哥，请输入你的" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局策略配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java"); // 生成文件的输出目录,默认D根目录
        gc.setFileOverride(true); // 是否覆盖已有文件
        gc.setAuthor("zhangpk");
        gc.setOpen(false); // 是否打开输出目录,默认true
        gc.setEnableCache(true); // 是否在xml中添加二级缓存配置,默认false
        gc.setSwagger2(true); // 开启 swagger2 模式,默认false

        gc.setEntityName("%sModel");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("I%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        Properties properties = new Properties();
        try {
//            properties.load(new FileInputStream("src/main/resources/generator.properties"));
//            dsc.setUrl(properties.getProperty("generator.jdbc.url"));
            // dsc.setSchemaName("public");
            dsc.setUrl("jdbc:mysql://192.168.11.232:3306/test_zpk?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=Asia/Shanghai");
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername("root");
            dsc.setPassword("hc");
            mpg.setDataSource(dsc);
        } catch (Exception e) {
            throw new RuntimeException("数据源配置错误", e);
        }

        // 包名配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.haohuo");
        pc.setEntity("bean");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/"
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        // templateConfig.setEntity();
        // templateConfig.setService();
        // templateConfig.setController();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        // strategy.setSuperEntityClass("com.shiyu.BaseEntity"); //自定义继承的Entity类全称，带包名
        // strategy.setSuperEntityColumns(new String[] {"id","gmtCreate","gmtModified"});// 自定义基础的Entity类，公共字段
        strategy.setEntityLombokModel(true); // 是否为lombok模型
        strategy.setEntityBooleanColumnRemoveIsPrefix(true); // Boolean类型字段是否移除is前缀
        strategy.setRestControllerStyle(true); // 生成 @RestController 控制器
        // strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");//controller继承所有
        strategy.setInclude(scanner("表名")); //包含的表名
//        strategy.setExclude("upms_role"); // 排除的表名 这里注意include和exclude只能有一个存在

        strategy.setControllerMappingHyphenStyle(true); // 驼峰转连字符 如 umps_user 变为 upms/user
        strategy.setTablePrefix(pc.getModuleName() + "_"); // 表前缀

        mpg.setStrategy(strategy);
        // mpg.setTemplateEngine(new FreemarkerTemplateEngine()); //设置模板引擎类型，默认为 velocity
        mpg.execute();
    }

}
