package be.groupe18.windowing.application.service.windowQuerying;

import java.util.List;

import be.groupe18.windowing.application.service.IService;
import be.groupe18.windowing.domain.model.Segment;

public interface IWindowQueryingService extends IService<WindowQueryRequest,List<Segment>> {}
