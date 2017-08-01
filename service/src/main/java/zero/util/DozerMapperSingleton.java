package zero.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class DozerMapperSingleton {
    private DozerMapperSingleton() {

    }

    private static class SingletonFactory {
        private static Mapper instance = new DozerBeanMapper();;
    }

    public static Mapper getInstance() {
        return SingletonFactory.instance;
    }
}
