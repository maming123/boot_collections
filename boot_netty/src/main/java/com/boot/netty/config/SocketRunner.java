package com.boot.netty.config;

import com.boot.netty.helper.SocketServerHelper;
import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @apiNote CommandLineRunner 监听容器启动后加载socket server
 * @date 2020/3/13 18:27
 **/
@Component
public class SocketRunner implements InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(SocketRunner.class);

	@Resource(name = SocketServerConfig.SERVER_NAME)
	private SocketIOServer server;
	@Resource
	private SocketServerHelper socketServerHelper;

	@Override
	public void destroy() throws Exception {
		server.getAllClients().forEach(ClientOperations::disconnect);
		logger.info("socket io 服务器销毁,将关闭所有客户端连接！");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		socketServerHelper.initEvent();
		server.start();
		logger.info("socket io 服务器启动！ip={}", server.getConfiguration().getHostname());
	}
}
