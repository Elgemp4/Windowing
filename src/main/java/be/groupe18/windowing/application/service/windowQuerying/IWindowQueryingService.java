package be.groupe18.windowing.application.service.windowQuerying;

import be.groupe18.windowing.application.service.IService;
import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

public interface IWindowQueryingService
  extends IService<WindowQueryRequest, List<Segment>> {}
