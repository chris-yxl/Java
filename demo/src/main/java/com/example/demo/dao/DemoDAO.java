package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author  chris
 * @className DemoDAO
 * @date
 * @description:
 */
@Mapper
public interface DemoDAO {
    List<Map<Object,Object>> querySQL();
}
