package per.posse.tool.interceptor;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by posse on 17-7-24.
 */
public class HibernateSessionInterceptor implements Interceptor {
    @Override
    public boolean onLoad(Object o, Serializable serializable, Object[] objects, String[] strings,
            Type[] types) throws CallbackException {
        System.out.println("load operation print by hibernateSessionInterceptor");
        return false;
    }

    @Override
    public boolean onFlushDirty(Object o, Serializable serializable, Object[] objects, Object[] objects1,
            String[] strings, Type[] types) throws CallbackException {
        System.out.println("flush operation print by hibernateSessionInterceptor");
        return false;
    }

    @Override
    public boolean onSave(Object o, Serializable serializable, Object[] objects, String[] strings,
            Type[] types) throws CallbackException {
        System.out.println("save operation print by hibernateSessionInterceptor");
        return false;
    }

    @Override
    public void onDelete(Object o, Serializable serializable, Object[] objects, String[] strings,
            Type[] types) throws CallbackException {
        System.out.println("delete operation print by hibernateSessionInterceptor");
    }

    @Override
    public void onCollectionRecreate(Object o, Serializable serializable) throws CallbackException {

    }

    @Override
    public void onCollectionRemove(Object o, Serializable serializable) throws CallbackException {

    }

    @Override
    public void onCollectionUpdate(Object o, Serializable serializable) throws CallbackException {

    }

    @Override
    public void preFlush(Iterator iterator) throws CallbackException {

    }

    @Override
    public void postFlush(Iterator iterator) throws CallbackException {

    }

    @Override public Boolean isTransient(Object o) {
        return null;
    }

    @Override
    public int[] findDirty(Object o, Serializable serializable, Object[] objects, Object[] objects1,
            String[] strings, Type[] types) {
        return new int[0];
    }

    @Override
    public Object instantiate(String s, EntityMode entityMode, Serializable serializable)
            throws CallbackException {
        System.out.println("instantiate print by hibernateSessionInterceptor");
        return null;
    }

    @Override
    public String getEntityName(Object o) throws CallbackException {
        System.out.println("get entity name operation print by hibernateSessionInterceptor");
        return null;
    }

    @Override
    public Object getEntity(String s, Serializable serializable) throws CallbackException {
        System.out.println("get entity operation print by hibernateSessionInterceptor");
        return null;
    }

    @Override
    public void afterTransactionBegin(Transaction transaction) {
        System.out.println("transaction begin print by hibernateSessionInterceptor");
    }

    @Override
    public void beforeTransactionCompletion(Transaction transaction) {
        System.out.println(transaction.getStatus());
        System.out.println("before transaction completion print by hibernateSessionInterceptor");
    }

    @Override
    public void afterTransactionCompletion(Transaction transaction) {
        System.out.println(transaction.getStatus());
        System.out.println("after transaction completion print by hibernateSessionInterceptor");
    }

    @Override
    public String onPrepareStatement(String s) {
        System.out.println("prepare statement print by hibernateSessionInterceptor");
        return null;
    }
}
