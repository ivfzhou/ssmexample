package cn.ivfzhou.ssmexample.service;

import com.github.pagehelper.PageInfo;

import cn.ivfzhou.ssmexample.entity.Item;

/**
 * 商品模块的service接口
 */
public interface ItemService {

    //1. 根据商品名称分页查询商品信息.
    PageInfo<Item> findProductByNameLimit(String name, Integer page, Integer size);

    //2. 执行添加
    void save(Item item);

    //3. 删除商品
    void deleteById(Integer id);

}
