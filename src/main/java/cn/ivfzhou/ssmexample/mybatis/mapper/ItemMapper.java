package cn.ivfzhou.ssmexample.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

import cn.ivfzhou.ssmexample.entity.Item;

/**
 * 商品表的mapper接口
 */
public interface ItemMapper extends Mapper<Item> {

    // 根据商品名称模糊查询
    List<Item> findByNameLike(@Param("name") String name);

}
