package ${packageName};

import ${beanClassName};

/**
 * ${remarks}
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${name} {


    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return ${beanName}
     */
    ${beanName} getById(Long id);

    /**
     * 删除
     *
     * @param id id
     * @return 影响的条数
     */
    int deleteById(Long id);

    /**
    * 更新
    *
    * @param ${paramName} 分组
    * @return 影响的条数
    */
    int updateById(${beanName} ${paramName});

    /**
     * 插入
     *
     * @param ${paramName} 分组
     * @return 影响的条数
     */
    int insert(${beanName} ${paramName});
}