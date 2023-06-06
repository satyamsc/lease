package com.allene.lease.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Custom identifier generator for generating lease contract IDs.
 */
public class LeaseContractIdGenerator implements IdentifierGenerator {

    /**
     * Generates a unique lease contract ID.
     *
     * @param sharedSessionContractImplementor The shared session contract implementor
     * @param o                              The object for which the ID is being generated
     * @return The generated lease contract ID
     * @throws HibernateException If an error occurs during ID generation
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException {
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        return random.nextLong(9000000) + 1000000;
    }
}
