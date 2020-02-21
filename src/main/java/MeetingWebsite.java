import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;


public class MeetingWebsite {
    private static final Logger logger = LogManager.getLogger();
    private static final int SLEEP = 1000;
    private JedisPool jedisPool = new JedisPool(new JedisPoolConfig(),
            "localhost");
    
    private void show() {
        try
        {
            Jedis jedis = jedisPool.getResource();
            jedis.rpush("CONTACTS", "Olya", "Kolya", "Vasya", "Mikl", "Nadya", "Fedya", "Nata", "Masha", "Katya", "Serg",
                                                  "Tanya", "Mitya", "Georg", "Vlad", "Anton","Viktor", "Tanya", "Maxim", "Katya", "Misha");
            String result;
            Long contactsSize = jedis.llen("CONTACTS") + 1;
            for (int i=0; i < 1; ){
                result = jedis.lpop("CONTACTS");

                jedis.rpush("CONTACTS", result);
//                System.out.println(" CONTACTS : " + result);
                logger.info(" CONTACTS : " + result);

                Thread.sleep(SLEEP);

                if ((int) Math.random()*10 < 1){
                    result = jedis.lindex("CONTACTS", (int) (Math.random()*contactsSize));
//                    System.out.println(" CONTACTS : " + result);
                    logger.info(" CONTACTS : " + result);

                    Thread.sleep(SLEEP);
                }
            }


//            jedisPool.returnResource(jedis);
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }finally{
            jedisPool.destroy();
        }
    }
    public static void main(String args[])
    {
        MeetingWebsite meetingWebsite = new MeetingWebsite();
        meetingWebsite.show();
    }
}
