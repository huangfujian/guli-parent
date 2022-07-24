package com.gx.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.gx.servicebase.config.exception.GuliException;
import com.gx.serviceedu.entity.EduSubject;
import com.gx.serviceedu.entity.vo.excel.ExcelSubjectData;
import com.gx.serviceedu.entity.vo.listener.SubjectExcelListener;
import com.gx.serviceedu.entity.vo.subject.TreeSubjectVO;
import com.gx.serviceedu.mapper.EduSubjectMapper;
import com.gx.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author FL
 * @since 2022-06-17
 */
@Service
@Transactional(readOnly = true)
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile multipartFile, EduSubjectService eduSubjectService) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20002, "添加课程失败");
        }

    }

    /**
     * 获取树形结构的
     *
     * @return
     */
    @Override
    public List<TreeSubjectVO> getTreeSubject() {
        List<TreeSubjectVO> treeSubjectVOList = new ArrayList<>();
        List<EduSubject> eduSubjectAll = baseMapper.selectList(null); //相同地址值
        Map<String, TreeSubjectVO> eduSubjectMap = new HashMap<>(); //相同地址值
        Map<String, TreeSubjectVO> eduSubjectParentMap = new HashMap<>();//一级菜单
        for (EduSubject eduSubject : eduSubjectAll) {
            TreeSubjectVO treeSubjectVO = new TreeSubjectVO();
            BeanUtils.copyProperties(eduSubject, treeSubjectVO);
            String id = treeSubjectVO.getId();
            eduSubjectMap.put(id, treeSubjectVO);
            String pid = eduSubject.getParentId();
            if (pid.equals("0")) {//等于0
                eduSubjectParentMap.put(id, treeSubjectVO);//这里先把地址给存起来,后期直接输出这个好了
            }
        }
        //再次进行遍历
        for (EduSubject eduSubject : eduSubjectAll) {
            String pid = eduSubject.getParentId();
            TreeSubjectVO oneTreeSubject = eduSubjectMap.get(pid);
            //一级菜单
            if (oneTreeSubject != null) {
                //二级菜单
                TreeSubjectVO twoSubjectVO = new TreeSubjectVO();
                BeanUtils.copyProperties(eduSubject, twoSubjectVO);
                List<TreeSubjectVO> children = oneTreeSubject.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    oneTreeSubject.setChildren(children);
                }
                children.add(twoSubjectVO);
            }
        }
        Set<String> keySet = eduSubjectParentMap.keySet();
        //将map集合中转成list集合
        for (String key : keySet) {
            TreeSubjectVO treeSubjectVO = eduSubjectParentMap.get(key);
            treeSubjectVOList.add(treeSubjectVO);
        }
        return treeSubjectVOList;
    }
}
