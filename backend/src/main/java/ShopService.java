import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);

    public static boolean addGood(Good good) {
        Good goodInFile = ShopDao.findByName(good.name);
        try {
            if (goodInFile == null) {
                return ShopDao.save(good);
            } else {
                ShopDao.deleteByName(good.name);
                Good newGood = new Good(goodInFile.name, goodInFile.count + good.count, good.price);
                return ShopDao.save(newGood);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;

    }

}



