

INSTALL

install 
	https://spring.io/tools/sts/all

open prospective git
 	clone repository git:   redelmondo77/util
 	import maven project /applicazione

spring
	maven>update project


HOME

md C:\dev\
md C:\dev\home\
md C:\dev\home\applicazione
md C:\dev\home\applicazione\config
md C:\dev\home\applicazione\sessionStore
md C:\dev\home\applicazione\storage
md C:\dev\home\applicazione\tomcat
md C:\dev\home\applicazione\tomcatLogging
echo #HOMECONFIG  > C:\dev\home\applicazione\config\application.yml

override application properties  with  application.yml



WEB TEST

run as > spring boot app
http://localhost/applicazione



DEPLOY

## spring boot as fat jar
run as>maven install
  [INFO] Installing .... to C:\dev\.m2\repository\it\applicazione\applicazione\0.0.1-SNAPSHOT\applicazione-0.0.1-SNAPSHOT.war

copy C:\dev\.m2\repository\it\applicazione\applicazione\0.0.1-SNAPSHOT\applicazione-0.0.1-SNAPSHOT.war C:\dev\home\applicazione



RUN

java -jar C:\dev\home\applicazione\applicazione-0.0.1-SNAPSHOT.war --spring.config.location=C:/dev/home/applicazione/config/application.yml



DB CONSOLE 

http://localhost/applicazione/h2




LINKS

https://getbootstrap.com/docs/4.0/

https://fontawesome.com/icons?d=gallery

https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html

    ${...} : Variable expressions.
    *{...} : Selection expressions.
    #{...} : Message (i18n) expressions.
    @{...} : Link (URL) expressions.
    ~{...} : Fragment expressions.

use scss
https://getbootstrap.com/docs/4.0/getting-started/theming/
https://github.com/warmuuh/libsass-maven-plugin
https://www.w3schools.com/html/html_layout.asp
https://www.w3schools.com/bootstrap/default.asp


