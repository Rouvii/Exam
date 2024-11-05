package dat.dao;

import dat.dto.TripDTO;

import java.util.Set;

public interface ITripGuideDAO {

    public void addGuideToTrip(int tripId, int guideId);

    public Set<TripDTO> getTripsByGuide(int guideId);

}
