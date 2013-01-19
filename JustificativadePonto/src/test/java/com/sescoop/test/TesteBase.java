package com.sescoop.test;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/11/13
 * Time: 7:50 PM
 * Classe base para usar nos testes
 */
@SpringApplicationContext("application.xml")
public abstract class TesteBase extends UnitilsJUnit4 {

    @SpringApplicationContext
    private ApplicationContext applicationContext;

}
