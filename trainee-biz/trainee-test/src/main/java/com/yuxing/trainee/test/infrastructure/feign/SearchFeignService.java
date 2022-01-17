package com.yuxing.trainee.test.infrastructure.feign;

import com.yuxing.trainee.common.core.Pager;
import com.yuxing.trainee.common.core.Result;
import com.yuxing.trainee.search.api.goods.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.goods.query.EsGoodsQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 搜索服务接口
 *
 * @author yuxing
 * @since 2022/1/17
 */
@FeignClient(value = "${trainee-test.provider.search}")
public interface SearchFeignService {

    /*
     * 素材搜索服务接口
     */

    /**
     * 新建/更新单个文档
     */
    @PostMapping("/api/goods")
    Result<?> save(@RequestBody SaveGoodsDocCommand command);

    /**
     * 根据主键查询单个文档
     */
    @GetMapping("/api/goods/{id}")
    Result<EsGoodsDTO> getById(@PathVariable("id") Long id);

    /**
     * 删除文档
     */
    @DeleteMapping("/api/goods/{id}")
    Result<?> deleteById(@PathVariable("id") Long id);

    /**
     * 搜索
     */
    @PostMapping("/api/goods/search")
    Result<Pager<EsGoodsDTO>> search(@RequestBody EsGoodsQuery query);
}
