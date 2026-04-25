package org.hibernate.tutorial.envers;

import jakarta.persistence.PrePersist;
import org.hibernate.envers.RevisionListener;

public class ExampleListener implements RevisionListener {


    @PrePersist
    public void addUserName(Object o) {
        ExampleRevEntity e = (ExampleRevEntity) o;
        e.setUsername("Sithu");

    }


    @Override
    public void newRevision(Object o) {
        ExampleRevEntity e = (ExampleRevEntity) o;
        e.setUsername("Sithu");
    }
}
