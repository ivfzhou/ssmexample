package cn.ivfzhou.ssmexample.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import cn.ivfzhou.ssmexample.entity.Item;
import cn.ivfzhou.ssmexample.enums.ExceptionInfoEnum;
import cn.ivfzhou.ssmexample.exception.SsmException;
import cn.ivfzhou.ssmexample.mybatis.mapper.ItemMapper;
import cn.ivfzhou.ssmexample.service.ItemService;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public PageInfo<Item> findProductByNameLimit(String name, Integer page, Integer size) {
        // 1. 分页助手开启分页
        PageHelper.startPage(page, size);
        // 2. 封装查询条件
        Example example = new Example(Item.class);
        Example.Criteria c = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            c.andLike("name", "%" + name + "%");
        }
        // 3. 执行查询
        List<Item> list = itemMapper.selectByExample(example);
        // List<Item> list = itemMapper.findByNameLike(name);
        // 4. 封装成PageInfo
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        // 5. 返回
        return pageInfo;
    }

    @Override
    @Transactional
    public void save(Item item) {
        int count = itemMapper.insertSelective(item);
        if (count != 1) {
            log.error("【添加商品】 添加商品失败!  item = {}", item);
            throw new SsmException(ExceptionInfoEnum.ITEM_ADD_ERROR);
        }
    }

    @Override
    public void deleteById(Integer id) {
        int count = itemMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            log.error("【删除商品】 删除商品失败!  id = {}", id);
            throw new SsmException(ExceptionInfoEnum.ITEM_DELETE_ERROR);
        }
    }

}
