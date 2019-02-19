package com.ssm.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	
	//由分片切换为哨兵
	@Autowired(required=false)
	private JedisSentinelPool jedisSentinelPool;
	
	public String get(String key){
		Jedis jedis = jedisSentinelPool.getResource();
		String result = jedis.get(key);
		jedisSentinelPool.returnResource(jedis);
		return result;
	}
	
	public void set(String key,String value){
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.set(key, value);
		jedisSentinelPool.returnResource(jedis);
	}
	
	public void set(String key,String value,Integer seconds){
		Jedis jedis = jedisSentinelPool.getResource();
		//jedis.set(key, value);
		//jedis.expire(key, seconds); //设定超时时间
		jedis.setex(key, seconds, value);
		jedisSentinelPool.returnResource(jedis);
	}
	
	
	
	
	
	
	
	
	
	/*//初始化时不会为属性赋值,当程序调用时,才会自动赋值.
    @Autowired(required=false)
    private ShardedJedisPool shardedJedisPool;
    
    
    //实现分片set操作
    public void set(String key,String value){
    	ShardedJedis jedis = shardedJedisPool.getResource();
    	jedis.set(key, value);
    	shardedJedisPool.returnResource(jedis);
    }
    
    
    //实现分片get操作
    public String get(String key){
    	ShardedJedis jedis = shardedJedisPool.getResource();
    	String result = jedis.get(key);
    	shardedJedisPool.returnResource(jedis);
    	return result;
    }
    
    //自己扩展一个添加超时时间的方法
*/}
