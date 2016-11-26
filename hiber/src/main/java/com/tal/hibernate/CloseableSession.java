package com.tal.hibernate;

import org.hibernate.Session;

public class CloseableSession implements AutoCloseable {

    private final Session session;

    public CloseableSession(Session session) {
        this.session = session;
    }

    public Session delegate() {
        return session;
    }

	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}



//    public void close() {
//        session.close();
//    }
}