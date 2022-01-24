package com.yuxing.trainee.example.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI测试接口
 * Java RMI规范规定要定义的RMI接口必须继承自{@link Remote}，并且在每个方法声明时抛出 {@link RemoteException}
 *
 * @author yuxing
 * @since 2022/1/24
 */
public interface RmiTestService extends Remote {

    String getMessage(String key) throws RemoteException;
}
