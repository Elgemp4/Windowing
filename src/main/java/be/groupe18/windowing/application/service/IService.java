package be.groupe18.windowing.application.service;

/**
 * @param <I> the type of the input provided to the service.
 * @param <O> the type of the result produced by the service.
 * @version 1.0
 */
public interface IService<I, O> {
  O execute(I input);
}
