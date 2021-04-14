package com.homecookingshare.common;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractUpdateService<T> extends AbstractPersistService<T> {

	@Transactional
	protected abstract void update(T obj);
	
	@Override
	protected void reigsterEntity(T obj) {
		update(obj);
	}

	public AbstractUpdateService(Validator<T> validate) {
		super(validate);
	}

}
