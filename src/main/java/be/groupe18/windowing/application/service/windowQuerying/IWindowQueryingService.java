package be.groupe18.windowing.application.service.windowQuerying;

import be.groupe18.windowing.application.service.IService;
import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

/**
 * Defines the contract for the window querying use case.
 * <p>
 * Implementations of this interface should handle the logic required to extract 
 * a subset of {@link Segment}s based on the requested spatial boundaries.
 */
public interface IWindowQueryingService
  extends IService<WindowQueryRequest, List<Segment>> {}
