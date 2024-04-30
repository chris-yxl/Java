package com.example.demo.util;

import com.example.demo.dto.DemoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  chris
 * @className SqlUtil
 * @date
 * @description:sql解析
 */
@Component
public class SqlUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlUtil.class);

    public String sqlAnalysis(String sql, DemoDTO dto){
        try {
            Map<Object, Object> map = new HashMap<>();
            Field[] fields = dto.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String firstLetter = fields[i].getName().substring(0, 1).toUpperCase();
                String getter="get"+firstLetter+fields[i].getName().substring(1);
                Method method=dto.getClass().getMethod(getter,new Class[]{});
                map.put(fields[i].getName(),method.invoke(dto,new Object[]{}));
            }
            Matcher matcher= Pattern.compile("\\#\\{([a-zA-Z]+)\\}").matcher(sql);
            while (matcher.find()){
                sql=sql.replace(matcher.group(0),"'"+map.get(matcher.group(1)).toString()+"'");
            }
        }catch (Exception e){
            LOGGER.error("sql解析异常，sql={},dto={}",sql,dto);
            throw new RuntimeException("sql解析出现异常！");
        }
        return sql;
    }
}
