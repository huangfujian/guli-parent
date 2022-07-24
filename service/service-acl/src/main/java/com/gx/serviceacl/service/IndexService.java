package com.gx.serviceacl.service;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/30 12:49
 */
@Service
public interface IndexService {
    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);
    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);
}
