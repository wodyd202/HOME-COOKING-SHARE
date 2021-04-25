package com.homecookingshare.common;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

abstract public class AbstractPersistService<T> {
	@Autowired
	protected ApplicationEventPublisher eventPublisher;
	protected Validator<T> validator;

	@Transactional
	abstract protected void reigsterEntity(T obj);
	
	@Transactional
	protected void afterValidation(T obj) {
	}

	@Transactional
	protected void beforeValidation(T obj) {
	}
	
	@Transactional
	public T register(T obj) {
		beforeValidation(obj);
		validator.validate(obj);
		afterValidation(obj);
		reigsterEntity(obj);
		return obj;
	}

	public AbstractPersistService(ApplicationEventPublisher eventPublisher, Validator<T> validate) {
		this(validate);
	}

	public AbstractPersistService(Validator<T> validate) {
		this.validator = validate;
	}
}
