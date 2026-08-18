package com.jodexindustries.donatecase.common.redis;

import com.jodexindustries.donatecase.common.DonateCase;
import redis.clients.jedis.JedisPool;

public class RedisManagerImpl {
   private final DonateCase api;
   private final JedisPool redisPool;

   public RedisManagerImpl(DonateCase api) {
      this.api = api;
      this.redisPool = new JedisPool();
   }
}
