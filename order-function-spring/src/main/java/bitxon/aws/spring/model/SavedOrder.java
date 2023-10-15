package bitxon.aws.spring.model;

import java.util.UUID;

public record SavedOrder(
    UUID id,
    String name,
    String processedBy
) {}