package com.yuxing.trainee.test.infrastructure.dao.mapper;

import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 素材
 *
 * @author yuxing
 * @since 2022/01/17
 */
public interface GoodsMapper {


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return Goods
     */
    GoodsPO getById(Long id);

    /**
     * 查询全部
     *
     * @return 素材ID集合
     */
    List<Long> listAll();

    /**
     * 删除
     *
     * @param id id
     * @return 影响的条数
     */
    int deleteById(Long id);

    /**
     * 删除素材(逻辑删除)
     * @param id       ID
     * @param operator 操作人ID
     */
    void removeById(@Param("id") Long id, @Param("operator") Long operator);

    /**
    * 更新
    *
    * @param Goods 素材
    * @return 影响的条数
    */
    int updateById(GoodsPO Goods);

    /**
     * 插入
     *
     * @param Goods 分组
     * @return 影响的条数
     */
    int insert(GoodsPO Goods);


}