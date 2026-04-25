/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tutorial.annotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import junit.framework.TestCase;

import static java.lang.System.out;
import static java.time.LocalDateTime.now;

/**
 * Illustrates the use of Hibernate native APIs, including the use
 * of org.hibernate.boot for configuration and bootstrap.
 * Configuration properties are sourced from hibernate.properties.
 *
 * @author Steve Ebersole
 */
public class HibernateIllustrationTest extends TestCase {
	private SessionFactory sessionFactory;

	@Override
	protected void setUp() {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry =
				new StandardServiceRegistryBuilder()
						.build();
		try {
			sessionFactory =
					new MetadataSources(registry)
							.addAnnotatedClass(Event.class)
							.addAnnotatedClass(Message.class)
							.buildMetadata()
							.buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we
			// had trouble building the SessionFactory so destroy it manually.
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	@Override
	protected void tearDown() {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	/*public void testBasicUsage() {
		// create a couple of events...
        LocalDateTime now = now();
        Event temp = new Event("A follow up event", now);
		sessionFactory.inTransaction(session -> {
			session.persist(new Event("Our very first event!", now));
			session.persist(temp);
		});

        temp.setTitle("A follow up event updated.");

        sessionFactory.inTransaction(session -> {

            session.merge(temp);
        });

		// now lets pull events from the database and list them
		sessionFactory.inTransaction(session -> {
			session.createSelectionQuery("from Event", Event.class).getResultList()
					.forEach(event -> out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
		});
	}*/

    public void testFetchTypeUsage() {
        // create a couple of events...
        LocalDateTime now = now();
        Event temp = new Event("A follow up event", now);

        Message message1 = new Message("Message 1", now(),temp);
        Message message2 = new Message("Message 2", now(),temp);
        sessionFactory.inTransaction(session -> {
            session.persist(new Event("Our very first event!", now));
            session.persist(temp);
            session.persist(message1);
            session.persist(message2);
        });

//        temp.setTitle("A follow up event updated.");
//
//        sessionFactory.inTransaction(session -> {
//
//            session.merge(temp);
//        });

        // now lets pull events from the database and list them
        sessionFactory.inTransaction(session -> {

            List<Event> eventList = session.createSelectionQuery("from Event", Event.class).getResultList();
//            eventList.forEach(event -> {out.println("Event (" + event.getDate() + ") : " + event.getTitle());
//                        event.getMessages().forEach( message -> out.println(message.toString()) ); } );

            for( Event event : eventList ) {
                out.println(event);
                for( Message message : event.getMessages() ) {
                    out.println( message );
                }
            }


        });
    }
}
