package be.groupe18.windowing.application.service.windowQuerying;

/**
 * A Data Transfer Object (DTO) representing the boundaries of a spatial window query.
 * * @param minX The minimum X-axis coordinate of the search window.
 * @param maxX The maximum X-axis coordinate of the search window.
 * @param minY The minimum Y-axis coordinate of the search window.
 * @param maxY The maximum Y-axis coordinate of the search window.
 */
public record WindowQueryRequest(
  double minX,
  double maxX,
  double minY,
  double maxY
) {}
