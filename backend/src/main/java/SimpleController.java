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

        Route helloGet = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String userName = request.queryParams("name");
                return "GET hello " + userName;
            }
        };

        Route postRoute = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "This is POST request";
            }
        };

        Route helloPost = new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String userName = request.queryParams("name");
                return "POST hello " + userName;
            }
        };

        get("/simpleGet", getRoute);
        get("/helloGet", helloGet);
        post("/simplePost", postRoute);
        post("/helloPost", helloPost);
    }


}
