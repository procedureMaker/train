package com.example.train.generator.server;

import cn.hutool.db.Db;
import com.example.train.generator.util.DbUtil;
import com.example.train.generator.util.Field;
import com.example.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static String serverPath = "[module]/src/main/java/com/example/train/[module]/";

    static String pomPath = "generator\\pom.xml";

    static {
        new File(serverPath).mkdirs();
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        //读取定义好的pom文件
        Document document = saxReader.read(pomPath);
        //使用XPath快速定位节点和属性  //：从根目录下寻找，pom是xml命名空间  configuration:在命名空间下寻找configuration的节点
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
        return node.getText();
    }

    public static void main(String[] args) throws Exception {
        //获取mybatis-generator
        String generatorPath = getGeneratorPath();
        //有多个的service 动态读取要配置持久层的xml表名称替换生成对应目录
        String module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        System.out.println("module: " + module);
        serverPath = serverPath.replace("[module]", module);
        System.out.println("serverPath: " + serverPath);

        // 读取table节点
        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        System.out.println(table);
        //读取table节点后拿到两个属性 tableName与domainOjectName
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());

        //从配置文件获取连接数据
//        Node jdbcConnection = document.selectSingleNode("//jdbcConnection");
//        System.out.println(jdbcConnection);
//        Node driverClass = jdbcConnection.selectSingleNode("@driverClass");
//        Node connectionURL = jdbcConnection.selectSingleNode("@connectionURL");
//        Node userId = jdbcConnection.selectSingleNode("@userId");
//        Node password = jdbcConnection.selectSingleNode("@password");
//        DbUtil.ConnectionInfo(connectionURL.getText(), userId.getText(), password.getText());

        Node connectionURL = document.selectSingleNode("//@connectionURL");
        Node userId = document.selectSingleNode("//@userId");
        Node password = document.selectSingleNode("//@password");
        System.out.println("url: " + connectionURL.getText());
        System.out.println("user: " + userId.getText());
        System.out.println("password: " + password.getText());

        DbUtil.url = connectionURL.getText();
        DbUtil.user = userId.getText();
        DbUtil.password = password.getText();

        // 示例：表名 sgjk_test
        // Domain = JsgjkTest
        String Domain = domainObjectName.getText();
        // domain = sgjkTest
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        // do_main = sgjk-test  controller层url用到
        String do_main = tableName.getText().replaceAll("_", "-");

        //表中文名  获得表注释
        String tableNameCn = DbUtil.getTableComment(tableName.getName());
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName.getText());
        System.out.println(tableName.getName() + "----" + tableName.getText());


        // 组装参数
        Map<String, Object> param = new HashMap<>();
        param.put("module", module);
        param.put("Domain", Domain);
        param.put("domain", domain);
        param.put("do_main", do_main);
        System.out.println("组装参数：" + param);

        gen(Domain, param, "service");
        gen(Domain, param, "controller");
    }

    private static void gen(String Domain, Map<String, Object> param, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        //针对不同的service或controller创建对应的目录
        String toPath = serverPath + target + "/";
        new File(toPath).mkdirs();
        String fileName = toPath + Domain + Target + ".java";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }
}


