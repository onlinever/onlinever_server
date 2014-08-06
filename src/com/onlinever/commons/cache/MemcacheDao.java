package com.onlinever.commons.cache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.transcoders.Transcoder;

@Repository("memcacheDao")
public class MemcacheDao {
	private Logger log = Logger.getLogger(MemcacheDao.class);
	@Autowired
	protected MemcachedClient memcachedClient;
	public final static String KEY_PREX = "ORDER_CENTER_";
    
    public boolean set(String key, int exp, Object o){
    	try {
	    	OperationFuture<Boolean> f = memcachedClient.set(genKey(key), exp < 3600*24*30 ? exp : (int)(System.currentTimeMillis()/1000 + exp), o);
	    	return f.get().booleanValue();
    	} catch (final Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
    }
    
    public Object get(String key){
    	try{
    		return memcachedClient.get(genKey(key));
    	}catch(Exception e){
    		log.error(e.getMessage(), e);
    		return null;
    	}
    }
    
    public <T> T get(String key, Transcoder<T> tc){
    	return memcachedClient.get(genKey(key), tc);
    }
    
    public boolean delete(String key){
    	try {
    		OperationFuture<Boolean> f = memcachedClient.delete(genKey(key));
    		return f.get().booleanValue();
    	} catch (final Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
    }
    
    private String genKey(String key){
    	StringBuilder strBuilder = new StringBuilder(KEY_PREX);
    	return strBuilder.append(key).toString();
    }
}
