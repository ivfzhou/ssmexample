package cn.ivfzhou.ssmexample.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import cn.ivfzhou.ssmexample.entity.Item;
import cn.ivfzhou.ssmexample.enums.ExceptionInfoEnum;
import cn.ivfzhou.ssmexample.exception.SsmException;
import cn.ivfzhou.ssmexample.service.ItemService;
import cn.ivfzhou.ssmexample.vo.ResultVO;

@Controller
@RequestMapping("/item")
@Slf4j
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Value("${pic.maxSize}")
    private Long picMaxSize;

    @Value("${pic.types}")
    private String types;

    // 商品列表
    @GetMapping("/list")
    public String list(String name, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size, Model model) {
        // 1. 映射请求路径和请求方式
        // 2. 接收请求参数
        // 3. 调用service查询数据,并获得PageInfo
        PageInfo<Item> pageInfo = itemService.findProductByNameLimit(name, page, size);
        // 4. 将数据放到request域中
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("name", name);
        // 5. 转发到item_list页面
        return "item/item_list";
    }

    // 跳转到添加页面
    @GetMapping("/add-ui")
    public String addUI() {
        return "item/item_add";
    }

    // 添加商品
    @PostMapping("/add")
    @ResponseBody
    public ResultVO add(MultipartFile picFile, @Valid Item item, BindingResult bindingResult, HttpServletRequest req) throws IOException {
        // 3. 在controller的方法参数上使用MultipartFile对象接收文件上传项.名字必须为请求参数的key
        // 4. 接收普通表单项Item item,并校验
        // 5. 文件上传项(非空判断)
        if (picFile == null || picFile.getSize() == 0) {
            // 没有文件上传项.
            log.info("【添加商品】 商品图片为必传项,岂能不传!!");
            throw new SsmException(ExceptionInfoEnum.PARAM_ERROR.getCode(), "商品图片为必传项,岂能不传!!");
        }
        // 5.1 大小判断
        if (picFile.getSize() > picMaxSize) {
            log.info("【添加商品】 图片大小过大, size = {}", picFile.getSize());
            throw new SsmException(ExceptionInfoEnum.PIC_GREATER);
        }
        //5.2 类型判断
        List<String> typeList = Arrays.asList(types.split(","));
        String fileName = picFile.getOriginalFilename();
        String typeName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!typeList.contains(typeName)) {
            log.info("【添加商品】 图片类型不正确!! typeName = {}", typeName);
            throw new SsmException(ExceptionInfoEnum.PIC_TYPE_ERROR);
        }
        // 5.3 是否损坏.
        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if (image == null) {
            log.info("【添加商品】 图片已损坏!! picFile = {}", picFile);
            throw new SsmException(ExceptionInfoEnum.PIC_BREAK);
        }
        // 5.4 新名字.
        String newName = UUID.randomUUID() + "." + typeName;
        String path = req.getServletContext().getRealPath("/") + "static/images/" + newName;
        // static/images/xxxxxx.jpg
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 5.5 保存到本地，picFile.transferTo(..)  临时文件copy的错误
        IOUtils.copy(picFile.getInputStream(), new FileOutputStream(file));
        // 5.6 将图片的访问路径设置到item对象中
        String pic = req.getContextPath() + "/static/images/" + newName;
        item.setPic(pic);
        // 6. 校验普通表单项.
        if (bindingResult.hasErrors()) {
            String msg = bindingResult.getFieldError().getDefaultMessage();
            log.info("【添加商品】参数不正确 item = {},msg = {}", item, msg);
            throw new SsmException(ExceptionInfoEnum.PARAM_ERROR.getCode(), msg);
        }
        // 7. 调用serivce保存
        itemService.save(item);
        // 8. 响应数据
        return new ResultVO(0, "成功", null);
    }

    // 删除商品
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVO del(@PathVariable Integer id) {
        itemService.deleteById(id);
        return new ResultVO(0, "成功", null);
    }

}
