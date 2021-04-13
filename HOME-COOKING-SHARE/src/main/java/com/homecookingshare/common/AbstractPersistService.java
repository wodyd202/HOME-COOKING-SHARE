package com.homecookingshare.common;

abstract public class AbstractPersistService<T> {
	protected Validator<T> validator;

	abstract protected void reigsterEntity(T obj);
	
	protected void afterValidation(T obj) {
	}
	
	protected void beforeValidation(T obj) {
	}
	
	public void register(T obj) {
		beforeValidation(obj);
		validator.validate(obj);
		afterValidation(obj);
		reigsterEntity(obj);
	}

	public AbstractPersistService(Validator<T> validate) {
		this.validator = validate;
	}
}
