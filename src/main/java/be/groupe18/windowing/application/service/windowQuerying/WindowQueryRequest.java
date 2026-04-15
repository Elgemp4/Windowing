package be.groupe18.windowing.application.service.windowQuerying;

public record WindowQueryRequest(
  double minX,
  double maxX,
  double minY,
  double maxY
) {}
