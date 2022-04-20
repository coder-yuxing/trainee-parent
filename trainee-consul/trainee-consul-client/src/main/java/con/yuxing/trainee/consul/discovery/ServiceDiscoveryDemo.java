package con.yuxing.trainee.consul.discovery;

import cn.hutool.json.JSONUtil;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.ServiceHealth;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 服务发现demo
 *
 * @author yuxing
 * @since 2022/4/20
 */
@Slf4j
public class ServiceDiscoveryDemo {

    static Consul consul = Consul.builder().withHostAndPort(HostAndPort.fromString("localhost:8500")).withPing(false).build();

    public static void main(String[] args) {
        getServices();
    }

    public static void getServices() {
        final String serviceName = "consul-server-test";
        HealthClient healthClient = consul.healthClient();
        int size = healthClient.getAllServiceInstances(serviceName).getResponse().size();

        log.info("The total of service: {} discovery is {}.", serviceName, size);
        // 打印所有正常的服务
        List<ServiceHealth> responses = healthClient.getAllServiceInstances(serviceName).getResponse();
        for(ServiceHealth resp : responses ) {
            System.out.println(JSONUtil.toJsonStr(resp.getService()));
        }
    }
}
