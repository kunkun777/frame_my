package com.zxc.from.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
@ApiModel
public class PageVO<T> {
    @ApiModelProperty("总记录数")
    private Long totalRows;
    @ApiModelProperty("总页数")
    private Integer totalPages;
    @ApiModelProperty("当前页码")
    private Integer pageNum;
    @ApiModelProperty("每页的记录数")
    private Integer curPageSize;
    @ApiModelProperty("数据列表")
    private List<T> data;
}
