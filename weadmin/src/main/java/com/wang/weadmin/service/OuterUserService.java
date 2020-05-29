package com.wang.weadmin.service;

/**
 * @description: Spring事务传播机制学习---外部事务
 * @author: WANG Y.G.
 * @time: 2020/04/30 15:37
 * @version: V1.0
 */
public interface OuterUserService {
    /*
     * Spring 中七种事务传播行为
     * Propagation.REQUIRED(默认) : 如果当前没有事务，就新建一个事务，如果已经存在事务，就加入当前事务中。
     * Propagation.SUPPORTS : 支持当前事务，如果当前没有事务，就以非事务方式执行。
     * Propagation.MANDATORY : 使用当前的事务，如果没有事务就抛出异常。必须在一个已有的事务中执行。
     * Propagation.REQUIRES_NEW : 不管是否存在事务，都创建新事务，当前事务挂起。
     * Propagation.NOT_SUPPORTED : 以非事务方式执行。如果当前存在事务，则当前事务挂起。
     * Propagation.NEVER : 以非事务方式执行。如果当前存在事务则抛出异常。
     * Propagation.NESTED : 如果当前存在事务，则嵌套事务执行。如果当前没有事务，则执行与Propagation.REQUIRED类似。
     */

    /*
     * REQUIRED,REQUIRES_NEW,NESTED 异同
     *
     * NESTED 和 REQUIRED 修饰的内部方法都属于外围方法事务，如果外围方法抛出异常，这两种方法的事务都会被回滚。
     * 但是 REQUIRED 是加入外围方法事务，所以和外围事务同属于一个事务，一旦 REQUIRED 事务抛出异常被回滚，外围方法事务也将被回滚。
     * 而 NESTED 是外围方法的子事务，有单独的保存点，所以 NESTED 方法抛出异常被回滚，不会影响到外围方法的事务。
     *
     * NESTED 和 REQUIRES_NEW 都可以做到内部方法事务回滚而不影响外围方法事务。
     * 但是因为 NESTED 是嵌套事务，所以外围方法回滚之后，作为外围方法事务的子事务也会被回滚。
     * 而 REQUIRES_NEW 是通过开启新的事务实现的，内部事务和外围事务是两个事务，外围事务回滚不会影响内部事务。
     *
     * ======================================================================================================
     * REQUIRED 和 SUPPORTS 在外围方法支持事务的时候没有区别，均加入外围方法的事务中。
	 * 当外围方法不支持事务，REQUIRED 开启新的事务，而 SUPPORTS 不开启事务。
	 * REQUIRED的事务一定和外围方法事务统一。如果外围方法没有事务，每一个内部 REQUIRED 方法都会开启一个新的事务，互不干扰。
     * ======================================================================================================
     * NOT_SUPPORTED明确表示不开启事务。
     * ======================================================================================================
     * https://github.com/TmTse/transaction-test
     * ======================================================================================================
     */
    /**
     * 通过场景一、二证明：
     * 在外围方法未开启事务的情况下Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     * 场景一：
     * 内部方法有事务，外部方法无事务且抛出异常
     */
    void requiredNoTransactionAndOuterException();

    /**
     * 场景二：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void requiredNoTransactionAndInnerException();

    /**
     * 通过场景三、四、五证明：
     * 在外围方法开启事务的情况下Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，
     * 所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚。
     *
     * 场景三：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void requiredWithTransactionAndOuterException();

    /**
     * 场景四：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void requiredWithTransactionAndInnerException();

    /**
     * 场景五：
     * 外部方法开启事务，内部方法有事务且抛异常，但异常被catch处理
     */
    void requiredWithTransactionAndTryInnerException();

    /**
     * 通过场景六、七证明：
     * 在外围方法未开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     *
     * 场景六：
     * 内部方法有事务，外部方法无事务且抛出异常
     */
    void requiresNewNoTransactionAndOuterException();

