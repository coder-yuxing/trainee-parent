package com.yuxing.trainee.search;

import com.yuxing.trainee.common.core.Pager;
import com.yuxing.trainee.common.core.Result;
import com.yuxing.trainee.search.api.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.query.EsGoodsQuery;
import com.yuxing.trainee.search.application.facade.EsGoodsSearchFacadeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 搜索服务启动类
 *
 * @author yuxing
 * @since 2022/1/14
 */
@RestController
@SpringBootApplication
public class TraineeSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraineeSearchApplication.class, args);
    }

    @Resource
    private EsGoodsSearchFacadeService esGoodsSearchFacadeService;

    @PostMapping("/goods")
    public Result<?> save(@RequestBody SaveGoodsDocCommand command) {
        esGoodsSearchFacadeService.saveDoc(command);
        return Result.success();
    }

    @GetMapping("/goods/{id}")
    public Result<EsGoodsDTO> getById(@PathVariable Long id) {
        return Result.success(esGoodsSearchFacadeService.getById(id));
    }

    @DeleteMapping("/goods/{id}")
    public Result<?> deleteById(@PathVariable Long id) {
        esGoodsSearchFacadeService.deletedById(id);
        return Result.success();
    }

    @GetMapping("/goods/search")
    public Result<Pager<EsGoodsDTO>> search(EsGoodsQuery query) {
        return Result.success(esGoodsSearchFacadeService.search(query));
    }
}
