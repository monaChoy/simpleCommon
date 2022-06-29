import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.junit.platform.commons.util.StringUtils;

import java.util.Collections;

public class CodeGenerator {
    //数据库信息
    private static final String URL = "jdbc:mysql://127.0.0.1:7890/common_frame";
    private static final String DB_USER_NAME = "comuser";
    private static final String DB_PASSWORD = "123456";
    //作者
    private static final String AUTHOR = "caiwenxiao";
    private static final String DIR = "";
    private static final String PACKAGE_STR = "test";

    private static final String MODULE_NAME="";
    private static final String TABLE_NAME = "";
    private static final DataSourceConfig.Builder DATA_CONFIG_BUILDER =
            new DataSourceConfig.Builder(URL, DB_USER_NAME, DB_PASSWORD)
                    .dbQuery(new MySqlQuery())
                    .schema("public")
                    .typeConvert(new MySqlTypeConvert())
                    .keyWordsHandler(new MySqlKeyWordsHandler());

    public static void main(String[] args) {

        //parentPath: 项目路径--到/src/main 此处为默认值
        final String parentPath = DIR + "/src/main";

        final String projectPath = parentPath + "/java";
        FastAutoGenerator.create(DATA_CONFIG_BUILDER)
                //全局设置
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author(AUTHOR) //设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath); // 指定输出目录
                })
                //包设置
                .packageConfig(builder -> {
                    // 设置父包名
                    builder.parent("com." + PACKAGE_STR)
                            // 设置父包模块名
                            .moduleName(MODULE_NAME)
                            // 设置mapperXml生成路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, parentPath + "/resources/mapper"));
                })
                //策略设置
                .strategyConfig(builder ->
                        //数据库表
                        builder.addInclude(TABLE_NAME)
                                //通用查询结果列和通用查询映射结果
                                .mapperBuilder().enableBaseColumnList().enableBaseResultMap()
                                //去掉service接口开头的字母I
                                .serviceBuilder().formatServiceFileName("%sService")
                                //以RESTful风格生成controller
                                .controllerBuilder().enableRestStyle().controllerBuilder()
                                //数据库字段转实体属性风格--驼峰
                                .entityBuilder().columnNaming(NamingStrategy.underline_to_camel)
                                // 设置过滤表前缀
//                            .addTablePrefix("t_", "c_")
                                //启用lombok注解
                                .enableLombok())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
