package com.ups.cra.icc.idugroute.idugdemo;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MainApp {

	public static void main(String[] args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		ActiveMQComponent activeMQComponent = new ActiveMQComponent();
		activeMQComponent.setBrokerURL("tcp://172.30.84.159:61616");
		//activeMQComponent.setBrokerURL("tcp://localhost:61616");
		activeMQComponent.setUserName("admin");
	    activeMQComponent.setPassword("admin");
	   
		context.addComponent("activemq", activeMQComponent);
		context.addRoutes(new ShipmentRoute());
		context.start();
		

		/*old code*/
		/*
		 Main main = new Main(); 
		 main.enableHangupSupport();
		 main.addRouteBuilder(new ShipmentRoute());
		 main.run(args);
		*/ 
	}

}
