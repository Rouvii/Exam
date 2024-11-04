package dat.dao;

import dat.dto.TripDto;

import java.util.Set;

public interface ITripGuideDAO {

    public void addGuideToTrip(int tripId, int guideId);

    public Set<TripDto> getTripsByGuide(int guideId);

}
