package com.zhangpingyang.springsecurity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangpingyang.springsecurity.dao.UserDao;
import com.zhangpingyang.springsecurity.dao.topic.ReplyDao;
import com.zhangpingyang.springsecurity.dao.topic.TopicContentDao;
import com.zhangpingyang.springsecurity.dao.topic.TopicDao;
import com.zhangpingyang.springsecurity.entity.Authority;
import com.zhangpingyang.springsecurity.entity.User;
import com.zhangpingyang.springsecurity.entity.react.Reply;
import com.zhangpingyang.springsecurity.entity.react.Topic;
import com.zhangpingyang.springsecurity.entity.react.TopicContent;
import com.zhangpingyang.springsecurity.enumeration.AuthorityEnum;
import com.zhangpingyang.springsecurity.enumeration.react.TopicType;
import com.zhangpingyang.springsecurity.service.UserService;
import com.zhangpingyang.springsecurity.util.MyHttpUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringsecurityApplicationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private TopicDao topicDao;
    @Autowired
    private TopicContentDao topicContentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReplyDao replyDao;

    @Test
    public void test10() {

    }
    @Test
    @Transactional
    @Rollback(false)
    public void initTopic() throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\administritor\\Desktop\\x.txt")));
//        StringBuilder sb = new StringBuilder();
//        String s;
//        while ((s = in.readLine()) != null) {
//            sb.append(s);
//        }
//        String topic = sb.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(new File("C:\\Users\\zhangpingyang\\Desktop\\x.txt"), Map.class);
//        System.out.println(map);
        List list = (List) map.get("data");
        Date now = new Date();
        User user = userService.getUserById(1L);
        for (Object o : list) {
            Map m = (Map) o;
//            System.out.println(o);
            Topic t = new Topic();
            t.setCreateTime(now);
            t.setGood((boolean)m.get("good")?1:0);
            t.setLastReplyAt(now);
            t.setReplyCount((Integer) m.get("reply_count"));
            Object tab = m.get("tab");
            TopicType topicType = TopicType.valueOf((String) tab);
            t.setTab(topicType);
            t.setTitle((String) m.get("title"));
            t.setTop((boolean) m.get("top")?1:0);
            String content = (String) m.get("content");
            TopicContent topicContent = new TopicContent();
            topicContent.setContent(content);
//            S save = topicContentDao.save(content);
            TopicContent save = topicContentDao.save(topicContent);
            t.setTopicContent(topicContent);
            t.setVisitCount((Integer) m.get("visit_count"));
//            Map author = (Map) m.get("author");
            t.setUser(user);
            t.setId((String) m.get("id"));
            Topic save1 = topicDao.save(t);
        }


    }
    @Test
    @Transactional
    @Rollback(false)
    public void testHttpClient() throws IOException {

        Map<String, String> param = new HashMap<>();
        param.put("mdreader", "false");//不进行渲染
        List<Topic> all = topicDao.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String queryStr = "mdrender=false";
        int flag = 0;
        for (Topic topic : all) {
            if (!topic.getId().equals("5d5cbb25421846662d983a25")) {
//            if (topic.getId().equals("5b8de66137b3005a0b0e6b3f")) {
//                flag++;
                continue;
            }
//            if (flag == 0) {
//                continue;
//            }
            //查询所有主题的回复
            long start = System.currentTimeMillis();
            String s = MyHttpUtil.clientGet("https://cnodejs.org/api/v1/topic/" + topic.getId(), queryStr, /*param,*/ 50000);
            long end = System.currentTimeMillis();
            System.out.println((end - start) / 1000 + "s");
            System.out.println(s);
            if (s == null) {
                System.out.println("urlurlurl " + topic.getId());
            }
            Map map = mapper.readValue(s, Map.class);
            Map data = (Map) map.get("data");
            if (data == null) {
                System.out.println("topic Id = " + topic.getId());
                continue;
            }
            List replies = (List) data.get("replies");
            if (replies == null) {
                continue;
            }
            for (Object reply : replies) {
                Map replyObj = (Map) reply;
                Reply r = new Reply();
                r.setId((String) replyObj.get("id"));
                //由于爬取的用户我这边都不存在所以新建一个
                Map author = (Map) replyObj.get("author");
                User user = this.fillUser(author);
                r.setTopic(topic);
                r.setUser(user);
                r.setContent((String) replyObj.get("content"));
                r.setReplyId((String) replyObj.get("reply_id"));
                r.setCreateTime(new Date());
                replyDao.save(r);
            }
        }

//        String s = MyHttpUtil.clientGet("https://cnodejs.org/api/v1/topic/5433d5e4e737cbe96dcef312", param, 10000);

//        Map map1 = mapper.readValue(s, Map.class);
//
//        System.out.println(s);
    }

    private User fillUser(Map author){
        String loginname = (String) author.get("loginname");
        String avatar_url = (String) author.get("avatar_url");
        User user = userDao.findByLoginname(loginname);
        Date now = new Date();
        Authority authority = new Authority();
        authority.setAuthority(AuthorityEnum.ROLE_USER);
        ArrayList<Authority> authorities = new ArrayList<>();
        if (user == null) {
            user = new User();
            user.setLoginname(loginname);
            user.setAvatarUrl(avatar_url);
            user.setCreateTime(now);
            user.setAuthorities(authorities);
            user.setPassword("$2a$10$P7n1ZKRyCH1QX69XlfxkweFK3TdS9zcEWFwn.DjmyL4deh5cDtDfa");
            user.setLastModifyTm(now);
            user.setUsername(loginname);
            user.setFirstName(loginname);
            user.setLastName(loginname);
            user.setEnable(true);
            user.setEmail("285442936@qq.com");
            user.setPhone("18516293517");
            userDao.save(user);

        }
        return user;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() throws JsonProcessingException {
//        Page<User> userList = userService.getUserList(1, 20);
        System.out.println();
//        System.out.println(ObjectMapperUtil.getObjectMapper().writeValueAsString(userList));
    }

    @Test
    public void test2() {
        User user = new User();
        user.setEmail("285442936@qq.com");
        user.setEnable(true);
        user.setFirstName("zhang");
        user.setLastName("pingyang");
        user.setPassword("123");
        user.setUsername("zhangpingyang");
        user.setLastModifyTm(new Date());
        user.setCreateTime(new Date());
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(1));
        user.setAuthorities(authorities);
        try {
            User user1 = userService.insertUser(user);
        } catch (DataIntegrityViolationException e) {

            System.out.println("duplicate");
        }
        System.out.println();
    }

    /**
     * 删除user
     */
    @Test
    public void test3() {
//        User zhangpingyang = userService.getUserByUsername("zhangpingyang");
//        userService.deleteUser(zhangpingyang.getUserId());
    }

}
