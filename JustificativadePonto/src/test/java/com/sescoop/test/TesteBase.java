package com.sescoop.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/11/13
 * Time: 7:50 PM
 * Classe base para usar nos testes
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application.xml")
public abstract class TesteBase {

}