    /**
     * 场景七：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void requiresNewNoTransactionAndInnerException();

    /**
     * 通过场景八、九、十证明：
     * 在外围方法开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法依然会单独开启独立事务，
     * 且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。
     *
     * 场景八：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void requiresNewWithTransactionAndOuterException();

    /**
     * 场景九：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void requiresNewWithTransactionAndInnerException();

    /**
     *  场景十：
     *  外部方法开启事务，内部方法有事务且抛异常，但异常被catch处理
     */
    void requiresNewWithTransactionAndTryInnerException();

    /**
     * 通过场景十一、十二证明：
     * 在外围方法未开启事务的情况下Propagation.NESTED和Propagation.REQUIRED作用相同，修饰的内部方法都会新开启自己的事务，
     * 且开启的事务相互独立，互不干扰。
     *
     * 场景十一：
     * 内部方法有事务，外部方法无事务且抛出异常
     */
    void nestedNoTransactionAndOuterException();

    /**
     * 场景十二：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void nestedNoTransactionAndInnerException();

    /**
     * 通过场景十三、十四、十五证明：
     * 在外围方法开启事务的情况下Propagation.NESTED修饰的内部方法属于外部事务的子事务，外围主事务回滚，子事务一定回滚，
     * 而内部子事务可以单独回滚而不影响外围主事务和其他子事务。
     *
     * 场景十三：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void nestedWithTransactionAndOuterException();

    /**
     * 场景十四：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void nestedWithTransactionAndInnerException();

    /**
     * 场景十五：
     * 外部方法开启事务，内部方法有事务且抛异常，但异常被catch处理
     */
    void nestedWithTransactionAndTryInnerException();

    /**
     * 通过场景十六、十七证明：
     * 外部方法不存在事务时，内部方法以非事务方式运行。由于不存在事务，所以内部外部的异常均不会导致回滚。
     *
     * 场景十六：
     * 外部方法无事务但抛出异常，内部方法有事务
     */
    void supportsNoTransactionAndOuterException();

    /**
     * 场景十七：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void supportsNoTransactionAndInnerException();

    /**
     * 通过场景十八、十九、二十证明：
     * 外部方法存在事务时，内部方法使用当前事务，与Propagation.REQUIRED表现相同
     * 场景十八：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void supportsWithTransactionAndOuterException();

    /**
     * 场景十九：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void supportsWithTransactionAndInnerException();

    /**
     * 场景二十：
     * 外部方法开启事务，内部方法有事务且抛异常，但异常被catch处理
     */
    void supportsWithTransactionAndTryInnerException();

    /**
     * NOT_SUPPORTED明确表示不开启事务。
     *
     * 场景二十一：
     * 外部方法无事务但抛出异常，内部方法有事务
     */
    void notSupportedNoTransactionAndOuterException();

    /**
     * 场景二十二：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void notSupportedNoTransactionAndInnerException();

    /**
     * 场景二十三：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void notSupportedWithTransactionAndOuterException();

    /**
     * 场景二十四：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void notSupportedWithTransactionAndInnerException();

    /**
     * 场景二十五：
     * 外部方法开启事务，内部方法有事务且抛异常，但异常被catch处理
     */
    void notSupportedWithTransactionAndTryInnerException();

    /**
     * 场景二十六：
     * 外部方法无事务，内部方法有事务
     */
    void mandatoryNoTransaction();

    /**
     * 场景二十七：
     * 外部方法开启事务且抛出异常，内部方法有事务无异常
     */
    void mandatoryWithTransactionAndOuterException();

    /**
     * 场景二十八：
     * 外部方法开启事务，内部方法有事务且抛异常
     */
    void mandatoryWithTransactionAndInnerException();

    /**
     * 场景二十九：
     * 外部方法无事务但抛出异常，内部方法有事务
     */
    void neverNoTransactionAndOuterException();

    /**
     * 场景三十：
     * 外部方法无事务，内部方法有事务且抛出异常
     */
    void neverNoTransactionAndInnerException();

    /**
     * 场景三十一：
     * 外部方法开启事务
     */
    void neverWithTransaction();
}
