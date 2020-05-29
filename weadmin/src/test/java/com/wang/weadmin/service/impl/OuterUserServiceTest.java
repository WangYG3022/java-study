package com.wang.weadmin.service.impl;

import com.wang.weadmin.WeAdminApplication;
import com.wang.weadmin.service.OuterUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeAdminApplication.class)
public class OuterUserServiceTest {

    @Autowired
    private OuterUserService outerUserService;

    @Test
    public void requiredNoTransactionAndOuterException() {
        outerUserService.requiredNoTransactionAndOuterException();
    }

    @Test
    public void requiredNoTransactionAndInnerException() {
        outerUserService.requiredNoTransactionAndInnerException();
    }

    @Test
    public void requiredWithTransactionAndOuterException() {
        outerUserService.requiredWithTransactionAndOuterException();
    }

    @Test
    public void requiredWithTransactionAndInnerException() {
        outerUserService.requiredWithTransactionAndInnerException();
    }

    @Test
    public void requiredWithTransactionAndTryInnerException() {
        outerUserService.requiredWithTransactionAndTryInnerException();
    }

    @Test
    public void requiresNewNoTransactionAndOuterException() {
        outerUserService.requiresNewNoTransactionAndOuterException();
    }

    @Test
    public void requiresNewNoTransactionAndInnerException() {
        outerUserService.requiresNewNoTransactionAndInnerException();
    }

    @Test
    public void requiresNewWithTransactionAndOuterException() {
        outerUserService.requiresNewWithTransactionAndOuterException();
    }

    @Test
    public void requiresNewWithTransactionAndInnerException() {
        outerUserService.requiresNewWithTransactionAndInnerException();
    }

    @Test
    public void requiresNewWithTransactionAndTryInnerException() {
        outerUserService.requiresNewWithTransactionAndTryInnerException();
    }

    @Test
    public void nestedNoTransactionAndOuterException() {
        outerUserService.nestedNoTransactionAndOuterException();
    }

    @Test
    public void nestedNoTransactionAndInnerException() {
        outerUserService.nestedNoTransactionAndInnerException();
    }

    @Test
    public void nestedWithTransactionAndOuterException() {
        outerUserService.nestedWithTransactionAndOuterException();
    }

    @Test
    public void nestedWithTransactionAndInnerException() {
        outerUserService.nestedWithTransactionAndInnerException();
    }

    @Test
    public void nestedWithTransactionAndTryInnerException() {
        outerUserService.nestedWithTransactionAndTryInnerException();
    }

    @Test
    public void supportsNoTransactionAndOuterException() {
        outerUserService.supportsNoTransactionAndOuterException();
    }

    @Test
    public void supportsNoTransactionAndInnerException() {
        outerUserService.supportsNoTransactionAndInnerException();
    }

    @Test
    public void supportsWithTransactionAndOuterException() {
        outerUserService.supportsWithTransactionAndOuterException();
    }

    @Test
    public void supportsWithTransactionAndInnerException() {
        outerUserService.supportsWithTransactionAndInnerException();
    }

    @Test
    public void supportsWithTransactionAndTryInnerException() {
        outerUserService.supportsWithTransactionAndTryInnerException();
    }

    @Test
    public void notSupportedNoTransactionAndOuterException() {
        outerUserService.notSupportedNoTransactionAndOuterException();
    }

    @Test
    public void notSupportedNoTransactionAndInnerException() {
        outerUserService.notSupportedNoTransactionAndInnerException();
    }

    @Test
    public void notSupportedWithTransactionAndOuterException() {
        outerUserService.notSupportedWithTransactionAndOuterException();
    }
    @Test
    public void notSupportedWithTransactionAndInnerException() {
        outerUserService.notSupportedWithTransactionAndInnerException();
    }

    @Test
    public void notSupportedWithTransactionAndTryInnerException() {
        outerUserService.notSupportedWithTransactionAndTryInnerException();
    }

    @Test
    public void mandatoryNoTransaction() {
        outerUserService.mandatoryNoTransaction();
    }
    @Test
    public void mandatoryWithTransactionAndOuterException() {
        outerUserService.mandatoryWithTransactionAndOuterException();
    }
    @Test
    public void mandatoryWithTransactionAndInnerException() {
        outerUserService.mandatoryWithTransactionAndInnerException();
    }

    @Test
    public void neverNoTransactionAndOuterException() {
        outerUserService.neverNoTransactionAndOuterException();
    }

    @Test
    public void neverNoTransactionAndInnerException() {
        outerUserService.neverNoTransactionAndInnerException();
    }

    @Test
    public void neverWithTransaction() {
        outerUserService.neverWithTransaction();
    }
}