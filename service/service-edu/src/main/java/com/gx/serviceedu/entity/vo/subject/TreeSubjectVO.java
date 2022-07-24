package com.gx.serviceedu.entity.vo.subject;

import lombok.Data;

import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/18 9:46
 */
@Data
public class TreeSubjectVO {
    private String id;
    private String title;
    private List<TreeSubjectVO> children;
}
