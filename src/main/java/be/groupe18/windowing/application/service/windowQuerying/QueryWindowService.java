package be.groupe18.windowing.application.service.windowQuerying;

import java.util.List;

import be.groupe18.windowing.application.strategy.query.QueryStrategy;
import be.groupe18.windowing.domain.model.QueryWindow;
import be.groupe18.windowing.domain.model.Scene;
import be.groupe18.windowing.domain.model.Segment;
import be.groupe18.windowing.domain.utils.Tuple;
import be.groupe18.windowing.infrastructure.repository.pst.IPSTRepository;

public class QueryWindowService implements IWindowQueryingService{
    private final QueryStrategy queryStrategy;
    private final IPSTRepository pstRepository;

    public QueryWindowService(QueryStrategy queryStrategy, IPSTRepository pstRepository) {
        this.queryStrategy = queryStrategy;
        this.pstRepository = pstRepository;
    }

    @Override
    public List<Segment> execute(WindowQueryRequest windowQueryRequest) {
        Scene scene = pstRepository.getScene();
        if(scene == null) {
            return null;
        }
        Tuple<QueryWindow,QueryWindow> queryWindows = QueryWindow.buildQueryWindows(windowQueryRequest.minX(),
            windowQueryRequest.maxX(), windowQueryRequest.minY(), windowQueryRequest.maxY());
        List<Segment> segments = queryStrategy.query(scene.getVerticalPst(), queryWindows.getV1());
        segments.addAll(queryStrategy.query(scene.getHorizontalPst(), queryWindows.getV2()));
        return segments;
    }
}
