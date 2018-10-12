package cn.mingyuliu.halo.test;

import cn.mingyuliu.halo.Application;
import cn.mingyuliu.halo.common.enums.AllProfiles;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Copyright (c) 1999-2018 携程旅行网
 * all rights reserved
 * <pre>
 *     base test
 * </pre>
 *
 * @author : liumy2009@126.com
 * date : 2018/09/07
 */
@ActiveProfiles(AllProfiles.TEST)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Resource
    protected TestRestTemplate testRestTemplate;

}
