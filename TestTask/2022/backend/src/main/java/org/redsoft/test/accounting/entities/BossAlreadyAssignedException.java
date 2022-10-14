package org.redsoft.test.accounting.entities;

public class BossAlreadyAssignedException extends EntityCustomException {
    public BossAlreadyAssignedException(String name) {
        super(String.format("The boss has already assigned for department \"%s\"!", name));
    }
}
