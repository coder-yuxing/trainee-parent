package com.yuxing.trainee.example.rmi;

import com.yuxing.trainee.example.rmi.service.RmiTestService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI服务消费者
 *
 * @author yuxing
 * @since 2022/1/24
 */
public class RmiClient {

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // 客户端只有接口而不存在实现类，其方法调用通过网络通信从服务端获取结果
        RmiTestService rmiTestService = (RmiTestService) registry.lookup("rmiTestService");
        String response = rmiTestService.getMessage("YuXing");
        System.err.println(response);
    }
}
