package dat.routes;


import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {


private static final TripRoutes tripRoutes = new TripRoutes();

  public EndpointGroup getRoutes() {
    return () -> {

        path("/trips", tripRoutes.getTripRoutes());

    };
  }



}
