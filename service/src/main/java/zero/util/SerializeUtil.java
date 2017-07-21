package zero.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 序列化工具
 */
public class SerializeUtil {
    private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            if (object != null) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                byte[] bytes = baos.toByteArray();
                return bytes;
            }
        } catch (Exception e) {
            logger.error("error message is {}", e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    logger.error("error message is {}", e);
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    logger.error("error message is {}", e);
                }
            }
        }
        return null;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            if (bytes != null) {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                return ois.readObject();
            }

        } catch (Exception e) {
            logger.error("error message is {}", e.getMessage());
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    logger.error("error message is {}", e.getMessage());
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    logger.error("error message is {}", e.getMessage());
                }
            }
        }
        return null;
    }

}
