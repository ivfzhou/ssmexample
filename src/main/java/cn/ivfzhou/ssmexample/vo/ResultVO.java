package cn.ivfzhou.ssmexample.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    private Integer code;

    private String msg;

    private Object data;

}
