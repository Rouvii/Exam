package dat.controller;

import io.javalin.http.Context;

public interface ITripController {

    public void addGuideToTrip(Context ctx);

    public void getTripsByGuide(Context ctx);
}
