Introdução
Configurando a aplicação

Detalhes
A configuração se dá em 2 passos, primeiro configurar o encoding do servidor MySQL e segundo configurar os recursos JNDI.

Configurando o encoding

O encoding utilizado será latin1 com collate latin1_swedish_ci. Para isso, será informado no servidor que este é o default encoding no arquivo my.cnf. Para informações em como configurar o my.cnf clique aqui

Dentro do arquivo adicionar o seguinte:

 [client] default-character-set = latin1
 
[mysql] default-character-set = latin1
 

[mysqld] character-set-client-handshake = FALSE
 character-set-server = latin1
 collation-server = latin1_swedish_ci
 

A aplicação precisa de 2 recursos JNDI configurados no servidor que for hospedá-la. As configurações abaixo são específicas para o Apache Tomcat versão 6 ou superior.

Entrar no arquivo <DIRETÓRIO_DE_INSTALACAO_TOMCAT>/conf/Context.xml

 <!-- MySql Datasource, no caso do banco ser outro trocar o driverClassName e colcocar a url apropriada -->
 
<Resource name="jdbc/sescoopDB" auth="Container" 
           type="javax.sql.DataSource" 
           maxActive="100" maxIdle="30" maxWait="10000" 
           username="[usuario-do-banco]" password="[senha-do-banco]" 
           driverClassName="com.mysql.jdbc.Driver" 
           url="jdbc:mysql://localhost/justificativadeponto"/>
 

<!-- JavaMail session factory -->
 

<Resource name="mail/session"
           auth="Container"
           type="javax.mail.Session"
           username="[conta-do-usuario@mailserver.com]"
           password="[senha-da-conta-de-email]"
           mail.user="[conta-do-usuario@mailserver.com]"
           mail.password="[senha-da-conta-de-email]"
           mail.transport.protocol="smtp"
           mail.smtp.host="smtp.mailserver.com"
           mail.smtp.port="587"
           mail.smtp.auth="true"
           mail.smtp.starttls.enable="true"
           mail.debug="true"
 />
 

O mais importante nas configurações são o tipo e o nome do recurso.

Depois de configurar o arquivo de context, tem que colocar as seguintes librarys no diretório <DIRETÓRIO_DE_INSTALACAO_TOMCAT>/lib:

activation.jar
mail.jar
Depois de configurar o arquivo de context, tem que colocar as seguintes librarys no diretório <DIRETÓRIO_DE_INSTALACAO_TOMCAT>/lib:

activation.jar
mail.jar
mysql-connector-java-5.xxx.jar

