package com.djh.shanjupay.generator.main;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * MyBatis Plus Generator 配置执行类示例
 *
 * @author MrMyHui
 * @date 2021/04/14
 */
public class MyBatisPlusGenerator {
    private static final String PREFIX_MODULE = "shanjupay-";
    private static final String MODULE = "merchant";
    private static final String MODULE_NAME = "merchant";
    // private static final String TO_ENTITY_PATH = "shanjupay-pojo\\shangjupay-"+ MODULE_NAME +"-pojo\\src\\main\\java\\com\\djh\\shanjupay\\"+ MODULE_NAME +"\\entity\\";
    private static final String TO_ENTITY_PATH = "\\shanjupay-pojo\\shanjupay-"+ MODULE_NAME +"-pojo";
    private static final String TO_SERVER_PATH = PREFIX_MODULE + MODULE +"\\src\\main\\java\\com\\djh\\shanjupay\\" + MODULE + "\\service\\";
    private static final String TO_SERVER_PATH_IMPL = PREFIX_MODULE + MODULE +"\\src\\main\\java\\com\\djh\\shanjupay\\" + MODULE + "\\service\\impl\\";
    private static final String TO_CONTROLLER_PATH = PREFIX_MODULE + MODULE + "\\src\\main\\java\\com\\djh\\shanjupay\\" + MODULE + "\\controller\\";
    private static final String TO_MAPPER_PATH = PREFIX_MODULE + MODULE + "\\src\\main\\java\\com\\djh\\shanjupay\\" + MODULE + "\\mapper\\";
    private static final String TO_MAPPER_XML_PATH = PREFIX_MODULE + MODULE + "\\src\\main\\resources\\" + MODULE;
    /**
     * 读取控制台内容
     *
     * @param tip 提示
     * @return {@link String}
     */
    /*private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }*/

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        //当前项目名
        String projectName = "/"+PREFIX_MODULE+MODULE_NAME;

        globalConfig.setOutputDir(projectPath + projectName+"/src/main/java");
        globalConfig.setAuthor("author");
        globalConfig.setOpen(false);
        globalConfig.setIdType(IdType.ID_WORKER);
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置 需配置
		DataSourceConfig dataSourceConfig = new DataSourceConfig();

		// 商户服务
		dataSourceConfig
				.setUrl("jdbc:mysql://127.0.0.1:3306/shanjupay_merchant_service?serverTimezone=Asia/Shanghai");

		// 交易服务
//		dataSourceConfig
//				.setUrl("jdbc:mysql://127.0.0.1:3306/mp2?serverTimezone=Asia/Shanghai");
		// dataSourceConfig.setSchemaName("public");
		dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
		dataSourceConfig.setUsername("root");
		dataSourceConfig.setPassword("YYpp1220");
		autoGenerator.setDataSource(dataSourceConfig);

        // 生成包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.djh.shanjupay");
        //如果需要手动输入模块名
        packageConfig.setModuleName(MODULE_NAME);
        /*packageConfig.setMapper("dao");
        packageConfig.setEntity("entity");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");*/
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名
                return projectPath + projectName+"/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig("/templates/entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名
                return projectPath + TO_ENTITY_PATH + "/src/main/java/com/djh/shanjupay/merchant/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("");
         templateConfig.setMapper("templates/mapper.java");
        templateConfig.setService("");
        templateConfig.setServiceImpl("templates/serviceImpl.java");
         templateConfig.setController("templates/controller.java");

        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //表名映射到实体策略，带下划线的转成驼峰
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        //列名映射到类型属性策略，带下划线的转成驼峰
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        //实体类使用lombok
        strategyConfig.setEntityLombokModel(true);
//        strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // 如果 setInclude() //设置表名不加参数, 会自动查找所有表
        // 如需要制定单个表, 需填写参数如: strategyConfig.setInclude("user_info);
        strategyConfig.setInclude();
        // strategyConfig.setSuperEntityColumns("id");
//        strategyConfig.setControllerMappingHyphenStyle(true);

