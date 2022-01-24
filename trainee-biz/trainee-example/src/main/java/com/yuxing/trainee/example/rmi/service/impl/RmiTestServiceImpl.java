package com.yuxing.trainee.example.rmi.service.impl;

import com.yuxing.trainee.example.rmi.service.RmiTestService;

import java.rmi.RemoteException;

/**
 * RMI接口实现类
 *
 * @author yuxing
 * @since 2022/1/24
 */
public class RmiTestServiceImpl implements RmiTestService {

    @Override
    public String getMessage(String key) throws RemoteException {
        return "hello RMI, " + key;
    }
}
