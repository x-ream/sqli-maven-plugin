# sqli-maven-plugin

    gensql

## pom.xml配置
```xml  
    <plugin>
        <groupId>io.xream.sqli.plugin</groupId>
        <artifactId>sqli-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <basePackages>
                <basePackage>com.yours</basePackage>
            </basePackages>
        </configuration>
    </plugin>
```  

## 使用gensql插件，推荐的java项目结构

1. 增加builder package
    创建builder类, 例如: OmsBuilder.java
    
        public Criteria.ResultMapCriteria orderDtoBuilder(OrderFindDto orderFindDto){
            CriteriaBuilder.ResultMapBuilder builder = CriteriaBuilder.resultMapBuilder();
            builder.distinct("o.id");
            builder.beginSub().eq("o.name",null).endSub();
            builder.in("i.name", Arrays.asList("test"));
            builder.nonNull("i.name").nonNull("l.log");
            builder.sourceBuilder().source("order").alia("o");
            builder.sourceBuilder().source("orderItem").alia("i").join(JoinType.INNER_JOIN)
                    .on("orderId", JoinFrom.of("o","id"));
            builder.sourceBuilder().with(//demo for clickhouse
                    subBuilder -> { // sub0
                        subBuilder.resultKey("ol.orderId", "orderId")
                                .resultKey("ol.log","log").gt("ol.orderId",1).groupBy("ol.orderId").groupBy("ol.log");
                        subBuilder.sourceBuilder().with(
                                subBuilder1 -> { //sub1
                                    subBuilder1.resultKey("ot.orderId","orderId")
                                            .resultKey("ot.log","log")
                                            .sourceScript("FROM orderLog ot ")
                                            .groupBy("ot.orderId").groupBy("ot.log");
                                } ).alia("ol");
                    }).alia("l")
                    .join("ANY LEFT JOIN")
                    .on("orderId", JoinFrom.of("o","id"));
    
            builder.groupBy("o.id").sort("o.id", Direction.DESC);
            builder.paged().page(1).rows(10);
    
            Criteria.ResultMapCriteria resultMapCriteria = builder.build();
            return resultMapCriteria;
        }
    
    以上orderDtoBuilder方法可以在service里调用，或在controller里调用,
    且可以通过此插件sqli:gensql 生成sql
    
2. 在test里创建生成sql的测试类, 例如: OmsBuilderToSql.java
    
    @GenSql
    public class OmsBuilderToSql {
    
        private OmsBuilder omsBuilder = new OmsBuilder();
    
        public Criteria.ResultMapCriteria orderDtoBuilder(){
            OrderFindDto dto = new OrderFindDto();
            // 设置参数 .....
            return omsBuilder.orderDtoBuilder(dto);
        }
        
     }
     

3. 生成SQL:
    mvn clean install sqli:gensql
    
    生成的SQL文件: ${project}/.sql/OmsBuilderToSql.sql

    