package com.yuxing.trainee.search.application.facade;

import com.yuxing.trainee.common.core.Pager;
import com.yuxing.trainee.search.api.command.SaveGoodsDocCommand;
import com.yuxing.trainee.search.api.dto.EsGoodsDTO;
import com.yuxing.trainee.search.api.query.EsGoodsQuery;

/**
 * 素材搜索门面接口
 *
 * @author yuxing
 * @since 2022/1/14
 */
public interface EsGoodsSearchFacadeService {

    /**
     * 创建素材文档
     *
     * @param command 新建文档命令
     */
    void saveDoc(SaveGoodsDocCommand command);

    /**
     * 查询文档
     *
     * @param id 素材ID
     * @return 素材文档 || null
     */
    EsGoodsDTO getById(Long id);

    /**
     * 删除文档
     *
     * @param id 素材ID
     */
    void deletedById(Long id);

    /**
     * 素材文档分页查询
     *
     * @param query 查询条件
     * @return 单页文档数据
     */
    Pager<EsGoodsDTO> search(EsGoodsQuery query);
}
