package com.jh.jbook;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jh.mapper.UserMapper;
import com.jh.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest extends BaseMapperTest {
    @Test
    public void testL1Cache(){
        //获取SqlSession
        SqlSession sqlSession = getSqlSession();
        User user1 = null;
        try {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectById方法
            user1 = userMapper.selectByPrimaryKey(1);
            //对当前获取的对象重新赋值
            user1.setNickname("New Name");
            //再次查询获取id相同的用户
            User user2 = userMapper.selectByPrimaryKey(1);
            //虽然没有更新数据库,但是user1和user2的名字相同
            Assert.assertEquals("New Name",user2.getNickname());
            //无论如何user1和user2是同一个实例
            Assert.assertEquals(user1,user2);
        }finally {
            sqlSession.close();
        }
        System.out.println("开启新的SqlSession");
        //开启一个新的sqlsession
        sqlSession = getSqlSession();
        try {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user2 = userMapper.selectByPrimaryKey(1);
            Assert.assertNotEquals("New Name",user2.getNickname());
            //这里的user2和前一个session查询的结果是两个不同的实例
            Assert.assertNotEquals(user1,user2);
            //执行删除操作
            userMapper.deleteByPrimaryKey(2);
            //获取user3
            User user3 = userMapper.selectByPrimaryKey(1);
            //这里的user2和user3是两个不同的实例
            Assert.assertNotEquals(user2,user3);
        }finally {
            sqlSession.close();
        }
    }

}
