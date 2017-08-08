package zero.util;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerUtil {

    public static <T> List<T> mapAsList(Iterable<?> sources, Class<T> destinationClass) {
        Mapper mapper = DozerMapperSingleton.getInstance();
        //can add validation methods to check if the object is iterable
        ArrayList<T> targets = new ArrayList<T>();
        for (Object source : sources) {
            targets.add(mapper.map(source, destinationClass));
        }
        return targets;
    }

}
