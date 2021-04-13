package com.homecookingshare.common;

public abstract class AbstractRegisterService<T> extends AbstractPersistService<T> {

	abstract protected void save(T obj);
	
	@Override
	protected void reigsterEntity(T obj) {
		save(obj);
	}

	public AbstractRegisterService(Validator<T> validate) {
		super(validate);
	}

}
