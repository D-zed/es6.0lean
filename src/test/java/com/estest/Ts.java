package com.estest;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import tonatyw.es.util.ESGroupby;
import tonatyw.es.util.ESQueryBuilderConstructor;
import tonatyw.es.util.ESQueryBuilders;
import tonatyw.es.util.ElasticSearchService;

import java.util.HashMap;
import java.util.Map;

public class Ts {

    ElasticSearchService ess;
    String index;
    String type;
    @Before
    public void pre(){
        // 添加es
//         index = "test";
//         type = "testtype";

        index = "cars";
        type = "product";
        ess = new ElasticSearchService();
    }

    @Test
    public void createIndex(){
         ess.createIndex(index, type);
    }

    @Test
    public void bulkInsertData(){
                // 加入数据
        Map<String,Object> mainMap = new HashMap<String,Object>();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("name", "刘琦");
        param.put("num", 1);
        param.put("begin_date", "2019-02-11 10:20:10");

        Map<String,Object> param1 = new HashMap<String,Object>();
        param1.put("name", "王霸");
        param1.put("num", 2);
        param1.put("begin_date", "2019-02-10 06:07:06");
        mainMap.put(DigestUtils.md5Hex(JSON.toJSONString(param)), param);
        mainMap.put(DigestUtils.md5Hex(JSON.toJSONString(param1)), param1);
       // ess.bulkInsertData(index, type, mainMap);
        ess.bulkInsertDataProcesser(index,type,mainMap);
    }

    @Test
    public void search(){
        //查询构造器
        ESQueryBuilderConstructor esbc = new ESQueryBuilderConstructor();
        //查询对象
        ESQueryBuilders esb = new ESQueryBuilders();
        //查询对象
        ESQueryBuilders esbor = new ESQueryBuilders();
        //查询对象
        ESQueryBuilders esbnot = new ESQueryBuilders();
        //分组对象
        ESGroupby esgb = new ESGroupby("num");
        //分组的排序
        esgb.desc(2);
        //度量方法
        esgb.avg("num","avgNum");
        //设置分组对象
        esbc.setEsGroupby(esgb);
        esb.term("name", "张");
        esbc.must(esb);
        esbor.match("name","刘");
        esbor.match("name","赵");
        esbc.should(esbor);
        esbnot.match("name","王");
        esbc.mustNot(esbnot);

        //                   -----------------------------------------------高亮字段
        Map<String,Object> data = ess.search(index, type, esbc, new String[]{"name","num"});
        data.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }

    @Test
    public void search2(){
        //查询构造器
        ESQueryBuilderConstructor esbc = new ESQueryBuilderConstructor();
        //查询对象
        ESQueryBuilders esb = new ESQueryBuilders();
        //查询对象
        ESQueryBuilders esbor = new ESQueryBuilders();
        //查询对象
        ESQueryBuilders esbnot = new ESQueryBuilders();
        esbor.term("num",1);
        //如果此时存在并列的must条件那么should条件将无效
        esbc.should(esbor);
        //                   -----------------------------------------------高亮字段
        Map<String,Object> data = ess.search(index, type, esbc, new String[]{});
        data.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }

    //先聚合为桶再聚合度量
    @Test
    public void searchAggCar(){
        //查询构造器
        ESQueryBuilderConstructor esbc = new ESQueryBuilderConstructor();
        ESGroupby esGroupby = new ESGroupby("color");
        esGroupby.desc(1);
        esGroupby.avg("price","avgPrice");
        esbc.setEsGroupby(esGroupby);
        esbc.setSize(0);

        //------------高亮字段
        Map<String,Object> data = ess.search(index, type, esbc, new String[]{});
        data.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }

    //先聚合为桶再聚合为桶
    @Test
    public void searchAggCar2(){
        //查询构造器
        ESQueryBuilderConstructor esbc = new ESQueryBuilderConstructor();
        ESGroupby esGroupby = new ESGroupby("color");

        //------------高亮字段
        Map<String,Object> data = ess.search(index, type, esbc, new String[]{});
        data.forEach((key,value)->{
            System.out.println(key);
            System.out.println(value);
        });
    }
}
