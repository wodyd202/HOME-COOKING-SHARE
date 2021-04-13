package com.homecookingshare.common;

public abstract class AbstractUpdateService<T> extends AbstractPersistService<T> {

	protected abstract void update(T obj);
	
	@Override
	protected void reigsterEntity(T obj) {
		update(obj);
	}

	public AbstractUpdateService(Validator<T> validate) {
		super(validate);
	}

}
