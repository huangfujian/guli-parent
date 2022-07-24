package com.gx.serviceedu.entity.vo.chapter;
import com.gx.serviceedu.entity.vo.video.VideoVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/19 8:39
 */
@Data
public class ChapterVO implements Serializable {
    private static final long serialVersionUID = 8018027411973128961L;
    private String id;
    @ApiModelProperty(value = "章节名称")
    private String title;
    @ApiModelProperty(value = "课程Id")
    private String courseId;
    @ApiModelProperty(value = "序号")
    private Integer sort;
    private List<VideoVO> children = new ArrayList<>();
}
