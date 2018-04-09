/*
package com.xt.mybatis.controller;

import com.xt.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;

*/
/**
 * Created with xt.
 * Date: 2018/3/20
 * Time: 17:06
 * Description:
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatistaFirstTest {

    //根据id查询用户信息，得到一条记录
    @Test
    public void findUserByIdTest() throws IOException {
        //mybatis配置文件
        String resource = "config/sqlMapConfig.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂，传入mybatis的配置文件信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //通过工厂得到sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //通过SqlSession操作数据库
        //第一个参数：映射文件中statement的id，等于=namespace+"."+statement的id
        //第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        //sqlSession.selectOne结果是与映射文件中所匹配的resultType类型的对象
        User user = sqlSession.selectOne("test.findUserById", 1);

        System.out.println(user);

        //释放资源
        sqlSession.close();
    }
}
*/
