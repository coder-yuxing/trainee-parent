package com.yuxing.trainee.test.domain.repository;

import com.yuxing.trainee.test.domain.entity.Goods;
import com.yuxing.trainee.test.domain.valueobject.GoodsProp;

import java.util.List;

/**
 * 素材仓储
 *
 * @author yuxing
 * @since 2022/1/17
 */
public interface GoodsRepository {

    /**
     * 查询素材
     *
     * @param id ID
     * @return 素材 || null
     */
    Goods getById(Long id);


    /**
     * 保存素材
     *
     * @param goods 素材
     */
    void save(Goods goods);

    /**
     * 保存素材
     *
     * @param goods 素材
     * @param props 素材属性属性值
     */
    void save(Goods goods, List<GoodsProp> props);

    /**
     * 删除素材
     *
     * @param id       ID
     * @param operator 操作人ID
     */
    void removeById(Long id, Long operator);

    /**
     * 查询素材属性属性值
     *
     * @param id 素材ID
     * @return 素材属性属性值集合
     */
    List<GoodsProp> getPropsById(Long id);
}