        //自动将数据库中表名为 user_info 格式的转为 UserInfo 命名
        //表名映射到实体名称去掉前缀
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        // Boolean类型字段是否移除is前缀处理
        strategyConfig.setEntityBooleanColumnRemoveIsPrefix(true);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        System.out.println("===================== MyBatis Plus Generator ==================");

        //自定义文件生成路径，包路径
        //这里调用customPackagePath方法，使用可以自己在内部灵活配置路径
        //如果不调用该方法、就会使用MyBatis-Plus默认的文件生成路径和包路径生成文件、但可以使用上面的PackageConfig做一些简单的配置
        /*try {
            customPackagePath(packageConfig,autoGenerator);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }*/

        autoGenerator.execute();

        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }

    /**
     * 自定义包路径，文件生成路径，这边配置更灵活
     * 虽然也可以使用InjectionConfig设置FileOutConfig的方式设置路径
     * 这里直接使用Map方式注入ConfigBuilder配置对象更加直观
     *
     * @param pc  包的配置信息
     * @param mpg mybatis plus的生成的总的配置类
     * @throws NoSuchFieldException   没有这样的磁场异常
     * @throws IllegalAccessException 非法访问异常
     */
    private static void customPackagePath(PackageConfig pc, AutoGenerator mpg) throws NoSuchFieldException, IllegalAccessException {

        String projectPath = System.getProperty("user.dir") + "\\";

        /*
         * packageInfo配置controller、service、serviceImpl、entity、mapper等文件的包路径
         * 这里包路径可以根据实际情况灵活配置
         */
        Map<String,String> packageInfo = new HashMap<>(16);
        packageInfo.put(ConstVal.CONTROLLER, TO_CONTROLLER_PATH);
        packageInfo.put(ConstVal.SERVICE, TO_SERVER_PATH);
        packageInfo.put(ConstVal.SERVICE_IMPL, TO_SERVER_PATH_IMPL);
        packageInfo.put(ConstVal.ENTITY, TO_ENTITY_PATH);
        packageInfo.put(ConstVal.MAPPER, TO_MAPPER_PATH);

        /*
         * pathInfo配置controller、service、serviceImpl、entity、mapper、mapper.xml等文件的生成路径
         * srcPath也可以更具实际情况灵活配置
         * 后面部分的路径是和上面packageInfo包路径对应的源码文件夹路径
         * 这里你可以选择注释其中某些路径，可忽略生成该类型的文件，例如:注释掉下面pathInfo中Controller的路径，就不会生成Controller文件
         */
        Map<String, String> pathInfo = new HashMap<>(16);
        pathInfo.put(ConstVal.CONTROLLER_PATH, projectPath + TO_CONTROLLER_PATH + packageInfo.get(ConstVal.CONTROLLER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, projectPath + TO_SERVER_PATH_IMPL + packageInfo.get(ConstVal.SERVICE_IMPL).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.ENTITY_PATH, projectPath + TO_ENTITY_PATH + packageInfo.get(ConstVal.ENTITY).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.MAPPER_PATH, projectPath + TO_MAPPER_PATH + packageInfo.get(ConstVal.MAPPER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.XML_PATH, TO_MAPPER_XML_PATH);
        pc.setPathInfo(pathInfo);

        /*
         * 创建configBuilder对象，传入必要的参数
         * 将以上的定义的包路径packageInfo配置到赋值到configBuilder对象的packageInfo属性上
         * 因为packageInfo是私有成员变量，也没有提交提供公共的方法，所以使用反射注入
         * 为啥要这么干，看源码去吧
         */
        ConfigBuilder configBuilder = new ConfigBuilder(mpg.getPackageInfo(), mpg.getDataSource(), mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig());
        Field packageInfoField = configBuilder.getClass().getDeclaredField("packageInfo");
        packageInfoField.setAccessible(true);
        packageInfoField.set(configBuilder,packageInfo);

        /*
         * 设置配置对象
         */
        mpg.setConfig(configBuilder);
    }
}