package cn.ivfzhou.ssmexample.mybatis.mapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

import cn.ivfzhou.ssmexample.SpringTests;
import cn.ivfzhou.ssmexample.entity.User;

public class UserMapperTest extends SpringTests {

    @Autowired
    private UserMapper userMapper;

    // 查询全部数据
    @Test
    public void selectAll() {
        //1. 执行分页.
        PageHelper.startPage(2, 5);
        //2. 执行分页查询.
        List<User> list = userMapper.selectAll();
        //3. 将list封装到PageInfo中.
        PageInfo<User> pageInfo = new PageInfo<>(list);
        //4. 输出
        System.out.println(pageInfo.getList());
        System.out.println("当前页:" + pageInfo.getPageNum());
        System.out.println("每页显示条数:" + pageInfo.getPageSize());
        System.out.println("当前页数据:" + pageInfo.getSize());
        System.out.println("数据的总条数:" + pageInfo.getTotal());
        System.out.println("总页数:" + pageInfo.getPages());
    }

    // 添加JPA注解.
    // @Table(name = "")    指定实体类映射表名
    // @Entity              当前类是一个实体类
    // @Id                  指定当前实体类的oid属性
    // @GeneratedValue(strategy=GenerationType.IDENTITY)        指定主键的生成策略.
    //      GenerationType.IDENTITY -> mysql     GenerationType.SEQUENCE -> oracle  序列.
    // @Transient           忽略当前属性,不与关系表产生映射关系.
    // @Column              手动映射当前属性和关系表的字段

    // 根据条件查询多条数据
    @Test
    public void select() {
        // 以param对象中不为null的属性,做等值判断.
        User param = new User();
        param.setUsername("admin123");
        List<User> list = userMapper.select(param);
        System.out.println(list);
    }

    // 根据条件查询一条数据
    @Test
    public void selectOne() {
        // 以param对象中不为null的属性,做等值判断.
        User param = new User();
        param.setUsername("admin123");
        // 只允许返回一个结果 | null
        User user = userMapper.selectOne(param);
        System.out.println(user);
    }

    // 查询数据条数.
    @Test
    public void selectCount() {
        User param = new User();
        param.setUsername("admin123");
        int count = userMapper.selectCount(param);
        System.out.println(count);
    }

    // 根据id查询
    @Test
    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1);
    }

    // 自定义条件查询.
    @Test
    public void selectByExample() {
        //1. 声明example
        Example example = new Example(User.class);
        //2. 创建Criteria. 单表查询之王.
        Example.Criteria c = example.createCriteria();
        //3. 添加条件.    =, >, >=, <, <=, between and, like, in, is null
        // id > 3 and salt is not  null
        c.andGreaterThan("id", 3);
        c.andIsNotNull("salt");

        List<User> list = userMapper.selectByExample(example);
    }

    // 增.
    @Test
    public void insert() {
        // 不会忽略user对象中为null的属性,添加全部.
        User user = userMapper.selectByPrimaryKey(1);
        user.setId(null);
        user.setPhone("13444444445");
        int count = userMapper.insert(user);
        System.out.println(count);
    }

    @Test
    public void insertSelective() {
        // 会忽略user对象中为null的属性,只添加不为null的属性.
        User user = new User();
        user.setUsername("zhangsan");
        userMapper.insertSelective(user);
    }

    // 改.
    @Test
    public void updateByPrimaryKey() {
        // 修改全部字段.
        User user = new User();
        user.setId(1L);
        user.setUsername("xxxxx");
        userMapper.updateByPrimaryKey(user);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        // 修改不为null的属性对应的字段,
        // 修改全部字段.
        User user = new User();
        user.setId(1L);
        user.setUsername("xxxxx");
        userMapper.updateByPrimaryKeySelective(user);
    }

    // 删.
    @Test
    public void delete() {
        userMapper.deleteByPrimaryKey(7);
    }

}