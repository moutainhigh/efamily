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


public class EjlHelp extends FmlBaseEntity {

	private static final long serialVersionUID = 123232323L;
	//alias
	public static final String TABLE_ALIAS = "EjlHelp";
	public static final String ALIAS_QUESTION = "question";
	public static final String ALIAS_ANSWER = "answer";
	public static final String ALIAS_DESC = "desc";
	public static final String ALIAS_IMAGES = "images";
	
	//date formats
	
	//columns START
	private String question;
	private String answer;
	private String descb;
	private String images;
	//columns END

	public EjlHelp(){
	}

	public EjlHelp(
		Long id
	){
		this.id = id;
	}

	public void setQuestion(String value) {
		this.question = value;
	}
	
	public String getQuestion() {
		return this.question;
	}
	public void setAnswer(String value) {
		this.answer = value;
	}
	
	public String getAnswer() {
		return this.answer;
	}
	public void setDescb(String value) {
		this.descb = value;
	}
	
	public String getDescb() {
		return this.descb;
	}
	public void setImages(String value) {
		this.images = value;
	}
	
	public String getImages() {
		return this.images;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Question",getQuestion())		
		.append("Answer",getAnswer())		
		.append("Desc",getDescb())		
		.append("Images",getImages())		
		.append("GmtCreated",getGmtCreated())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getQuestion())
		.append(getAnswer())
		.append(getDescb())
		.append(getImages())
		.append(getGmtCreated())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof EjlHelp == false) return false;
		if(this == obj) return true;
		EjlHelp other = (EjlHelp)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getQuestion(),other.getQuestion())

		.append(getAnswer(),other.getAnswer())

		.append(getDescb(),other.getDescb())

		.append(getImages(),other.getImages())

		.append(getGmtCreated(),other.getGmtCreated())

			.isEquals();
	}
}

