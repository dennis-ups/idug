package com.ups.cra.icc.idugroute.idugdemo;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apacheextras.camel.component.couchbase.CouchbaseConstants;

public class ShipmentRoute extends RouteBuilder {

	Random random = new Random();
	@Override
	public void configure() throws Exception {
		
		onException(Exception.class).process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				Throwable t = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
						Throwable.class);

				if (t != null)
					System.out.print("Exception message is: " + t.getMessage());
			}
		});


		
	    from("activemq:topic:mqtt.SensorTag")	 
		.process(new Processor(){

			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("Shipment Route running : " + exchange.getIn().getBody(String.class));	
			}
		})
		
		.setHeader(CouchbaseConstants.HEADER_ID,constant(random.nextInt())).id("shipmentHeaderId")
		//.to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT)
		.to("couchbase:http://172.30.10.67:11210/shipment?operation=" + CouchbaseConstants.COUCHBASE_PUT)
		.end();
		 
		
		/* sample code
		//from("direct:deviceInsert").marshal().json(JsonLibrary.Jackson).to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT);
		from("direct:deviceInsert")
		.setHeader(CouchbaseConstants.HEADER_ID, constant(2))
		.to("couchbase:http://localhost:11210/idug-sample?operation=" + CouchbaseConstants.COUCHBASE_PUT);
		*/
		
		
	}

}
