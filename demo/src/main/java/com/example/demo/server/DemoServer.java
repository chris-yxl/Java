package com.example.demo.server;

import com.example.demo.dao.DemoDAO;
import com.example.demo.dto.DemoDTO;
import com.example.demo.util.ResponseResult;
import com.example.demo.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author  chris
 * @className DemoServer
 * @date
 * @description:
 */
@Service
public class DemoServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServer.class);

    @Autowired
    @Qualifier("demods")
    private DataSource demods;

    @Autowired
    private DemoDAO demoDAO;

    @Autowired
    private SqlUtil sqlUtil;

    @Autowired
    private SqlServer sqlServer;

    //通过入参替换sql中的#{}来拼接成正常sql并执行
    @Transactional
    public ResponseResult<String> TestSql(DemoDTO data) throws Exception{
        LOGGER.info("解析sql测试开始：");
        //查询需要拼接的sql
        List<Map<Object, Object>> maps = demoDAO.querySQL();
        //连接数据库
        Connection conn=null;
        conn=demods.getConnection();
        LOGGER.info("conn:{}",conn);
        conn.setAutoCommit(false);
        //拼接sql
        for (int i = 0; i < maps.size(); i++) {
            Object o = maps.get(i).get("sqlURL");
            String newsql = sqlUtil.sqlAnalysis((String) o, data);
            LOGGER.info("拼接完成，需要执行的sql：{}",newsql);
            //执行sql
            try{
                sqlServer.sqlUpdate("",conn,newsql);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //提交数据
        conn.commit();
        LOGGER.info("测试结束！");
        ResponseResult<String> result=null;
        return  result;
    }
}
