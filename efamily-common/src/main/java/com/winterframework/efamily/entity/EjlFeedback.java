 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.winterframework.efamily.entity;



import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.efamily.core.base.FmlBaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class EjlFeedback extends FmlBaseEntity {
	
	private static final long serialVersionUID = 213343434343l;
	//alias
	public static final String TABLE_ALIAS = "EjlFeedback";
	public static final String ALIAS_CONTACTS = "contacts";
	public static final String ALIAS_CONTACTS_METHOD = "contactsMethod";
	public static final String ALIAS_CONTENT = "content";
	public static final String ALIAS_FILES = "files";
	
	//date formats
	
	//columns START
	private String contacts;
	private String contactsMethod;
	private String content;
	private String files;
	//columns END

	public EjlFeedback(){
	}

	public EjlFeedback(
		Long id
	){
		this.id = id;
	}

	public void setContacts(String value) {
		this.contacts = value;
	}
	
	public String getContacts() {
		return this.contacts;
	}
	public void setContactsMethod(String value) {
		this.contactsMethod = value;
	}
	
	public String getContactsMethod() {
		return this.contactsMethod;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setFiles(String value) {
		this.files = value;
	}
	
	public String getFiles() {
		return this.files;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Contacts",getContacts())		
		.append("ContactsMethod",getContactsMethod())		
		.append("Content",getContent())		
		.append("Files",getFiles())		
		.append("GmtCreated",getGmtCreated())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getContacts())
		.append(getContactsMethod())
		.append(getContent())
		.append(getFiles())
		.append(getGmtCreated())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlFeedback == false) return false;
		if(this == obj) return true;
		EjlFeedback other = (EjlFeedback)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getContacts(),other.getContacts())

		.append(getContactsMethod(),other.getContactsMethod())

		.append(getContent(),other.getContent())

		.append(getFiles(),other.getFiles())

		.append(getGmtCreated(),other.getGmtCreated())

			.isEquals();
	}
}

