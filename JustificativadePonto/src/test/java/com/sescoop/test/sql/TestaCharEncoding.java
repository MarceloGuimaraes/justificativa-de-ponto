package com.sescoop.test.sql;

import junit.framework.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/7/13
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestaCharEncoding {

    @Test
    public void verificandoEncoding(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/justificativadeponto", "root", "itamonte");

            PreparedStatement stmt = connection.prepareStatement("insert into user (userId, ativo, nome, cpf, email, senha) " +
                    "values (100, true, 'Róger Çaricô', '120.120.201-09', 'rcar@mail.com', 'senha')");
            stmt.execute();

            stmt = connection.prepareStatement("select userId, nome from user where userId=100");
            ResultSet rs = stmt.executeQuery();

            try {
                while(rs.next()) {
                    String nome = rs.getString("nome");
                    Long id = rs.getLong("userId");
                    Assert.assertEquals("O nome deve ser ", "Róger Çaricô", nome);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                rs.close();
                stmt.close();
                stmt = connection.prepareStatement("delete from user where userId=100");
                stmt.execute();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
