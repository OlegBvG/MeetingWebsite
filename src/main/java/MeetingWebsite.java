import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import java.util.ArrayList;
import java.util.Set;


public class MeetingWebsite {
    private static final Logger logger = LogManager.getLogger();
    private static final int SLEEP = 1000;
    private JedisPool jedisPool = new JedisPool(new JedisPoolConfig(),"localhost");

    private void show() {
        try
        {
            Jedis jedis = jedisPool.getResource();

            jedis.zadd("CONTACT",  1, "Olya");
            jedis.zadd("CONTACT",  2, "Kolya");
            jedis.zadd("CONTACT",  3, "Vasya");
            jedis.zadd("CONTACT",  4, "Mikl");
            jedis.zadd("CONTACT",  5, "Nadya");
            jedis.zadd("CONTACT",  6, "Fedya");
            jedis.zadd("CONTACT",  7, "Nata");
            jedis.zadd("CONTACT",  8, "Masha");
            jedis.zadd("CONTACT",  9, "Katya");
            jedis.zadd("CONTACT",  10, "Serg");
            jedis.zadd("CONTACT",  11, "Tanya");
            jedis.zadd("CONTACT",  12, "Mitya");
            jedis.zadd("CONTACT",  13, "Georg");
            jedis.zadd("CONTACT",  14, "Vlad");
            jedis.zadd("CONTACT",  15, "Anton");
            jedis.zadd("CONTACT",  16, "Viktor");
            jedis.zadd("CONTACT",  17, "Dasha");
            jedis.zadd("CONTACT",  18, "Maxim");
            jedis.zadd("CONTACT",  19, "Kat");
            jedis.zadd("CONTACT",  20, "Misha");

            Set<String> result;
            Long contactSize = jedis.zcard("CONTACT");
            result = jedis.zrange("CONTACT", 0, contactSize);
            logger.info(" CONTACTS : " + contactSize);
            logger.info(" CONTACTS : " + result);

            ArrayList<String> listContacts = new ArrayList<String>();
            listContacts.addAll(result);

            for (int i=0; i < 1; ){

                for (Object r: result) {
                    logger.info(jedis.zscore("CONTACT", (String) r).intValue() + " CONTACT: "  + " " + r);
                    Thread.sleep(SLEEP);

                    if (Math.random()*10 < 1){
                        int res =  (int) (Math.random()*(contactSize + 1));
                        logger.info(jedis.zscore("CONTACT", listContacts.get(res)).intValue() + " EXTRA CONTACT: "  + " " + listContacts.get(res));
                        Thread.sleep(SLEEP);
                    }
                }
            }
        }

        catch (Exception e)
        {
            System.err.println(e.toString());
        } finally{
            jedisPool.destroy();
//            jedisPool.returnResource(jedis);
        }
    }
    public static void main(String args[])
    {
        MeetingWebsite meetingWebsite = new MeetingWebsite();
        meetingWebsite.show();
    }
}
