package org.application.util;
import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;


/**
 * 
 * @author Vinicius Trind
 * @see http://stackoverflow.com/questions/25988818/deploying-java-schedule-with-wildfly-8-1-0-final
 */
@Singleton
@Startup
public class Scheduler {
  
  @Inject
  EntityManager em;

  @PostConstruct
  private void init(){

    if (em != null){
     System.out.println("EntityManager inject is working");
    }

     System.out.println("EntityManager inject is not working");
  }




}


