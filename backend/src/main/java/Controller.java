import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import spark.Request;
import spark.Response;
import spark.Route;


import static spark.Spark.get;
import static spark.Spark.post;

public class Controller {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        get("/getAllGoods", getAllGoodsRoute());
        get("/findGoodByName", getFindGoodByNameRoute());
        post("/addGood", getAddGoodRoute());
    }

    private static Route getAllGoodsRoute() {
        Route getRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String json = mapper.writeValueAsString(ShopDao.findAll());
                return json;
            }
        };
        return getRoute;
    }

    private static Route getFindGoodByNameRoute() {
        Route getRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String goodName = request.queryParams("goodName");
                if (StringUtils.isBlank(goodName)) {
                    return "Please specify correct good name";
                }

                Good good = ShopDao.findByName(goodName);
                if (good == null) {
                    return "Good with name " + goodName + " not found";
                }

                String json = mapper.writeValueAsString(good);
                return json;
            }
        };
        return getRoute;
    }

    private static Route getAddGoodRoute() {
        Route postRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String goodName = request.queryParams("name");
                String count = request.queryParams("count");
                String price = request.queryParams("price");
                String isValid = validateParametersForGood(goodName, count, price);
                if (StringUtils.isNotBlank(isValid)) {
                    return isValid;
                }
                Integer priceInt = convertStringToInt(price);
                if (priceInt == null) {
                    return "Please specify price as a number";
                }
                Integer contInt = convertStringToInt(count);
                if (contInt == null) {
                    return "Please specify count as a number";
                }
                Good good = new Good(goodName, contInt, priceInt);
                boolean result = ShopService.addGood(good);
                if (result) {
                    return "Good was successfully added";
                } else {
                    return "Something went wrong during adding process";
                }
            }
        };
        return postRoute;
    }

    public static String validateParametersForGood(String goodName, String count, String price) {
        if (StringUtils.isBlank(goodName)) {
            return "Please specify correct good name";
        }
        if (StringUtils.isBlank(count)) {
            return "Please specify correct good count";
        }
        if (StringUtils.isBlank(price)) {
            return "Please specify correct good price";
        }
        return StringUtils.EMPTY;
    }

    public static Integer convertStringToInt(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }


}
