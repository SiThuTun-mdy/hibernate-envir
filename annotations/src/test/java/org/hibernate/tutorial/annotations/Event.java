/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.tutorial.annotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Events")
public class Event {

	@Id
	@GeneratedValue
    private Long id;

    private String title;

	@Column(name = "eventDate")
    private LocalDateTime date;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Message> messages = new ArrayList<>();

	public Event() {
		// this form used by Hibernate
	}

	public Event(String title, LocalDateTime date) {
		// for application use, to create new events
		this.title = title;
		this.date = date;
	}

    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }

    public LocalDateTime getDate() {
		return date;
    }

    public void setDate(LocalDateTime date) {
		this.date = date;
    }

    public String getTitle() {
		return title;
    }

    public void setTitle(String title) {
		this.title = title;
    }

	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public void addMessage(String body, LocalDateTime postedAt) {
		Message message = new Message(body, postedAt, this);
		messages.add(message);
	}

	public void removeMessage(Message message) {
		messages.remove(message);
		message.setEvent(null);
	}
}
