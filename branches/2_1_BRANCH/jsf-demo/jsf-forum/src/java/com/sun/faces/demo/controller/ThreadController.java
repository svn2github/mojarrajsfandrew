/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.faces.demo.controller;

import com.sun.faces.demo.model.Messages;
import com.sun.faces.demo.model.Threads;
import com.sun.faces.demo.model.Topics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 *
 * @author netdance
 */
@ManagedBean(name = "thread")
@RequestScoped
public class ThreadController implements Serializable {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private String title = null;
    private String text = null;
    private Threads thread = null;
    private Integer threadId = null;
    private Topics topic = null;
    private String topicId = null;

    public ThreadController() {

        FacesContext context = FacesContext.getCurrentInstance();

        topicId = context.getExternalContext().getRequestParameterMap().get("topicId");
        String thid = context.getExternalContext().getRequestParameterMap().get("threadId");

        // if threadId not set, we need the topicId
        if (thid == null) {
            if (topicId == null || !topicId.matches("[0-9]+")) {
                FacesMessage message = new FacesMessage(
                        "Parameter topicId not set correctly, " + topicId);
                context.addMessage(null, message);
                return;
            }
            // Since threadId not set, we're making a new thread
            thread = new Threads();
            return;
        }

        try {
            threadId = Integer.valueOf(thid);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(
                    "Parameter threadId not set correctly, " + thid);
            context.addMessage(null, message);
            thread = new Threads();
            return;
        }
        if (threadId == null || threadId.intValue() <= 0) {
            thread = new Threads();
            return;
        }

    }

    @PostConstruct
    private void init() {

        FacesContext context = FacesContext.getCurrentInstance();

        EntityManager em = emf.createEntityManager();

        if (thread == null) {
            try {
                Query query = em.createNamedQuery("Threads.findByThreadid");
                query.setParameter("threadid", threadId);
                thread = (Threads) query.getSingleResult();
            } catch (Exception e) {
                FacesMessage message = new FacesMessage((e.getLocalizedMessage()));
                context.addMessage(null, message);
                thread = new Threads();
            }
        }

        topic = thread.getTopicid();

        if (topic == null) {
            try {
                Query query = em.createNamedQuery("Topics.findByTopicid");
                query.setParameter("topicid", Integer.valueOf(topicId));
                topic = (Topics) query.getSingleResult();
            } catch (Exception e) {
                FacesMessage message = new FacesMessage((e.getLocalizedMessage()));
                context.addMessage(null, message);
                topic = new Topics();
            }
        }
        em.close();
        thread.setTopicid(topic);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        thread.setTitle(title);
        this.title = title;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        if (thread.getThreadid() == null) {
            return 0;
        } else {
            return thread.getThreadid();
        }
    }

    public Topics getParent() {
        return topic;
    }

    public Collection<Messages> getMessages() {
        return thread.getMessagesCollection();
    }

    public void createThread() {
        EntityManager em = emf.createEntityManager();

        Messages message = new Messages();
        message.setSubject(title);
        message.setText(text);
        message.setThreadid(thread);

        try {
            em.getTransaction().begin();
            em.persist(message);
            Collection<Messages> cm = thread.getMessagesCollection();
            if (cm == null) {
                cm = new ArrayList();
            }
            cm.add(message);
            thread.setMessagesCollection(cm);
            em.persist(thread);
            Collection<Threads> ct = topic.getThreadsCollection();
            if (ct == null) {
                ct = new ArrayList();
            }
            ct.add(thread);
            topic.setThreadsCollection(ct);
            em.merge(topic);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
