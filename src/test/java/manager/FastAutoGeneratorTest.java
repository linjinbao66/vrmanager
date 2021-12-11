package manager;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.sql.SQLException;
import java.util.Collections;

/**
 * <p>
 * 快速生成
 * </p>
 *
 * @author lanjerry
 * @since 2021-09-16
 */
public class FastAutoGeneratorTest {

    public static void main(String[] args) throws SQLException {

        new StrategyConfig.Builder()
                .entityBuilder()
                .disableSerialVersionUID()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
                .enableActiveRecord()
                .addSuperEntityColumns("id", "create_date", "creator", "updater", "update_date")
                .addTableFills(new Column("create_date", FieldFill.INSERT))
                .addTableFills(new Column("creator", FieldFill.INSERT))
                .addTableFills(new Column("updater", FieldFill.INSERT))
                .addTableFills(new Column("update_date", FieldFill.INSERT))
                .idType(IdType.AUTO)
                .build();

        FastAutoGenerator.create("jdbc:mysql://tcp.linjinbao.tk:3306/vrmanager2", "amrom", "369369")
            .globalConfig(builder -> {
                builder.author("linjinbao66") // 设置作者
                        .enableSwagger()
                        .fileOverride()
                        .commentDate("yyyy-MM-dd")
                        .outputDir("/Users/linjinbao/code/vrmanager/src/main/java/vrmanager");
            })
            .packageConfig(builder -> {
                builder.entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .controller("controller")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                "/Users/linjinbao/code/vrmanager/src/main/resources/mapper"));
            })
            .execute();
    }
}
