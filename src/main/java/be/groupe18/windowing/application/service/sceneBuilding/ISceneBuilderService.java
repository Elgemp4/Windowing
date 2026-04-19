package be.groupe18.windowing.application.service.sceneBuilding;

import be.groupe18.windowing.application.service.IService;
import be.groupe18.windowing.domain.model.Segment;
import java.util.List;

/**
 * Defines the contract for the scene building use case.
 * <p>
 * Implementations of this interface are responsible for taking a raw collection 
 * of {@link Segment}s and transforming them into a structured, queryable environment 
 * (a Scene) before persisting it.
 */
public interface ISceneBuilderService extends IService<List<Segment>, Void> {}
