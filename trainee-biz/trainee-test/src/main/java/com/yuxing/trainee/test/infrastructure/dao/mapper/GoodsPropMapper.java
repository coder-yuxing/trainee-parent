package com.yuxing.trainee.test.infrastructure.dao.mapper;

import com.yuxing.trainee.test.infrastructure.dao.model.GoodsPropPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 素材属性属性值
 *
 * @author yuxing
 * @since 2022/01/17
 */
public interface GoodsPropMapper {


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return GoodsProp
     */
    GoodsPropPO getById(Long id);

    /**
     * 根据goods_id查询数据
     *
     * @param goodsId 素材ID
     * @return 全部素材属性属性值
     */
    List<GoodsPropPO> listByGoods(Long goodsId);

    /**
     * 删除
     *
     * @param id id
     * @return 影响的条数
     */
    int deleteById(Long id);

    /**
     * 删除素材属性属性值
     *
     * @param goodsId 素材ID
     */
    void deleteByGoodsId(Long goodsId);

    /**
    * 更新
    *
    * @param GoodsProp 分组
    * @return 影响的条数
    */
    int updateById(GoodsPropPO GoodsProp);

    /**
     * 插入
     *
     * @param GoodsProp 分组
     * @return 影响的条数
     */
    int insert(GoodsPropPO GoodsProp);

    /**
     * 批量插入
     * @param props
     */
    void batchInsert(@Param("props") List<GoodsPropPO> props);
}