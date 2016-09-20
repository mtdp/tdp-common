package com.github.mtdp.persistence;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mtdp.util.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis缓存
 * 
 * @ClassName: RedisManager
 * @Description: TODO
 * @author gqwang
 * @date 2015年8月6日 下午2:26:07
 * 
 */
public class RedisManager {

	private static final Logger logger = LoggerFactory
			.getLogger(RedisManager.class);

	private String host = "127.0.0.1";
	private int port = 6379;
	private String redisPwd = null;
	/** 过期时间,0=不过期 单位s **/
	private int expire = 1800;
	/** jedis 连接redis超时时间 单位s **/
	private int timeout = 0;

	private JedisPoolConfig config;

	private JedisPool jedisPool;

	public RedisManager() {
	}

	public RedisManager(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public RedisManager(String host, int port, int expire) {
		this(host, port);
		this.expire = expire;
	}

	public RedisManager(String host, int port, String redisPwd) {
		this.host = host;
		this.port = port;
		this.redisPwd = redisPwd;
	}

	public RedisManager(String host, int port, int expire, String redisPwd) {
		this.host = host;
		this.port = port;
		this.expire = expire;
		this.redisPwd = redisPwd;
	}

	/**
	 * 初始化Jedis
	 */
	public void init() {
		logger.info("connect redis host={},expire={}", this.host + ":"
				+ this.port, this.expire);
		if (jedisPool != null || config == null) {
			return;
		}
		if (StringUtil.isBlank(redisPwd)) {
			jedisPool = new JedisPool(config, host, port);
		} else {
			jedisPool = new JedisPool(config, host, port, timeout, redisPwd);
		}
	}

	/**
	 * 根据byte数组key获取数据
	 * 
	 * @param key
	 *            byte[]
	 * @return byte[]
	 */
	public byte[] get(byte[] key) {
		Jedis jedis = null;
		byte[] bytes = null;
		try {
			jedis = jedisPool.getResource();
			bytes = jedis.get(key);
		} catch (Exception e) {
			logger.error("获取缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return bytes;
	}

	/**
	 * 根据String key获取数据
	 * 
	 * @param key
	 *            byte[]
	 * @return String
	 */
	public String get(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			logger.error("获取缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return value;
	}

	/**
	 * 根据key ,value存储,value最大1G
	 * 
	 * @param key
	 *            byte[]
	 * @param value
	 *            byte[]
	 */
	public void set(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String status = jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 根据key ,value ,expire(单位s)过期时间存储,value最大1G
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void set(byte[] key, byte[] value, int expire) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String status = jedis.set(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 根据key ,value ,expire(单位s)过期时间存储,value最大1G
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void set(String key, String value, int expire) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 并发设置key
	 * 
	 * @param key
	 * @param value
	 */
	public void setnx(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.setnx(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 并发设置key
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void setnx(String key, String value, int expire) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.setnx(key, value);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 并发设置key
	 * 
	 * @param key
	 * @param value
	 */
	public void setnx(byte[] key, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.setnx(key, value);
			if (this.expire != 0) {
				jedis.expire(key, this.expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 并发设置key
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void setnx(byte[] key, byte[] value, int expire) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long status = jedis.setnx(key, value);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			logger.info("jedis set status = {}", status);
		} catch (Exception e) {
			logger.error("保存缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 */
	public void del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long l = jedis.del(key);
			logger.info("jedis del number = {}", l);
		} catch (Exception e) {
			logger.error("删除缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 
	 * @param key
	 */
	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long l = jedis.del(key);
			logger.info("jedis del number = {}", l);
		} catch (Exception e) {
			logger.error("删除缓存出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取所有的keys
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		Jedis jedis = null;
		Set<String> set = null;
		try {
			jedis = jedisPool.getResource();
			set = jedis.keys(pattern);
		} catch (Exception e) {
			logger.error("通过表达式获取keys出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return set;
	}

	/**
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(byte[] pattern) {
		Jedis jedis = null;
		Set<byte[]> set = null;
		try {
			jedis = jedisPool.getResource();
			set = jedis.keys(pattern);
		} catch (Exception e) {
			logger.error("通过表达式获取keys出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return set;
	}

	/**
	 * 删除当前选中的DB
	 */
	public void delCurrentAll() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String status = jedis.flushDB();
			logger.info("jedis delAll status = {}", status);
		} catch (Exception e) {
			logger.error("删除DB出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 删除所有的DB
	 */
	public void delAll() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String status = jedis.flushAll();
			logger.info("jedis delAll status = {}", status);
		} catch (Exception e) {
			logger.error("删除所有DB出错", e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public Long DBSize() {
		return jedisPool.getResource().dbSize();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRedisPwd() {
		return redisPwd;
	}

	public void setRedisPwd(String redisPwd) {
		this.redisPwd = redisPwd;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	public static void main(String[] args) {
		RedisManager rm = new RedisManager("192.168.10.150", 6379);

		rm.config = new JedisPoolConfig();
		rm.config.setMaxTotal(5);// 设置最大连接数
		rm.config.setMaxIdle(1); // 设置最大空闲数
		rm.config.setMaxWaitMillis(5000);// 设置超时时间
		rm.config.setTestOnBorrow(true);

		rm.init();

		Set<String> set = rm.keys("*");
		System.out.println(set);
		// rm.set("test", "session");
	}
}
