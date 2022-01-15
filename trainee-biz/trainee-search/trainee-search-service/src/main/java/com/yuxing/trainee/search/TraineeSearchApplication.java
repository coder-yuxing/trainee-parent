package com.yuxing.trainee.search;

import com.yuxing.trainee.common.core.Pager;
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
    public String save(@RequestBody SaveGoodsDocCommand command) {
        esGoodsSearchFacadeService.saveDoc(command);
        return "success";
    }

    @GetMapping("/goods/{id}")
    public Object getById(@PathVariable Long id) {
        return esGoodsSearchFacadeService.getById(id);
    }

    @DeleteMapping("/goods/{id}")
    public String deleteById(@PathVariable Long id) {
        esGoodsSearchFacadeService.deletedById(id);
        return "success";
    }

    @GetMapping("/goods/search")
    public Pager<EsGoodsDTO> search(EsGoodsQuery query) {
        return esGoodsSearchFacadeService.search(query);
    }
}
