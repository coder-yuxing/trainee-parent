package con.yuxing.trainee.consul.register;

import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegCheck;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务注册demo
 *
 * @author yuxing
 * @since 2022/4/20
 */
@Slf4j
public class ServiceRegisterDemo {

    static Consul consul = Consul.builder()
            .withHostAndPort(HostAndPort.fromString("localhost:8500"))
            .withPing(true).build();

    public static void main(String[] args) throws Exception {
        register();

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void register() {
        AgentClient agent = consul.agentClient();
        // 健康检测
        ImmutableRegCheck check = ImmutableRegCheck.builder().http("http://localhost:9020/health").interval("5s").build();
        ImmutableRegistration.Builder builder = ImmutableRegistration.builder();
        builder.id("consul-server-test")
                .name("consul-server-test")
                .addTags("v1")
                .address("localhost")
                .port(8080)
                .addChecks(check);

        agent.register(builder.build());

        log.info("The service: {} register success.", "consul-server-test");
    }
}
