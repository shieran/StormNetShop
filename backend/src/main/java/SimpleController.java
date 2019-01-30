import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class SimpleController {

    public static void main(String[] args) {
        Route getRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "This is GET request";
            }
        };

        Route postRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "This is POST request";
            }
        };

        get("/simpleGet", getRoute);
        post("/simplePost", postRoute);
    }


}
