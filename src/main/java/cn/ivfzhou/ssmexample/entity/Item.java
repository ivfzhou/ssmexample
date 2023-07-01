package cn.ivfzhou.ssmexample.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "商品名称为必填项,岂能不填!!")
    private String name;

    @NotNull(message = "商品价格为必填项,岂能不填!!")
    private BigDecimal price;

    @NotNull(message = "商品生产日期为必填项,岂能不填!!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;

    @NotBlank(message = "商品描述为必填项,岂能不填!!")
    private String description;

    private String pic;

    private Date created;

}
