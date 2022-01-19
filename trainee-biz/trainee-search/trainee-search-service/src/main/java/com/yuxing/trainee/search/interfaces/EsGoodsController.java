package com.yuxing.trainee.search.interfaces;

import com.yuxing.trainee.core.Pager;
import com.yuxing.trainee.core.Result;
import com.yuxing.trainee.search.api.goods.command.BatchSaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.goods.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.goods.query.EsGoodsQuery;
import com.yuxing.trainee.search.application.facade.EsGoodsSearchFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 素材搜索接口
 *
 * @author yuxing
 * @since 2022/1/16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/goods")
public class EsGoodsController {

    private final EsGoodsSearchFacadeService esGoodsSearchFacadeService;

    /**
     * 新建/更新单个文档
     */
    @PostMapping
    public Result<?> save(@RequestBody @Valid SaveGoodsDocCommand command) {
        esGoodsSearchFacadeService.saveDoc(command);
        return Result.success();
    }

    /**
     * 批量保存
     */
    @PostMapping("/all")
    public Result<?> batchSave(@RequestBody @Valid BatchSaveGoodsDocCommand command) {
        esGoodsSearchFacadeService.batchSaveDocs(command);
        return Result.success();
    }

    /**
     * 根据主键查询单个文档
     */
    @GetMapping("/{id}")
    public Result<EsGoodsDTO> getById(@PathVariable("id") Long id) {
        return Result.success(esGoodsSearchFacadeService.getById(id));
    }

    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    public Result<?> deleteById(@PathVariable("id") Long id) {
        esGoodsSearchFacadeService.deletedById(id);
        return Result.success();
    }

    /**
     * 搜索
     */
    @PostMapping("/search")
    public Result<Pager<EsGoodsDTO>> search(@RequestBody EsGoodsQuery query) {
        return Result.success(esGoodsSearchFacadeService.search(query));
    }
}
