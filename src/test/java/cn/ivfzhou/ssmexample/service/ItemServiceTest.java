package cn.ivfzhou.ssmexample.service;

import com.github.pagehelper.PageInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

import cn.ivfzhou.ssmexample.SpringTests;
import cn.ivfzhou.ssmexample.entity.Item;

public class ItemServiceTest extends SpringTests {

    @Autowired
    private ItemService itemService;

    @Test
    public void findProductByNameLimit() {
        PageInfo<Item> pageInfo = itemService.findProductByNameLimit(" ", 22, 5);
        for (Item item : pageInfo.getList()) {
            System.out.println(item);
        }
    }

    @Test
    @Transactional
    public void save() {
        Item item = new Item();
        item.setId(1L);
        item.setName("xxx");
        item.setPrice(new BigDecimal("12.3"));
        item.setProductionDate(new Date());
        item.setDescription("yyyyyyy");
        item.setPic("zzzzzzzzzzz");
        itemService.save(item);
    }

}