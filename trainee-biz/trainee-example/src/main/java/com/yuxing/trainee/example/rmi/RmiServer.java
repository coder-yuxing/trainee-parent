package com.yuxing.trainee.example.rmi;

import com.yuxing.trainee.example.rmi.service.RmiTestService;
import com.yuxing.trainee.example.rmi.service.impl.RmiTestServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI服务启动类
 *
 * @author yuxing
 * @since 2022/1/24
 */
public class RmiServer {

    public static void main(String[] args) throws Exception {
        // 实例化RMI服务接口
        RmiTestService rmiTestService = new RmiTestServiceImpl();

        // 将此接口转换为远程服务接口
        RmiTestService skeleton = (RmiTestService) UnicastRemoteObject.exportObject(rmiTestService, 0);
        // 将RMI服务注册到1099端口
        Registry registry = LocateRegistry.createRegistry(1099);
        // 注册rmi服务，并设置服务名为：rmiTestService
        registry.bind("rmiTestService", skeleton);

    }
}
