package com.wang.weadmin.service.impl;

import com.wang.weadmin.entity.pojo.User1;
import com.wang.weadmin.entity.pojo.User2;
import com.wang.weadmin.service.OuterUserService;
import com.wang.weadmin.service.User1Service;
import com.wang.weadmin.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:44
 * @version: V1.0
 */
@Service
public class OuterUserServiceImpl implements OuterUserService {

    @Autowired
    private User1Service user1Service;
    @Autowired
    private User2Service user2Service;

    @Override
    public void requiredNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
            外部方法未开启事务，插入“张三”、“李四”的操作在自己的事务中独立运行，不受外部方法异常干扰
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.required(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void requiredNoTransactionAndInnerException() {
        /*
            张三正常、李四回滚
            外部方法未开启事务，插入“张三”、“李四”的操作在自己的事务中独立运行，所以“李四”方法中抛出异常回滚，“张三”不受影响
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiredWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void requiredWithTransactionAndOuterException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，内部方法加入外部的事务，外部方法抛出异常回滚，内部也跟着回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.required(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void requiredWithTransactionAndInnerException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，内部方法加入外部的事务，内部方法抛出异常，外部方法感知到异常导致整体回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiredWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void requiredWithTransactionAndTryInnerException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，内部方法加入外部的事务，内部方法抛出异常，即使异常被捕获不被外部方法感知，整个事务依然回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        try {
            user2Service.requiredWithException(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void requiresNewNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
            外部方法未开启事务，插入“张三”、“李四”的操作在自己的事务中独立运行，不受外部方法异常干扰
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.requiresNew(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiresNew(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void requiresNewNoTransactionAndInnerException() {
        /*
            张三正常、李四回滚
            外部方法未开启事务，插入“张三”、“李四”的操作在自己的事务中独立运行，所以“李四”方法中抛出异常回滚，“张三”不受影响
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.requiresNew(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiresNewWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void requiresNewWithTransactionAndOuterException() {
        /*
            张三回滚、李四正常、王五正常
            外部方法开启事务，张三和外部方法时同一事务，李四和王五分别在独立的新建事务中，当外围方法抛出异常时只回滚
            和外部方法同一事务的方法，故张三回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiresNew(user2);

        User2 user3 = new User2();
        user3.setUserName("王五");
        user2Service.requiresNew(user3);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void requiresNewWithTransactionAndInnerException() {
        /*
            张三回滚、李四正常、王五回滚
            外部方法开启事务，张三和外部方法时同一事务，李四和王五分别在独立的新建事务中，王五发生异常导致回滚时，外部方法感知异常导致回滚，
            因此张三也回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiresNew(user2);

        User2 user3 = new User2();
        user3.setUserName("王五");
        user2Service.requiresNewWithException(user3);
    }

    @Override
    public void requiresNewWithTransactionAndTryInnerException() {
        /*
            张三正常、李四正常、王五回滚
            外部方法开启事务，张三和外部方法时同一事务，李四和王五分别在独立的新建事务中，王五发生异常导致回滚，但异常被catch不会被
            外部方法感知，外部事务不回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.required(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.requiresNew(user2);

        User2 user3 = new User2();
        user3.setUserName("王五");
        try {
            user2Service.requiresNewWithException(user3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void nestedNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
            外部方法未开启事务，张三、李四在自己的事务中独立运行，外部方法的异常不会影响内部事务。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.nested(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.nested(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void nestedNoTransactionAndInnerException() {
        /*
            张三正常、李四回滚
            外部方法未开启事务，张三、李四在自己的事务中独立运行，因此李四抛出异常时只回滚李四，张三不受影响。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.nested(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.nestedWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void nestedWithTransactionAndOuterException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，内部事务为外部事务的子事务，故当外部事务回滚时内务事务也需回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.nested(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.nested(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void nestedWithTransactionAndInnerException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，内部事务为外部事务的子事务，内部方法抛出异常回滚，且外部方法感知到异常导致整体事务回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.nested(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.nestedWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void nestedWithTransactionAndTryInnerException() {
        /*
            张三正常、李四回滚
            外部方法开启事务，内部事务为外部事务的子事务，李四内部异常，外部事务可以对子事务单独回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.nested(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        try {
            user2Service.nestedWithException(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supportsNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
            外部方法未开启事务，内部方法张三、李四也未开启事务，因为不存在事务所以无论外部方法或者内部方法抛出异常都不会回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.supports(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.supports(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void supportsNoTransactionAndInnerException() {
        /*
            张三正常、李四正常
            外部方法未开启事务，内部方法张三、李四也未开启事务，因为不存在事务所以无论外部方法或者内部方法抛出异常都不会回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.supports(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.supportsWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void supportsWithTransactionAndOuterException() {
        /*
            张三回滚、李四回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.supports(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.supports(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void supportsWithTransactionAndInnerException() {
        /*
            张三回滚、李四回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.supports(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.supportsWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void supportsWithTransactionAndTryInnerException() {
        /*
            张三回滚、李四回滚
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.supports(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        try {
            user2Service.supportsWithException(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void notSupportedNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.notSupported(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.notSupported(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void notSupportedNoTransactionAndInnerException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.notSupported(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.notSupportedWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void notSupportedWithTransactionAndOuterException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.notSupported(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.notSupported(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void notSupportedWithTransactionAndInnerException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.notSupported(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.notSupportedWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void notSupportedWithTransactionAndTryInnerException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.notSupported(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        try {
            user2Service.notSupportedWithException(user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void mandatoryNoTransaction() {
        /*
            抛出异常：
            org.springframework.transaction.IllegalTransactionStateException:
            No existing transaction found for transaction marked with propagation 'mandatory'
            外部方法未开启事务，导致内部张三方法执行时因为没有事务而直接抛出异常，内部方法没有机会执行。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.mandatory(user1);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void mandatoryWithTransactionAndOuterException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，张三和李四方法都加入外部事务。外部方法抛出异常，整个事务回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.mandatory(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.mandatory(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void mandatoryWithTransactionAndInnerException() {
        /*
            张三回滚、李四回滚
            外部方法开启事务，张三和李四方法都加入外部事务。内部方法抛出异常，整个事务回滚。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.mandatory(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.mandatoryWithException(user2);
    }

    @Override
    public void neverNoTransactionAndOuterException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.never(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.never(user2);

        throw new RuntimeException("外部方法抛出异常");
    }

    @Override
    public void neverNoTransactionAndInnerException() {
        /*
            张三正常、李四正常
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.never(user1);

        User2 user2 = new User2();
        user2.setUserName("李四");
        user2Service.neverWithException(user2);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void neverWithTransaction() {
        /*
            抛出异常：
            org.springframework.transaction.IllegalTransactionStateException:
            Existing transaction found for transaction marked with propagation 'never'
            外部方法开启了事务。内部插入“张三”方法执行时，由于存在事务而直接抛出异常，插入方法没有机会执行。
         */
        User1 user1 = new User1();
        user1.setUserName("张三");
        user1Service.never(user1);
    }

}
