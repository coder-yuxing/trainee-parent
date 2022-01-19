package com.yuxing.trainee.test;

import com.yuxing.trainee.core.Pager;
import com.yuxing.trainee.core.Result;
import com.yuxing.trainee.search.api.goods.constant.EsGoodsAggregationField;
import com.yuxing.trainee.search.api.goods.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.goods.query.EsGoodsQuery;
import com.yuxing.trainee.test.infrastructure.feign.SearchFeignService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 测试服务启动类
 *
 * @author yuxing
 */
@Slf4j
@RestController
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.yuxing.trainee.test.infrastructure.dao")
public class TraineeTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraineeTestApplication.class, args);
    }

    @Resource
    private SearchFeignService searchFeignService;

    @GetMapping("/api/test/goods/{id}")
    public Result<EsGoodsDTO> getById(@PathVariable Long id) {
        return searchFeignService.getById(id);
    }

    @PostMapping("/api/test/goods/search")
    public Result<Pager<EsGoodsDTO>> search(@RequestBody EsGoodsQuery query) {
        EsGoodsQuery.Aggregation category = new EsGoodsQuery.Aggregation("category", EsGoodsAggregationField.CATEGORY);
        EsGoodsQuery.Aggregation props = new EsGoodsQuery.Aggregation("props", EsGoodsAggregationField.PROP);
        query.setAggregations(Arrays.asList(category, props));
        return searchFeignService.search(query);
    }
}
