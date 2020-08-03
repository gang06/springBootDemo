package com.example.demo.mapper;

import com.example.demo.model.RegisterAuthDO;
import com.example.demo.model.RegisterAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * com.example.demo.mapper.RegisterAuthMapper
 *
 * @author EDZ
 * @since 2020-08-03
 */
@Mapper
public interface RegisterAuthMapper {
    /**
     * 根据example条件查询记录总数
     * 
     * @param example 查询参数
     * @return 满足条件的记录总数
     */
    long countByExample(RegisterAuthExample example);

    /**
     * 根据example参数删除记录
     * 
     * @param example 执行参数
     * @return 删除记录条数
     */
    int deleteByExample(RegisterAuthExample example);

    /**
     * 根据主键删除记录
     * 
     * @param id 自增id
     * @return 删除记录条数
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入一条记录
     * 
     * @param record param
     * @return 插入记录条数
     */
    int insert(RegisterAuthDO record);

    /**
     * 根据example查询记录别表
     * 
     * @param example 查询参数
     * @return 查询记录列表
     */
    List<RegisterAuthDO> selectByExample(RegisterAuthExample example);

    /**
     * 分页查询
     * 
     * @param example 查询参数
     * @param offset offset
     * @param limit limit
     * @return 查询记录列表
     */
    List<RegisterAuthDO> limitSelectByExample(@Param("example") RegisterAuthExample example, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 根据主键查询记录
     * 
     * @param id 自增id
     * @return 查询记录
     */
    RegisterAuthDO selectByPrimaryKey(Long id);

    /**
     * 根据example条件更新记录，record里面为null的字段跳过更新
     * 
     * @param record 需要更新的字段
     * @param example 更新条件
     * @return 更新记录条数
     */
    int updateByExampleSelective(@Param("record") RegisterAuthDO record, @Param("example") RegisterAuthExample example);

    /**
     * 根据example更新记录，记录会由record里面的字段完全覆盖，包含值null的字段
     * 
     * @param record 需要更新的字段
     * @param example 更新条件
     * @return 更新记录条数
     */
    int updateByExample(@Param("record") RegisterAuthDO record, @Param("example") RegisterAuthExample example);

    /**
     * 根据主键更新记录，record里面为null的字段跳过更新
     * 
     * @param record 需要更新的字段
     * @return 更新记录条数
     */
    int updateByPrimaryKeySelective(RegisterAuthDO record);

    /**
     * 根据主键更新记录，记录会由record里面的字段完全覆盖，包含值为null的字段
     * 
     * @param record 需要更新的字段
     * @return 更新记录条数
     */
    int updateByPrimaryKey(RegisterAuthDO record);
}