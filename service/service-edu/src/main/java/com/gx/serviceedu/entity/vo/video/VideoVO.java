package com.gx.serviceedu.entity.vo.video;
import lombok.Data;
import java.io.Serializable;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/19 8:41
 */
@Data
public class VideoVO implements Serializable {
    private static final long serialVersionUID = 779825074768179985L;
    private String id;
    private String title;
    private Boolean free; //是否为免费的
    private String videoSourceId;
}
