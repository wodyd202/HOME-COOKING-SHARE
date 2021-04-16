package com.homecookingshare.common;

import javax.transaction.Transactional;

public abstract class AbstractRegisterService<T> extends AbstractPersistService<T> {

	@Transactional
	abstract protected void save(T obj);
	
	@Override
	protected void reigsterEntity(T obj) {
		save(obj);
	}

	public AbstractRegisterService(Validator<T> validate) {
		super(validate);
	}

}
