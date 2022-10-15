package org.redsoft.test.accounting.entities;

public class PositionAlreadyCreatedException extends EntityCustomException {
    public PositionAlreadyCreatedException(String name) {
        super(String.format("Position \"%s\" already created!", name));
    }
}
